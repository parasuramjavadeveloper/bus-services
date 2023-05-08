package se.sbab.busservices.services;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.exception.InValidBusTypeException;
import se.sbab.busservices.model.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * BusServiceImpl calls TrafikLabs apis and gives the Top 10 BusLines and its BusStops
 *
 * @author Parasuram
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BusLineServiceImpl implements BusLineService {

    private final ConfigProperties configProperties;
    private final BusService busService;

    /**
     * Gives Top 10 BusLines and Its BusStopNames from API
     */
    @Override
    public List<BusLinesResponse> getTopTenBusLinesAndBusStopNames() throws IOException {
        Map<String, Collection<String>> map = mapBusLinesWithBusStops().asMap();
        List<BusLinesResponse> response = new ArrayList<>();
        map.keySet().forEach(key -> response.add(new BusLinesResponse(key, map.get(key))));
        return response;
    }

    /**
     * Mapping with BusLines to JourneyPoint
     * and Returns Key as BusLine and Value as List of Journey Point
     */
    private Map<String, List<String>> mapBusLineAndJourneyPoint()
        throws IOException {
        TrafikLabResponse journeyPatternResponse = busService.getBusServiceDetails(BusModelType.JOURNEY_PATTERN_POINT_ONLINE);
        if(journeyPatternResponse == null ){
            throw new InValidBusTypeException("Invalid bus type");
        }
        log.info("Journey{}", journeyPatternResponse.getResponseData());

        return busService.getBusLinesFromAPI().stream()
            .flatMap(line -> journeyPatternResponse.getResponseData().getResult().stream()
                .filter(journeyPoint -> line.equals(((ResultJourney) journeyPoint).getLineNumber()))
                .map(journeyPoint -> Map.entry(line, ((ResultJourney) journeyPoint).getJourneyPatternPointNumber())))
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }

    /**
     * Mapping BusLines with BusStops
     * and Returns Key as BusLine and Value as List of BusStops
     */
    private Multimap<String, String> mapBusLinesWithBusStops()
        throws IOException {
        Map<String, List<String>> map = mapBusLineAndJourneyPoint();
        TrafikLabResponse stopPointResponse = busService.getBusServiceDetails(BusModelType.STOP_POINT);
        List<ResultStop> resultStops = stopPointResponse.getResponseData().getResult()
                .stream()
                .map(e -> (ResultStop) e)
                .collect(Collectors.toList());
        Multimap<String, String> busLineAndStops = LinkedListMultimap.create();
        if (!CollectionUtils.isEmpty(map.values()) && !ObjectUtils.isEmpty(resultStops)) {
            map.entrySet().stream().sorted((left, right) -> Integer.compare(right.getValue().size(),
                            left.getValue().size())).limit(10)
                    .forEach(stops -> stops.getValue().forEach(stop ->
                            resultStops.stream().filter(result -> stop.equals(result.getStopPointNumber()))
                                    .map(ResultStop::getStopPointName)
                                    .forEach(res -> busLineAndStops.put(stops.getKey(), res))));
        }
        return busLineAndStops;
    }
}
