package com.azhuoinfo.pshare.api.task;


public interface OnDataLoader<T>{

    /**
     * 开始
     */
	void onStart();

    /**
     *
     * @param page 是否有下一页
     * @param t
     */
	void onSuccess(boolean page,T t);

    /**
     * 失败
     * @param code
     * @param message
     */
	void onFailure(String code,String message) ;
	 
}
