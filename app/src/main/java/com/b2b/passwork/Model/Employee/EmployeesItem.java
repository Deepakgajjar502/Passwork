package com.b2b.passwork.Model.Employee;

import com.google.gson.annotations.SerializedName;

public class EmployeesItem{

	@SerializedName("role")
	private String role;

	@SerializedName("code")
	private String code;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("password")
	private String password;

	@SerializedName("corporate_id")
	private int corporateId;

	@SerializedName("id")
	private int id;

	@SerializedName("designation")
	private Object designation;

	@SerializedName("admin_login_allowed")
	private String adminLoginAllowed;

	@SerializedName("department")
	private Object department;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("status")
	private String status;

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setCorporateId(int corporateId){
		this.corporateId = corporateId;
	}

	public int getCorporateId(){
		return corporateId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setDesignation(Object designation){
		this.designation = designation;
	}

	public Object getDesignation(){
		return designation;
	}

	public void setAdminLoginAllowed(String adminLoginAllowed){
		this.adminLoginAllowed = adminLoginAllowed;
	}

	public String getAdminLoginAllowed(){
		return adminLoginAllowed;
	}

	public void setDepartment(Object department){
		this.department = department;
	}

	public Object getDepartment(){
		return department;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}