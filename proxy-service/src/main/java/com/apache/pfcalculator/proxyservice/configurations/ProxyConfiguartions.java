package com.apache.pfcalculator.proxyservice.configurations;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.apache.pfcalculator.proxyservice.loggeraspect.LoggerAspect;

@Configuration
@EnableAspectJAutoProxy
public class ProxyConfiguartions {
		
	@Value("${elasticsearch.host}")
	private String elasticsearchHost;
	
	@Value("${elasticsearch.port}")
	private String elasticsearchPort;
	
	Logger logger = LoggerFactory.getLogger(ProxyConfiguartions.class);		
	
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
	
//	@Bean
//	public LoggerAspect loggerAspect()
//	{
//		return new LoggerAspect();
//	}
}
