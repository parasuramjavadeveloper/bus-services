package se.sbab.busservices.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("bus")
@Getter
@Setter
/**
 * ConfigProperties helps read the TrafikLab API's BaseURL and APIKey
 * @author Parasuram
 */
public class ConfigProperties {
    private String baseUrl;
    private String key;
    private String defaultTransportModeCode;
}