package se.sbab.busservices.services;

import se.sbab.busservices.model.BusLinesResponse;

import java.util.List;

public interface BusLineService {
    List<BusLinesResponse> getTopTenBusLinesAndBusStopNames();
}
