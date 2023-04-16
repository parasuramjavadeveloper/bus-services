package se.sbab.busservices.services;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.exception.InValidBusTypeException;
import se.sbab.busservices.model.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.when;


@PropertySources(value = {@PropertySource("classpath:application.properties")})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ImportAutoConfiguration
@ComponentScan("se.*")
@FixMethodOrder(MethodSorters.DEFAULT)
public class BusLineServiceTest {

    @Autowired
    private BusLineServiceImpl busLineService;

    @Mock
    private WebClient webClientMock;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private BusLineServiceImpl self;


    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @BeforeEach
    public void init(){

    }

    //Exception reading TrafikLab API and gets BusLines From API
    @Test(expected = InValidBusTypeException.class)
    public void getBusLinesFromAPIInvalidBusLineException() {
        BusLineServiceImpl mockService = Mockito.mock(BusLineServiceImpl.class);
        ReflectionTestUtils.setField(busLineService,"self",mockService);
        Mockito.when(mockService.getBusServiceDetails(Mockito.any(),Mockito.any())).thenReturn(null);
        busLineService.getTopTenBusLinesAndBusStopNames();
    }

    //REAL METHOD: Success scenario
    @Test(expected = InValidBusTypeException.class)
    public void testTopTenBusLinesAndBusStopNamesNullModelType() {
        ConfigProperties configPropertiesMock =  new ConfigProperties();
        ReflectionTestUtils.setField(busLineService,"configProperties",configPropertiesMock);
        busLineService.getTopTenBusLinesAndBusStopNames();
    }

