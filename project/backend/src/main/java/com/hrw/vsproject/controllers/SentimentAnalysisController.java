package com.hrw.vsproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrw.vsproject.entities.SentimentResult;
import com.hrw.vsproject.services.SentimentAnalysisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin(origins = "https://hrw-vs-app.herokuapp.com/")
@RestController
public class SentimentAnalysisController {
	private final SentimentAnalysisService sentimentAnalysis;

	public SentimentAnalysisController(SentimentAnalysisService sentimentAnalysis){
		this.sentimentAnalysis = sentimentAnalysis;
	}

	@Operation(summary = "Sucht Meinungen aller Tweets und fast diese zusammen.", description = "Dieser Endpunkt liefert eine Meinungsübersicht aller Tweets zu den folgenden Hastags/ Benutzern: @SignatureChain, @wavesprotocol, @neutrino_proto, $Waves, $USDN, @sasha35625")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Kummulierte Meinungsübersicht der Tweets.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SentimentResult.class))
                    })
    })
	@GetMapping("/popularitytrend")
	public ResponseEntity<SentimentResult> getPopularityTrend() {
		SentimentResult result = sentimentAnalysis.calculateSentiment();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
