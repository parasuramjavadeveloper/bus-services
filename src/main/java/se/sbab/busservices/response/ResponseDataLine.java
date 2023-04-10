package se.sbab.busservices.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
/**
 * ResponseDataLine gives  BusLines api
 * @author Parasuram
 */
public class ResponseDataLine {
    @JsonProperty("Version")
    public String version;
    @JsonProperty("Type")
    public String type;
    @JsonProperty("Result")
    public List<ResultLine> result;

}
