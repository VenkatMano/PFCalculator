package com.apache.pfcalculator.proxyservice.model;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

public class CustomeRequest {
			
	private String requestId;
	
	private String redirectedService;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-YYYY hh:mm:ss.SSS")
	private Date inTime;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-YYYY hh:mm:ss.SSS")
	private Date outTime;
	
	private int statusCode;
	
	private String message;
	
	private String errorMessage;
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRedirectedService() {
		return redirectedService;
	}

	public void setRedirectedService(String redirectedService) {
		this.redirectedService = redirectedService;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
