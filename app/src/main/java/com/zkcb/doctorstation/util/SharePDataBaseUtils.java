package com.zkcb.doctorstation.util;

import android.content.Context;

/**
 * Created by shy on 2017/3/15.
 * 共享参数工具类 临时存些变量
 */
public class SharePDataBaseUtils {
    //省市区更新时间
    public static void saveUpdateTime(Context context, long lasttime) {
        SharedPrefrencesUtil.saveData(context, "user", "lasttime", lasttime);
    }

    public static long getUpdateTime(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "lasttime", 0l);
    }

    //个人端跳过资料完善
    public static void saveJump(Context context, String exitapp) {
        SharedPrefrencesUtil.saveData(context, "user", "jump", exitapp);
    }

    public static String getJump(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "jump", "0");
    }

    //记录上次从个人端还是企业端首页退出APP的
    public static void saveExitApp(Context context, String exitapp) {
        SharedPrefrencesUtil.saveData(context, "user", "exitapp", exitapp);
    }

    public static String getExitApp(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "exitapp", "0");
    }

    //存储启动页广告图的Url、Bitmap字符串
    public static void saveSplahAddsBitmap(Context context, String bitmapStr) {
        SharedPrefrencesUtil.saveData(context, "user", "AddsBitmapStr", bitmapStr);
    }

    public static String getSplahAddsBitmap(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "AddsBitmapStr", "");
    }

    //用户uid
    public static void saveUid(Context context, long uid) {
        SharedPrefrencesUtil.saveData(context, "user", "uid", uid);
    }

    public static long getUid(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "uid", 0L);
    }

    //校园大使身份
    public static void saveType(Context context, int type ) {
        SharedPrefrencesUtil.saveData(context, "user", "type", type );
    }

    public static int getType(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "type", 0);
    }

    //存储uType
    public static void saveUType(Context context, int utype ) {
        SharedPrefrencesUtil.saveData(context, "user", "utype", utype );
    }

    public static int getUType(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "utype", 0);
    }

    //存储token
    public static void saveToken(Context context, String token) {
        SharedPrefrencesUtil.saveData(context, "user", "token", token);
    }

    public static String getToken(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "token", "");
    }

    //是否点击记住密码
    public static void saveIsChecked(Context context, boolean isChecked) {
        SharedPrefrencesUtil.saveData(context, "user", "isCheck", isChecked);
    }

    public static boolean getIsChecked(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "isCheck", false);
    }

    //用户名
    public static void saveUserName(Context context, String username) {
        SharedPrefrencesUtil.saveData(context, "user", "username", username);
    }

    public static String getUserName(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "username", "");
    }

    //密码
    public static void saveUserPwd(Context context, String userpwd) {
        SharedPrefrencesUtil.saveData(context, "user", "userpwd", userpwd);
    }

    public static String getUserPwd(Context context) {
        return SharedPrefrencesUtil.getData(context, "user", "userpwd", "");
    }
}
