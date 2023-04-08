package se.sbab.busservices.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sbab.busservices.response.BusResponse;
import se.sbab.busservices.response.TrafikLabResponse;
import se.sbab.busservices.services.BusService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 100000L)
@RequestMapping("/api/v1")
public class BusController {

    @Autowired
    private BusService busService;

    @Operation(summary = "This is to get the Bus model type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus modelType found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Bus not found", content = @Content)
    })
    @GetMapping("/bus/{modelType}")
    public ResponseEntity<TrafikLabResponse> busService(@PathVariable String modelType) {
        return new ResponseEntity<>(busService.getBusService(modelType), HttpStatus.OK);
    }

    @Operation(summary = "This is to get the Top 10 BusLines and its BusStops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus Service details found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Bus Service details not found", content = @Content)
    })
    @GetMapping("/bus/services")
    public ResponseEntity<List<BusResponse>> busServiceDetails() {
        return new ResponseEntity<>(busService.getBusService(), HttpStatus.OK);
    }
}
