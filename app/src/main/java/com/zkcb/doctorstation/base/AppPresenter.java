package com.zkcb.doctorstation.base;

/**
 * TODO:
 * Author: Yong Liu
 * Time : 2018/3/12 15:47
 * E-Mail : liuy@zkcbmed.com
 */

public abstract class AppPresenter<T extends BaseView> {

    private T view;

    public AppPresenter(T view) {
        this.view = view;
    }

}
