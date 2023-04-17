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
import java.util.LinkedHashMap;
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
        List<BusLinesResponse> busResponses = getBusResponses();
        Mockito.when(busLineService.getTopTenBusLinesAndBusStopNames()).thenReturn(busResponses);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        String res = mvcResult.getResponse().getContentAsString();
        List responseList = objectMapper.readValue(res, List.class);
        List<String> busStopNames = (List<String>) ((LinkedHashMap) responseList.get(0)).get("busStopNames");
        Assert.assertEquals(10, busStopNames.size());
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
        busResponses.add(busResponse);
        return busResponses;
    }
}