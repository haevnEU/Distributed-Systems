package com.hrw.vsproject.controllers;

import com.hrw.vsproject.entities.PriceRange;
import com.hrw.vsproject.services.WavesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://hrw-vs-app.herokuapp.com/")
@RestController
public class WavesController {
    private final WavesService wavesService;

    public WavesController(WavesService wavesService) {
        this.wavesService = wavesService;
    }

    @Operation(
            summary = "Anzeige des MovingAverage von Wave Coin",
            description = "Dieser Endpunkt zeigt den aktuellen Moving Average von Wave Coin an."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Preisdaten für ein Jahr und ein entsprechend der period festgelegten Moving Average.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PriceRange.class)) }),
            @ApiResponse(responseCode = "400", description = "Es dürfen nur positive Zahlen für period genutzt werden."),
            @ApiResponse(responseCode = "503", description = "Externe API nicht verfügbar.")
    })
    @GetMapping("/getAllPricesAndMovingAverage")
    public ResponseEntity<PriceRange> getAllPricesAndMovingAverage(@RequestParam int period) {
        return new ResponseEntity<>(wavesService.getAllPricesAndMovingAverage(period), HttpStatus.OK);
    }

    @Operation(
            summary = "Anzeige des aktuellen Waves Coin Preis",
            description = "Dieser Endpunkt zeigt den aktuellen Preis von Waves Coin an"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Aktueller Wert für einen Waves Coin in USD.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class)) }),
            @ApiResponse(responseCode = "503", description = "Externe API nicht verfügbar.")
    })
    @GetMapping("/getCurrentPrice")
    public ResponseEntity<Double> getCurrentPrice() {
        return new ResponseEntity<>(wavesService.getCurrentPrice(), HttpStatus.OK);
    }
    
    @Operation(
            summary = "Berechnet den SharpeRatio von Waves Coin",
            description = "Dieser Endpunkt berechnet den aktuellen Sharpe Ratio Wert von Waves Coin"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Sharpe Ratio vom Waves Coin.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class)) }),
            @ApiResponse(responseCode = "503", description = "Externe API nicht verfügbar.")
    })
    @GetMapping("/getSharpeRatio")
    public ResponseEntity<Double> getSharpeRatio() {
        return new ResponseEntity<>(wavesService.getSharpeRatio(), HttpStatus.OK);
    }
}