package com.zkcb.doctorstation.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zkcb.doctorstation.http.ApiWrapper;
import com.zkcb.doctorstation.http.BaseCallBack;
import com.zkcb.doctorstation.http.BaseObserver;
import com.zkcb.doctorstation.http.BaseResponse;
import com.zkcb.doctorstation.util.ResourcesUtil;

/**
 * TODO:BaseActivity 基类
 * Author: Yong Liu
 * Time : 2018/3/12 13:44
 * E-Mail : liuy@zkcbmed.com
 */

public abstract class BaseActivity<T extends AppPresenter> extends RxAppCompatActivity implements View.OnClickListener {

    protected ApiWrapper apiWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        apiWrapper = ApiWrapper.getInstance();
        initContent();
        initData();
        bindView();
    }

    public abstract @LayoutRes
    int getLayoutId();

    public abstract void initContent();

    public abstract void initData();

    public abstract void bindView();


    //更改状态栏颜色，
    protected void setStatusBarColor(int resId) {
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ResourcesUtil.getColor(resId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(resId);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否可以隐藏键盘
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 跳转页面
     *
     * @param clazz
     */
    public void skipAct(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("fromWhere", getClass().getSimpleName());
        startActivity(intent);
    }

    public void skipAct(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        intent.putExtra("fromWhere", getClass().getSimpleName());
        startActivity(intent);
    }

    public void skipAct(Class clazz, Bundle bundle, int flags) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("fromWhere", getClass().getSimpleName());
        intent.setFlags(flags);
        startActivity(intent);
    }

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
