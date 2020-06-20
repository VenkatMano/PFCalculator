package com.apache.pfcalculator.datauploadservice.service;

import org.springframework.stereotype.Service;

import com.apache.pfcalculator.datauploadservice.model.Employee;


public interface EmployeeService {
	
	public Employee createEmployee(Employee employee);

}