    //REAL METHOD: Success scenario
    @Test
    public void testTopTenBusLinesAndBusStopNames() {
        ConfigProperties  configProperties = new ConfigProperties();
        configProperties.setKey("5999d786b593421493702b5fa2528b7f");
        configProperties.setBaseUrl("https://api.sl.se");
        configProperties.setDefaultTransportModeCode("BUS");
        ReflectionTestUtils.setField(busLineService,"configProperties",configProperties);
        final var responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);
        when(webClientMock.get())
                .thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri((Function<UriBuilder, URI>) Mockito.any()))
                .thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve())
                .thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToMono((Class<Object>) Mockito.any()))
                .thenReturn(Mono.just(getTrafikLabResponseLine())).thenReturn(Mono.just(getTrafikLabResponseJourney())).thenReturn(Mono.just(getTrafikLabResponseStopPoint()));
        List<BusLinesResponse> busServices = busLineService.getTopTenBusLinesAndBusStopNames();
        Assert.assertNotNull("Top 10 Bus Lines and Bus Stop Names", busServices);
        Assert.assertEquals(10,busServices.size());
    }

    private TrafikLabResponse getTrafikLabResponseLine() {
        TrafikLabResponse response = new TrafikLabResponse();
        response.setMessage(null);
        response.setStatusCode(0);
        response.setExecutionTime(403);
        ResponseData responseData = new ResponseData();
        responseData.setType("Line");
        responseData.setVersion("2023-04-11 00:11");
        List<Result> resultLines = new ArrayList<Result>();
        ResultLine result = new ResultLine();
        result.setLineNumber("1");
        result.setLineDesignation("1");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("blåbuss");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("112");
        result.setLineDesignation("112");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("113");
        result.setLineDesignation("113");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);


        result = new ResultLine();
        result.setLineNumber("115");
        result.setLineDesignation("115");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("116");
        result.setLineDesignation("116");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("117");
        result.setLineDesignation("117");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("118");
        result.setLineDesignation("118");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);


        result = new ResultLine();
        result.setLineNumber("119");
        result.setLineDesignation("119");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("124");
        result.setLineDesignation("124");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("127");
        result.setLineDesignation("127");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        //-
        result = new ResultLine();
        result.setLineNumber("151");
        result.setLineDesignation("151");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("152");
        result.setLineDesignation("152");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("153");
        result.setLineDesignation("153");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("154");
        result.setLineDesignation("154");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        result = new ResultLine();
        result.setLineNumber("155");
        result.setLineDesignation("155");
        result.setDefaultTransportModeCode("BUS");
        result.setDefaultTransportMode("");
        result.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        result.setExistsFromDate("2007-08-24 00:00:00.000");
        resultLines.add(result);

        responseData.setResult(resultLines);
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
        List<Result> results = new ArrayList<>();
        ResultStop result = new ResultStop();
        result.setStopPointName("Stadshagsplan");
        result.setStopPointNumber("10001");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("John Bergs plan");
        result.setStopPointNumber("10002");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2015-09-24 00:00:00.000");
        result.setExistsFromDate("2015-09-24 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("John Bergs plan");
        result.setStopPointNumber("10003");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Arbetargatan");
        result.setStopPointNumber("10006");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Arbetargatan");
        result.setStopPointNumber("10007");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("S:t Eriksgatan");
        result.setStopPointNumber("10008");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);



        result = new ResultStop();
        result.setStopPointName("S:t Eriksgatan");
        result.setStopPointNumber("10009");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Frihamnens färjeterminal");
        result.setStopPointNumber("10010");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);



        result = new ResultStop();
        result.setStopPointName("Frihamnsporten");
        result.setStopPointNumber("10011");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Celsiusgatan");
        result.setStopPointNumber("10012");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);

        result = new ResultStop();
        result.setStopPointName("Stadshagsplan");
        result.setStopPointNumber("10018");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Kungsbroplan");
        result.setStopPointNumber("10017");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Kungsbroplan");
        result.setStopPointNumber("10016");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Scheelegatan");
        result.setStopPointNumber("10015");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);


        result = new ResultStop();
        result.setStopPointName("Scheelegatan");
        result.setStopPointNumber("10014");
        result.setZoneShortName("A");
        result.setStopAreaTypeCode("BUSTERM");
        result.setLastModifiedUtcDateTime("2022-10-28 00:00:00.000");
        result.setExistsFromDate("2022-10-28 00:00:00.000");
        results.add(result);

        responseData.setResult(results);
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
        List<Result> results = new ArrayList<>();
        ResultJourney result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10008");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10012");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10014");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10016");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10024");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10034");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10038");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10042");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10008");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10066");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10062");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10061");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10059");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10057");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10055");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10053");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("1");
        result.setDirectionCode("1");
        result.setJourneyPatternPointNumber("10044");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        responseData.setResult(results);
        response.setResponseData(responseData);
        return response;
    }

    private List<BusLinesResponse> getBusResponses() {
        List<BusLinesResponse> busResponses = new ArrayList<>();
        BusLinesResponse busResponse = new BusLinesResponse();
        busResponse.setBusLineName("636");
        Collection<String> busStopNames = List.of("StationsGatan", "SundbybergStation", "SolnaBusinessPark", "SolnaCentrum",
                "Hudusta", "Hallonberg", "Risny", "Odenplan", "StockholmSodra", "SolnaStation");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);

        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("626");
        busStopNames = List.of("Danderyds sjukhus", "Roslags Näsby trafikplats", "Åkersberga station", "Arninge station",
                "Ekebo", "Tallebo", "Singö handel", "Brännbacken", "Hjälmsättra", "Lillsättra");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);

        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("637");
        busStopNames = List.of("Norrtälje busstation", "Steneborg", "Norrfjäll", "Söderfjäll södra",
                "Fjällbo", "Kolskär", "Backby", "Åmyran", "Boda", "Dalviken");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);


        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("620");
        busStopNames = List.of("Åkersberga station", "Mellangården", "Ekhamravägen", "Sjöhamra",
                "Gallboda", "Skogen", "Nykvarn", "Nyboda", "Fåfängan", "Lillnäs");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);


        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("639");
        busStopNames = List.of("Danderyds sjukhus", "Karlberg", "Gillingebanan", "Kristinelund",
                "Fälle", "Väsbylund", "Hagby", "Lugnet", "Nydal", "Görvälstorp");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);


        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("312");
        busStopNames = List.of("Rånövägen","Åkeshovsvägen","Brommaplan","Nockeby","Brostugan","Drottningholm",
                "Tillflykten","Lindö lada","Tappström","Ekebyhov","Ekvägen","Närlundavägen","Träkvista idrottsplats");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);


        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("652");
        busStopNames = List.of("Norrtälje busstation","Norrtälje busstation","Narvagatan","Narvagatan","Rodenkyrkan",
                "Utvägen","Utvägen","Grind","Grind","Solbacka" );
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);

        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("785");
        busStopNames = List.of("Hertig Carls väg","Scania centralkontor","Vibergen","Järna station",
                "Överjärna kyrka","Smedjan","Skäve","Pilkrog norra","Pilkrog","Säbylund","Stensta","Ytter Eneby");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);


        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("783");
        busStopNames = List.of("Vårsta centrum","Sjövreten","Smällan","Rosenhill","Polhemsgatan","Södertälje sjukhus",
                "Högåsavägen","Hagabergs folkhögskola","Gökvägen","Gjuteriporten");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);


        busResponse = new BusLinesResponse();
        busResponse.setBusLineName("631");
        busStopNames = List.of( "Norrtälje busstation","Norrtälje busstation","Backtorp","Gustavslund",
                "Gustavslund","Görla industriområde","Görla mellersta","Görla östra","Östhamra","Björnö gård","Strömsborg");
        busResponse.setBusStopNames(busStopNames);
        busResponses.add(busResponse);
        return busResponses;
    }
}