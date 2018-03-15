package com.zkcb.doctorstation.util;

import android.widget.EditText;

/**
 * Created by shy on 2017/3/2.
 */
public class EdtUtil {

    public static boolean isEdtEmpty(EditText editText) {
        if (editText == null) {
            return true;
        }
        String text = editText.getText().toString().trim();
        return EmptyUtils.compareWithNull(text);
    }


    public static String getEdtText(EditText editText) {
        if (editText == null) {
            return null;
        }
        String text = editText.getText().toString().trim().replace(" ", "");
        return text;
    }
}
