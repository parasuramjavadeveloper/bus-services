package se.sbab.busservices.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
/**
 * TrafikLabResponse holds Root Level Response From External API
 * @author Parasuram
 */
public class TrafikLabResponse implements Serializable {

    @JsonProperty("StatusCode")
    private int statusCode;
    @JsonProperty("Message")
    private Object message;
    @JsonProperty("ExecutionTime")
    private int executionTime;
    @JsonProperty("ResponseData")
    private ResponseData responseData;
}
