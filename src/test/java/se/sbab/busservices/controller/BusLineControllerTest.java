package se.sbab.busservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import se.sbab.busservices.model.BusLinesResponse;
import se.sbab.busservices.services.BusLineService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BusLineController.class)
@WebAppConfiguration
@EnableWebMvc
public class BusLineControllerTest {
    private final String BASE_URL = "/api/v1/bus/services";

    private final String INVALID_BASE_URL = "/api/v1/services";

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private BusLineService busLineService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testTopThreeBusLinesAndItsBusStopNamesWhenValidURLGiven() throws Exception {
        Mockito.when(busLineService.getTopTenBusLinesAndBusStopNames()).thenReturn(getBusResponses());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)).andReturn();
        Assert.assertEquals(10, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class).size());
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testTopThreeBusLinesAndItsBusStopNamesWhenInValidURLGiven() throws Exception {
        List<BusLinesResponse> busResponses = getBusResponses();
        Mockito.when(busLineService.getTopTenBusLinesAndBusStopNames()).thenReturn(busResponses);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(INVALID_BASE_URL)).andReturn();
        Assert.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    private List<BusLinesResponse> getBusResponses() {
        BusLinesResponse busResponse = new BusLinesResponse();
        busResponse.setBusLineName("636");
        Collection<String> busStopNames = List.of("StationsGatan", "SundbybergStation", "SolnaBusinessPark", "SolnaCentrum",
                "Hudusta", "Hallonberg", "Risny", "Odenplan", "StockholmSodra", "SolnaStation");
        busResponse.setBusStopNames(busStopNames);
        List<BusLinesResponse> busResponses = new ArrayList<>();
        BusLinesResponse busResponseTwo = new BusLinesResponse();
        busResponseTwo.setBusLineName("637");
        Collection<String> busStopNamesTwo = List.of("StationsGatanOne", "SundbybergStationOne", "SolnaBusinessParkOne", "SolnaCentrumOne",
                "HudustaOne", "HallonbergOne", "RisnyOne", "OdenplanOne", "StockholmSodraOne", "SolnaStationOne");
        busResponseTwo.setBusStopNames(busStopNamesTwo);
        BusLinesResponse busResponseThree = new BusLinesResponse();
        busResponseThree.setBusLineName("645");
        Collection<String> busStopNamesThree = List.of("StationsGatanOne", "SundbybergStationOne", "SolnaBusinessParkOne", "SolnaCentrumOne",
                "HudustaOne", "HallonbergOne", "RisnyOne", "OdenplanOne", "StockholmSodraOne", "SolnaStationOne");
        busResponseThree.setBusStopNames(busStopNamesThree);
        BusLinesResponse busResponseFour = new BusLinesResponse();
        busResponseFour.setBusLineName("644");
        Collection<String> busStopNamesFour = List.of("StationsGatanOne", "SundbybergStationOne", "SolnaBusinessParkOne", "SolnaCentrumOne",
                "HudustaOne", "HallonbergOne", "RisnyOne", "OdenplanOne", "StockholmSodraOne", "SolnaStationOne");
        busResponseFour.setBusStopNames(busStopNamesFour);
        BusLinesResponse busResponseFive = new BusLinesResponse();
        busResponseFive.setBusLineName("643");
        Collection<String> busStopNamesFive = List.of("StationsGatanOne", "SundbybergStationOne", "SolnaBusinessParkOne", "SolnaCentrumOne",
                "HudustaOne", "HallonbergOne", "RisnyOne", "OdenplanOne", "StockholmSodraOne", "SolnaStationOne");
        busResponseFive.setBusStopNames(busStopNamesFive);
        BusLinesResponse busResponseSix = new BusLinesResponse();
        busResponseSix.setBusLineName("642");
        Collection<String> busStopNamesSix = List.of("StationsGatanOne", "SundbybergStationOne", "SolnaBusinessParkOne", "SolnaCentrumOne",
                "HudustaOne", "HallonbergOne", "RisnyOne", "OdenplanOne", "StockholmSodraOne", "SolnaStationOne");
        busResponseSix.setBusStopNames(busStopNamesSix);
        BusLinesResponse busResponseSeven = new BusLinesResponse();
        busResponseSeven.setBusLineName("641");
        Collection<String> busStopNamesSeven = List.of("StationsGatanOne", "SundbybergStationOne", "SolnaBusinessParkOne", "SolnaCentrumOne",
                "HudustaOne", "HallonbergOne", "RisnyOne", "OdenplanOne", "StockholmSodraOne", "SolnaStationOne");
        busResponseSeven.setBusStopNames(busStopNamesSeven);
        BusLinesResponse busResponseEight = new BusLinesResponse();
        busResponseEight.setBusLineName("640");
        Collection<String> busStopNamesEight = List.of("StationsGatanOne", "SundbybergStationOne", "SolnaBusinessParkOne", "SolnaCentrumOne",
                "HudustaOne", "HallonbergOne", "RisnyOne", "OdenplanOne", "StockholmSodraOne", "SolnaStationOne");
        busResponseEight.setBusStopNames(busStopNamesEight);
        BusLinesResponse busResponseNine = new BusLinesResponse();
        busResponseNine.setBusLineName("639");
        Collection<String> busStopNamesNine = List.of("StationsGatanNine", "SundbybergStationNine", "SolnaBusinessParkNine", "SolnaCentrumNine",
                "HudustaNine", "HallonbergNine", "RisnyNine", "OdenplanNine", "StockholmSodraNine", "SolnaStationNine");
        busResponseNine.setBusStopNames(busStopNamesNine);
        BusLinesResponse busResponseTen = new BusLinesResponse();
        busResponseTen.setBusLineName("638");
        Collection<String> busStopNamesTen = List.of("StationsGatanTen", "SundbybergStationTen", "SolnaBusinessParkTen", "SolnaCentrumTen",
                "HudustaTen", "HallonbergTen", "RisnyTen", "OdenplanTen", "StockholmSodraTen", "SolnaStationTen");
        busResponseTen.setBusStopNames(busStopNamesTen);
        busResponses.add(busResponse);
        busResponses.add(busResponseTwo);
        busResponses.add(busResponseTen);
        busResponses.add(busResponseNine);
        busResponses.add(busResponseEight);
        busResponses.add(busResponseSeven);
        busResponses.add(busResponseSix);
        busResponses.add(busResponseFive);
        busResponses.add(busResponseFour);
        busResponses.add(busResponseThree);
        return busResponses;
    }
}