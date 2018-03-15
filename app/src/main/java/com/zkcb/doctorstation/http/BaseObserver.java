package com.zkcb.doctorstation.http;


import android.content.Context;

import com.google.gson.JsonParseException;
import com.zkcb.doctorstation.ui.view.LoadingDialog;
import com.zkcb.doctorstation.util.AppProfile;
import com.zkcb.doctorstation.util.LogUtil;
import com.zkcb.doctorstation.util.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava.HttpException;

/**
 * TODO:RXJava2 事件消费
 * Author: Yong Liu
 * Time : 2018/3/12 16:55
 * E-Mail : liuy@zkcbmed.com
 */

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {
    private Disposable disposable;
    private Context context;
    private LoadingDialog loadingDialog;
    private String string = "加载中...";

    public BaseObserver(Context context) {
        this.context = context;
        loadingDialog = new LoadingDialog(this.context);
    }

    public BaseObserver(Context context, String str) {
        this.context = context;
        this.string = str;
        loadingDialog = new LoadingDialog(context);
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        loadingDialog.showDialog(string);
    }

    @Override
    public void onNext(T value) {
        loadingDialog.dismiss();
        if (value.getCode() == 1) {
            onFail(value);
        } else {
            onSuccess(value);
        }
    }

    @Override
    public void onComplete() {
        disposable.dispose();
    }

    @Override
    public void onError(Throwable e) {
        loadingDialog.dismiss();
        LogUtil.e("Retrofit", "" + e.getMessage());
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException
                || e instanceof SocketTimeoutException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    public abstract void onSuccess(T response);

    public abstract void onFail(T response);

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showShortToast(AppProfile.getContext(), "网络连接错误");
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.showShortToast(AppProfile.getContext(), "连接超时");
                break;
            case BAD_NETWORK:
                ToastUtils.showShortToast(AppProfile.getContext(), "网络错误");
                break;
            case PARSE_ERROR:
                ToastUtils.showShortToast(AppProfile.getContext(), "Json数据解析错误");
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.showShortToast(AppProfile.getContext(), "未知错误" + reason.getClass().toString());
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
