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
 * ResultJourney holds ResultJourney api response
 * @author Parasuram
 */
public class ResultJourney extends Result{
    @JsonProperty("LineNumber")
    private String lineNumber;
    @JsonProperty("DirectionCode")
    private String directionCode;
    @JsonProperty("JourneyPatternPointNumber")
    private String journeyPatternPointNumber;
    @JsonProperty("LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @JsonProperty("ExistsFromDate")
    private String existsFromDate;
}
