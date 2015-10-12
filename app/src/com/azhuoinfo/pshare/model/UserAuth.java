package com.azhuoinfo.pshare.model;


import mobi.cangol.mobile.parser.Element;

public class UserAuth {
	private String customer_id;
	public UserAuth(){
	}

    public UserAuth(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
}
