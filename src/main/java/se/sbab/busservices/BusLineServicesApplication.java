package se.sbab.busservices;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@OpenAPIDefinition
public class BusLineServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusLineServicesApplication.class, args);
	}

}
