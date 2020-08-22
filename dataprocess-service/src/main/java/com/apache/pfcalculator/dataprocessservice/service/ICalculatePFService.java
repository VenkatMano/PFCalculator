package com.apache.pfcalculator.dataprocessservice.service;

import com.apache.pfcalculator.dataprocessservice.model.CalculateInput;

@FunctionalInterface
public interface ICalculatePFService {
	
	public Double calculate(CalculateInput calculateInput);

}
