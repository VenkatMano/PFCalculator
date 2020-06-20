package com.apache.pfcalculator.datauploadservice.dao;

import org.springframework.stereotype.Repository;

import com.apache.pfcalculator.datauploadservice.model.Employee;


public interface EmployeeElkDao {
	
	public Employee createEmployee(Employee employee);

}
