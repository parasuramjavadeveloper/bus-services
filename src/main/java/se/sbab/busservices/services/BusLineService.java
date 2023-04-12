package se.sbab.busservices.services;

import se.sbab.busservices.model.BusLinesResponse;
import se.sbab.busservices.model.TrafikLabResponse;

import java.util.List;

public interface BusLineService {
    TrafikLabResponse getBusService(String modelType);

    List<BusLinesResponse> getTopTenBusLinesAndBusStopNames();
}
