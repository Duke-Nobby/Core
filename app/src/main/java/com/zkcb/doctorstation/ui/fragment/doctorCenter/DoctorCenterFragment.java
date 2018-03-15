package com.zkcb.doctorstation.ui.fragment.doctorCenter;

import android.widget.TextView;

import com.zkcb.doctorstation.R;
import com.zkcb.doctorstation.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * TODO:我的中心-医生中心
 * Author: liuyong
 * Time : 2018/3/12 10:23
 */

public class DoctorCenterFragment extends BaseFragment {


    @BindView(R.id.text)
    TextView text;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_doctor_center;
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
