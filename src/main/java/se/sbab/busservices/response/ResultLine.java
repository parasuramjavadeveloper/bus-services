package se.sbab.busservices.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * ResultLine holds BusLines api response
 * @author Parasuram
 */
public class ResultLine extends Result{
    @JsonProperty("LineNumber")
    private String lineNumber;
    @JsonProperty("LineDesignation")
    private String lineDesignation;
    @JsonProperty("DefaultTransportMode")
    private String defaultTransportMode;
    @JsonProperty("DefaultTransportModeCode")
    private String defaultTransportModeCode;
    @JsonProperty("LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @JsonProperty("ExistsFromDate")
    private String existsFromDate;
}
