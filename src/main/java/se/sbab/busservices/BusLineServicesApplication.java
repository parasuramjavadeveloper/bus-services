package se.sbab.busservices;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import se.sbab.busservices.services.BusLineService;

import java.util.Date;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@OpenAPIDefinition
@EnableCaching
@Slf4j
public class BusLineServicesApplication implements CommandLineRunner {

	@Autowired
	private BusLineService busLineService;
	public static void main(String[] args) {
		SpringApplication.run(BusLineServicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("TrafikLab api execution  ........started = {}",new Date());
		busLineService.getTopTenBusLinesAndBusStopNames();
		log.info("TrafikLab api execution ........completed = {} ",new Date());
	}
}
