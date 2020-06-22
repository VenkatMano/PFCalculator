package com.apache.pfcalculator.proxyservice.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apache.pfcalculator.proxyservice.utils.FilterUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostRequestFilter extends ZuulFilter {

	@Autowired
	FilterUtils filterUtils;
	
	@Override
	public Object run() throws ZuulException {
		filterUtils.processFinishRequestContext(RequestContext.getCurrentContext());
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "post";
	}

}
