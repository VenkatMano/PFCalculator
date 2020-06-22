package com.apache.pfcalculator.proxyservice.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apache.pfcalculator.proxyservice.filters.PreRequestFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class FilterUtils {
	
	public void processRequestContext(RequestContext context)
	{		
		context.addZuulRequestHeader("requestId", UUID.randomUUID().toString());
	}
	
	public void processFinishRequestContext(RequestContext context)
	{		
		context.getRequest().setAttribute("completed", true);
	}
	
	

}
