package com.zkcb.doctorstation.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;


/**
 * Created by ht-template
 **/
public class ResourcesUtil {
    public static String getString(@StringRes int stringId) {
        LogUtil.e("getString"+"/////"+(AppProfile.getContext()== null));
        LogUtil.e("getString"+"/////"+(AppProfile.getContext().getResources() == null));
        return AppProfile.getContext().getResources().getString(stringId);
    }

    public static String[] getStringArray(@ArrayRes int arrayId) {
        return AppProfile.getContext().getResources().getStringArray(arrayId);
    }

    public static String stringFormat(@StringRes int stringId, Object... objects) {
        String formatString = getString(stringId);
        return String.format(formatString, objects);
    }

    public static int getColor(@ColorRes int colorId) {
        LogUtil.e("getColor"+(AppProfile.getContext().getResources() == null));
        return AppProfile.getContext().getResources().getColor(colorId);
    }

    public static float getDimen(@DimenRes int dimenId) {
        return AppProfile.getContext().getResources().getDimension(dimenId);
    }


    public static int getDimenPxSize(@DimenRes int dimenId) {
        return AppProfile.getContext().getResources().getDimensionPixelSize(dimenId);
    }

    public static Drawable getDrawable(@DrawableRes int drawableID) {

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP_MR1){
            return SDK22.getDrawable(drawableID);
        }else{
        return AppProfile.getContext().getResources().getDrawable(drawableID);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    static class SDK22 {
        public static Drawable getDrawable(int drawableID) {
            return AppProfile.getContext().getResources().getDrawable(drawableID, null);
        }
    }

    public static Uri getUri(int resourceId) {
        Resources r = AppProfile.getContext().getResources();
        String url = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resourceId) + "/"
                + r.getResourceTypeName(resourceId) + "/"
                + r.getResourceEntryName(resourceId);
        return Uri.parse(url);
    }

    public static Resources getResources() {
        return AppProfile.getContext().getResources();
    }
}
