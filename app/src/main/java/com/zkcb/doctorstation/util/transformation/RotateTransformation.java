package com.zkcb.doctorstation.util.transformation;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Glide图片旋转转换
 */
public class RotateTransformation extends BitmapTransformation {
    private float rotateAngle = 0f;

    public RotateTransformation(Context context) {
        this(context,0f);
    }

    public RotateTransformation(Context context, float rotateAngle ) {
        super(context);
        this.rotateAngle = rotateAngle;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateAngle);
        Bitmap result = Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            result.setConfig(Bitmap.Config.ARGB_8888);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
