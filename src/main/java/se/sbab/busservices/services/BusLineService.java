package se.sbab.busservices.services;

import se.sbab.busservices.response.BusResponse;
import se.sbab.busservices.response.TrafikLabResponse;

import java.util.List;

public interface BusLineService {
    TrafikLabResponse getBusService(String modelType);

    List<BusResponse> getBusService();
}
