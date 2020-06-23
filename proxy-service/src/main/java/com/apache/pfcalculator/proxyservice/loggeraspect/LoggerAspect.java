package com.apache.pfcalculator.proxyservice.loggeraspect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apache.pfcalculator.proxyservice.model.CustomeRequest;
import com.apache.pfcalculator.proxyservice.service.LoggerServiceImple;
import com.apache.pfcalculator.proxyservice.utils.CommonUtils;
import com.netflix.zuul.context.RequestContext;

@Component
@Aspect
public class LoggerAspect {
	
	@Autowired
	LoggerServiceImple loggerService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
			
	@Around(value = "execution(* com.apache.pfcalculator.proxyservice.utils.FilterUtils.processRequestContext(..))")
	public void logTheRequestAspect(ProceedingJoinPoint joinPoint) throws Throwable
	{
		RequestContext requestContext = RequestContext.getCurrentContext();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		CustomeRequest customRequest = new CustomeRequest();
		customRequest.setInTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
		//The redirected service should be name of the service to which the request is redirected, a function will be 
		//soon added
		customRequest.setRedirectedService(CommonUtils.findServiceBasedOnUri(requestContext.getRequest().getRequestURI()));
		//Continuing the pre filter call
		joinPoint.proceed();
		
		//After the pre filter ended
		requestContext = RequestContext.getCurrentContext();		
		customRequest.setRequestId(requestContext.getZuulRequestHeaders().get("requestid"));
		requestContext.getRequest().setAttribute("customRequest", customRequest);		
	}
	
	@Around(value = "execution(* com.apache.pfcalculator.proxyservice.utils.FilterUtils.processFinishRequestContext(..))")
	public void logTheRequestAspectAfterCompletion(ProceedingJoinPoint joinPoint) throws Throwable
	{
		RequestContext requestContext = RequestContext.getCurrentContext();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		CustomeRequest customRequest = (CustomeRequest)requestContext.getRequest().getAttribute("customRequest");
		customRequest.setOutTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
		customRequest.setStatusCode(requestContext.getResponseStatusCode());		
		joinPoint.proceed();
		requestContext = RequestContext.getCurrentContext();
		if(customRequest.getStatusCode()>=400)
		{
			customRequest.setErrorMessage(IOUtils.toString(requestContext.getResponseDataStream()));
		}		
		else
		{			
			customRequest.setMessage(IOUtils.toString(requestContext.getResponseDataStream()));
		}		
		logger.info("Indexing the request to elastic search - {}", customRequest.getRequestId());
		loggerService.createLoggerForRequest(customRequest);
	}
}
