package com.apache.pfcalculator.datauploadservice.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfiguartions {
	
	@Value("${elasticsearch.host}")
	private String elasticsearchHost;
	
	@Value("${elasticsearch.port}")
	private String elasticsearchPort;
	
	Logger logger = LoggerFactory.getLogger(UploadConfiguartions.class);		
	
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
