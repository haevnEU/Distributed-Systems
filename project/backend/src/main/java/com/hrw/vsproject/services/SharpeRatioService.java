package com.hrw.vsproject.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


/**
 * Class provides the Methods for calculation the values for sharpe ratio 
 * 
 */
@AllArgsConstructor
public class SharpeRatioService {

	/**
	 * Method to calculate the standard deviation  of wave coin P(F)
	 * 
	 * @return the current standard deviation of wave coin in {@link Double}.
	 */
	public double calculateSD(List<Double> wave) {
		
		
		double sum = 0.0;
		double standardDeviation = 0.0;
		
		
		List <Double> list = new ArrayList();
		
		int lenght = wave.size();

		for(int i = 0; i < wave.size() - 1; i++) {
			double profit = wave.get((i+1)) - wave.get(i);
			list.add(profit * 100 / wave.get(i));
		}

		
		for(double num : list) {
			sum += num;
			
		}
		
		double mean = sum/list.size();
		
		for(double num : list) {
			standardDeviation += Math.pow(num - mean, 2);
		}
		
		return Math.sqrt(standardDeviation/lenght);
		
		
	}
	
	/**
	 * Method to calculate the asset Return of wave coin R(P)
	 * @return the current asset return of wave coin in {@link Double}.
	 */
	public double calculateAR(List<Double> wave) {
		
		double startValue = 0.0; 
		int x = 0;				
		double currentValue = 0.0;	
		double profit = 0.0;		
		
		startValue = wave.get(0);
		x = wave.size();
		currentValue = wave.get(x-1);
		
		profit = currentValue - startValue;
		
		
		
		
		return profit * 100 / startValue;
	}


}
