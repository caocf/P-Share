package com.azhuoinfo.pshare.model;

import java.io.Serializable;

/**
 * Created by Azhuo on 2015/10/9.
 */
public class UnfinishedOrderInfo implements Serializable{
    private static final long serialVersionUID = 0L;
    private String  car_brand;//": "",
    private String parker_id_back;//": "0000-00-00 00:00:00",
    private String parker_carid_back;//": "",
    private String car_number;//": "辽A666666",
    private String  create_at;//": "2015-10-15 11:35:20",
    private String parker_mobile_back;//": "",
    private String  customer_email;//": "",
    private String  customer_head;//": "",
    private String  customer_id;//": "39f9f4f0-8378-4e7b-8241-32b7859d47c2",
    private String customer_mobile;//": "18301969764",
    private String parker_name_back;//": "",
    private String customer_nickname;//": "",
    private String customer_sex;//": "",
    private String order_actual_begin_start;//": "2015-10-15 14:06:59",
    private String order_actual_end_stop;//": "0000-00-00 00:00:00",
    private String  order_duration;//": 0,
    private String  order_id;//": "3345e536-7d32-4680-be62-41d5378ec005",
    private String  order_img_count;//": "",
    private String  order_path;//": "http://139.196.12.103/upload/20151015/3345e536-7d32-4680-be62-41d5378ec005/getcar/bit19.jpg,http://139.196.12.103/upload/20151015/3345e536-7d32-4680-be62-41d5378ec005/getcar/bit22.jpg,http://139.196.12.103/upload/20151015/3345e536-7d32-4680-be62-41d5378ec005/getcar/bit18.jpg,http://139.196.12.103/upload/20151015/3345e536-7d32-4680-be62-41d5378ec005/getcar/bit17.jpg,",
    private String  order_plan_begin;//": "2015-10-15 11:35:20",
    private String  order_plan_end;//": "2015-10-15 14:06",
    private String  order_state;//": 3,
    private String   order_total_fee;//": 0,
    private String   order_unit_fee;//": 34,
    private String   parker_cardid;//": "323232323",
    private String   parker_id;//": "2",
    private String   parker_level;//": "",
    private String   parker_mobile;//": "10086",
    private String   parker_name;//": "那家停车场",
    private String   parking_address;//": "后变",
    private String   parking_area;//": "",
    private String   parking_id;//": "3",
    private String   parking_img_count;//": "",
    private String   parking_latitude;//": "",
    private String   parking_longitude;//": "",
    private String   parking_name;//": "那家停车场",
    private String   parking_path;//": "",
    private String  updated_at;//": "2015-10-15 14:06:59"

    public UnfinishedOrderInfo() {
    }

