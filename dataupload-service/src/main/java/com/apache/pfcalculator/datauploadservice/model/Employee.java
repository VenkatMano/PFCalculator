package com.apache.pfcalculator.datauploadservice.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Employee {
	
		
	private String id = UUID.randomUUID().toString();
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
	
	private String role;
	
	private String aadharId;
	
	private String panCardId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern="dd-MM-yyyy" )
	private Date pfStartDate;
	
	private Long salary;
	
	private Long totalPfInAccount;

	public String getFirstName() {
		return firstName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAadharId() {
		return aadharId;
	}

	public void setAadharId(String aadharId) {
		this.aadharId = aadharId;
	}

	public String getPanCardId() {
		return panCardId;
	}

	public void setPanCardId(String panCardId) {
		this.panCardId = panCardId;
	}

	public Date getPfStartDate() {
		return pfStartDate;
	}

	public void setPfStartDate(Date pfStartDate) {
		this.pfStartDate = pfStartDate;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public Long getTotalPfInAccount() {
		return totalPfInAccount;
	}

	public void setTotalPfInAccount(Long totalPfInAccount) {
		this.totalPfInAccount = totalPfInAccount;
	}	

}
