package com.hrw.vsproject.controllers;

import com.hrw.vsproject.entities.Interest;
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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://hrw-vs-app.herokuapp.com/")
@RestController
public class PyWavesController {
    private final WavesService wavesService;

    public PyWavesController(WavesService wavesService) {
        this.wavesService = wavesService;
    }

    @Operation(
            summary = "Anzeige des APY diverser Coins",
            description = "Dieser Endpunkt zeigt die aktuellen Zinsdaten diverser Coins an."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Zinsdaten für ausgewählte Echtwährungen.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Interest.class)) }),
            @ApiResponse(responseCode = "503", description = "Externe API nicht verfügbar.")
    })
    @GetMapping("/getInterest")
    public ResponseEntity<Interest> getInterest() {
        return new ResponseEntity<>(wavesService.getInterest(), HttpStatus.OK);
    }
}
