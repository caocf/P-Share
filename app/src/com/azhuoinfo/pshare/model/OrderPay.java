package com.azhuoinfo.pshare.model;


public class OrderPay {
	private String totalPay;
	public OrderPay(){
	}

    public OrderPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public String toTalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }
}
