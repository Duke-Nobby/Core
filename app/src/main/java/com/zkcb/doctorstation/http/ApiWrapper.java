package com.zkcb.doctorstation.http;

import java.util.Map;

import io.reactivex.Observable;

/**
 * TODO:
 * Author: Yong Liu
 * Time : 2018/3/13 17:23
 * E-Mail : liuy@zkcbmed.com
 */

public class ApiWrapper extends Api {

    public static ApiWrapper instance;

    public static ApiWrapper getInstance() {
        if (instance == null) {
            instance = new ApiWrapper();
        }
        return instance;
    }

    public Observable<BaseResponse> login(Map<String, String> map) {
        return post("login", map);
    }

    public Observable<BaseResponse> getPatientList(Map<String, String> map) {
        return get("patientList", map);
    }

}
