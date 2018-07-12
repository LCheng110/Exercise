package com.inno.home.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.DrawableRes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by qhm on 2017/12/20
 * <p>
 * 图片处理工具类
 */

public class BitmapUtil {

    public static Bitmap autoFixOrientation(File bitmapFile) {

        if (bitmapFile == null || !bitmapFile.exists()) {
            return null;
        }

        int degree;

        try {
            ExifInterface exif = new ExifInterface(bitmapFile.getPath());
            int rotateValue = Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            switch (rotateValue) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
        } catch (Exception ee) {
            return null;
        }

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(bitmapFile.getPath());
            Matrix m = new Matrix();
            m.preRotate(degree);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param bm
     * @throws IOException
     */
    public static File saveFile(Bitmap bm) throws IOException {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, "1.jpg");
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        bitmap.recycle();
        return output;
    }

    /**
     * 将Drawable转换成带透明度的bitmap
     *
     * @param drawable
     */
    public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), //
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 处理BitmapFactory.decodeResource获取bitmap为null的问题
     *
     * @param vectorDrawableId
     */
    public static Bitmap getBitmap(Context context, @DrawableRes int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    public static BitmapDrawable getDrawableFromRes(Context context, int res) {
        if (context == null) {
            return null;
        }
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(res);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}
