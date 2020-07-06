package com.apache.pfcalculator.dataviewerservice.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apache.pfcalculator.dataviewerservice.model.Employee;

@Service
public class EmployeeDetailServiceImple extends Sample implements EmployeeDetailService {
	
	@Autowired
	RestHighLevelClient elasticClient;
		
	@Override
	public Employee getEmployeeById(String id) {		
		try
		{
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.query(QueryBuilders.matchQuery("id.keyword", id));
			SearchRequest searchRequest = new SearchRequest("employeedata").source(sourceBuilder);
			SearchResponse response = elasticClient.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits searchHits = response.getHits();
			Employee employee = new Employee();
			for(int i=0; i<searchHits.getTotalHits().value; i++)
			{
				SearchHit searchHit = searchHits.getAt(i);			
				Map<String, Object> documentFieldMap = searchHit.getSourceAsMap();
				employee.setId((String)documentFieldMap.get("id"));
				employee.setEmailId((String)documentFieldMap.get("emailId"));
				employee.setAadharId((String)documentFieldMap.get("aadharId"));
				employee.setFirstName((String)documentFieldMap.get("firstName"));
				employee.setLastName((String)documentFieldMap.get("lastName"));
				employee.setPanCardId((String)documentFieldMap.get("panCardId"));
				employee.setPfStartDate((String)documentFieldMap.get("pfStartDate"));
				employee.setRole((String)documentFieldMap.get("role"));
				employee.setSalary(Long.valueOf((String.valueOf(documentFieldMap.get("salary")))));
				employee.setTotalPfInAccount((Long.valueOf(String.valueOf(documentFieldMap.get("totalPfInAccount")))));
				
			}
			
			return employee;
		}
		catch(Exception e)
		{
			System.out.println("Error occured");
		}
		return null;
	}

}

class Sample {
	public static final void returnHappy(){
		String s = "happy";
	}
}
