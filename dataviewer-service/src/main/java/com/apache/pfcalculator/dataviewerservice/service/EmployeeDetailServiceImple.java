package com.apache.pfcalculator.dataviewerservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apache.pfcalculator.dataviewerservice.model.Employee;
import com.apache.pfcalculator.dataviewerservice.model.EmployeeAggregate;
import com.apache.pfcalculator.dataviewerservice.model.PfAndSalaryModel;
import com.apache.pfcalculator.dataviewerservice.model.PfModel;
import com.apache.pfcalculator.dataviewerservice.model.RoleAndSalaryAggregateModel;
import com.apache.pfcalculator.dataviewerservice.model.RoleModel;

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
				searchSourceBuilder.aggregation(AggregationBuilders.terms("role_aggregation").field("role.keyword")
						.subAggregation(AggregationBuilders.avg("avg_aggregation").field("salary")));
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDateAndSalary())) {
				searchSourceBuilder
						.aggregation(AggregationBuilders.terms("pfStartDate_aggregation").field("pfStartDate.keyword")
								.subAggregation(AggregationBuilders.avg("avg_aggregation").field("salary")));
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnRole())) {
				searchSourceBuilder.aggregation(AggregationBuilders.terms("role_aggregation").field("role.keyword"));
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDate())) {
				searchSourceBuilder
						.aggregation(AggregationBuilders.terms("pfStartDate_aggregation").field("pfStartDate.keyword"));
			}

			SearchRequest searchRequest = new SearchRequest("employeedata").source(searchSourceBuilder);
			SearchResponse response = elasticClient.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregationFromResult = response.getAggregations();
			List<Aggregation> aggregationList = new ArrayList<>();
			Map<String, Aggregation> aggregationsMapFromResult = aggregationFromResult.asMap();
			if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnRoleAndSalary())
					|| findAnObjectNullAndTrue(employeeAggregate.getBasedOnRole())) {
				ParsedStringTerms roleAggregation = (ParsedStringTerms) aggregationsMapFromResult
						.get("role_aggregation");
				List<? extends Bucket> bucketList = roleAggregation.getBuckets();
				if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnRoleAndSalary())) {
					bucketList.stream().forEach(e -> {
						RoleAndSalaryAggregateModel roleAndSalaryAggregateModel = ServiceUtils
								.getRoleAndSalaryModelFromBucket(e);
						returnList.add(roleAndSalaryAggregateModel);

					});
					return returnList;
				}
				else {
					bucketList.stream().forEach(e -> {
						RoleModel roleModel = ServiceUtils.getRoleModel(e);
						returnList.add(roleModel);
					});
					return returnList;
				}
			} else if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDateAndSalary())
					|| findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDate())) {				
				ParsedStringTerms roleAggregation = (ParsedStringTerms) aggregationsMapFromResult
						.get("pfStartDate_aggregation");
				List<? extends Bucket> bucketList = roleAggregation.getBuckets();
				if (findAnObjectNullAndTrue(employeeAggregate.getBasedOnCreationDateAndSalary())) {
					bucketList.stream().forEach(e -> {
						PfAndSalaryModel pfSalaryModel = ServiceUtils
								.getPfAndSalaryModel(e);
						returnList.add(pfSalaryModel);

					});
					return returnList;
				}
				else {
					bucketList.stream().forEach(e -> {
						PfModel pfModel = ServiceUtils.getPfModel(e);
						returnList.add(pfModel);
					});
					return returnList;
				}
			}			
		} catch (Exception e) {
			System.out.println("Error occured");
		}
		return Collections.EMPTY_LIST;
	}

	private boolean findAnObjectNullAndTrue(Boolean value) {
		if (value != null && value) {
			return value;
		}
		return false;
	}

}
