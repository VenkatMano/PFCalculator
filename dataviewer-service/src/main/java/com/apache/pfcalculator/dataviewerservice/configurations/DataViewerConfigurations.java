package com.apache.pfcalculator.dataviewerservice.configurations;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataViewerConfigurations {
	
	@Value("${elasticsearch.host}")
	private String elasticsearchHost;
	
	@Value("${elasticsearch.port}")
	private String elasticsearchPort;
	
	Logger logger = LoggerFactory.getLogger(DataViewerConfigurations.class);		
	
	@Bean
	public RestHighLevelClient client() {
		
		RestHighLevelClient client = null;
		try
		{
			client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)));
			return client;
		}
		catch (Exception e)
		{
			logger.info("Failed initiate client");
		}
		return client;
	}

}
