package com.apache.pfcalculator.dataviewerservice.model;

import java.util.List;

public class EmployeeAggregate {

	private Boolean sort;

	private String sortField;

	private Boolean basedOnRole;

	private Boolean basedOnCreationDate;

	private Boolean basedOnRoleAndSalary;
	
	private Boolean basedOnCreationDateAndSalary;

	public Boolean getSort() {
		return sort;
	}

	public void setSort(Boolean sort) {
		this.sort = sort;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public Boolean getBasedOnRole() {
		return basedOnRole;
	}

	public void setBasedOnRole(Boolean basedOnRole) {
		this.basedOnRole = basedOnRole;
	}

	public Boolean getBasedOnCreationDate() {
		return basedOnCreationDate;
	}

	public void setBasedOnCreationDate(Boolean basedOnCreationDate) {
		this.basedOnCreationDate = basedOnCreationDate;
	}

	public Boolean getBasedOnRoleAndSalary() {
		return basedOnRoleAndSalary;
	}

	public void setBasedOnRoleAndSalary(Boolean basedOnRoleAndSalary) {
		this.basedOnRoleAndSalary = basedOnRoleAndSalary;
	}

	public Boolean getBasedOnCreationDateAndSalary() {
		return basedOnCreationDateAndSalary;
	}

	public void setBasedOnCreationDateAndSalary(Boolean basedOnCreationDateAndSalary) {
		this.basedOnCreationDateAndSalary = basedOnCreationDateAndSalary;
	}
}
