package com.azhuoinfo.pshare.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Azhuo on 2015/10/13.
 */
public class OrderList implements Parcelable {

    private String car_brand;//": "",
    private String car_buy_date;//": "0000-00-00 00:00:00",
    private String car_color;//": "",
    private String car_number;//": "辽A666666",
    private String create_at;//": "2015-10-15 11:35:20",
    private String customer_age;//": "",
    private String customer_email;//": "",
    private String customer_head;//": "",
    private String customer_id;//": "39f9f4f0-8378-4e7b-8241-32b7859d47c2",
    private String customer_mobile;//": "18301969764",
    private String customer_name;//": "",
    private String customer_nickname;//": "",
    private String customer_sex;//": "",
    private String order_actual_begin_start;//": "2015-10-15 14:06:59",
    private String order_actual_end_stop;//": "2015-10-15 15:07:47",
    private String order_duration;//": 0,
    private String order_id;//": "3345e536-7d32-4680-be62-41d5378ec005",
    private String order_img_count;//": "",
    private String order_path;//": "http:\/\/139.196.12.103\/upload\/20151015\/3345e536-7d32-4680-be62-41d5378ec005\/getcar\/bit19.jpg,http:\/\/139.196.12.103\/upload\/20151015\/3345e536-7d32-4680-be62-41d5378ec005\/getcar\/bit22.jpg,http:\/\/139.196.12.103\/upload\/20151015\/3345e536-7d32-4680-be62-41d5378ec005\/getcar\/bit18.jpg,http:\/\/139.196.12.103\/upload\/20151015\/3345e536-7d32-4680-be62-41d5378ec005\/getcar\/bit17.jpg,",
    private String order_plan_begin;//": "2015-10-15 11:35:20",
    private String order_plan_end;//": "2015-10-15 14:06",
    private String order_state;//": 5,
    private String order_total_fee;//": 0,
    private String order_unit_fee;//": 34,
    private String parker_cardid;//": "323232323",
    private String parker_id;//": "2",
    private String parker_level;//": "",
    private String parker_mobile;//": "10086",
    private String parker_name;//": "那家停车场",
    private String parking_address;//": "后变",
    private String parking_area;//": "",
    private String parking_id;//": "3",
    private String parking_img_count;//": "",
    private String parking_latitude;//": "",
    private String parking_longitude;//": "",
    private String parking_name;//": "那家停车场",
    private String parking_path;//": "http:\/\/139.196.12.103\/upload\/20151015\/3345e536-7d32-4680-be62-41d5378ec005\/parked\/bit17.jpg,",
    private String updated_at;//": "2015-10-15 15:07:47"*/


    public OrderList() {
    }

    public OrderList(String car_brand, String car_buy_date, String car_color, String car_number, String create_at, String customer_age, String customer_email, String customer_head, String customer_id, String customer_mobile, String customer_name, String customer_nickname, String customer_sex, String order_actual_begin_start, String order_actual_end_stop, String order_duration, String order_id, String order_img_count, String order_path, String order_plan_begin, String order_plan_end, String order_state, String order_total_fee, String order_unit_fee, String parker_cardid, String parker_id, String parker_level, String parker_mobile, String parker_name, String parking_address, String parking_area, String parking_id, String parking_img_count, String parking_latitude, String parking_longitude, String parking_name, String parking_path, String updated_at) {
        this.car_brand = car_brand;
        this.car_buy_date = car_buy_date;
        this.car_color = car_color;
        this.car_number = car_number;
        this.create_at = create_at;
        this.customer_age = customer_age;
        this.customer_email = customer_email;
        this.customer_head = customer_head;
        this.customer_id = customer_id;
        this.customer_mobile = customer_mobile;
        this.customer_name = customer_name;
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

    protected OrderList(Parcel in) {
        parking_path=in.readString();
        parking_img_count=in.readString();
        parking_area=in.readString();
        parking_area=in.readString();
        order_img_count=in.readString();
        updated_at=in.readString();
        parking_address=in.readString();
        order_id = in.readString();
        order_unit_fee = in.readString();
        order_total_fee = in.readString();
        order_plan_begin = in.readString();
        order_plan_end = in.readString();
        order_actual_begin_start = in.readString();
        order_actual_begin_start = in.readString();
        order_path=in.readString();
        order_state=in.readString();
        create_at=in.readString();
        parking_id = in.readString();
        parking_name=in.readString();
        parking_latitude = in.readString();
        parking_longitude = in.readString();
        parker_id = in.readString();
        parker_name = in.readString();
        parker_mobile = in.readString();
        parker_cardid = in.readString();
        parker_level=in.readString();
        car_brand = in.readString();
        car_color = in.readString();
        car_number = in.readString();
        car_buy_date = in.readString();
        customer_id = in.readString();
        customer_nickname = in.readString();
        customer_head = in.readString();
        customer_sex = in.readString();
        customer_name = in.readString();
        customer_age = in.readString();
        customer_mobile = in.readString();
        customer_email = in.readString();

    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_buy_date() {
        return car_buy_date;
    }

    public void setCar_buy_date(String car_buy_date) {
        this.car_buy_date = car_buy_date;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
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

    public String getCustomer_age() {
        return customer_age;
    }

    public void setCustomer_age(String customer_age) {
        this.customer_age = customer_age;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public static final Creator<OrderList> CREATOR = new Creator<OrderList>() {
        @Override
        public OrderList createFromParcel(Parcel in) {
            return new OrderList(in);
        }

        @Override
        public OrderList[] newArray(int size) {
            return new OrderList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(updated_at);
        dest.writeString(parking_path);
        dest.writeString(parking_img_count);
        dest.writeString(parking_area);
        dest.writeString(order_img_count);
        dest.writeString(parking_address);
        dest.writeString(order_id);
        dest.writeString(order_unit_fee);
        dest.writeString(order_total_fee);
        dest.writeString(order_plan_begin);
        dest.writeString(order_plan_end);
        dest.writeString(order_actual_begin_start);
        dest.writeString(order_actual_end_stop);
        dest.writeString(parking_id);
        dest.writeString(order_path);
        dest.writeString(parking_latitude);
        dest.writeString(parking_longitude);
        dest.writeString(parking_name);
        dest.writeString(order_duration);
        dest.writeString(order_state);
        dest.writeString(create_at);
        dest.writeString(parker_id);
        dest.writeString(parker_name);
        dest.writeString(parker_mobile);
        dest.writeString(parker_cardid);
        dest.writeString(parker_level);
        dest.writeString(car_brand);
        dest.writeString(car_color);
        dest.writeString(car_number);
        dest.writeString(car_buy_date);
        dest.writeString(customer_id);
        dest.writeString(customer_nickname);
        dest.writeString(customer_head);
        dest.writeString(customer_sex);
        dest.writeString(customer_name);
        dest.writeString(customer_age);
        dest.writeString(customer_mobile);
        dest.writeString(customer_email);

    }
}
