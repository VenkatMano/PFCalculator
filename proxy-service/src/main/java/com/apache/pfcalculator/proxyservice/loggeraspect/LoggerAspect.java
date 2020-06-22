package com.apache.pfcalculator.proxyservice.loggeraspect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.apache.pfcalculator.proxyservice.model.CustomeRequest;
import com.apache.pfcalculator.proxyservice.utils.CommonUtils;
import com.netflix.zuul.context.RequestContext;

@Component
@Aspect
public class LoggerAspect {
			
	@Around(value = "execution(* com.apache.pfcalculator.proxyservice.filters.PreRequestFilter.run())")
	public void logTheRequestAspect(ProceedingJoinPoint joinPoint) throws Throwable
	{
		RequestContext requestContext = RequestContext.getCurrentContext();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
		CustomeRequest customRequest = new CustomeRequest();
		customRequest.setInTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
		//The redirected service should be name of the service to which the request is redirected, a function will be 
		//soon added
		customRequest.setRedirectedService(CommonUtils.findServiceBasedOnUri(requestContext.getRequest().getRequestURI()));
		//Continuing the pre filter call
		joinPoint.proceed();
		
		//After the pre filter ended
		customRequest.setRequestId(requestContext.getRequest().getHeader("requestId"));
		requestContext.getRequest().setAttribute("customRequest", customRequest);		
	}
	
	@Around(value = "execution(* com.apache.pfcalculator.proxyservice.filters.PostRequestFilter.run())")
	public void logTheRequestAspectAfterCompletion(ProceedingJoinPoint joinPoint) throws Throwable
	{
		RequestContext requestContext = RequestContext.getCurrentContext();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
		CustomeRequest customRequest = (CustomeRequest)requestContext.getRequest().getAttribute("customRequest");
		customRequest.setOutTime(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
		customRequest.setStatusCode(requestContext.getResponseStatusCode());
		if(customRequest.getStatusCode()>=400)
		{
			customRequest.setErrorMessage(requestContext.getResponseBody());
		}		
		else
		{
			customRequest.setMessage(requestContext.getResponseBody());
		}
		joinPoint.proceed();
		//An elk based logging function should be called here
	}
}
