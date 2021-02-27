package com.b2b.passwork.Model.Employee;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EmployeeResponse{

	@SerializedName("employees")
	private List<EmployeesItem> employees;

	@SerializedName("status")
	private int status;

	public void setEmployees(List<EmployeesItem> employees){
		this.employees = employees;
	}

	public List<EmployeesItem> getEmployees(){
		return employees;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}