package se.sbab.busservices.model;

import lombok.*;

import java.util.Collection;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * BusResponse gives api response with top 10 BusLines and its BusStops
 * @author Parasuram
 */
public class BusLinesResponse {
    private String busLineName;
    private Collection<String> busStopNames;
}
