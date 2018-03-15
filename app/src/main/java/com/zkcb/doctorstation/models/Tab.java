package com.zkcb.doctorstation.models;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class Tab {
    private int title;
    private int icon;
    private Class fragment;

    public Tab() {
    }

    public Tab(int title, Class fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public Tab(int title, int icon, Class fragment) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
