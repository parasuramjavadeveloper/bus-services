package se.sbab.busservices.services;

import org.springframework.web.reactive.function.client.ExchangeStrategies;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.exception.InvalidBusTypeException;
import se.sbab.busservices.exception.TrafikLabException;
import se.sbab.busservices.utils.BusModelType;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import se.sbab.busservices.response.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusServiceImpl implements BusLineService {
    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private WebClient webClient;

    @Override
    public TrafikLabResponse getBusService(String modelType) {
        return getBusServiceDetails(BusModelType.findByAbbr(modelType),
                configProperties.getDefaultTransportModeCode());
    }

    @Override
    public List<BusResponse> getBusService() {
        return makeRestCalls();
    }

    public TrafikLabResponse getBusServiceDetails(BusModelType busModelType,String modelType){
        log.info("Executing webclient.......");
        return webClient.get()
                .uri(builder -> builder.path("/api2/LineData.json")
                        .queryParam("key", configProperties.getKey())
                        .queryParam("DefaultTransportModeCode", modelType)
                        .queryParam("model", busModelType.getModel()).build())
                .retrieve()
                .bodyToMono(TrafikLabResponse.class).block();
    }

    private List<BusResponse> makeRestCalls(){
        try {
            TrafikLabResponse lineResponse = getBusServiceDetails(BusModelType.LINE,configProperties.getDefaultTransportModeCode());
            log.info("Line=====request");
            TrafikLabResponse journeyPatternResponse = getBusServiceDetails(BusModelType.JOURNEY_PATTERN_POINT_ONLINE,configProperties.getDefaultTransportModeCode());
            log.info("Journey=====request");
            TrafikLabResponse stopPointResponse = getBusServiceDetails(BusModelType.STOP_POINT,configProperties.getDefaultTransportModeCode());
            log.info("Stop=====request");
            return parseBusLine(lineResponse, journeyPatternResponse, stopPointResponse);
        }catch (Exception e){
            throw new InvalidBusTypeException(e.getMessage());
        }
    }

    private List<BusResponse> parseBusLine(TrafikLabResponse trafikLabResponseLine, TrafikLabResponse journeyPatternResponse, TrafikLabResponse stopPointResponse){
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

    private List<BusResponse> parseBusJourneyPoint(List<String> busLines, List<ResultJourney> resultJourneys,
                                                   List<ResultStop> resultStops){
        if(!CollectionUtils.isEmpty(busLines) && !ObjectUtils.isEmpty(resultJourneys) && !ObjectUtils.isEmpty(resultStops)) {
            Map<String, List<String>> map = new HashMap<String, List<String>>();
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

    private List<BusResponse> parseAndMapBusStop(Map<String,List<String>> map, List<ResultStop> results){
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

    private List<BusResponse> formatAndPrintOutput(Map<String, Collection<String>> map){

        log.info("========================================================================================\n");
        log.info(String.format("Top 10 bus lines have the most bus stops on their route.BUSLINE : %s"
                ,map.keySet().toString()));
        log.info("\n========================================================================================");
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        String json = gsonBuilder.toJson(map);
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        String prettyJsonString = gsonBuilder.toJson(je);
        log.info(prettyJsonString);
        List<BusResponse> responses = new ArrayList<>();
        map.keySet().forEach(key->{
            responses.add(new BusResponse(key,map.get(key)));
        });
        return responses;
    }
}
