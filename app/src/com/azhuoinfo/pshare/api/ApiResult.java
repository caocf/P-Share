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
	private String error;
	private String errorCode;
	private ApiPage page;
	private T object;
	private List<T> list;
	public final static String SUCCESS_CODE = "200";
	public final static String RETCODE = "retcode";
	public final static String MESSAGE = "message";
	public final static String DATA = "data";
	public final static String PAGE = "page";

	private ApiResult() {

	}

	public ApiPage getPage() {
		return page;
	}

	public void setPage(ApiPage page) {
		this.page = page;
	}



	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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
	public static <T> ApiResult<T> parserObject(Class<T> c, JSONObject json){
		ApiResult<T> result = new ApiResult<T>();
		try {
			result.setSource(json.toString());
			result.setSuccess(SUCCESS_CODE.equals(JsonUtils.getString(json,
					RETCODE)));
			if (result.isSuccess() && c != null) {
				Object resultObject = JsonUtils.getObject(json, DATA);
				if (resultObject != null) {
					if (resultObject instanceof JSONObject) {
						result.setObject(JsonUtils.parserToObjectByAnnotation(
								c, JsonUtils.getJSONObject(json, DATA)));
						
					} else if (resultObject instanceof JSONArray) {
						result.setList(JsonUtils.parserToList(c,
								JsonUtils.getJSONArray(json, DATA), true));
					}else{
						result.setObject((T) resultObject);
					}
				} else {
					result.setObject(null);
				}
				result.setError(JsonUtils.getString(json, MESSAGE));
				if (JsonUtils.getObject(json, PAGE)!=null) 
				result.setPage(JsonUtils.parserToObjectByAnnotation(ApiPage.class, JsonUtils.getJSONObject(json, PAGE)));
			} else {
				result.setErrorCode(JsonUtils.getString(json, RETCODE,ERROR_DEFAULT));
				result.setError(JsonUtils.getString(json, MESSAGE, ERROR_TEXT));
			}
		} catch (JSONParserException e) {
			e.printStackTrace();
		}
		return result;
	}

}
