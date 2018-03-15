package com.zkcb.doctorstation.ui.activity.login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zkcb.doctorstation.R;
import com.zkcb.doctorstation.base.BaseActivity;
import com.zkcb.doctorstation.http.ApiWrapper;
import com.zkcb.doctorstation.http.BaseCallBack;
import com.zkcb.doctorstation.ui.activity.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TODO:
 * Author: Yong Liu
 * Time : 2018/3/13 11:29
 * E-Mail : liuy@zkcbmed.com
 */

public class LoginActivity extends BaseActivity<LoginPresenter> {

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initContent() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView() {
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
        }
    }

    public void login() {
        Map<String, String> map = new HashMap<>();
        map.put("account", "123456");
        map.put("password", "123456");
        apiWrapper.login(map).subscribe(newSubscribe(this, "登录中...", new BaseCallBack() {
            @Override
            public void onSuccess(Object o) {
                Log.e("hahha", "hahha");
                skipAct(MainActivity.class);
            }

            @Override
            public void onFail(Object o) {
                skipAct(MainActivity.class);
            }
        }));
    }

}
