package se.sbab.busservices.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
/**
 * TrafikLabConfig helps to configure WeBClient and ObjectMapper used to hit the API
 * @author Parasuram
 */
public class TrafikLabConfig {

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public WebClient webClient() {
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(60 * 1024 * 1024))
                .build();
        return WebClient.builder().exchangeStrategies(strategies)
                .baseUrl(configProperties.getBaseUrl())
                .build();
    }

}
