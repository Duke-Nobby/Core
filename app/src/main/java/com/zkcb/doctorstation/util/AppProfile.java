package com.zkcb.doctorstation.util;

import android.content.Context;


/**
 * Created by ht-template
 **/
public abstract class AppProfile {

 /*   public static final String AppName = "MyNurse";

    public static final String USER_NAME = "UserName";
    public static final String USER_ID = "UserId";
    public static final String USER_PSD = "UserPsd";

    public static final String LOGIN_STATE = "LoginState";*/

    static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static void setContext(Context context) {
        sContext = context;
    }

    public static final String getPackageName() {
        return sContext.getPackageName();
    }


}
