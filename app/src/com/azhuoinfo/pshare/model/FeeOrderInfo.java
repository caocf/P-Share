package com.azhuoinfo.pshare.model;


import android.os.Parcel;
import android.os.Parcelable;

public class FeeOrderInfo implements Parcelable{
    String ID;
    String amount;
    String area;
    String buyerEmail;
    String carNumber;
    String county;
    String customerId;
    String customerName;
    String orderSerialNumber;
    String orderStatus;
    String orderType;
    String price;
    String receivablesPlatformType;
    String timeQuantum;
    String validityEndTime;
    String validityStartTime;
    String villageId;
    String villageName;

    public FeeOrderInfo() {
    }

    public FeeOrderInfo(String ID,
                        String amount,
                        String area,
                        String buyerEmail,
                        String carNumber,
                        String county,
                        String customerId,
                        String customerName,
                        String orderSerialNumber,
                        String orderStatus,
                        String orderType,
                        String price,
                        String receivablesPlatformType,
                        String timeQuantum,
                        String validityEndTime,
                        String validityStartTime,
                        String villageId,
                        String villageName) {
        this.ID = ID;
        this.amount = amount;
        this.area = area;
        this.buyerEmail = buyerEmail;
        this.carNumber = carNumber;
        this.county = county;
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderSerialNumber = orderSerialNumber;
        this.orderStatus = orderStatus;
        this.orderType = orderType;
        this.price = price;
        this.receivablesPlatformType = receivablesPlatformType;
        this.timeQuantum = timeQuantum;
        this.validityEndTime = validityEndTime;
        this.validityStartTime = validityStartTime;
        this.villageId = villageId;
        this.villageName = villageName;
    }

    protected FeeOrderInfo(Parcel in) {
        ID = in.readString();
        amount = in.readString();
        area = in.readString();
        buyerEmail = in.readString();
        carNumber = in.readString();
        county = in.readString();
        customerId = in.readString();
        customerName = in.readString();
        orderSerialNumber = in.readString();
        orderStatus = in.readString();
        orderType = in.readString();
        price = in.readString();
        receivablesPlatformType = in.readString();
        timeQuantum = in.readString();
        validityEndTime = in.readString();
        validityStartTime = in.readString();
        villageId = in.readString();
        villageName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(amount);
        dest.writeString(area);
        dest.writeString(buyerEmail);
        dest.writeString(carNumber);
        dest.writeString(county);
        dest.writeString(customerId);
        dest.writeString(customerName);
        dest.writeString(orderSerialNumber);
        dest.writeString(orderStatus);
        dest.writeString(orderType);
        dest.writeString(price);
        dest.writeString(receivablesPlatformType);
        dest.writeString(timeQuantum);
        dest.writeString(validityEndTime);
        dest.writeString(validityStartTime);
        dest.writeString(villageId);
        dest.writeString(villageName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeeOrderInfo> CREATOR = new Creator<FeeOrderInfo>() {
        @Override
        public FeeOrderInfo createFromParcel(Parcel in) {
            return new FeeOrderInfo(in);
        }

        @Override
        public FeeOrderInfo[] newArray(int size) {
            return new FeeOrderInfo[size];
        }
    };

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderSerialNumber() {
        return orderSerialNumber;
    }

    public void setOrderSerialNumber(String orderSerialNumber) {
        this.orderSerialNumber = orderSerialNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReceivablesPlatformType() {
        return receivablesPlatformType;
    }

    public void setReceivablesPlatformType(String receivablesPlatformType) {
        this.receivablesPlatformType = receivablesPlatformType;
    }

    public String getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(String timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public String getValidityEndTime() {
        return validityEndTime;
    }

    public void setValidityEndTime(String validityEndTime) {
        this.validityEndTime = validityEndTime;
    }

    public String getValidityStartTime() {
        return validityStartTime;
    }

    public void setValidityStartTime(String validityStartTime) {
        this.validityStartTime = validityStartTime;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    @Override
    public String toString() {
        return "FeeOrderInfo{" +
                "ID='" + ID + '\'' +
                ", amount='" + amount + '\'' +
                ", area='" + area + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", county='" + county + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", orderSerialNumber='" + orderSerialNumber + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderType='" + orderType + '\'' +
                ", price='" + price + '\'' +
                ", receivablesPlatformType='" + receivablesPlatformType + '\'' +
                ", timeQuantum='" + timeQuantum + '\'' +
                ", validityEndTime='" + validityEndTime + '\'' +
                ", validityStartTime='" + validityStartTime + '\'' +
                ", villageId='" + villageId + '\'' +
                ", villageName='" + villageName + '\'' +
                '}';
    }
}
