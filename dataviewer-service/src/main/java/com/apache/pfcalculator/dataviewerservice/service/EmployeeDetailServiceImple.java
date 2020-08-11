package com.apache.pfcalculator.dataviewerservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apache.pfcalculator.dataviewerservice.model.Employee;
import com.apache.pfcalculator.dataviewerservice.model.EmployeeAggregate;

import static com.apache.pfcalculator.dataviewerservice.service.ServiceConstants.*;

@Service
public class EmployeeDetailServiceImple implements EmployeeDetailService {

	@Autowired
	RestHighLevelClient elasticClient;

	@Override
	public Employee getEmployeeById(String id) {
		try {
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.query(QueryBuilders.matchQuery("id.keyword", id));
			SearchRequest searchRequest = new SearchRequest("employeedata").source(sourceBuilder);
			SearchResponse response = elasticClient.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits searchHits = response.getHits();
			Employee employee = new Employee();
			for (int i = 0; i < searchHits.getTotalHits().value; i++) {
				SearchHit searchHit = searchHits.getAt(i);
				Map<String, Object> documentFieldMap = searchHit.getSourceAsMap();
				employee.setId((String) documentFieldMap.get("id"));
				employee.setEmailId((String) documentFieldMap.get("emailId"));
				employee.setAadharId((String) documentFieldMap.get("aadharId"));
				employee.setFirstName((String) documentFieldMap.get("firstName"));
				employee.setLastName((String) documentFieldMap.get("lastName"));
				employee.setPanCardId((String) documentFieldMap.get("panCardId"));
				employee.setPfStartDate((String) documentFieldMap.get("pfStartDate"));
				employee.setRole((String) documentFieldMap.get("role"));
				employee.setSalary(Long.valueOf((String.valueOf(documentFieldMap.get("salary")))));
				employee.setTotalPfInAccount((Long.valueOf(String.valueOf(documentFieldMap.get("totalPfInAccount")))));

			}

			return employee;
		} catch (Exception e) {
			System.out.println("Error occured");
		}
		return null;
	}

	@Override
	public List<Object> aggregateEmployee(EmployeeAggregate employeeAggregate) {
		try {
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			List<Object> returnList = new ArrayList<>();
			if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnRoleAndSalary())) {
				searchSourceBuilder.aggregation(AggregationBuilders.terms(ROLE_AGGREGATION).field("role.keyword")
						.subAggregation(AggregationBuilders.avg("avg_aggregation").field("salary")));
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDateAndSalary())) {
				searchSourceBuilder
						.aggregation(AggregationBuilders.terms(PF_AGGREGATION).field("pfStartDate.keyword")
								.subAggregation(AggregationBuilders.avg("avg_aggregation").field("salary")));
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnRole())) {
				searchSourceBuilder.aggregation(AggregationBuilders.terms(ROLE_AGGREGATION).field("role.keyword"));
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDate())) {
				searchSourceBuilder
						.aggregation(AggregationBuilders.terms(PF_AGGREGATION).field("pfStartDate.keyword"));
			}

			SearchRequest searchRequest = new SearchRequest("employeedata").source(searchSourceBuilder);
			SearchResponse response = elasticClient.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregationFromResult = response.getAggregations();			
			Map<String, Aggregation> aggregationsMapFromResult = aggregationFromResult.asMap();			 
			if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnRoleAndSalary())
					|| findAnObjectNullAndTrue(employeeAggregate.getBasedOnRole())) {
				ParsedStringTerms roleAggregation = (ParsedStringTerms) aggregationsMapFromResult
						.get(ROLE_AGGREGATION);
				List<? extends Bucket> bucketList = roleAggregation.getBuckets();
				if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnRoleAndSalary())) {
					returnList = bucketList.stream().map(ServiceUtils::getRoleAndSalaryModelFromBucket).collect(Collectors.toList());
					return returnList;
				}
				else {
					returnList = bucketList.stream().map(ServiceUtils::getRoleModel).collect(Collectors.toList());
					return returnList;
				}
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDateAndSalary())
					|| findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDate())) {				
				ParsedStringTerms roleAggregation = (ParsedStringTerms) aggregationsMapFromResult
						.get(PF_AGGREGATION);
				List<? extends Bucket> bucketList = roleAggregation.getBuckets();
				if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDateAndSalary())) {
					returnList = bucketList.stream().map(ServiceUtils::getPfAndSalaryModel).collect(Collectors.toList());
					return returnList;
				}
				else {
					returnList = bucketList.stream().map(ServiceUtils::getPfModel).collect(Collectors.toList());
					return returnList;
				}
			}			
		} catch (Exception e) {
			System.out.println("Error occured");
		}
		return Collections.emptyList();
	}

	private boolean findAnObjectNullAndTrue(Boolean value) {		
		return ServiceUtils.CHECKNOTNULL.and(ServiceUtils.CHECKTRUE).test(value);
	}

	@Override
	public List<Object> sortEmployeesPresentBasedOnField(String sortField) {
		List<Object> employeeList = new ArrayList<>();
		try
		{
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			searchSourceBuilder.sort(sortField, SortOrder.ASC);
			SearchRequest searchRequest = new SearchRequest("employeedata").source(searchSourceBuilder);
			SearchResponse searchResponse = elasticClient.search(searchRequest, RequestOptions.DEFAULT);			
			List<SearchHit> hitList = Arrays.asList(searchResponse.getHits().getHits());			
			hitList.stream().forEach(e ->  {
				Employee employee = new Employee();
				Map<String, Object> documentFieldMap = e.getSourceAsMap();				
				employee.setId((String) documentFieldMap.get("id"));
				employee.setEmailId((String) documentFieldMap.get("emailId"));
				employee.setAadharId((String) documentFieldMap.get("aadharId"));
				employee.setFirstName((String) documentFieldMap.get("firstName"));
				employee.setLastName((String) documentFieldMap.get("lastName"));
				employee.setPanCardId((String) documentFieldMap.get("panCardId"));				
				employee.setRole((String) documentFieldMap.get("role"));
				employee.setSalary(Long.valueOf((String.valueOf(documentFieldMap.get("salary")))));
				employee.setTotalPfInAccount((Long.valueOf(String.valueOf(documentFieldMap.get("totalPfInAccount")))));
				employeeList.add(employee);
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return employeeList;
	}

}
