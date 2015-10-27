package com.azhuoinfo.pshare.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import mobi.cangol.mobile.parser.JSONParserException;
import mobi.cangol.mobile.parser.JsonUtils;

public class ApiResult<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String ERROR_TEXT = "API接口解析错误!";
    private static final String ERROR_DEFAULT = "-1";

    private boolean success;
    private String source;
    private String message;
    private String code;
    private String timestamp;

    private boolean page;
    private T object;
    private List<T> list;
    public final static String SUCCESS_CODE = "000000";
    public final static String CODE = "code";
    public final static String MESSAGE = "msg";
    public final static String DATA = "datas";
    public final static String PAGE = "hasmore";

    private ApiResult() {

    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public static <T> ApiResult<T> parserObject(Class<T> c, JSONObject json,String root) {
        ApiResult<T> result = new ApiResult<T>();
        try {
            result.setSource(json.toString());
            result.setSuccess(SUCCESS_CODE.equals(JsonUtils.getString(json, CODE)));
            result.setTimestamp(JsonUtils.getString(json,"timestamp"));
            if (result.isSuccess() && c != null) {
                Object resultObject = JsonUtils.getObject(json, DATA);
                if (resultObject != null&&resultObject!=JSONObject.NULL) {

                    if(root==null){
                        if (resultObject instanceof JSONObject) {
                            result.setObject(JsonUtils.parserToObjectByAnnotation(
                                    c, JsonUtils.getJSONObject(json, DATA)));

                        } else if (resultObject instanceof JSONArray) {
                            result.setList(JsonUtils.parserToList(c,
                                    JsonUtils.getJSONArray(json, DATA), true));
                        } else {
                            result.setObject((T) resultObject);
                        }
                    }else{
                        resultObject=JsonUtils.getObject(JsonUtils.getJSONObject(json,DATA), root);
                        if (resultObject != null&&resultObject!=JSONObject.NULL) {
                            if (resultObject instanceof JSONObject) {
                                result.setObject(JsonUtils.parserToObjectByAnnotation(
                                        c, JsonUtils.getJSONObject(JsonUtils.getJSONObject(json,DATA), root)));
                            } else if (resultObject instanceof JSONArray) {
                                result.setList(JsonUtils.parserToList(c,
                                        JsonUtils.getJSONArray(JsonUtils.getJSONObject(json,DATA), root), true));
                            } else {
                                result.setObject((T) resultObject);
                            }
                        }else{
                            result.setSuccess(false);
                            result.setObject(null);
                        }
                    }
                } else {
                    result.setSuccess(false);
                    result.setObject(null);
                }
                result.setMessage(JsonUtils.getString(json, MESSAGE));
                if (JsonUtils.getObject(json, PAGE) != null)
                    result.setPage(JsonUtils.getBoolean(json,PAGE,false));
            } else {
                result.setCode(JsonUtils.getString(json, CODE, ERROR_DEFAULT));
                result.setMessage(JsonUtils.getString(json, MESSAGE, ERROR_TEXT));
            }
        } catch (JSONParserException e) {
            e.printStackTrace();
        }
        return result;
    }

}
