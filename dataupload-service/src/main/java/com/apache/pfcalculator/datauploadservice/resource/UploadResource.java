package com.apache.pfcalculator.datauploadservice.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apache.pfcalculator.datauploadservice.model.Employee;
import com.apache.pfcalculator.datauploadservice.service.EmployeeService;

@RestController
@RequestMapping("/upload-service")
public class UploadResource {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping
	private ResponseEntity<Employee> uploadAJson(@RequestBody Employee employee)
	{
		//Stub will be updated soon
		Employee createdEmployee = employeeService.createEmployee(employee);
		if(createdEmployee!=null)
		{
			return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
		}		
		else
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
