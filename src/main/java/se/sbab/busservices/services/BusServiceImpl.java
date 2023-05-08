package se.sbab.busservices.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import se.sbab.busservices.config.ConfigProperties;
import se.sbab.busservices.model.BusModelType;
import se.sbab.busservices.model.ResultLine;
import se.sbab.busservices.model.TrafikLabResponse;
import static se.sbab.busservices.utils.BusConstants.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The type Bus service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BusServiceImpl implements BusService{

  private final ConfigProperties configProperties;
  private final WebClient webClient;

  /**
   * It calls TrafikLab External API's Of BusLine,Journey and StopPoint and gets the response
   */

  @Cacheable(value = "trafikLabResponse", key = "#busModelType.model")
  public TrafikLabResponse getBusServiceDetails(BusModelType busModelType) throws IOException {
    Path cacheFilePath = getCacheFilePath(busModelType.getModel());

    // Check if the cached file exists
    if (Files.exists(cacheFilePath)) {
      byte[] cachedData = Files.readAllBytes(cacheFilePath);
      // Deserialize the cached data and return the response
      return deserialize(cachedData);
    }
    // Cache miss, fetch the response from the WebClient
    TrafikLabResponse response = fetchBusServiceDetailsFromWebClient(busModelType);

    // Cache the response to the local file
    cacheResponse(cacheFilePath, response);

    return response;
  }

  @Override
  public List<String> getBusLinesFromAPI() throws IOException {
    TrafikLabResponse lineResponse = getBusServiceDetails(BusModelType.LINE);
    log.info("Successfully Received Response from BusLineAPI");

    return lineResponse.getResponseData().getResult()
        .stream()
        .map(ResultLine.class::cast)
        .map(ResultLine::getLineNumber)
        .collect(Collectors.toList());
  }

  private TrafikLabResponse fetchBusServiceDetailsFromWebClient(BusModelType busModelType) {
    return webClient.get()
        .uri(builder -> builder.path("/api2/LineData.json")
            .queryParam(KEY, configProperties.getKey())
            .queryParam(DEFAULT_TRANSPORT_MODE_CODE, configProperties.getDefaultTransportModeCode())
            .queryParam(MODEL, busModelType.getModel()).build())
        .retrieve()
        .bodyToMono(TrafikLabResponse.class).block();
  }

  private void cacheResponse(Path cacheFilePath, TrafikLabResponse response) throws IOException {
    byte[] serializedData = serialize(response);
    Files.write(cacheFilePath, serializedData);
  }

  private Path getCacheFilePath(String cacheKey) throws IOException {
    // Specify the directory where the cache files will be stored
    String cacheDirectory = "/path/to/cache/directory";
    // Generate a unique file name for the cache entry
    String cacheFileName = cacheKey + ".cache";

    Path cachePath = Paths.get(cacheDirectory);
    Files.createDirectories(cachePath);
    return cachePath.resolve(cacheFileName);
  }

  private byte[] serialize(TrafikLabResponse response) throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(response);
    oos.flush();
    return bos.toByteArray();
  }

  private TrafikLabResponse deserialize(byte[] data)  {
    ByteArrayInputStream bis = new ByteArrayInputStream(data);
    ObjectInputStream ois = null;
    try {
      ois = new ObjectInputStream(bis);
      return (TrafikLabResponse) ois.readObject();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
