package com.h.android.utils.view

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.h.android.R
import java.io.ByteArrayOutputStream

/**
 * 图片加载
 * @author zhangxiaohui
 */
object ImageLoadUtil {
    fun loadHttpImageCircle(context: Context?, imageUrl: String?, imageView: ImageView?) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(context!!).load(imageUrl).apply(requestOptions).circleCrop().placeholder(R.drawable.loading_default_image)
            .into(
                imageView!!
            )
    }

    @JvmOverloads
    fun loadHttpImage(
        context: Context?,
        imageUrl: String?,
        imageView: ImageView?,
        defaultImage: Int = R.drawable.loading_default_image
    ) {
        if (context == null) {
            return
        }
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(context).load(imageUrl).apply(requestOptions).placeholder(defaultImage).into(imageView!!)
    }

    fun loadHttpImage(context: Context?, bitmap: Bitmap, imageView: ImageView?) {
        if (context == null) {
            return
        }
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        Glide.with(context).asBitmap().load(stream.toByteArray()).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.loading_default_image).into(
            imageView!!
        )
        bitmap.recycle()
    }
}