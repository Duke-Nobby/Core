package com.zkcb.doctorstation.util.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class CircleTransformation extends BitmapTransformation {
    public CircleTransformation(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool,toTransform);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        //1. 先计算出合适的坐标，就是重新绘制的正方形bitmap的左上角的坐标
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        //2. 创建一个空白Bitmap，将在该Bitmap上铺设画布进行绘图
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_4444);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_4444);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//画笔抗锯齿

        //3. 选择原图中的中心矩形，绘制在画布上
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));

        //4. 绘制图形
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }
}
