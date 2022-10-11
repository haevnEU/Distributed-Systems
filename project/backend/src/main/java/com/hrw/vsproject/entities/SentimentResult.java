package com.hrw.vsproject.entities;

import lombok.Data;

/**
 * Representation of the Sentimentanalysis results.
 */
@Data
public class SentimentResult {
    double positive;
    double neutral;
    double negative;
}
