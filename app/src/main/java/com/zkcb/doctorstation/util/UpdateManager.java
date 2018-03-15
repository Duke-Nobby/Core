package com.zkcb.doctorstation.util;//package a7890.com.umi.baselibrary.util;
//
//import android.content.Context;
//import android.view.View;
//

//
///**
// * 主要功能:APP更新管理类
// * 修订历史:
// */
//public class UpdateManager {
//    private Context mContext;
//    private VersionModel.VersionBean versionBean;
//    private AppAlertDialog appAlertDialog;
//    private boolean isToast=false;//用于是否提示用户是最新版本
//    public UpdateManager(VersionModel.VersionBean versionBean, Context context) {
//        this(versionBean,context,false);
//    }
//    public UpdateManager(VersionModel.VersionBean versionBean, Context context,boolean isToast) {
//        this.mContext = context;
//        this.versionBean=versionBean;
//        this.isToast=isToast;
//        checkUpdate();
//    }
//
//    /**
//     * 检测软件更新
//     */
//    public void checkUpdate() {
//        /**
//         * 在这里请求后台接口，获取更新的内容和最新的版本号
//         */
//        int app_code = AppUtils.getVersionCode(mContext);// 当前的版本号
//        int server_code = (int)versionBean.getVersionnum();//服务器版本号
//        Logger.d("版本比对：本地："+app_code + "...................服务器：" + server_code);
//        if (app_code < server_code) {
//            // 显示提示对话
//            showNoticeDialog(versionBean);
//        } else {
//            if (isToast) {
//                ToastUtils.showShortToast(mContext, "已经是最新版本");
//            }
//        }
//    }
//
//    /**
//     * 显示更新对话框
//     *
//     * @param version_info
//     */
//    private void showNoticeDialog(VersionModel.VersionBean version_info) {
//        if(version_info.getMustlevelup()==0){//非强制
//            appAlertDialog= new AppAlertDialog.Builder(mContext)
//                    .setDialogView(R.layout.dialog_normal)
//                    .setText(R.id.tv_title,"更新提示")
//                    .setText(R.id.bt_left,"更新")
//                    .setText(R.id.bt_right,"取消")
//                    .setText(R.id.tv_message,version_info.getUpdatecontent())
//                    .setCancelable(true)
//                    .setOnclickListener(R.id.bt_left, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            new UpdateVersionUtil(mContext).downloadBackground(versionBean.getReleaseaddress());
//                            appAlertDialog.dismiss();
//                        }
//                    })
//                    .setOnclickListener(R.id.bt_right, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            appAlertDialog.dismiss();
//                        }
//                    })
//                    .create();
//        }else{
//            appAlertDialog= new AppAlertDialog.Builder(mContext)
//                    .setDialogView(R.layout.dialog_normal)
//                    .setText(R.id.bt_left,"更新")
//                    .setText(R.id.tv_title,"更新提示")
//                    .setText(R.id.tv_message,version_info.getUpdatecontent())
//                    .setCancelable(false)
//                    .setOnclickListener(R.id.bt_left, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            new UpdateVersionUtil(mContext).downloadBackground(versionBean.getReleaseaddress());
//                            appAlertDialog.dismiss();
//                        }
//                    })
//                    .setViewVisiable(R.id.bt_right,View.GONE)
//                    .create();
//        }
//        appAlertDialog.show();
//
//    }
//
//}
