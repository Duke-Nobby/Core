package com.zkcb.doctorstation.base;

/**
 * TODO:基类Base
 * Author: Yong Liu
 * Time : 2018/3/12 15:20
 * E-Mail : liuy@zkcbmed.com
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
