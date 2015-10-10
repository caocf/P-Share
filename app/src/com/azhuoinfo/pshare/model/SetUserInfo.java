package com.azhuoinfo.pshare.model;

/**
 * Created by Azhuo on 2015/9/29.
 */
public class SetUserInfo {

    private String customer_id;
    private String customer_point;
    private String customer_nickname;
    private String customer_head;
    private int customer_sex;
    //注册时间
    private String created_at;
    //等级
    private String customer_level;
    private String customer_job;
    private String customer_region;

    private String customer_mobile;
    private int customer_age;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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

    public int getCustomer_sex() {
        return customer_sex;
    }

    public void setCustomer_sex(int customer_sex) {
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

    public int getCustomer_age() {
        return customer_age;
    }

    public void setCustomer_age(int customer_age) {
        this.customer_age = customer_age;
    }

    private String customer_email;

    public SetUserInfo() {
    }

    public SetUserInfo(String customer_id, String customer_point, String customer_nickname, String customer_head, int customer_sex, String created_at, String customer_level, String customer_job, String customer_region, String customer_mobile, String customer_email, int customer_age) {
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
        this.customer_age = customer_age;
    }

    @Override
    public String toString() {
        return "SetUserInfo{" +
                "customer_id='" + customer_id + '\'' +
                ", customer_point='" + customer_point + '\'' +
                ", customer_nickname='" + customer_nickname + '\'' +
                ", customer_head='" + customer_head + '\'' +
                ", customer_sex=" + customer_sex +
                ", created_at='" + created_at + '\'' +
                ", customer_level='" + customer_level + '\'' +
                ", customer_job='" + customer_job + '\'' +
                ", customer_region='" + customer_region + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", customer_age=" + customer_age +
                ", customer_email='" + customer_email + '\'' +
                '}';
    }
}
