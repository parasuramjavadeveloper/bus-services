package se.sbab.busservices.services;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.exception.InValidBusTypeException;
import se.sbab.busservices.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
/**
 * BusServiceImpl calls TrafikLabs apis and gives the Top 10 BusLines and its BusStops
 * @author Parasuram
 */
public class BusLineServiceImpl implements BusLineService {
    private static final String KEY = "key";
    private static final String DEFAULT_TRANSPORT_MODE_CODE = "DefaultTransportModeCode";
    private static final String MODEL = "model";

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private WebClient webClient;

    /**
     * Self Autowired BusServiceImpl for cache method calling within the method for same class.
     */
    @Autowired
    private BusLineServiceImpl busLineServiceImpl;

    @Override
    public TrafikLabResponse getBusService(String modelType) {
        return this.getBusServiceDetails(BusModelType.findByAbbr(modelType),
                configProperties.getDefaultTransportModeCode());
    }

    /**
     * Gives Top 10 BusLines and Its BusStopNames from API
     */
    @Override
    public List<BusLinesResponse> getTopTenBusLinesAndBusStopNames() {
        List<String> busSerLine = getBusSerLine();
        return getTopTenBusLinesAndBusStopNames(mapBusLinesWithBusStops(mapBusLineAndJourneyPoint(busSerLine)).asMap());
    }

    private List<BusLinesResponse> getTopTenBusLinesAndBusStopNames(Map<String, Collection<String>> map) {
        List<BusLinesResponse> response = new ArrayList<>();
        map.keySet().forEach(key -> {
            response.add(new BusLinesResponse(key, map.get(key)));
        });
        return response;
    }

    /**
     * It calls TrafikLab External API's Of BusLine,Journey and StopPoint and gets the response
     */
    @Cacheable(value = "trafikLabResponse", key = "#busModelType.model")
    public TrafikLabResponse getBusServiceDetails(BusModelType busModelType, String modelType) {
        log.info("Executing WebClient.......");
        return webClient.get()
                .uri(builder -> builder.path("/api2/LineData.json")
                        .queryParam(KEY, configProperties.getKey())
                        .queryParam(DEFAULT_TRANSPORT_MODE_CODE, modelType)
                        .queryParam(MODEL, busModelType.getModel()).build())
                .retrieve()
                .bodyToMono(TrafikLabResponse.class).block();
    }

    private List<String> getBusSerLine() {
        TrafikLabResponse lineResponse = busLineServiceImpl.getBusServiceDetails(BusModelType.LINE, configProperties.getDefaultTransportModeCode());
        log.info("Successfully Received Response from BusLineAPI");
        if (Objects.isNull(lineResponse)) {
            throw new InValidBusTypeException("Invalid Bus type Exception ");
        }
        return lineResponse.getResponseData().getResult()
                .stream()
                .map(e -> (ResultLine) e)
                .collect(Collectors.toList()).stream().map(ResultLine::getLineNumber).collect(Collectors.toList());
    }


    /**
     *  Mapping with BusLines to JourneyPoint
     *  and Returns Key as BusLine and Value as List of BusStops
     */
    private Map<String, List<String>> mapBusLineAndJourneyPoint(List<String> busLines) {
        TrafikLabResponse journeyPatternResponse = busLineServiceImpl.getBusServiceDetails(BusModelType.JOURNEY_PATTERN_POINT_ONLINE,
                configProperties.getDefaultTransportModeCode());
        List<ResultJourney> resultJourneys = journeyPatternResponse.getResponseData().getResult()
                .stream()
                .map(e -> (ResultJourney) e)
                .collect(Collectors.toList());
        Map<String, List<String>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(busLines) && !ObjectUtils.isEmpty(resultJourneys)) {
            for (String line : busLines) {
                for (ResultJourney journeyPoint : resultJourneys) {
                    if (line.equals(journeyPoint.getLineNumber())) {
                        if (map.containsKey(line)) {
                            map.get(line).add(journeyPoint.getJourneyPatternPointNumber());
                        } else {
                            List<String> journeyPoints = new ArrayList<String>();
                            journeyPoints.add(journeyPoint.getJourneyPatternPointNumber());
                            map.put(line, journeyPoints);
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     *  Mapping BusLines with BusStops
     *  and Returns Key as BusLine and Value as List of BusStops
     */
    private Multimap<String, String> mapBusLinesWithBusStops(Map<String, List<String>> map) {
        TrafikLabResponse stopPointResponse = busLineServiceImpl.getBusServiceDetails(BusModelType.STOP_POINT,
                configProperties.getDefaultTransportModeCode());
        List<ResultStop> resultStops = stopPointResponse.getResponseData().getResult()
                .stream()
                .map(e -> (ResultStop) e)
                .collect(Collectors.toList());
        Multimap<String, String> dataMultiMap = LinkedListMultimap.create();
        if (!CollectionUtils.isEmpty(map.values()) && !ObjectUtils.isEmpty(resultStops)) {
            map.entrySet().stream().sorted((left, right) -> Integer.compare(right.getValue().size(),
                            left.getValue().size())).limit(10)
                    .forEach(stops -> stops.getValue().forEach(stop ->
                            resultStops.stream().filter(result -> stop.equals(result.getStopPointNumber()))
                                    .map(ResultStop::getStopPointName)
                                    .forEach(res -> {
                                        dataMultiMap.put(stops.getKey(), res);
                                    })));
        }
        return dataMultiMap;
    }
}
