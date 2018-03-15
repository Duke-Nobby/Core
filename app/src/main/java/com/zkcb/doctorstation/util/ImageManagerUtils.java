package com.zkcb.doctorstation.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.zkcb.doctorstation.util.transformation.CircleTransformation;

import java.io.File;

/**
 * Created by hzh on 2017/3/7.
 * 第三方图片加载框架封装
 * 依据开源框架Glide版本 包括本地图片加载 网络图片加载 图片缓存管理(路径以及清理)
 */
public class ImageManagerUtils {

    private static ImageManagerUtils ImageManagerer;
    private static final String tag = "ImageManager";
    private File cacheLocation;


    /**
     * 获取ImageManagerer的实例
     *
     * @return 图片管理实例
     */
    public static ImageManagerUtils getInstance() {
        if (ImageManagerer == null) {
            synchronized (ImageManagerUtils.class) {
                if (ImageManagerer == null) {
                    ImageManagerer = new ImageManagerUtils();
                }
            }
        }
        return ImageManagerer;
    }

    /*
    * 设置图片缓存路径和大小
    * */
    private ImageManagerUtils() {

        GlideBuilder builder = new GlideBuilder(AppProfile.getContext());
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                // 设置当前图片缓存路径,最大缓存尺寸100M
                cacheLocation = new File(AppProfile.getContext().getExternalCacheDir(), "image_cache_dir");
                cacheLocation.mkdirs();
                return DiskLruCacheWrapper.get(cacheLocation, 1024 * 1024 * 100);
            }
        });
        if (!Glide.isSetup()) {
            Glide.setup(builder);
        }
    }

    /*
    * 传过来的三种对象 Fragment、Activity、Context
    * */
    private RequestManager setContext(Object object) {
        if (object instanceof Fragment) {
            return baseGlide((Fragment) object);
        } else if (object instanceof Activity) {
            return baseGlide((Activity) object);
        } else if (object instanceof Context) {
            return baseGlide((Context) object);
        }
        return baseGlide(AppProfile.getContext());
    }

