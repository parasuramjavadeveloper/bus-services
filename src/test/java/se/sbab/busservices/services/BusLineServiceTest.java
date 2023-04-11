package se.sbab.busservices.services;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.response.*;
import se.sbab.busservices.utils.BusModelType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class BusLineServiceTest {

    @Spy
    private BusLineServiceImpl busLineServiceImpl;

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpecMock;

    @Mock
    private WebClient.RequestBodySpec requestBodySpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @Mock
    private Mono<TrafikLabResponse> trafikLabResponseMock;

    @BeforeEach
    public void beforeEach() {
        ConfigProperties configProperties = new ConfigProperties();
        configProperties.setBaseUrl("https://api.sl.se/api2/LineData.json");
        configProperties.setKey("dc49f2dab16643c09b01d66953fd0871");
        configProperties.setDefaultTransportModeCode("BUS");
        ReflectionTestUtils.setField(busLineServiceImpl, "configProperties", configProperties);
    }

    @Test
    @Ignore
    public void testTopTenBusLinesAndBusStopNames() {
        ReflectionTestUtils.setField(busLineServiceImpl, "webClient", webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono(
                ArgumentMatchers.<Class<TrafikLabResponse>>notNull())).thenReturn(Mono.just(getTrafikLabResponseLine()));
        // Mono<TrafikLabResponse> response = webClientMock.get().retrieve();
        //Assertions.assertEquals(1001, response.block().getId());
        //Assertions.assertEquals("Some title", response.block().getTitle());
        when(busLineServiceImpl.getBusServiceDetails(any(BusModelType.class), anyString())).thenReturn(getTrafikLabResponseLine());
        when(busLineServiceImpl.getBusServiceDetails(any(BusModelType.class), anyString())).thenReturn(getTrafikLabResponseJourney());
        when(busLineServiceImpl.getBusServiceDetails(any(BusModelType.class), anyString())).thenReturn(getTrafikLabResponseStopPoint());
        List<BusResponse> busServices = busLineServiceImpl.getTopTenBusLinesAndBusStopNames();
        Assert.assertNotNull("Getting response", busServices);
    }

    private List<BusResponse> getBusResponses() {
        BusResponse busResponse = new BusResponse();
        busResponse.setBusLineName("636");
        Collection<String> busStopNames = List.of("StationsGatan", "SundbybergStation", "SolnaBusinessPark", "SolnaCentrum",
                "Hudusta", "Hallonberg", "Risny", "Odenplan", "StockholmSodra", "SolnaStation");
        busResponse.setBusStopNames(busStopNames);
        List<BusResponse> busResponses = new ArrayList<>();
        busResponses.add(busResponse);
        return busResponses;
    }

    private TrafikLabResponse getTrafikLabResponseLine() {
        TrafikLabResponse response = new TrafikLabResponse();
        response.setMessage(null);
        response.setStatusCode(0);
        response.setExecutionTime(403);
        ResponseData responseData = new ResponseData();
        responseData.setType("Line");
        responseData.setVersion("2023-04-11 00:11");
        ResultLine result = new ResultLine();
        result.setLineNumber("1");
        result.setLineDesignation("1");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("blåbuss");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        responseData.setResult(List.of(result));
        response.setResponseData(responseData);
        return response;
    }

    private TrafikLabResponse getTrafikLabResponseStopPoint() {
        TrafikLabResponse response = new TrafikLabResponse();
        response.setMessage(null);
        response.setStatusCode(0);
        response.setExecutionTime(0);
        ResponseData responseData = new ResponseData();
        responseData.setType("StopPoint");
        responseData.setVersion("2023-04-11 00:11");
        ResultStop result = new ResultStop();
        result.setStopPointName("Stadshagsplan");
        result.setStopPointNumber("10001");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        responseData.setResult(List.of(result));
        response.setResponseData(responseData);
        return response;
    }


    private TrafikLabResponse getTrafikLabResponseJourney() {
        TrafikLabResponse response = new TrafikLabResponse();
        response.setMessage(null);
        response.setStatusCode(0);
        response.setExecutionTime(496);
        ResponseData responseData = new ResponseData();
        responseData.setType("JourneyPatternPointOnLine");
        responseData.setVersion("2023-04-11 00:11");
        ResultJourney result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10008");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        responseData.setResult(List.of(result));
        response.setResponseData(responseData);
        return response;
    }
}
