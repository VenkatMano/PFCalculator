package com.apache.pfcalculator.dataviewerservice.model;

public class PfAndSalaryModel {
	
	private String pfStartDate;
	private Long aggregatedDocumentCount;
	private String averageSalaryPerRole;
	
	
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
	public String getAverageSalaryPerRole() {
		return averageSalaryPerRole;
	}
	public void setAverageSalaryPerRole(String averageSalaryPerRole) {
		this.averageSalaryPerRole = averageSalaryPerRole;
	}

}
