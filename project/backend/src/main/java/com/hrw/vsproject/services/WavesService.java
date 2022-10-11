package com.hrw.vsproject.services;

import java.util.List;

import com.hrw.vsproject.entities.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hrw.vsproject.utils.WavesUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for implementing the business logic of the waves api. <br>
 * This class represents the interface between the wavescap api and the waves controller layer of the application. <br>
 *
 */
@Service
public class WavesService {
    private WavesUtils wavesUtils;

    public WavesService(WavesUtils wavesUtils) {
        this.wavesUtils = wavesUtils;
    }

    /**
     * Method to provide the current price of one waves coin in USD.<br>
     *
     * @return Current price of one waves coin in USD in {@link Double}.
     */
    public Double getCurrentPrice() {
        WavesResponse response = wavesUtils.getWavesResponse();

        return response.getData().get("lastPrice_usd-n").asDouble();
    }

    /**
     * Method to provide the current sharpe ratio for a waves investment.<br>
     *
     * @return Current sharpe ratio in {@link Double}.
     */
    public Double getSharpeRatio() {
    	
    	WavesHistoricData data = wavesUtils.getHistoricData();
    	SharpeRatioService sharpeRatioService = new SharpeRatioService();
		
		List<Double> list = wavesUtils.getPrices(data);
		
		double Op = 0.0; // standard deviation
		double Rp = 0.0; // asset Return (Rendite) 
		double Rf = 0.0; // Risk free rate (Risikofreie Rendite) 
		
		Op = sharpeRatioService.calculateSD(list);
		Rp = sharpeRatioService.calculateAR(list);
		Rf = -0.625;
		
		
		return (Rp - Rf) / Op; // Calculation of the SharpeRate
    	
    }

    /**
     * Provides a {@link PriceRange} for historic data of the last year and the moving average based on {@code period} days.<br>
     *
     * @param period in days for calculating the moving average.
     * @return {@link PriceRange} with data from one year.
     * @throws ResponseStatusException in case period is small or equals 0
     */
    public PriceRange getAllPricesAndMovingAverage(int period) throws ResponseStatusException {
        if(period <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only positive numbers for period are allowed");

        WavesHistoricData data = wavesUtils.getHistoricData();
        List<Double> prices = wavesUtils.getPrices(data);

        return getPriceRange(period, data.getStart().getTime(), prices);
    }

    /**
     * Private method to calculate the {@link PriceRange} of the last year, including the moving average of {@code period} days. <br>
     *
     * @param period in days for calculating the moving average.
     * @param start machine time for the start of the last year.
     * @param prices {@link List} with prices of the last year.
     * @return {@link PriceRange}
     */
    private PriceRange getPriceRange(int period, long start, List<Double> prices) {
        final long time24h = 86400000L; // 24h in ms

        PriceRange result = new PriceRange();
        Double sum = 0.0;
        for(int i = 0; i < prices.size(); i++) {
            Long currentTime = start + time24h * i;
            Double currentPrice = prices.get(i);

            Price price = new Price();
            price.setPriceEod(currentPrice);
            sum += currentPrice;

            if(i - period >= 0) {
                sum -= prices.get(i - period);
            }

            if(i + 1 < period) {
                price.setMovingAvg(null);
            } else {
                price.setMovingAvg(sum / period);
            }


            result.addPrice(currentTime, price);
        }
        return result;
    }

    /**
     * Provides interest data for the respective coins
     *
     * @return interest data for the respective coins
     */
    public Interest getInterest() {
        return wavesUtils.getInterest();
    }

}





