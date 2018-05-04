package com.inno.home.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.inno.home.utils.AppUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by liucheng on 2018/4/12
 * 图片加载工具类
 */

public class ImageLoader {

    /**
     * 图片加载 原图不进行图片的大小缩放
     */
    public static void loadImg(Context context, String url, ImageView iv) {
        loadImg(context, url, iv, 0, 0);
    }

    /**
     * 图片加载 原图不进行图片的大小缩放
     */
    public static void loadImg(Context context, String url, ImageView iv, @DrawableRes int placeholderId,
                               @DrawableRes int errorId) {
        Glide.with(context).load(url).placeholder(placeholderId).error(errorId).into(iv);
    }

    public static void loadImg(Context context, String url, final OnImageLoadingCompleted callback) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                callback.onLoadingCompleted(resource);
            }
        });
    }

    public static Bitmap loadImageReSize(Context context, String url, int width, int height) throws
            ExecutionException, InterruptedException {
        return Glide.with(context).load(url).asBitmap() //必须
                .into(width, height).get();
    }

    /**
     * 加载文件中的图片 且以圆形图片的形式展示 同时不启用 内存缓存及硬盘缓存
     */
    public static void loadCircleImage(Context context, File file, ImageView imageView, @DrawableRes int
            placeholderId) {
        Glide.with(context).load(Uri.fromFile(file)).placeholder(placeholderId).transform(new CenterCrop(context),
                new GlideCircleTransform(context)).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        loadCircleImage(context, url, imageView, 0);
    }

    /**
     * 加载圆形图片并在加载中时存在占位图
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView, @DrawableRes int
            placeholderId) {
        Glide.with(context).load(url).placeholder(placeholderId).transform(new CenterCrop(context), new
                GlideCircleTransform(context)).into(imageView);
    }

    /**
     * 加载圆形图片后返回加载成功的bitmap
     */
    public static void loadCircleImage(Context context, String url, final OnImageLoadingCompleted
            imageLoadingCompleted) {
        Glide.with(context).load(url).asBitmap().transform(new CenterCrop(AppUtil.getContext()), new
                GlideCircleTransform(context)).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                imageLoadingCompleted.onLoadingCompleted(resource);
            }
        });
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView) {
        loadRoundImage(context, url, imageView, 0);
    }

    /**
     * 加载圆角图片并在加载中时存在占位图
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView, @DrawableRes int
            placeholderId) {
        Glide.with(context).load(url).placeholder(placeholderId).transform(new CenterCrop(context), new
                GlideRoundTransform(context)).into(imageView);
    }

    /**
     * 加载圆角图片并设置圆角大小
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView, float round) {
        Glide.with(context).load(url).transform(new CenterCrop(context), new GlideRoundTransform(context, round))
                .into(imageView);
    }

    /**
     * 加载圆角图片后返回加载成功的bitmap
     */
    public static void loadRoundImage(Context context, String url, final OnImageLoadingCompleted callback) {
        Glide.with(context).load(url).asBitmap().transform(new CenterCrop(context), new GlideRoundTransform(context))
                .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                callback.onLoadingCompleted(resource);
            }
        });
    }

    public interface OnImageLoadingCompleted {
        void onLoadingCompleted(Bitmap drawable);
    }
}
