package com.azhuoinfo.pshare.model;

import mobi.cangol.mobile.parser.Element;

/**
 * Created by Azhuo on 2015/9/29.
 */
public class CustomerInfo {
    /**
     *
     */
    private String token;
    private String customer_id;
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
    private String customer_password;

    public CustomerInfo(){

    }
    public CustomerInfo(String token, String customer_id,
                        String customer_point, String customer_nickname,
                        String customer_head, String customer_sex, String created_at,
                        String customer_level, String customer_job, String customer_region,
                        String customer_mobile, String customer_email,String customer_password) {
        this.token = token;
        this.customer_id = customer_id;
        this.customer_point = customer_point;
        this.customer_nickname = customer_nickname;
        this.customer_head = customer_head;
        this.customer_sex = customer_sex;
        this.created_at = created_at;
        this.customer_level = customer_level;
        this.customer_job = customer_job;
        this.customer_region = customer_region;
        this.customer_mobile = customer_mobile;
        this.customer_email = customer_email;
        this.customer_password=customer_password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomer_Id() {
        return customer_id;
    }

    public void setCustomer_Id(String userId) {
        this.customer_id = userId;
    }

    public String getCustomer_point() {
        return customer_point;
    }

    public void setCustomer_point(String customer_point) {
        this.customer_point = customer_point;
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

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }


    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }
    @Override
    public String toString() {
        return "CustomerInfo{" +
                "token='" + token + '\'' +
                ", userId='" + customer_id + '\'' +
                ", customer_point='" + customer_point + '\'' +
                ", customer_nickname='" + customer_nickname + '\'' +
                ", customer_head='" + customer_head + '\'' +
                ", customer_sex='" + customer_sex + '\'' +
                ", created_at='" + created_at + '\'' +
                ", customer_level='" + customer_level + '\'' +
                ", customer_job='" + customer_job + '\'' +
                ", customer_region='" + customer_region + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", customer_password='" + customer_password + '\'' +
                '}';
    }
}
