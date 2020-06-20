package com.apache.pfcalculator.datauploadservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apache.pfcalculator.datauploadservice.dao.EmployeeElkDao;
import com.apache.pfcalculator.datauploadservice.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeElkDao employeeElkDao;
	
	@Override
	public Employee createEmployee(Employee employee) {		
		return employeeElkDao.createEmployee(employee);
	}

}
