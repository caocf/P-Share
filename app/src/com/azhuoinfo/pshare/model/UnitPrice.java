package com.azhuoinfo.pshare.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Azhuo on 2015/10/10.
 */
public class UnitPrice implements Parcelable{
    String unitPrice;
    String unitPriceType;

    public UnitPrice() {
    }

    public UnitPrice(String unitPrice, String unitPriceType) {
        this.unitPrice = unitPrice;
        this.unitPriceType = unitPriceType;
    }



    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceType() {
        return unitPriceType;
    }

    public void setUnitPriceType(String unitPriceType) {
        this.unitPriceType = unitPriceType;
    }

    protected UnitPrice(Parcel in) {
        unitPrice = in.readString();
        unitPriceType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(unitPrice);
        dest.writeString(unitPriceType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UnitPrice> CREATOR = new Creator<UnitPrice>() {
        @Override
        public UnitPrice createFromParcel(Parcel in) {
            return new UnitPrice(in);
        }

        @Override
        public UnitPrice[] newArray(int size) {
            return new UnitPrice[size];
        }
    };

    @Override
    public String toString() {
        return "UnitPrice{" +
                "unitPrice='" + unitPrice + '\'' +
                ", unitPriceType='" + unitPriceType + '\'' +
                '}';
    }
}