/** ---------------  加载图片方式，可根据实际需求进行添加修改  ---------------**/
    /**
     * String参数加载
     *
     * @param object
     * @param url
     * @return
     */
    private DrawableTypeRequest<String> baseGlide(Object object, String url) {
        return setContext(object).load(url);
    }

    /**
     * 资源文件加载
     *
     * @param object
     * @param resourceId
     * @return
     */
    private DrawableTypeRequest<Integer> baseGlide(Object object, int resourceId) {
        return setContext(object).load(resourceId);
    }

    /**
     * 本地文件加载
     *
     * @param object
     * @param file
     * @return
     */
    private DrawableTypeRequest<File> baseGlide(Object object, File file) {
        return setContext(object).load(file);
    }

    /**
     * Uri加载
     *
     * @param object
     * @param uri
     * @return
     */
    private DrawableTypeRequest<Uri> baseGlide(Object object, Uri uri) {
        return setContext(object).load(uri);
    }

    //根据传入参数Activity/Fragment的生命周期保持一致，去暂停和执行图片加载
    private RequestManager baseGlide(Context context) {
        return Glide.with(context);
    }

    private RequestManager baseGlide(Activity activity) {
        return Glide.with(activity);
    }

    private RequestManager baseGlide(Fragment fragment) {
        return Glide.with(fragment);
    }

    /**
     * 基本加载，缓存图片尺寸到本地
     *
     * @param object    Activity Fragment Context (传递对应的,方便Glide管理生命周期)
     * @param url       网络图片地址或者本地图片地址
     * @param imageView
     * @param defImgId  占位的图片资源id,没有传0
     * @param errImgId  图片加载错误显示的id,没有传0
     */
    public void displayImage(Object object, String url, ImageView imageView, int defImgId, int errImgId) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, url)
                .placeholder(defImgId)
                .error(errImgId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//是将图片原尺寸缓存到本地
                .into(imageView);
    }

    /**
     * 设置跳过内存缓存,和磁盘缓存
     */
    public void displayImageSkipCache(Object object, String url, ImageView imageView, int defImgId, int errImgId) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, url)
                .placeholder(defImgId)
                .error(errImgId)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    /**
     * 自定义图片转换器,可以参考圆形图片和圆角图片的写法(保存的图片为转换后的) 想要保存原图片的调用下面的方法
     *
     * @param object
     * @param url
     * @param imageView
     * @param trans     图片转换器
     */
    public void displayImageByTransfrom(Object object, String url, ImageView imageView, BitmapTransformation trans) {
        if (EmptyUtils.compareWithNull(object, imageView, trans))
            return;
        baseGlide(object, url)
                .transform(trans)
                .error(0)
                .into(imageView);
    }

    /**
     * 加载
     *
     * @param object
     * @param url
     * @param imageView
     * @param trans
     * @param errorRes
     * @param isCache   是否做缓存操作
     */
    public void displayImageByCircleTransfrom(Object object, String url, ImageView imageView, BitmapTransformation trans, int errorRes, boolean isCache) {
        if (EmptyUtils.compareWithNull(object, imageView, trans))
            return;
        baseGlide(object, url)
                .diskCacheStrategy(isCache ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                .skipMemoryCache(!isCache)
                .transform(trans)
                .error(errorRes)
                .into(imageView);
    }

    /***
     * 加载用户头像 默认缓存处理
     * @param context
     * @param url
     * @param imageView
     * @param isCache 是否缓存
     */
    public void displayCircleTransfrom(Context context, String url, ImageView imageView, boolean isCache) {
        if (EmptyUtils.compareWithNull(context, imageView)) {
            return;
        }
        displayImageByCircleTransfrom(context, url, imageView, new CircleTransformation(context), 0, isCache);
    }

    /**
     * /*
     * 自定义图片转换器,无缓存加载图片
     */
    public void displayImageWithOutCache(Object object, String url, ImageView imageView, BitmapTransformation trans) {
        if (EmptyUtils.compareWithNull(object, imageView, trans))
            return;
        baseGlide(object, url)
                .transform(trans)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    /**
     * 自定义图片转换器,无缓存加载图片（公司头像专用）
     */
    public void displayImageWithOutCacheIcon(Object object, String url, ImageView imageView, BitmapTransformation trans) {
        if (EmptyUtils.compareWithNull(object, imageView, trans))
            return;
        baseGlide(object, url)
                .transform(trans)
                .error(0)
                .into(imageView);
    }

    /**
     * 自定义图片转换器,无缓存加载图片（用户头像专用）
     */
    public void displayImageWithOutCacheIconComp(Object object, String url, ImageView imageView, BitmapTransformation trans) {
        if (EmptyUtils.compareWithNull(object, imageView, trans))
            return;
        baseGlide(object, url)
                .transform(trans)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(0)
                .into(imageView);
    }


    /**
     * 图片转换器,并且会缓存不同尺寸的图片
     *
     * @param object
     * @param url
     * @param imageView
     * @param trans
     */
    public void displayImageByTrans4All(Object object, String url, ImageView imageView, BitmapTransformation trans) {
        if (EmptyUtils.compareWithNull(object, imageView, trans))
            return;
        baseGlide(object, url).transform(trans).diskCacheStrategy(DiskCacheStrategy.ALL).error(0).into(imageView);
    }

    /**
     * 本地不做缓存的图片加载
     *
     * @param object
     * @param url
     * @param imageView
     * @param defID
     * @param errID
     */
    public void displayImageWithOutCache(Object object, String url, ImageView imageView, int defID, int errID) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(defID).error(errID).into(imageView);
    }

    /**
     * 本地不做缓存的图片加载,添加加载状态的监听
     *
     * @param object
     * @param url
     * @param imageView
     * @param defID
     * @param errID
     */
    public void displayImageWithOutCache(Object object, String url, GlideDrawableImageViewTarget imageView, int defID, int errID) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(defID).error(errID).into(imageView);
    }

    /**
     * 加载图片资源
     *
     * @param object
     * @param id
     * @param imageView
     * @param defImgId
     * @param errImgId
     */
    public void displayImageById(Object object, int id, ImageView imageView, int defImgId, int errImgId) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, id).placeholder(defImgId).error(errImgId).into(imageView);
    }

    /**
     * 判断图片资源，本地存在加载本地，否则加载网络图片资源
     *
     * @param object
     * @param defUrl    本地存储路径
     * @param url
     * @param imageView
     * @param defImgId
     * @param errImgId
     */
    public void displayImage(Object object, String defUrl, String url, ImageView imageView, int defImgId, int errImgId) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        if (!StringUtils.isEmpty(defUrl) && FileUtils.isFileExists(defUrl)) {
            displayImage(object, defUrl, imageView, defImgId, errImgId);
        } else {
            displayImage(object, url, imageView, defImgId, errImgId);
        }
    }

    /**
     * 加载图片直接转换为bitmap
     *
     * @param object
     * @param url
     * @param simpleTarget 下载完成的对象
     */
    public void downloadImage(Object object, String url, SimpleTarget<Bitmap> simpleTarget) {
        if (EmptyUtils.compareWithNull(object, simpleTarget))
            return;
        baseGlide(object, url).asBitmap().into(simpleTarget);
    }

    /**
     * 下载图片文件,提供一个最简单的实现BaseFileTarget抽象类(文件下载完成,文件下载失败)
     *
     * @param object
     * @param url
     * @param target
     */
    public void downloadImage4File(Object object, String url, Target<File> target) {
        if (EmptyUtils.compareWithNull(object, target))
            return;
        baseGlide(object, url).downloadOnly(target);
    }

    /**
     * 下载图片,并获取下载的状态(可以显示加载状态等)
     *
     * @param object
     * @param url
     * @param imageView
     * @param requestListener 下载状态的listener
     * @param defImgId
     * @param errImgId
     */
    public void displayImageByStatus(Object object, String url, ImageView imageView, RequestListener<String, GlideDrawable> requestListener, int defImgId, int errImgId) {
        if (EmptyUtils.compareWithNull(object, imageView, requestListener))
            return;
        DrawableTypeRequest<String> load = baseGlide(object, url);
        load.placeholder(defImgId).error(errImgId).listener(requestListener).into(imageView);
    }

    /**
     * 显示指定尺寸的图片
     *
     * @param object
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public void displayImageBySize(Object object, String url, final ImageView imageView, int width, int height) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, url).asBitmap().error(0).into(new SimpleTarget<Bitmap>(width, height) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (resource != null)
                    imageView.setImageBitmap(resource);
            }
        });
    }

    /**
     * 显示缩略图后再显示全图 0.1f 先显示一个原图1/10的缩率图,然后再显示全图
     *
     * @param object
     * @param url
     * @param imageView
     * @param thumb
     */
    public void displayImageByThumb(Object object, String url, ImageView imageView, float thumb) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, url).thumbnail(thumb).error(0).into(imageView);
    }

    /*
     * 聊天列表加载
     */
