package com.b2b.passwork.Model.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("profile")
	private Profile profile;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	@SerializedName("token")
	private String token;

	public void setProfile(Profile profile){
		this.profile = profile;
	}

	public Profile getProfile(){
		return profile;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}
}