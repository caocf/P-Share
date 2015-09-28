package com.azhuoinfo.pshare.api.task;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class ApiTask {
	public static final boolean DEBUG = true;
	private Context mContext;
	private String mMethod;
	private String mUrl;
	private HashMap<String, Object> mParams;
	private String mTag;
	private boolean mRunning = false;
	private ApiClient mApiClient;
	private String mRoot;
	private ApiTask(Context context, String tag) {
		mContext = context;
		mParams = new HashMap<String, Object>();
		mTag = tag;
		mMethod = "GET";
		mApiClient = ApiClient.getInstance(mContext);
	}
	public static ApiTask build(Context context, String tag){
		return new ApiTask(context,tag);
	}
	public boolean IsRunning() {
		return mRunning;
	}
	public void cancel(boolean mayInterruptIfRunning) {
		mApiClient.cancel(mTag, mayInterruptIfRunning);
	}
	public static void  cancel(Context context,String tag) {
		ApiClient.getInstance(context).cancel(tag,true);
	}
	public void setUrl(String url) {
		this.mUrl = url;
	}

	public void setMethod(String method) {
		this.mMethod = method;
	}

	public void setParams(HashMap<String, String> params) {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			mParams.put(entry.getKey(), entry.getValue());
		}
	}

	public String getRoot() {
		return mRoot;
	}

	public void setRoot(String root) {
		this.mRoot = root;
	}

	public HashMap<String, Object> getParams() {
		return mParams;
	}
	@SuppressLint("DefaultLocale")
	public <T> void execute(final OnDataLoader<T> onDataLoader) {
		if ("GET".equals(mMethod.toUpperCase())) {
			mApiClient.execute(mTag, ApiClient.Method.GET, mUrl, mParams,mRoot,
					onDataLoader);
		} else {
			mApiClient.execute(mTag, ApiClient.Method.POST, mUrl, mParams,mRoot,
					onDataLoader);
		}
	}
	public <T> void execute(String method,String url,HashMap<String, Object> params,String root,final OnDataLoader<T> onDataLoader) {
		this.mMethod=method;
		this.mUrl=url;
		this.mParams=params;
		if ("GET".equals(mMethod.toUpperCase())) {
			mApiClient.execute(mTag, ApiClient.Method.GET, mUrl, mParams,root,
					onDataLoader);
		} else {
			mApiClient.execute(mTag, ApiClient.Method.POST, mUrl, mParams,root,
					onDataLoader);
		}
	}
}