//    public void displayChatPhoto(Context context, String url, ImageView view, boolean isReceive) {
//        // baseGlide(context, url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(target);
//        if (EmptyUtils.compareWithNull(context, view))
//            return;
//        baseGlide(context, url).asBitmap().placeholder(R.drawable.img_chat_picture_def).error(R.drawable.img_chat_picture_fail).diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(new GlideChatTarget(context, view, 0, isReceive));
//    }

    /**
     * 照片墙
     *
     * @param context
     * @param url
     * @param view
     * @param defID
     * @param errID
     */
    public void displayPhotoWall(Context context, String url, ImageView view, int defID, int errID) {
        if (EmptyUtils.compareWithNull(context, view))
            return;
        baseGlide(context, url).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE).// 设置本地不缓存
                placeholder(defID).error(errID).crossFade().into(view);
    }

    /**
     * 加载GIf图片
     *
     * @param object
     * @param url       Gif路径
     * @param imageView
     * @param defID
     * @param errID
     */
    public void displayImageWithGif(Object object, String url, ImageView imageView, int defID, int errID) {
        if (EmptyUtils.compareWithNull(object, imageView))
            return;
        baseGlide(object, url).asGif().placeholder(defID).error(errID).into(imageView);
    }

    /**
     * 停请求(listview滑动时可以调用等等)
     *
     * @param object
     */
    public void pauseRequsts(Object object) {
        if (EmptyUtils.compareWithNull(object))
            return;
        setContext(object).pauseRequests();
    }

    /**
     * 恢复请求
     *
     * @param object Activity Fragment Context (传递对应的,方便Glide管理生命周期)
     */
    public void resumeRequests(Object object) {
        if (EmptyUtils.compareWithNull(object))
            return;
        setContext(object).resumeRequests();
    }

    /**
     * 手动清理缓存
     */
    public void clearMemoryChace(Context context) {
        if (EmptyUtils.compareWithNull(context))
            return;
        Glide.get(context).clearMemory();
    }

    /**
     * 清理本地缓存文件,慎用!!
     */
    public void clearDiskChace(final Context context) {
        if (EmptyUtils.compareWithNull(context))
            return;
        new Thread() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }.start();

    }

    /**
     * 根据url判断是否是gif，然后显示
     *
     * @param object
     * @param url
     * @param imageView
     * @param gifDefID
     * @param gifErrID
     * @param defID
     * @param errID
     */
    public void showGifOrOther(Object object, String url, ImageView imageView, int gifDefID, int gifErrID, int defID, int errID) {
        LogUtil.e(tag, url + "url");
        if ("gif".equals(url.substring(url.length() - 3))) {
            ImageManagerUtils.getInstance().displayImageWithGif(this, url, imageView, gifDefID, gifErrID);
        } else {
            ImageManagerUtils.getInstance().displayImage(this, url, imageView, defID, errID);
        }
    }
}
