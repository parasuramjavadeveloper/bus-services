package se.sbab.busservices.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.sbab.busservices.services.BusLineService;

import java.util.Date;

@Configuration
@Slf4j
public class AppConfig {
     
    @Bean
    CommandLineRunner initDatabase(BusLineService busLineService) {
        return args -> {
            log.info("TrafikLab api execution  ........started = {}",new Date());
            busLineService.getBusService();
            log.info("TrafikLab api execution ........completed = {} ",new Date());
        };
    }
}