package com.zkcb.doctorstation.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zkcb.doctorstation.http.ApiWrapper;
import com.zkcb.doctorstation.http.BaseCallBack;
import com.zkcb.doctorstation.http.BaseObserver;
import com.zkcb.doctorstation.http.BaseResponse;

/**
 * Author: glory
 * Date: 2018-03-07.
 */

public abstract class BaseFragment extends RxFragment {

    protected View mContentView;
    protected ApiWrapper apiWrapper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), container, false);
        apiWrapper = ApiWrapper.getInstance();
        initContent();
        initData();
        bindView();

        return mContentView;
    }

    public abstract @LayoutRes
    int getLayoutId();

    public abstract void initContent();

    public abstract void initData();

    public abstract void bindView();


    public <T extends BaseResponse> BaseObserver newSubscribe(Context context, final BaseCallBack callBack) {
        return new BaseObserver<T>(context) {
            @Override
            public void onSuccess(T response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(T response) {
                callBack.onFail(response);
            }
        };
    }

    public <T extends BaseResponse> BaseObserver newSubscribe(Context context, String string, final BaseCallBack callBack) {
        return new BaseObserver<T>(context, string) {
            @Override
            public void onSuccess(T response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFail(T response) {
                callBack.onFail(response);
            }
        };
    }

}




