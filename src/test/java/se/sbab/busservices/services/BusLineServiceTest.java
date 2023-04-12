package se.sbab.busservices.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.model.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ImportAutoConfiguration
@ComponentScan("se.*")
public class BusLineServiceTest {

    @Autowired
    private BusLineService busLineService;

    @MockBean
    private WebClient webClientMock;

    @MockBean
    private ConfigProperties configProperties;

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
    }

    @Test
    public void testTopTenBusLinesAndBusStopNames() {
        whenCommonStubs();
        List<BusLinesResponse> busServices = busLineService.getTopTenBusLinesAndBusStopNames();
        Assert.assertNotNull("Getting response", busServices);
    }

    @Test
    public void testBusLineServiceByModelTypeWithDefaultTransportModeCodeBUS() {
        whenCommonStubs();
        TrafikLabResponse busServiceResponse = busLineService.getBusService("ModelType");
        Assert.assertNotNull("Getting response", busServiceResponse);
    }

    private void whenCommonStubs() {
        final var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);
        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) Mockito.any()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono((Class<Object>) Mockito.any()))
                .thenReturn(Mono.just(getTrafikLabResponseLine())).thenReturn(Mono.just(getTrafikLabResponseJourney())).thenReturn(Mono.just(getTrafikLabResponseStopPoint()));
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
        responseData.setResult(Arrays.asList(result));
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
        responseData.setResult(Arrays.asList(result));
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
        responseData.setResult(Arrays.asList(result));
        response.setResponseData(responseData);
        return response;
    }
}