package com.apache.pfcalculator.dataviewerservice.model;

public class PfModel {
	
	private String pfStartDate;
	private Long aggregatedDocumentCount;
	
	
	public String getPfStartDate() {
		return pfStartDate;
	}
	public void setPfStartDate(String pfStartDate) {
		this.pfStartDate = pfStartDate;
	}
	public Long getAggregatedDocumentCount() {
		return aggregatedDocumentCount;
	}
	public void setAggregatedDocumentCount(Long aggregatedDocumentCount) {
		this.aggregatedDocumentCount = aggregatedDocumentCount;
	}
	
}
