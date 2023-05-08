package se.sbab.busservices.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.exception.InValidBusTypeException;
import se.sbab.busservices.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BusLineServiceTest {

    @InjectMocks
    private BusLineServiceImpl busLineService;


    @Mock
    private BusService busService;


    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Test
    public void testTopTenBusLinesAndBusStopNames() throws IOException {
        ConfigProperties configProperties = new ConfigProperties();
        configProperties.setKey("5999d786b593421493702b5fa2528b7f");
        configProperties.setBaseUrl("https://api.sl.se");
        configProperties.setDefaultTransportModeCode("BUS");
        ReflectionTestUtils.setField(busLineService, "configProperties", configProperties);
        when(busService.getBusServiceDetails(BusModelType.STOP_POINT)).thenReturn(getTrafikLabResponseStopPoint());
        when(busService.getBusServiceDetails(BusModelType.JOURNEY_PATTERN_POINT_ONLINE)).thenReturn(getTrafikLabResponseJourney());
        when(busService.getBusLinesFromAPI()).thenReturn(getTrafikLabResponseLine().getResponseData().getResult()
            .stream()
            .map(ResultLine.class::cast)
            .map(ResultLine::getLineNumber)
            .collect(Collectors.toList()));


        List<BusLinesResponse> busServices = busLineService.getTopTenBusLinesAndBusStopNames();
        Assert.assertNotNull("Top 5 Bus Lines and Bus Stop Names", busServices);
        Assert.assertEquals(5, busServices.size());
    }

    @Test(expected = InValidBusTypeException.class)
    public void getBusLinesFromAPIInvalidBusLineException() throws IOException {
        Mockito.when(busService.getBusServiceDetails(Mockito.any())).thenReturn(null);
        busLineService.getTopTenBusLinesAndBusStopNames();
    }

    @Test(expected = InValidBusTypeException.class)
    public void testTopTenBusLinesAndBusStopNamesNullModelType() throws IOException {
        ConfigProperties configPropertiesMock = new ConfigProperties();
        ReflectionTestUtils.setField(busLineService, "configProperties", configPropertiesMock);
        busLineService.getTopTenBusLinesAndBusStopNames();
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
        result.setLineNumber("112");
        result.setDirectionCode("112");
        result.setJourneyPatternPointNumber("10012");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("113");
        result.setDirectionCode("113");
        result.setJourneyPatternPointNumber("10014");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("114");
        result.setDirectionCode("114");
        result.setJourneyPatternPointNumber("10015");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);



        result = new ResultJourney();
        result.setLineNumber("115");
        result.setDirectionCode("115");
        result.setJourneyPatternPointNumber("10016");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("116");
        result.setDirectionCode("116");
        result.setJourneyPatternPointNumber("10024");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("117");
        result.setDirectionCode("117");
        result.setJourneyPatternPointNumber("10034");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("118");
        result.setDirectionCode("118");
        result.setJourneyPatternPointNumber("10038");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("119");
        result.setDirectionCode("119");
        result.setJourneyPatternPointNumber("10042");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("124");
        result.setDirectionCode("124");
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
        result.setLineNumber("127");
        result.setDirectionCode("127");
        result.setJourneyPatternPointNumber("10062");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("151");
        result.setDirectionCode("151");
        result.setJourneyPatternPointNumber("10061");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("152");
        result.setDirectionCode("152");
        result.setJourneyPatternPointNumber("10052");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);


        result = new ResultJourney();
        result.setLineNumber("153");
        result.setDirectionCode("153");
        result.setJourneyPatternPointNumber("10057");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("154");
        result.setDirectionCode("154");
        result.setJourneyPatternPointNumber("10055");
        result.setLastModifiedUtcDateTime("2022-02-15 00:00:00.000");
        result.setExistsFromDate("2022-02-15 00:00:00.000");
        results.add(result);

        result = new ResultJourney();
        result.setLineNumber("155");
        result.setDirectionCode("155");
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
}
