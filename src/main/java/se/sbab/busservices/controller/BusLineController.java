package se.sbab.busservices.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.sbab.busservices.model.BusLinesResponse;
import se.sbab.busservices.services.BusLineService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 100000L)
@RequestMapping("/api/v1")
/**
 * BusLineController gives the REST EndPoint to get the Top 10 BusLines and its BusStops
 * @author Parasuram
 */
public class BusLineController {

    @Autowired
    private BusLineService busLineService;

    @Operation(summary = "This is to get the Top 10 BusLines and its BusStops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus Service details found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Bus Service details not found", content = @Content)
    })
    @GetMapping("/bus/services")
    public ResponseEntity<List<BusLinesResponse>> getTopTenBusLinesAndBusStops() {
        return new ResponseEntity<>(busLineService.getTopTenBusLinesAndBusStopNames(), HttpStatus.OK);
    }
}
