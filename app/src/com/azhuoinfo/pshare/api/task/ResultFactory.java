package com.azhuoinfo.pshare.api.task;

import com.azhuoinfo.pshare.api.ApiResult;
import com.azhuoinfo.pshare.api.ApiResult2;

import org.json.JSONArray;
import org.json.JSONObject;

import mobi.cangol.mobile.parser.JSONParserException;
import mobi.cangol.mobile.parser.JsonUtils;

/**
 * Created by weixuewu on 15/10/27.
 */
public class ResultFactory {

    private static final String ERROR_TEXT = "API接口解析错误!";
    private static final String ERROR_DEFAULT = "-1";

    private static ResultFactory ourInstance = new ResultFactory();

    private ResultFactory() {

    }

    public static ResultFactory getInstance() {
        return ourInstance;
    }

    /**
     * 默认解析
     * @param c
     * @param json
     * @param root
     * @return
     */
    public static <T> Result<T> parserResult(Class<T> c,JSONObject json,String root){
        return parserApiResult(new ApiResult(),c,json,root);
    }
    /**
     * 默认解析 带result 类
     * @param clazz
     * @param c
     * @param json
     * @param root
     * @param <T>
     * @return
     */
    public static <T> Result<T> parserResult(Class<? extends Result> clazz,Class<T> c,JSONObject json,String root){
        Result result=null;
        try {
            result=clazz.newInstance();
            if(result instanceof ApiResult){
                return parserApiResult((ApiResult) result,c,json,root);
            }else if(result instanceof ApiResult2){
                return parserApiResult2((ApiResult2) result, c, json, root);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static <T> Result<T> parserApiResult(ApiResult result,Class<T> c,JSONObject json,String root){
        try {
            result.setSource(json.toString());
            result.setSuccess(ApiResult.SUCCESS_CODE.equals(JsonUtils.getString(json, ApiResult.CODE)));
            result.setTimestamp(JsonUtils.getString(json,ApiResult.TIMESTAMP));
            if (result.isSuccess() && c != null) {
                Object resultObject = JsonUtils.getObject(json, ApiResult.DATA);
                if (resultObject != null&&resultObject!=JSONObject.NULL) {

                    if(root==null){
                        if (resultObject instanceof JSONObject) {
                            result.setObject(JsonUtils.parserToObjectByAnnotation(
                                    c, JsonUtils.getJSONObject(json,ApiResult.DATA)));

                        } else if (resultObject instanceof JSONArray) {
                            result.setList(JsonUtils.parserToList(c,
                                    JsonUtils.getJSONArray(json, ApiResult.DATA), true));
                        } else {
                            result.setObject((T) resultObject);
                        }
                    }else{
                        resultObject=JsonUtils.getObject(JsonUtils.getJSONObject(json,ApiResult.DATA), root);
                        if (resultObject != null&&resultObject!=JSONObject.NULL) {
                            if (resultObject instanceof JSONObject) {
                                result.setObject(JsonUtils.parserToObjectByAnnotation(
                                        c, JsonUtils.getJSONObject(JsonUtils.getJSONObject(json,ApiResult.DATA), root)));
                            } else if (resultObject instanceof JSONArray) {
                                result.setList(JsonUtils.parserToList(c,
                                        JsonUtils.getJSONArray(JsonUtils.getJSONObject(json,ApiResult.DATA), root), true));
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
                result.setMessage(JsonUtils.getString(json, ApiResult.MESSAGE));
                if (JsonUtils.getObject(json, ApiResult.PAGE) != null)
                    result.setPage(JsonUtils.getBoolean(json,ApiResult.PAGE,false));
            } else {
                result.setCode(JsonUtils.getString(json, ApiResult.CODE, ERROR_DEFAULT));
                result.setMessage(JsonUtils.getString(json, ApiResult.MESSAGE, ERROR_TEXT));
            }
        } catch (JSONParserException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> Result<T> parserApiResult2(ApiResult2 result, Class<T> c, JSONObject json, String root) {
        try {
            result.setSource(json.toString());
            result.setSuccess(ApiResult2.SUCCESS_CODE.equals(JsonUtils.getString(json, ApiResult2.CODE)));
            if (result.isSuccess() && c != null) {
                Object resultObject = JsonUtils.getObject(json, ApiResult2.DATA);
                if (resultObject != null&&resultObject!=JSONObject.NULL) {

                    if(root==null){
                        if (resultObject instanceof JSONObject) {
                            result.setObject(JsonUtils.parserToObjectByAnnotation(
                                    c, JsonUtils.getJSONObject(json,ApiResult2.DATA)));

                        } else if (resultObject instanceof JSONArray) {
                            result.setList(JsonUtils.parserToList(c,
                                    JsonUtils.getJSONArray(json, ApiResult2.DATA), true));
                        } else {
                            result.setObject((T) resultObject);
                        }
                    }else{
                        resultObject=JsonUtils.getObject(JsonUtils.getJSONObject(json,ApiResult2.DATA), root);
                        if (resultObject != null&&resultObject!=JSONObject.NULL) {
                            if (resultObject instanceof JSONObject) {
                                result.setObject(JsonUtils.parserToObjectByAnnotation(
                                        c, JsonUtils.getJSONObject(JsonUtils.getJSONObject(json,ApiResult2.DATA), root)));
                            } else if (resultObject instanceof JSONArray) {
                                result.setList(JsonUtils.parserToList(c,
                                        JsonUtils.getJSONArray(JsonUtils.getJSONObject(json,ApiResult2.DATA), root), true));
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
                result.setMessage(JsonUtils.getString(json, ApiResult2.MESSAGE));
            } else {
                result.setCode(JsonUtils.getString(json, ApiResult2.CODE, ERROR_DEFAULT));
                result.setMessage(JsonUtils.getString(json, ApiResult2.MESSAGE, ERROR_TEXT));
            }
        } catch (JSONParserException e) {
            e.printStackTrace();
        }
        return result;
    }

}
