package com.zkcb.doctorstation.http;

/**
 * TODO:
 * Author: Yong Liu
 * Time : 2018/3/14 14:01
 * E-Mail : liuy@zkcbmed.com
 */

public interface BaseCallBack<T> {
    void onSuccess(T t);

    void onFail(T t);
}
