package com.zkcb.doctorstation.common;

import android.app.Application;
import android.content.Context;

import com.zkcb.doctorstation.util.AppProfile;

/**
 * Author: glory
 * Date: 2018-03-07.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    private void init() {
        context = getApplicationContext();
        AppProfile.setContext(context);
    }
}
