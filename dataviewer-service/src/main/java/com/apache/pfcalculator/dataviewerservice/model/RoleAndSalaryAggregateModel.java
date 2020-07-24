package com.apache.pfcalculator.dataviewerservice.model;

public class RoleAndSalaryAggregateModel {
	
	private String role;
	private Long aggregatedDocumentCount;
	private String averageSalaryPerRole;
	
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
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
