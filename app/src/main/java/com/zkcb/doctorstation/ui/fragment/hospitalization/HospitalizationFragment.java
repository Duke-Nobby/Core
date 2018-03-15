package com.zkcb.doctorstation.ui.fragment.hospitalization;

import android.view.View;
import android.widget.Button;

import com.zkcb.doctorstation.R;
import com.zkcb.doctorstation.base.BaseFragment;
import com.zkcb.doctorstation.http.BaseCallBack;
import com.zkcb.doctorstation.http.BaseResponse;
import com.zkcb.doctorstation.util.AppProfile;
import com.zkcb.doctorstation.util.LogUtil;
import com.zkcb.doctorstation.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * TODO:住院
 * Author: liuyong
 * Time : 2018/3/12 10:21
 */

public class HospitalizationFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.test)
    Button test;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragement_hospitalization;
    }

    @Override
    public void initContent() {
        unbinder = ButterKnife.bind(this, mContentView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindView() {
        test.setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        Map<String, String> map = new HashMap<>();
        apiWrapper.getPatientList(map).subscribe(newSubscribe(this.getContext(), new BaseCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                ToastUtils.showShortToast(AppProfile.getContext(), baseResponse.getMessage());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                ToastUtils.showShortToast(AppProfile.getContext(), "baseResponse" + baseResponse.getMessage());
                LogUtil.e(baseResponse.getMessage() + "baseResponse", "baseResponse" + baseResponse.getCode() + baseResponse.getMessage());
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }
}
