package se.sbab.busservices.services;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.exception.InValidBusTypeException;
import se.sbab.busservices.exception.TrafikLabException;
import se.sbab.busservices.model.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
/**
 * BusServiceImpl calls TrafikLabs apis and gives the Top 10 BusLines and its BusNames
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
        return callTrafikLabAPIs();
    }

    /**
     * It calls TrafikLab External API's Of BusLine,Journey and StopPoint and gets the response
     */
    @Cacheable(value = "trafikLabResponse", key = "#busModelType.model")
    public TrafikLabResponse getBusServiceDetails(BusModelType busModelType,String modelType){
        log.info("Executing webclient.......");
        return webClient.get()
                .uri(builder -> builder.path("/api2/LineData.json")
                        .queryParam(KEY, configProperties.getKey())
                        .queryParam(DEFAULT_TRANSPORT_MODE_CODE, modelType)
                        .queryParam(MODEL, busModelType.getModel()).build())
                .retrieve()
                .bodyToMono(TrafikLabResponse.class).block();
    }

    public List<BusLinesResponse> callTrafikLabAPIs(){
        try {
            TrafikLabResponse lineResponse = this.getBusServiceDetails(BusModelType.LINE,configProperties.getDefaultTransportModeCode());
            log.info("Successfully Received Response from BusLineAPI");
            TrafikLabResponse journeyPatternResponse = this.getBusServiceDetails(BusModelType.JOURNEY_PATTERN_POINT_ONLINE,configProperties.getDefaultTransportModeCode());
            log.info("Journey=====request");
            TrafikLabResponse stopPointResponse = this.getBusServiceDetails(BusModelType.STOP_POINT,configProperties.getDefaultTransportModeCode());
            log.info("Stop=====request");
            return parseBusLine(lineResponse, journeyPatternResponse, stopPointResponse);
        }catch (Exception e){
            throw new InValidBusTypeException(e.getMessage());
        }
    }

    private List<BusLinesResponse> parseBusLine(TrafikLabResponse trafikLabResponseLine, TrafikLabResponse journeyPatternResponse, TrafikLabResponse stopPointResponse){
        List<String> busLines = trafikLabResponseLine.getResponseData().getResult()
                .stream()
                .map(e -> (ResultLine) e)
                .collect(Collectors.toList()).stream().map(ResultLine::getLineNumber).collect(Collectors.toList());

        List<ResultJourney> resultJourneyList = journeyPatternResponse.getResponseData().getResult()
                .stream()
                .map(e -> (ResultJourney) e)
                .collect(Collectors.toList());

        List<ResultStop> resultStops = stopPointResponse.getResponseData().getResult()
                .stream()
                .map(e -> (ResultStop) e)
                .collect(Collectors.toList());

        return parseBusJourneyPoint(busLines, resultJourneyList, resultStops);
    }

    private List<BusLinesResponse> parseBusJourneyPoint(List<String> busLines, List<ResultJourney> resultJourneys,
                                                        List<ResultStop> resultStops){
        if(!CollectionUtils.isEmpty(busLines) && !ObjectUtils.isEmpty(resultJourneys) && !ObjectUtils.isEmpty(resultStops)) {
            Map<String, List<String>> map = new HashMap<>();
            for (String line : busLines) {
                for (ResultJourney journeyPoint : resultJourneys) {
                    if (line.equals(journeyPoint.getLineNumber())) {
                        if (map.containsKey(line)) {
                            map.get(line).add(journeyPoint.getJourneyPatternPointNumber());
                        } else {
                            List<String> journeyPoints= new ArrayList<String>();
                            journeyPoints.add(journeyPoint.getJourneyPatternPointNumber());
                            map.put(line, journeyPoints);
                        }
                    }
                }
            }
            return parseAndMapBusStop(map, resultStops);
        } else {
            log.error("ParseBusLine result is either empty or null.");
            throw new TrafikLabException("ParseBusLine result is either empty or null.");
        }
    }

    private List<BusLinesResponse> parseAndMapBusStop(Map<String,List<String>> map, List<ResultStop> results){
        Multimap<String, String> dataMultiMap = LinkedListMultimap.create();
        if(!CollectionUtils.isEmpty(map.values()) && !ObjectUtils.isEmpty(results) ) {
            map.entrySet().stream().sorted((left, right) ->Integer.compare(right.getValue().size(),
                            left.getValue().size())).limit(10)
                    .forEach(stops -> stops.getValue().forEach(stop ->
                            results.stream().filter(result ->stop.equals(result.getStopPointNumber()))
                                    .map(ResultStop::getStopPointName)
                                    .forEach(res -> {
                                        dataMultiMap.put(stops.getKey(),res);
                                    })));
        }else {
            log.error("parseBusJourneyPoint result is either empty or null.");
            throw new TrafikLabException("parseBusJourneyPoint result is either empty or null.");
        }
        return formatAndPrintOutput(dataMultiMap.asMap());

    }

    private List<BusLinesResponse> formatAndPrintOutput(Map<String, Collection<String>> map){

        log.info("========================================================================================\n");
        log.info(String.format("Top 10 bus lines have the most bus stops on their route.BUSLINE : %s"
                ,map.keySet()));
        log.info("\n========================================================================================");
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        String json = gsonBuilder.toJson(map);
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        String prettyJsonString = gsonBuilder.toJson(je);
        log.info(prettyJsonString);
        List<BusLinesResponse> responses = new ArrayList<>();
        map.keySet().forEach(key->{
            responses.add(new BusLinesResponse(key,map.get(key)));
        });
        return responses;
    }
}
