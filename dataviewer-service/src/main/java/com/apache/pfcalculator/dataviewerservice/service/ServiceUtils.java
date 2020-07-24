package com.apache.pfcalculator.dataviewerservice.service;

import java.util.List;
import java.util.Map;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;

import com.apache.pfcalculator.dataviewerservice.model.PfAndSalaryModel;
import com.apache.pfcalculator.dataviewerservice.model.PfModel;
import com.apache.pfcalculator.dataviewerservice.model.RoleAndSalaryAggregateModel;
import com.apache.pfcalculator.dataviewerservice.model.RoleModel;

public class ServiceUtils {

	public static RoleAndSalaryAggregateModel getRoleAndSalaryModelFromBucket(Bucket bucket) {
		RoleAndSalaryAggregateModel roleAndSalaryModel = new RoleAndSalaryAggregateModel();
		roleAndSalaryModel.setRole(bucket.getKeyAsString());
		Aggregations averageAggregation = bucket.getAggregations();
		Map<String, Aggregation> averageAggregationMap = averageAggregation.asMap();
		ParsedAvg averageValue = (ParsedAvg) averageAggregationMap.get("avg_aggregation");
		roleAndSalaryModel.setAverageSalaryPerRole(String.valueOf(averageValue.getValue()));
		roleAndSalaryModel.setAggregatedDocumentCount(bucket.getDocCount());
		return roleAndSalaryModel;
	}

	public static PfAndSalaryModel getPfAndSalaryModel(Bucket bucket) {
		PfAndSalaryModel pfAndSalaryModel = new PfAndSalaryModel();
		pfAndSalaryModel.setPfStartDate(bucket.getKeyAsString());
		Aggregations averageAggregation = bucket.getAggregations();
		Map<String, Aggregation> averageAggregationMap = averageAggregation.asMap();
		ParsedAvg averageValue = (ParsedAvg) averageAggregationMap.get("avg_aggregation");
		pfAndSalaryModel.setAverageSalaryPerRole(String.valueOf(averageValue.getValue()));
		pfAndSalaryModel.setAggregatedDocumentCount(bucket.getDocCount());
		return pfAndSalaryModel;
	}
	
	public static RoleModel getRoleModel(Bucket bucket) {
		RoleModel roleModel = new RoleModel();
		roleModel.setRole(bucket.getKeyAsString());
		roleModel.setAggregatedDocumentCount(bucket.getDocCount());
		return roleModel;
	}
	
	public static PfModel getPfModel(Bucket bucket) {
		PfModel pfModel = new PfModel();
		pfModel.setPfStartDate(bucket.getKeyAsString());
		pfModel.setAggregatedDocumentCount(bucket.getDocCount());
		return pfModel;
	}

}
