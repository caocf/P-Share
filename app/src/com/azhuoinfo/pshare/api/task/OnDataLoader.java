package com.azhuoinfo.pshare.api.task;


import com.azhuoinfo.pshare.api.ApiResult;

abstract  public class OnDataLoader<T>{
    private ApiResult apiResult;

    public ApiResult getApiResult() {
        return apiResult;
    }

    public void setApiResult(ApiResult apiResult) {
        this.apiResult = apiResult;
    }
    /**

     * 开始
     */
    abstract public void onStart();

    /**
     *
     * @param page 是否有下一页
     * @param t
     */
	abstract public void onSuccess(boolean page,T t);

    /**
     * 失败
     * @param code
     * @param message
     */
    abstract public void onFailure(String code,String message) ;
	 
}
