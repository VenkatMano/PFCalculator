package com.apache.pfcalculator.dataviewerservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apache.pfcalculator.dataviewerservice.model.Employee;
import com.apache.pfcalculator.dataviewerservice.service.EmployeeDetailServiceImple;

@RestController
@RequestMapping(value="/viewer-service")
public class DataViewResource {
	
	@Autowired
	EmployeeDetailServiceImple employeeDetailService;
	
	
	@GetMapping("details/{requestId}")
	public Employee getEmployeeBasedOnRequestId(@PathVariable("requestId") String requestId)
	{
		return employeeDetailService.getEmployeeById(requestId);
	}

}
