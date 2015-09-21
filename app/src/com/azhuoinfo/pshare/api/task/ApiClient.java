package com.azhuoinfo.pshare.api.task;

import android.content.Context;

import com.azhuoinfo.pshare.AccountVerify;
import com.azhuoinfo.pshare.api.ApiResult;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import mobi.cangol.mobile.http.AsyncHttpClient;
import mobi.cangol.mobile.http.JsonHttpResponseHandler;
import mobi.cangol.mobile.http.RequestParams;
import mobi.cangol.mobile.logging.Log;
import mobi.cangol.mobile.service.PoolManager;
import mobi.cangol.mobile.utils.DeviceInfo;


public  class ApiClient {
	public enum Method{
		GET,
		POST
	}
	public static final String TAG="ApiClient";
	public static final boolean DEBUG=true;
	public static final int MAX_THREAD=5;
	public static final String ERROR="-1";
	public static final String ERROR_CONNECT="网络无法连接,请检查网络";
	public static final String TOKEN_ERROR = "40015";
	private AccountVerify mAccountVerify;
	private AsyncHttpClient mAsyncHttpClient;
	private Context mContext;
	private static ApiClient client=null;
	private ApiClient(Context context){
		mContext=context;
		mAsyncHttpClient= AsyncHttpClient.build(TAG);
		mAsyncHttpClient.setThreadool(PoolManager.buildPool(TAG, MAX_THREAD));
		mAccountVerify = AccountVerify.getInstance(context);
	}
	public static ApiClient getInstance(Context context){
		if(client==null){
			client=new ApiClient(context);
		}
		return client;
	}
	public  void cancel(Object tag,boolean mayInterruptIfRunning){
		mAsyncHttpClient.cancelRequests(tag, mayInterruptIfRunning);
	}
	public <E, T> void execute(Object tag,Method method,final String url,HashMap<String, Object> params,final OnDataLoader<T> onDataLoader){
		RequestParams requestParams=new RequestParams();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if(entry.getValue() instanceof File){
				try {
					requestParams.put(entry.getKey(), (File)entry.getValue());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}else if(entry.getValue() instanceof InputStream){
					requestParams.put(entry.getKey(), (InputStream)entry.getValue());
			}else if(entry.getValue()!=null){
				requestParams.put(entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		if(DEBUG)Log.d(AsyncHttpClient.getUrlWithQueryString(url, requestParams));
		if(DEBUG)Log.d(requestParams.toDebugString());
		if(onDataLoader!=null)onDataLoader.onStart();
		if(!DeviceInfo.isConnection(mContext)){
			if(onDataLoader!=null)onDataLoader.onFailure(ERROR,ERROR_CONNECT);
			return;
		}
		if(method==Method.GET){
			executeGet(tag,url,requestParams, onDataLoader);
		}else{
			executePost(tag,url,requestParams, onDataLoader);
		}
	}
	/**深度递归获取泛型**/
	private Class<?> getGenericClass(Type type){
		if (type  instanceof ParameterizedType) {
			type=((ParameterizedType) type).getActualTypeArguments()[0];
			return getGenericClass(type);
		}else{
			return (Class<?>) type;
		}
	}
	private String getAction(String url){
		String action=url.substring(url.indexOf("/", 8));
		action=action.substring(0,action.contains("?")?action.indexOf("?"):action.length());
		return action;
	}
	private <T> void executeGet(Object tag,final String url,RequestParams params,final OnDataLoader<T> onDataLoader){
		final String action=getAction(url);
		mAsyncHttpClient.get(tag,url,params, new JsonHttpResponseHandler() {
			long lasttime=0;
			@Override
			public void onStart() {
				super.onStart();
				lasttime=System.currentTimeMillis();
			}

			@Override
			public void onSuccess(JSONObject response) {
				super.onSuccess(response);
				if(DEBUG)Log.d("Success response:"+response);
				Class<?> c=getGenericClass(onDataLoader.getClass().getGenericInterfaces()[0]);
			    if(DEBUG)Log.d("Parser class:"+c);
				ApiResult<T> result=(ApiResult<T>) ApiResult.parserObject(c, response);
				if(result.isSuccess()){
					int totalPage=0;
					if(result.getPage()!=null)
						totalPage=(int) result.getPage().getTotalPageCount();
					if(result.getObject()!=null){
						if(onDataLoader!=null)onDataLoader.onSuccess(totalPage,result.getObject());
					}else{
						if(onDataLoader!=null)onDataLoader.onSuccess(totalPage,(T) result.getList());
					}
				}else{
					//用户token验证错误
					if(TOKEN_ERROR.equals(result.getErrorCode())){
						mAccountVerify.notifyExpire();
					}
					if(onDataLoader!=null)onDataLoader.onFailure(result.getErrorCode(),result.getError());
				}
			}

			@Override
			public void onFailure(Throwable e, String errorResponse) {
				if(DEBUG)Log.d("Fail response:"+errorResponse+" "+e.getMessage());
				if(onDataLoader!=null)onDataLoader.onFailure(ERROR,errorResponse);
			}
			
		});
	}
	
	private <E, T> void executePost(Object tag,final String url,RequestParams params,final OnDataLoader<T> onDataLoader){
		final String action=getAction(url);
		mAsyncHttpClient.post(tag,url,params, new JsonHttpResponseHandler() {
			long lasttime=0;
			@Override
			public void onStart() {
				super.onStart();
				lasttime=System.currentTimeMillis();
			}

			@Override
			public void onSuccess(JSONObject response) {
				super.onSuccess(response);
				if(DEBUG)Log.d( "Success response:"+response);
				Class<?> c=getGenericClass(onDataLoader.getClass().getGenericInterfaces()[0]);
			    if(DEBUG)Log.d("Parser class:"+c);
				ApiResult<T> result=(ApiResult<T>) ApiResult.parserObject(c, response);
				if(result.isSuccess()){
					int totalPage=0;
					if(result.getPage()!=null)
						totalPage=(int) result.getPage().getTotalPageCount();
					if(result.getObject()!=null){
						if(onDataLoader!=null)onDataLoader.onSuccess(totalPage,result.getObject());
					}else{
						if(onDataLoader!=null)onDataLoader.onSuccess(totalPage,(T) result.getList());
					}
				}else{
					//用户token验证错误
					if(TOKEN_ERROR.equals(result.getErrorCode())){
						mAccountVerify.notifyExpire();
					}
					if(onDataLoader!=null)onDataLoader.onFailure(result.getErrorCode(),result.getError());
				}
			}

			@Override
			public void onFailure(Throwable e, String errorResponse) {
				if(DEBUG)Log.d("Fail response:"+errorResponse+" "+e.getMessage());
				if(onDataLoader!=null)onDataLoader.onFailure(ERROR,errorResponse);
			}
			
		});
	}
}
