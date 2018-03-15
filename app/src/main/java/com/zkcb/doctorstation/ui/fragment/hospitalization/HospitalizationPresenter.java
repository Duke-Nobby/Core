package com.zkcb.doctorstation.ui.fragment.hospitalization;

import com.zkcb.doctorstation.base.AppPresenter;

/**
 * TODO:
 * Author: Yong Liu
 * Time : 2018/3/14 18:05
 * E-Mail : liuy@zkcbmed.com
 */

public class HospitalizationPresenter extends AppPresenter<HospitalizationContract.View> implements HospitalizationContract.Presenter {
    public HospitalizationPresenter(HospitalizationContract.View view) {
        super(view);
    }
}
