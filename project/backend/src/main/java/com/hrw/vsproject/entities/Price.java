package com.hrw.vsproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Price class holding the price of a waves coin in USD and the moving average.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Double priceEod;
    private Double movingAvg;

}
