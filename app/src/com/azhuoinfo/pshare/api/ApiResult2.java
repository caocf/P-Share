package com.azhuoinfo.pshare.api;

import com.azhuoinfo.pshare.api.task.Result;

public class ApiResult2<T> extends Result<T> {

    //以确定的
    public final static String CODE = "messageCode";
    public final static String MESSAGE = "message";
    public final static String DATA = "result";
    public final static String SUCCESS_CODE = "OK";

    public final static String PAGE = "hasmore";
    public final static String TIMESTAMP = "timestamp";

}
