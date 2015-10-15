package com.azhuoinfo.pshare.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by weixuewu on 15/10/12.
 */
public class Parking implements Parcelable ,Serializable{

    private String parking_address; //德平路333号
    private String parking_area; //浦东区
    private int parking_can_use; //32
    private int parking_charging_standard; //20
    private String parking_city; //上海
    private int parking_count; //42
    private String parking_country; //中国
    private String parking_county; //上海
    private String parking_id; //1
    private String parking_info; //黑胡椒诶方基本了深刻水电费分析
    private String parking_latitude; //31.225476
    private String parking_longitude; //121.481532
    private String parking_name; //赛博坦停车场
    private String parking_path; //0
    private String parking_province; //上海
    private String parking_status; //0
    private String parking_distance;

    public Parking() {
    }
    protected Parking(Parcel in) {
        parking_address = in.readString();
        parking_area = in.readString();
        parking_can_use = in.readInt();
        parking_charging_standard = in.readInt();
        parking_city = in.readString();
        parking_count = in.readInt();
        parking_country = in.readString();
        parking_county = in.readString();
        parking_id = in.readString();
        parking_info = in.readString();
        parking_latitude = in.readString();
        parking_longitude = in.readString();
        parking_name = in.readString();
        parking_path = in.readString();
        parking_province = in.readString();
        parking_status = in.readString();
        parking_distance = in.readString();
    }

    public static final Creator<Parking> CREATOR = new Creator<Parking>() {
        @Override
        public Parking createFromParcel(Parcel in) {
            return new Parking(in);
        }

        @Override
        public Parking[] newArray(int size) {
            return new Parking[size];
        }
    };

    public String getParking_distance() {
        return parking_distance;
    }

    public void setParking_distance(String parking_distance) {
        this.parking_distance = parking_distance;
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



    public String getParking_city() {
        return parking_city;
    }

    public void setParking_city(String parking_city) {
        this.parking_city = parking_city;
    }

    public int getParking_can_use() {
        return parking_can_use;
    }

    public void setParking_can_use(int parking_can_use) {
        this.parking_can_use = parking_can_use;
    }

    public int getParking_charging_standard() {
        return parking_charging_standard;
    }

    public void setParking_charging_standard(int parking_charging_standard) {
        this.parking_charging_standard = parking_charging_standard;
    }

    public int getParking_count() {
        return parking_count;
    }

    public void setParking_count(int parking_count) {
        this.parking_count = parking_count;
    }

    public String getParking_country() {
        return parking_country;
    }

    public void setParking_country(String parking_country) {
        this.parking_country = parking_country;
    }

    public String getParking_county() {
        return parking_county;
    }

    public void setParking_county(String parking_county) {
        this.parking_county = parking_county;
    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getParking_info() {
        return parking_info;
    }

    public void setParking_info(String parking_info) {
        this.parking_info = parking_info;
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

    public String getParking_province() {
        return parking_province;
    }

    public void setParking_province(String parking_province) {
        this.parking_province = parking_province;
    }

    public String getParking_status() {
        return parking_status;
    }

    public void setParking_status(String parking_status) {
        this.parking_status = parking_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(parking_address);
        dest.writeString(parking_area);
        dest.writeInt(parking_can_use);
        dest.writeInt(parking_charging_standard);
        dest.writeString(parking_city);
        dest.writeInt(parking_count);
        dest.writeString(parking_country);
        dest.writeString(parking_county);
        dest.writeString(parking_id);
        dest.writeString(parking_info);
        dest.writeString(parking_latitude);
        dest.writeString(parking_longitude);
        dest.writeString(parking_name);
        dest.writeString(parking_path);
        dest.writeString(parking_province);
        dest.writeString(parking_status);
        dest.writeString(parking_distance);
    }
}
