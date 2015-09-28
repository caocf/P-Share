package com.azhuoinfo.pshare.model;


import mobi.cangol.mobile.parser.Element;

public class UserAuth {
    @Element("customer_id")
	private String userId;
	private String token;
	public UserAuth(){
	}
	public UserAuth(String userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
	}



	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
