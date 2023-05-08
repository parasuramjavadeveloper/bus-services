package se.sbab.busservices.services;

import se.sbab.busservices.model.BusModelType;
import se.sbab.busservices.model.TrafikLabResponse;

import java.io.IOException;
import java.util.List;

/**
 * The interface Bus service.
 */
public interface BusService {
  /**
   * Gets bus service details.
   *
   * @param mode the mode
   * @return the bus service details
   * @throws IOException the io exception
   */
  TrafikLabResponse getBusServiceDetails(BusModelType mode) throws IOException;

  /**
   * Gets bus lines from api.
   *
   * @return the bus lines from api
   * @throws IOException the io exception
   */
  List<String>  getBusLinesFromAPI() throws IOException;
}
