package com.apache.pfcalculator.dataprocessservice.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apache.pfcalculator.dataprocessservice.model.CalculateInput;
import com.apache.pfcalculator.dataprocessservice.model.CalculateOutput;
import com.apache.pfcalculator.dataprocessservice.service.ICalculatePFService;

@RestController
@RequestMapping("/")
public class PFCalculatorResource {
	
	@PostMapping("calculatePf")
	public ResponseEntity<CalculateOutput> returnCalculatedPf(@RequestBody CalculateInput calculateInput) {
		ICalculatePFService pfService = input -> {
			Double pf = null;
			Double myContribution = (input.getBasicSalary()*calculateInput.getYourContribution())/100;
			Double employerContribution = (input.getBasicSalary()*calculateInput.getEmployerContribution())/100;
			Double perMonthAmount = myContribution+employerContribution;
			if(calculateInput.getCurrentBalance()<=0){
				pf = perMonthAmount;
			}
			else{
				pf = calculateInput.getCurrentBalance();
			}			 
			int months = (int) (input.getRetirementAge()-input.getCurrentAge())*12;
			for(int i=0; i<months; i++){
				pf+=((pf*input.getCurrentInterestRate())/100)/12;
				pf+=perMonthAmount;
			}
			return pf;
		};		
		Double totalPfValue = pfService.calculate(calculateInput);
		CalculateOutput calculateOutput = new CalculateOutput();
		calculateOutput.setFundAvailableAfterRetirement(String.valueOf(totalPfValue));
		return new ResponseEntity<>(calculateOutput, HttpStatus.OK);
	}
}
