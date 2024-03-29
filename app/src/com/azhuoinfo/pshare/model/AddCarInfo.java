package com.azhuoinfo.pshare.model;

/**
 * Created by Azhuo on 2015/10/8.
 */
public class AddCarInfo {
    /* "car_buy_date": "0000-00-00 00:00:00",
        "car_driver_image": "",
        "car_image": "",
        "car_issue_date": "0000-00-00 00:00:00",
        "car_lasts": "0000-00-00 00:00:00",
        "car_mine_image": "",
        "car_use_date": "0000-00-00 00:00:00",
        "car_use_type": "1",
        "owner_id_number": ""
*/
    private String customer_id;
    private String car_id;
    private String car_number;
    private String car_brand;
    private String car_color;
    private String car_size;
    private String car_buy_date;
    private String car_driver_image;
    private String car_image;
    private String car_issue_date;
    private String car_lasts;
    private String car_mine_image;
    private String car_use_date;
    private String car_use_type;
    private String owner_id_number;

    public AddCarInfo() {

    }
    public AddCarInfo(String customer_id, String car_id, String car_number, String car_brand, String car_color, String car_size, String car_buy_date, String car_driver_image, String car_image, String car_issue_date, String car_lasts, String car_mine_image, String car_use_date, String car_use_type, String owner_id_number) {
        this.customer_id = customer_id;
        this.car_id = car_id;
        this.car_number = car_number;
        this.car_brand = car_brand;
        this.car_color = car_color;
        this.car_size = car_size;
        this.car_buy_date = car_buy_date;
        this.car_driver_image = car_driver_image;
        this.car_image = car_image;
        this.car_issue_date = car_issue_date;
        this.car_lasts = car_lasts;
        this.car_mine_image = car_mine_image;
        this.car_use_date = car_use_date;
        this.car_use_type = car_use_type;
        this.owner_id_number = owner_id_number;
    }
    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
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

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getCar_size() {
        return car_size;
    }

    public void setCar_size(String car_size) {
        this.car_size = car_size;
    }

    public String getCar_buy_date() {
        return car_buy_date;
    }

    public void setCar_buy_date(String car_buy_date) {
        this.car_buy_date = car_buy_date;
    }

    public String getCar_driver_image() {
        return car_driver_image;
    }

    public void setCar_driver_image(String car_driver_image) {
        this.car_driver_image = car_driver_image;
    }

    public String getCar_image() {
        return car_image;
    }

    public void setCar_image(String car_image) {
        this.car_image = car_image;
    }

    public String getCar_issue_date() {
        return car_issue_date;
    }

    public void setCar_issue_date(String car_issue_date) {
        this.car_issue_date = car_issue_date;
    }

    public String getCar_lasts() {
        return car_lasts;
    }

    public void setCar_lasts(String car_lasts) {
        this.car_lasts = car_lasts;
    }

    public String getCar_mine_image() {
        return car_mine_image;
    }

    public void setCar_mine_image(String car_mine_image) {
        this.car_mine_image = car_mine_image;
    }

    public String getCar_use_date() {
        return car_use_date;
    }

    public void setCar_use_date(String car_use_date) {
        this.car_use_date = car_use_date;
    }

    public String getCar_use_type() {
        return car_use_type;
    }

    public void setCar_use_type(String car_use_type) {
        this.car_use_type = car_use_type;
    }

    public String getOwner_id_number() {
        return owner_id_number;
    }

    public void setOwner_id_number(String owner_id_number) {
        this.owner_id_number = owner_id_number;
    }

    @Override
    public String toString() {
        return "AddCarInfo{" +
                "customer_id='" + customer_id + '\'' +
                ", car_id='" + car_id + '\'' +
                ", car_number='" + car_number + '\'' +
                ", car_brand='" + car_brand + '\'' +
                ", car_color='" + car_color + '\'' +
                ", car_size='" + car_size + '\'' +
                ", car_buy_date='" + car_buy_date + '\'' +
                ", car_driver_image='" + car_driver_image + '\'' +
                ", car_image='" + car_image + '\'' +
                ", car_issue_date='" + car_issue_date + '\'' +
                ", car_lasts='" + car_lasts + '\'' +
                ", car_mine_image='" + car_mine_image + '\'' +
                ", car_use_date='" + car_use_date + '\'' +
                ", car_use_type='" + car_use_type + '\'' +
                ", owner_id_number='" + owner_id_number + '\'' +
                '}';
    }
}
