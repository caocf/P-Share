package com.azhuoinfo.pshare.model;

/**
 * Created by Azhuo on 2015/10/11.
 */
public class UserCode {
    /*"code": "返回码",
            "msg": "返回信息",
            "timestamp": "时间戳",
            "datas": {
        "token":"令牌"
    }*/
    private String code;
    private String msg;
    private String timestamp;
    private UserAuth datas;

    public UserCode() {
    }

    public UserCode(String code, String msg, String timestamp, UserAuth datas) {
        this.code = code;
        this.msg = msg;
        this.timestamp = timestamp;
        this.datas = datas;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UserAuth getDatas() {
        return datas;
    }

    public void setDatas(UserAuth datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "UserCode{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", datas=" + datas +
                '}';
    }
}
