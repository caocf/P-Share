package com.azhuoinfo.pshare.api;

import com.azhuoinfo.pshare.api.task.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import mobi.cangol.mobile.parser.JSONParserException;
import mobi.cangol.mobile.parser.JsonUtils;

public class ApiResult<T> extends Result<T> {

    public final static String SUCCESS_CODE = "000000";
    public final static String CODE = "code";
    public final static String MESSAGE = "msg";
    public final static String DATA = "datas";
    public final static String PAGE = "hasmore";
    public final static String TIMESTAMP = "timestamp";

    private String timestamp;
    private boolean page;

    public boolean getPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
