package se.sbab.busservices.services;

import se.sbab.busservices.model.BusLinesResponse;

import java.io.IOException;
import java.util.List;

/**
 * The interface Bus line service.
 */
public interface BusLineService {
    /**
     * Gets top ten bus lines and bus stop names.
     *
     * @return the top ten bus lines and bus stop names
     * @throws IOException the io exception
     */
    List<BusLinesResponse> getTopTenBusLinesAndBusStopNames() throws IOException;
}
