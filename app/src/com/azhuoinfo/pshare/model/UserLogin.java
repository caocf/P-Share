package com.azhuoinfo.pshare.model;


import mobi.cangol.mobile.parser.Element;

public class UserLogin {
    @Element("customer_id")
	private String token;
	private String userId;
	private String customer_point;
	private String customer_nickname;
	private String customer_head;
	private String customer_sex;
	//注册时间
	private String created_at;
	//等级
	private String customer_level;
	private String customer_job;
	private String customer_region;
	private String customer_mobile;
	private String customer_email;

	public UserLogin(){

	}

	public UserLogin(String userId, String userPoint, String userNickName, String userHead, String userSex, String userAt, String userLevel, String userJob, String userRegion, String userMobile, String userEmail, String token) {
		this.userId = userId;
		this.customer_point = userPoint;
		this.customer_nickname = userNickName;
		this.customer_head = userHead;
		this.customer_sex = userSex;
		this.created_at= userAt;
		this.customer_level = userLevel;
		this.customer_job = userJob;
		this.customer_region = userRegion;
		this.customer_mobile = userMobile;
		this.customer_email = userEmail;
		this.token = token;
	}

	public String getCustomer_mobile() {
		return customer_mobile;
	}

	public void setCustomer_mobile(String customer_mobile) {
		this.customer_mobile = customer_mobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomer_nickname() {
		return customer_nickname;
	}

	public void setCustomer_nickname(String customer_nickname) {
		this.customer_nickname = customer_nickname;
	}

	public String getCustomer_head() {
		return customer_head;
	}

	public void setCustomer_head(String customer_head) {
		this.customer_head = customer_head;
	}

	public String getCustomer_sex() {
		return customer_sex;
	}

	public void setCustomer_sex(String customer_sex) {
		this.customer_sex = customer_sex;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getCustomer_level() {
		return customer_level;
	}

	public void setCustomer_level(String customer_level) {
		this.customer_level = customer_level;
	}

	public String getCustomer_point() {
		return customer_point;
	}

	public void setCustomer_point(String customer_point) {
		this.customer_point = customer_point;
	}

	public String getCustomer_job() {
		return customer_job;
	}

	public void setCustomer_job(String customer_job) {
		this.customer_job = customer_job;
	}

	public String getCustomer_region() {
		return customer_region;
	}

	public void setCustomer_region(String customer_region) {
		this.customer_region = customer_region;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
