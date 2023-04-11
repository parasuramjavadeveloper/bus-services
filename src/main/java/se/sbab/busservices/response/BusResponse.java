package se.sbab.busservices.response;

import lombok.*;

import java.util.Collection;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * BusResponse gives api response with top 10 BusLines and its BusNames
 * @author Parasuram
 */
public class BusResponse {
    private String busLineName;
    private Collection<String> busStopNames;
}
