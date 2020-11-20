package com.h.android.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.h.android.R;

import java.io.ByteArrayOutputStream;

/**
 * 图片加载
 * @author zhangxiaohui
 */
public class ImageLoadUtil {

    public static void loadHttpImage(Context context, String imageUrl, ImageView imageView) {
        loadHttpImage(context, imageUrl, imageView, R.drawable.loading_default_image);
    }

    public static void loadHttpImageCircle(Context context, String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(imageUrl).apply(requestOptions).circleCrop().placeholder(R.drawable.loading_default_image).into(imageView);
    }

    public static void loadHttpImage(Context context, String imageUrl, ImageView imageView, int defaultImage) {
        if (context == null) {
            return;
        }
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(imageUrl).apply(requestOptions).placeholder(defaultImage).into(imageView);
    }

    public static void loadHttpImage(Context context, Bitmap bitmap, ImageView imageView) {
        if (context == null) {
            return;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(context).asBitmap().load(stream.toByteArray()).diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.loading_default_image).into(imageView);
        bitmap.recycle();
    }

}
