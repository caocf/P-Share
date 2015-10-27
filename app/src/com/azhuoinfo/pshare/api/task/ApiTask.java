package com.azhuoinfo.pshare.api.task;

import android.content.Context;

import com.azhuoinfo.pshare.api.ApiResult;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import mobi.cangol.mobile.logging.Log;

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
    private Class resultClass= ApiResult.class;
	private ApiTask(Context context, String tag) {
		mContext = context;
		mParams = new HashMap<String, Object>();
		mTag = tag;
		mMethod = "GET";
		mApiClient = ApiClient.getInstance(mContext);
	}

    public void setResultClass(Class<? extends Result> clazz) {
        this.resultClass = clazz;
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
		ApiClient.getInstance(context).cancel(tag, true);
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
	public void setObjectParams(HashMap<String, Object> params) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			mParams.put(entry.getKey(), entry.getValue());
		}
	}
    private String getAction(String url){
		String action=url.substring(url.indexOf("/", 8));
		action=action.substring(0,action.contains("?")?action.indexOf("?"):action.length());
		return action;
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

    /**深度递归获取泛型**/
    private Class<?> getGenericClass(Type type){
        if (type  instanceof ParameterizedType) {
            type=((ParameterizedType) type).getActualTypeArguments()[0];
            return getGenericClass(type);
        }else{
            return (Class<?>) type;
        }
    }

	public <T> void execute(final OnDataLoader<T> onDataLoader) {
        this.execute(mMethod,mUrl,mParams,mRoot,onDataLoader);
	}

	public <T> void execute(String methodStr,String url,HashMap<String, Object> params,String root,final OnDataLoader<T> onDataLoader) {
		this.mMethod=methodStr;
		this.mUrl=url;
		this.mParams=params;
        ApiClient.Method method;
        if ("GET".equals(methodStr.toUpperCase())) {
            method=ApiClient.Method.GET;
        } else {
            method=ApiClient.Method.POST;
        }
        mApiClient.execute(mTag, method, mUrl, mParams, mRoot, new ApiClient.OnResponse(){
            long lastTime=0;
            @Override
            public void onStart() {
                lastTime=System.currentTimeMillis();
                if(onDataLoader!=null)onDataLoader.onStart();
            }

            @Override
            public void onSuccess(JSONObject response) {
                if (DEBUG) Log.d("executePost idle:" + (System.currentTimeMillis() - lastTime));
                if (DEBUG) Log.d("onSuccess response="+response);


                if(DEBUG)Log.d("Parser resultClass:"+resultClass);

                Class<?> c=getGenericClass(onDataLoader.getClass().getGenericSuperclass());
                if(DEBUG)Log.d("Parser class:"+c);

                Result result=ResultFactory.getInstance().parserResult(resultClass,c,response,mRoot);

                if(onDataLoader!=null)onDataLoader.setResult(result);

                if(result.isSuccess()){
                    if(result.getObject()!=null){
                        if(onDataLoader!=null)onDataLoader.onSuccess((T) result.getObject());
                    }else{
                        if(onDataLoader!=null)onDataLoader.onSuccess((T) result.getList());
                    }
                }else{
                    if(onDataLoader!=null)onDataLoader.onFailure(result.getCode(),result.getMessage());
                }
            }

            @Override
            public void onFailure(String code, String response) {
                if (DEBUG) Log.d("executePost idle:" + (System.currentTimeMillis()-lastTime));
                if (DEBUG) Log.d("onFailure code="+code+",response="+response);
                if(onDataLoader!=null)onDataLoader.onFailure(code, response);

            }
        });
	}

}
