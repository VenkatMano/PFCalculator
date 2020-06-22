package com.apache.pfcalculator.proxyservice.filters;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apache.pfcalculator.proxyservice.utils.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreRequestFilter extends ZuulFilter{

	@Autowired
	FilterUtils filterUtils;
	
	@Override
	public Object run() throws ZuulException {		
		filterUtils.processRequestContext(RequestContext.getCurrentContext());
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}
}
