package com.b2b.passwork.Model.Login;

import com.google.gson.annotations.SerializedName;

public class Profile{

	@SerializedName("role")
	private String role;

	@SerializedName("id")
	private String id;

	@SerializedName("balance_meetings")
	private int balanceMeetings;

	@SerializedName("corporate_id")
	private String corporateId;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("corporate_name")
	private String corporateName;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("balance_credits")
	private int balanceCredits;

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setBalanceMeetings(int balanceMeetings){
		this.balanceMeetings = balanceMeetings;
	}

	public int getBalanceMeetings(){
		return balanceMeetings;
	}

	public void setCorporateId(String corporateId){
		this.corporateId = corporateId;
	}

	public String getCorporateId(){
		return corporateId;
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

	public void setCorporateName(String corporateName){
		this.corporateName = corporateName;
	}

	public String getCorporateName(){
		return corporateName;
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

	public void setBalanceCredits(int balanceCredits){
		this.balanceCredits = balanceCredits;
	}

	public int getBalanceCredits(){
		return balanceCredits;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}