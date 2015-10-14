package com.azhuoinfo.pshare.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Azhuo on 2015/10/13.
 */
public class OrderList implements Parcelable {

    private String order_sn; //:"订单唯一标识"
    private String order_unit_fee;//: "订单单位金额",
    private String order_paid_amount;//:"订单已支付金额",
    private String order_total_fee;//:"订单总金额",
    private String order_plan_begin;//": "计划开始时间",
    private String order_plan_end;//": "计划结束时间",
    private String order_actual_begin;//": "实际开始时间",
    private String order_actual_end;//": "实际结束时间",
    private String order_duration;//": "停车时长",
    private String order_path;//": "照片路径",
    private String order_state;//": "状态",
    private String create_at;//": "创建时间"

    private String parking_id;//": "停车场ID",
    private String parking_name;//": "停车场名称",
    private String parking_region;//": "停车场地区",
    private String parking_latitude;//": "停车场经度 ",
    private String parking_longitude;//": "停车场纬度",

    private String parker_id;//": "代泊员ID",
    private String parker_name;//": "代泊员名字",
    private String parker_mobile;//": "代泊员手机号",
    private String parker_cardid;//": "代泊员身份证号",
    private String parker_level;//职务

    private String car_brand;//": "车品牌型号",
    private String car_color;//": "车颜色",
    private String car_number;//": "车牌号 ",
    private String car_buy_date;//": "车购买时间",

    private String customer_id;//": "客户ID",
    private String customer_nickname;//": "客户昵称 ",
    private String customer_head;//": "客户头像 ",
    private String customer_sex;//": "客户性别 ",
    private String customer_name;//": "客户真实姓名 ",
    private String customer_age;//": "客户年龄 ",
    private String customer_mobile;//": "客户手机号 ",
    private String customer_email;//": "客户邮箱 ",

    public OrderList() {
    }

    public OrderList(String order_sn, String order_unit_fee, String order_paid_amount, String order_total_fee, String order_plan_begin, String order_plan_end, String order_actual_begin, String order_actual_end, String order_duration, String order_path, String order_state, String create_at, String parking_id, String parking_name, String parking_region, String parking_latitude, String parking_longitude, String parker_id, String parker_name, String parker_mobile, String parker_cardid, String parker_level,String car_brand, String car_color, String car_number, String car_buy_date, String customer_id, String customer_nickname, String customer_head, String customer_sex, String customer_name, String customer_age, String customer_mobile, String customer_email) {
        this.order_sn = order_sn;
        this.order_unit_fee = order_unit_fee;
        this.order_paid_amount = order_paid_amount;
        this.order_total_fee = order_total_fee;
        this.order_plan_begin = order_plan_begin;
        this.order_plan_end = order_plan_end;
        this.order_actual_begin = order_actual_begin;
        this.order_actual_end = order_actual_end;
        this.order_duration = order_duration;
        this.order_path = order_path;
        this.order_state = order_state;
        this.create_at = create_at;
        this.parking_id = parking_id;
        this.parking_name = parking_name;
        this.parking_region = parking_region;
        this.parking_latitude = parking_latitude;
        this.parking_longitude = parking_longitude;
        this.parker_id = parker_id;
        this.parker_name = parker_name;
        this.parker_mobile = parker_mobile;
        this.parker_cardid = parker_cardid;
        this.parker_level=parker_level;
        this.car_brand = car_brand;
        this.car_color = car_color;
        this.car_number = car_number;
        this.car_buy_date = car_buy_date;
        this.customer_id = customer_id;
        this.customer_nickname = customer_nickname;
        this.customer_head = customer_head;
        this.customer_sex = customer_sex;
        this.customer_name = customer_name;
        this.customer_age = customer_age;
        this.customer_mobile = customer_mobile;
        this.customer_email = customer_email;
    }
    protected OrderList(Parcel in) {
        order_sn = in.readString();
        order_unit_fee = in.readString();
        order_paid_amount = in.readString();
        order_total_fee = in.readString();
        order_plan_begin = in.readString();
        order_plan_end = in.readString();
        order_actual_begin = in.readString();
        order_actual_end = in.readString();
        order_path=in.readString();
        order_state=in.readString();
        create_at=in.readString();
        parking_id = in.readString();
        parking_name=in.readString();
        parking_region = in.readString();
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
    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getParkerLevel() {
        return parker_level;
    }

    public void setParkerLevel(String parker_level) {
        this.parker_level = parker_level;
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

    public String getOrder_duration() {
        return order_duration;
    }

    public void setOrder_duration(String order_duration) {
        this.order_duration = order_duration;
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

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
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

    public String getCar_buy_date() {
        return car_buy_date;
    }

    public void setCar_buy_date(String car_buy_date) {
        this.car_buy_date = car_buy_date;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(order_sn);
        dest.writeString(order_unit_fee);
        dest.writeString(order_paid_amount);
        dest.writeString(order_total_fee);
        dest.writeString(order_plan_begin);
        dest.writeString(order_plan_end);
        dest.writeString(order_actual_begin);
        dest.writeString(order_actual_end);
        dest.writeString(parking_id);
        dest.writeString(order_path);
        dest.writeString(parking_latitude);
        dest.writeString(parking_longitude);
        dest.writeString(parking_name);
        dest.writeString(order_duration);
        dest.writeString(order_state);
        dest.writeString(create_at);
        dest.writeString(parking_region);
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
