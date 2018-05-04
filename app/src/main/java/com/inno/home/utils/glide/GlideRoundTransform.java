package com.inno.home.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.inno.home.utils.UiUtil;

/**
 * Created by rookie on 17/6/27.
 */

public class GlideRoundTransform extends BitmapTransformation {
    private float radiusX = UiUtil.dip2px(4);
    private float radiusY = UiUtil.dip2px(4);
    private float roundScale = 0.15f;

    public GlideRoundTransform(Context context) {
        this(context, 0);
    }

    public GlideRoundTransform(Context context, float roundScale) {
        super(context);
        this.roundScale = roundScale;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int bitmapWidth = source.getWidth();
        int bitmapHeight = source.getHeight();
        Bitmap result = pool.get(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        }
        if (roundScale != 0) {
            radiusX = bitmapWidth * roundScale;
            radiusY = bitmapHeight * roundScale;
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, bitmapWidth, bitmapHeight);
        canvas.drawRoundRect(rectF, radiusX, radiusY, paint);
//        source.recycle();
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radiusX) + Math.round(radiusY);
    }
}
