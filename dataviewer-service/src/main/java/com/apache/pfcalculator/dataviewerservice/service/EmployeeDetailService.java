package com.apache.pfcalculator.dataviewerservice.service;

import java.util.List;

import org.elasticsearch.search.aggregations.Aggregation;

import com.apache.pfcalculator.dataviewerservice.model.Employee;
import com.apache.pfcalculator.dataviewerservice.model.EmployeeAggregate;
import com.apache.pfcalculator.dataviewerservice.model.RoleAndSalaryAggregateModel;

public interface EmployeeDetailService {		
	
	public Employee getEmployeeById(String id);	
	
	public List<Object> aggregateEmployee(EmployeeAggregate employeeAggregate);
	
	public List<Object> sortEmployeesPresentBasedOnField(String sortField);

}
