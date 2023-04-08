package se.sbab.busservices.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;


@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value=ResultJourney.class, name = "ResultJourney"),
        @JsonSubTypes.Type(value=ResultLine.class, name = "ResultLine"),
        @JsonSubTypes.Type(value=ResultStop.class, name = "ResultStop")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public abstract class Result{




}