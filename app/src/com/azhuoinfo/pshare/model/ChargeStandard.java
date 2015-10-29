package com.azhuoinfo.pshare.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by weixuewu on 15/10/29.
 */
public class ChargeStandard implements Serializable,Parcelable{
    private static final long serialVersionUID = 0L;

    private int charge_preferential;
    private String charge_status;
    private int charge_time;
    private String charge_type;
    private int charge_unit;
    private String parking_id;
    public ChargeStandard() {

    }
    public int getCharge_preferential() {
        return charge_preferential;
    }

    public void setCharge_preferential(int charge_preferential) {
        this.charge_preferential = charge_preferential;
    }

    public String getCharge_status() {
        return charge_status;
    }

    public void setCharge_status(String charge_status) {
        this.charge_status = charge_status;
    }

    public int getCharge_time() {
        return charge_time;
    }

    public void setCharge_time(int charge_time) {
        this.charge_time = charge_time;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public int getCharge_unit() {
        return charge_unit;
    }

    public void setCharge_unit(int charge_unit) {
        this.charge_unit = charge_unit;
    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    protected ChargeStandard(Parcel in) {
        charge_preferential = in.readInt();
        charge_status = in.readString();
        charge_time = in.readInt();
        charge_type = in.readString();
        charge_unit = in.readInt();
        parking_id = in.readString();
    }

    public static final Creator<ChargeStandard> CREATOR = new Creator<ChargeStandard>() {
        @Override
        public ChargeStandard createFromParcel(Parcel in) {
            return new ChargeStandard(in);
        }

        @Override
        public ChargeStandard[] newArray(int size) {
            return new ChargeStandard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(charge_preferential);
        dest.writeString(charge_status);
        dest.writeInt(charge_time);
        dest.writeString(charge_type);
        dest.writeInt(charge_unit);
        dest.writeString(parking_id);
    }
}
