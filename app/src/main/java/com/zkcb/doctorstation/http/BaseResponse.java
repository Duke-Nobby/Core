package com.zkcb.doctorstation.http;

/**
 * TODO:基础返回类
 * Author: Yong Liu
 * Time : 2018/3/12 16:56
 * E-Mail : liuy@zkcbmed.com
 */

public class BaseResponse {
    private String message;
    private int code;
    private Object data;

    public BaseResponse(String message, int code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
