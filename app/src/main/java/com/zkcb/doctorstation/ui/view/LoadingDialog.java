package com.zkcb.doctorstation.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zkcb.doctorstation.R;
import com.zkcb.doctorstation.util.ScreenUtils;

/**
 * TODO: 数据加载弹窗
 * Author: Yong Liu
 * Time : 2018/3/12 17:31
 * E-Mail : liuy@zkcbmed.com
 */

public class LoadingDialog {
    private static Dialog mDialog;
    private static Context mContext;

    private static LayoutInflater mInflater;
    private static TextView loadingText;

    public LoadingDialog(Context mContext) {
        this.mContext = mContext;
        mDialog = new Dialog(mContext, R.style.loading_dialog);
        initLoadingDialog();
    }

    private static void initLoadingDialog() {
        mInflater = LayoutInflater.from(mContext);
        View v = mInflater.inflate(R.layout.layout_loading_dialog, null);        // 得到加载view
        LinearLayout layout = v.findViewById(R.id.dialog_view);  // 加载布局
        loadingText = v.findViewById(R.id.loading_text);
        mDialog.setCancelable(false);// 不可以用"返回键"取消
        mDialog.setContentView(layout, new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext) / 3, ScreenUtils.getScreenWidth(mContext) / 3));
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    public void showDialog(String string) {
        loadingText.setText(string);
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