    public UnfinishedOrderInfo(String car_brand, String parker_id_back, String parker_carid_back, String car_number, String create_at, String parker_mobile_back, String customer_email, String customer_head, String customer_id, String customer_mobile, String parker_name_back, String customer_nickname, String customer_sex, String order_actual_begin_start, String order_actual_end_stop, String order_duration, String order_id, String order_img_count, String order_path, String order_plan_begin, String order_plan_end, String order_state, String order_total_fee, String order_unit_fee, String parker_cardid, String parker_id, String parker_level, String parker_mobile, String parker_name, String parking_address, String parking_area, String parking_id, String parking_img_count, String parking_latitude, String parking_longitude, String parking_name, String parking_path, String updated_at) {
        this.car_brand = car_brand;
        this.parker_id_back = parker_id_back;
        this.parker_carid_back = parker_carid_back;
        this.car_number = car_number;
        this.create_at = create_at;
        this.parker_mobile_back = parker_mobile_back;
        this.customer_email = customer_email;
        this.customer_head = customer_head;
        this.customer_id = customer_id;
        this.customer_mobile = customer_mobile;
        this.parker_name_back = parker_name_back;
        this.customer_nickname = customer_nickname;
        this.customer_sex = customer_sex;
        this.order_actual_begin_start = order_actual_begin_start;
        this.order_actual_end_stop = order_actual_end_stop;
        this.order_duration = order_duration;
        this.order_id = order_id;
        this.order_img_count = order_img_count;
        this.order_path = order_path;
        this.order_plan_begin = order_plan_begin;
        this.order_plan_end = order_plan_end;
        this.order_state = order_state;
        this.order_total_fee = order_total_fee;
        this.order_unit_fee = order_unit_fee;
        this.parker_cardid = parker_cardid;
        this.parker_id = parker_id;
        this.parker_level = parker_level;
        this.parker_mobile = parker_mobile;
        this.parker_name = parker_name;
        this.parking_address = parking_address;
        this.parking_area = parking_area;
        this.parking_id = parking_id;
        this.parking_img_count = parking_img_count;
        this.parking_latitude = parking_latitude;
        this.parking_longitude = parking_longitude;
        this.parking_name = parking_name;
        this.parking_path = parking_path;
        this.updated_at = updated_at;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getParker_id_back() {
        return parker_id_back;
    }

    public void setParker_id_back(String parker_id_back) {
        this.parker_id_back = parker_id_back;
    }

    public String getParker_carid_back() {
        return parker_carid_back;
    }

    public void setParker_carid_back(String parker_carid_back) {
        this.parker_carid_back = parker_carid_back;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getParker_mobile_back() {
        return parker_mobile_back;
    }

    public void setParker_mobile_back(String parker_mobile_back) {
        this.parker_mobile_back = parker_mobile_back;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_head() {
        return customer_head;
    }

    public void setCustomer_head(String customer_head) {
        this.customer_head = customer_head;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getParker_name_back() {
        return parker_name_back;
    }

    public void setParker_name_back(String parker_name_back) {
        this.parker_name_back = parker_name_back;
    }

    public String getCustomer_nickname() {
        return customer_nickname;
    }

    public void setCustomer_nickname(String customer_nickname) {
        this.customer_nickname = customer_nickname;
    }

    public String getCustomer_sex() {
        return customer_sex;
    }

    public void setCustomer_sex(String customer_sex) {
        this.customer_sex = customer_sex;
    }

    public String getOrder_actual_begin_start() {
        return order_actual_begin_start;
    }

    public void setOrder_actual_begin_start(String order_actual_begin_start) {
        this.order_actual_begin_start = order_actual_begin_start;
    }

    public String getOrder_actual_end_stop() {
        return order_actual_end_stop;
    }

    public void setOrder_actual_end_stop(String order_actual_end_stop) {
        this.order_actual_end_stop = order_actual_end_stop;
    }

    public String getOrder_duration() {
        return order_duration;
    }

    public void setOrder_duration(String order_duration) {
        this.order_duration = order_duration;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_img_count() {
        return order_img_count;
    }

    public void setOrder_img_count(String order_img_count) {
        this.order_img_count = order_img_count;
    }

    public String getOrder_path() {
        return order_path;
    }

    public void setOrder_path(String order_path) {
        this.order_path = order_path;
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

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_total_fee() {
        return order_total_fee;
    }

    public void setOrder_total_fee(String order_total_fee) {
        this.order_total_fee = order_total_fee;
    }

    public String getOrder_unit_fee() {
        return order_unit_fee;
    }

    public void setOrder_unit_fee(String order_unit_fee) {
        this.order_unit_fee = order_unit_fee;
    }

    public String getParker_cardid() {
        return parker_cardid;
    }

    public void setParker_cardid(String parker_cardid) {
        this.parker_cardid = parker_cardid;
    }

    public String getParker_id() {
        return parker_id;
    }

    public void setParker_id(String parker_id) {
        this.parker_id = parker_id;
    }

    public String getParker_level() {
        return parker_level;
    }

    public void setParker_level(String parker_level) {
        this.parker_level = parker_level;
    }

    public String getParker_mobile() {
        return parker_mobile;
    }

    public void setParker_mobile(String parker_mobile) {
        this.parker_mobile = parker_mobile;
    }

    public String getParker_name() {
        return parker_name;
    }

    public void setParker_name(String parker_name) {
        this.parker_name = parker_name;
    }

    public String getParking_address() {
        return parking_address;
    }

    public void setParking_address(String parking_address) {
        this.parking_address = parking_address;
    }

    public String getParking_area() {
        return parking_area;
    }

    public void setParking_area(String parking_area) {
        this.parking_area = parking_area;
    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getParking_img_count() {
        return parking_img_count;
    }

    public void setParking_img_count(String parking_img_count) {
        this.parking_img_count = parking_img_count;
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

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getParking_path() {
        return parking_path;
    }

    public void setParking_path(String parking_path) {
        this.parking_path = parking_path;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "UnfinishedOrderInfo{" +
                "car_brand='" + car_brand + '\'' +
                ", parker_id_back='" + parker_id_back + '\'' +
                ", parker_carid_back='" + parker_carid_back + '\'' +
                ", car_number='" + car_number + '\'' +
                ", create_at='" + create_at + '\'' +
                ", parker_mobile_back='" + parker_mobile_back + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", customer_head='" + customer_head + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", customer_mobile='" + customer_mobile + '\'' +
                ", parker_name_back='" + parker_name_back + '\'' +
                ", customer_nickname='" + customer_nickname + '\'' +
                ", customer_sex='" + customer_sex + '\'' +
                ", order_actual_begin_start='" + order_actual_begin_start + '\'' +
                ", order_actual_end_stop='" + order_actual_end_stop + '\'' +
                ", order_duration='" + order_duration + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_img_count='" + order_img_count + '\'' +
                ", order_path='" + order_path + '\'' +
                ", order_plan_begin='" + order_plan_begin + '\'' +
                ", order_plan_end='" + order_plan_end + '\'' +
                ", order_state='" + order_state + '\'' +
                ", order_total_fee='" + order_total_fee + '\'' +
                ", order_unit_fee='" + order_unit_fee + '\'' +
                ", parker_cardid='" + parker_cardid + '\'' +
                ", parker_id='" + parker_id + '\'' +
                ", parker_level='" + parker_level + '\'' +
                ", parker_mobile='" + parker_mobile + '\'' +
                ", parker_name='" + parker_name + '\'' +
                ", parking_address='" + parking_address + '\'' +
                ", parking_area='" + parking_area + '\'' +
                ", parking_id='" + parking_id + '\'' +
                ", parking_img_count='" + parking_img_count + '\'' +
                ", parking_latitude='" + parking_latitude + '\'' +
                ", parking_longitude='" + parking_longitude + '\'' +
                ", parking_name='" + parking_name + '\'' +
                ", parking_path='" + parking_path + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
