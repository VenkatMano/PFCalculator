package com.apache.pfcalculator.proxyservice.service;

import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.IndexOptions;
import org.springframework.stereotype.Service;

import com.apache.pfcalculator.proxyservice.model.CustomeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoggerServiceImple implements LoggerService{

	@Autowired
	RestHighLevelClient elasticClient;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String index = "zuullogs";
			
	private String type = "logging";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public CustomeRequest createLoggerForRequest(CustomeRequest customRequest) {
		logger.info("Creating a log for request {}", customRequest.getRequestId());
		try
		{
			Map sourceMap = objectMapper.convertValue(customRequest, Map.class);
			IndexRequest request = new IndexRequest(index, type, customRequest.getRequestId()).source(sourceMap);
			IndexResponse response = elasticClient.index(request, RequestOptions.DEFAULT);
			System.out.println(response.getResult());
		}
		catch(Exception ex)
		{
			logger.info("Error occurred while loading logs into elasticsearch {} with error {}",customRequest.getRequestId(),ex.getMessage());
		}
		return null;
	}
	
	

}
