package com.apache.pfcalculator.datauploadservice.dao;

import java.util.Map;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apache.pfcalculator.datauploadservice.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class EmployeeElkDaoImpl implements EmployeeElkDao {

	@Autowired
	private RestHighLevelClient client;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String index = "employeedata";
			
	private String type = "employee";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Employee createEmployee(Employee employee) {
		logger.info("creating index employee");
		
		Map employeeMap = objectMapper.convertValue(employee, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, employee.getId()).source(employeeMap);
		try
		{
			IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
			if(response.getResult().equals(Result.CREATED))
			{
				logger.info("The index has been created");
			}
		}
		catch(Exception e)
		{
			logger.info("Error on creating index");
			return null;
		}
		return employee;
	}

}
