package com.apache.pfcalculator.dataviewerservice.resource;

import java.util.List;

import org.elasticsearch.search.aggregations.Aggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apache.pfcalculator.dataviewerservice.model.Employee;
import com.apache.pfcalculator.dataviewerservice.model.EmployeeAggregate;
import com.apache.pfcalculator.dataviewerservice.service.EmployeeDetailServiceImple;
import com.sun.research.ws.wadl.Response;

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
	
	@PostMapping("/details")
	public ResponseEntity aggregateAndgetEmployeeDetails(@RequestBody EmployeeAggregate employeeAggregate)
	{
		return new ResponseEntity<>(employeeDetailService.aggregateEmployee(employeeAggregate), HttpStatus.OK);
	}

}
