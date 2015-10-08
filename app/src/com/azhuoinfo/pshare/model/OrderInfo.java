package com.azhuoinfo.pshare.model;

/**
 * Created by Azhuo on 2015/9/30.
 */
public class OrderInfo {

    private String order_sn;
    private String order_unit_fee;
    private String order_paid_amount;
    private String order_total_fee;
    private String order_plan_begin;

    private String order_plan_end;
    private String order_actual_begin;
    private String order_actual_end;
    private String order_path;
    private String order_state;

    private String create_at;
    private String customer_id;
    private String customer_nickname;
    private String customer_head;
    private String customer_sex;
    private String customer_name;
    private String customer_age;
    private String customer_mobile;
    private String customer_email;

    private String parking_id;
    private String parking_name;
    private String parking_region;
    private String parking_latitude;
    private String parking_longitude;
    private String parker_id;
    private String parker_name;
    private String parker_mobile;
    private String parker_cardid;

    private String car_number;
    private String car_brand;
    private String car_color;
    private String car_buy_date;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "order_sn='" + order_sn + '\'' +
                ", order_unit_fee='" + order_unit_fee + '\'' +
                ", order_paid_amount='" + order_paid_amount + '\'' +
                ", order_total_fee='" + order_total_fee + '\'' +
                ", order_plan_begin='" + order_plan_begin + '\'' +
                ", order_plan_end='" + order_plan_end + '\'' +
                ", order_actual_begin='" + order_actual_begin + '\'' +
                ", order_actual_end='" + order_actual_end + '\'' +
                ", order_path='" + order_path + '\'' +
                ", order_state='" + order_state + '\'' +
                ", create_at='" + create_at + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", customer_nickname='" + customer_nickname + '\'' +
                ", customer_head='" + customer_head + '\'' +
                ", customer_sex='" + customer_sex + '\'' +
                ", customer_name='" + customer_name + '\'' +
                ", customer_age='" + customer_age + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", parking_id='" + parking_id + '\'' +
                ", parking_name='" + parking_name + '\'' +
                ", parking_region='" + parking_region + '\'' +
                ", parking_latitude='" + parking_latitude + '\'' +
                ", parking_longitude='" + parking_longitude + '\'' +
                ", parker_id='" + parker_id + '\'' +
                ", parker_name='" + parker_name + '\'' +
                ", parker_mobile='" + parker_mobile + '\'' +
                ", parker_cardid='" + parker_cardid + '\'' +
                ", car_number='" + car_number + '\'' +
                ", car_brand='" + car_brand + '\'' +
                ", car_color='" + car_color + '\'' +
                ", car_buy_date='" + car_buy_date + '\'' +
                '}';
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_unit_fee() {
        return order_unit_fee;
    }

    public void setOrder_unit_fee(String order_unit_fee) {
        this.order_unit_fee = order_unit_fee;
    }

    public String getOrder_paid_amount() {
        return order_paid_amount;
    }
    public void setOrder_paid_amount(String order_paid_amount) {
        this.order_paid_amount = order_paid_amount;
    }
    public String getOrder_total_fee() {
        return order_total_fee;
    }
    public void setOrder_total_fee(String order_total_fee) {
        this.order_total_fee = order_total_fee;
    }
    public String getOrder_plan_begin() {
        return order_plan_begin;
    }
    public void setOrder_plan_begin(String order_plan_begin) {
        this.order_plan_begin = order_plan_begin;
    }
    public String getOrder_plan_end() {
        return order_plan_end;
    }
    public void setOrder_plan_end(String order_plan_end) {
        this.order_plan_end = order_plan_end;
    }
    public String getOrder_actual_begin() {
        return order_actual_begin;
    }
    public void setOrder_actual_begin(String order_actual_begin) {
        this.order_actual_begin = order_actual_begin;
    }
    public String getOrder_actual_end() {
        return order_actual_end;
    }

    public void setOrder_actual_end(String order_actual_end) {
        this.order_actual_end = order_actual_end;
    }

    public String getOrder_path() {
        return order_path;
    }

    public void setOrder_path(String order_path) {
        this.order_path = order_path;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_age() {
        return customer_age;
    }

    public void setCustomer_age(String customer_age) {
        this.customer_age = customer_age;
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

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getParking_region() {
        return parking_region;
    }

    public void setParking_region(String parking_region) {
        this.parking_region = parking_region;
    }

    public String getParking_latitude() {
        return parking_latitude;
    }

    public void setParking_latitude(String parking_latitude) {
        this.parking_latitude = parking_latitude;
    }

    public String getParking_longitude() {
        return parking_longitude;
    }

    public void setParking_longitude(String parking_longitude) {
        this.parking_longitude = parking_longitude;
    }

    public String getParker_id() {
        return parker_id;
    }

    public void setParker_id(String parker_id) {
        this.parker_id = parker_id;
    }

    public String getParker_name() {
        return parker_name;
    }

    public void setParker_name(String parker_name) {
        this.parker_name = parker_name;
    }

    public String getParker_mobile() {
        return parker_mobile;
    }

    public void setParker_mobile(String parker_mobile) {
        this.parker_mobile = parker_mobile;
    }

    public String getParker_cardid() {
        return parker_cardid;
    }

    public void setParker_cardid(String parker_cardid) {
        this.parker_cardid = parker_cardid;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }
    public OrderInfo(String order_sn, String order_unit_fee, String order_paid_amount,
                     String order_total_fee, String order_plan_begin, String order_plan_end,
                     String order_actual_begin, String order_actual_end, String order_path,
                     String order_state, String create_at, String customer_id, String customer_nickname,
                     String customer_head, String customer_sex, String customer_name, String customer_age,
                     String customer_mobile, String customer_email, String parking_id, String parking_name,
                     String parking_region, String parking_latitude, String parking_longitude, String parker_id,
                     String parker_name, String parker_mobile, String parker_cardid, String car_number, String car_brand,
                     String car_color, String car_buy_date) {
        this.order_sn = order_sn;
        this.order_unit_fee = order_unit_fee;
        this.order_paid_amount = order_paid_amount;
        this.order_total_fee = order_total_fee;
        this.order_plan_begin = order_plan_begin;
        this.order_plan_end = order_plan_end;
        this.order_actual_begin = order_actual_begin;
        this.order_actual_end = order_actual_end;
        this.order_path = order_path;
        this.order_state = order_state;
        this.create_at = create_at;
        this.customer_id = customer_id;
        this.customer_nickname = customer_nickname;
        this.customer_head = customer_head;
        this.customer_sex = customer_sex;
        this.customer_name = customer_name;
        this.customer_age = customer_age;
        this.customer_mobile = customer_mobile;
        this.customer_email = customer_email;
        this.parking_id = parking_id;
        this.parking_name = parking_name;
        this.parking_region = parking_region;
        this.parking_latitude = parking_latitude;
        this.parking_longitude = parking_longitude;
        this.parker_id = parker_id;
        this.parker_name = parker_name;
        this.parker_mobile = parker_mobile;
        this.parker_cardid = parker_cardid;
        this.car_number = car_number;
        this.car_brand = car_brand;
        this.car_color = car_color;
        this.car_buy_date = car_buy_date;
    }

}
