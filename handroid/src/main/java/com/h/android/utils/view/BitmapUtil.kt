package com.h.android.utils.view

import android.graphics.*
import android.view.View
import androidx.core.widget.NestedScrollView
import com.h.android.utils.HLog.d

/**
 * @author zhangxiaohui
 * @describe
 * @date 2020/3/16
 */
object BitmapUtil {
    fun createBitmapSafely(width: Int, height: Int, config: Bitmap.Config?, retryCount: Int): Bitmap? {
        return try {
            Bitmap.createBitmap(width, height, config!!)
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            if (retryCount > 0) {
                System.gc()
                return createBitmapSafely(width, height, config, retryCount - 1)
            }
            null
        }
    }

    fun createBitmapFromView(view: View): Bitmap? {
        view.clearFocus()
        val bitmap = createBitmapSafely(view.width, view.height, Bitmap.Config.ARGB_8888, 1)
        if (bitmap != null) {
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            canvas.setBitmap(null)
        }
        return bitmap
    }

    fun getBitmapByView(scrollView: NestedScrollView): Bitmap? {
        var h = 0
        var bitmap: Bitmap? = null
        // 获取scrollview实际高度
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height //获取scrollView的屏幕高度
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(
            scrollView.width, h,
            Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap) //把创建的bitmap放到画布中去
        scrollView.draw(canvas) //绘制canvas 画布
        return bitmap
    }

    fun getRoundedCornerBitmap(bitmap: Bitmap, roundPx: Float): Bitmap {
        try {
            val output = Bitmap.createBitmap(
                bitmap.width,
                bitmap.height, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(output)
            val color = Color.RED
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun imageScale(bitmap: Bitmap, dst_w: Int, dst_h: Int): Bitmap {
        val src_w = bitmap.width
        val src_h = bitmap.height
        val scale_w = dst_w.toFloat() / src_w
        val scale_h = dst_h.toFloat() / src_h
        val matrix = Matrix()
        matrix.postScale(scale_w, scale_h)
        return Bitmap.createBitmap(
            bitmap, 0, 0, src_w, src_h, matrix,
            true
        )
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     *
     * @param topBitmap
     * @param bottomBitmap
     * @param isBaseMax    是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return
     */
    fun mergeBitmap_TB(topBitmap: Bitmap?, bottomBitmap: Bitmap?, isBaseMax: Boolean): Bitmap? {
        try {
            if (topBitmap == null || topBitmap.isRecycled
                || bottomBitmap == null || bottomBitmap.isRecycled
            ) {
                d("mergetopBitmap=$topBitmap;bottomBitmap=$bottomBitmap")
                return null
            }
            var width = 0
            width = if (isBaseMax) {
                if (topBitmap.width > bottomBitmap.width) topBitmap.width else bottomBitmap.width
            } else {
                if (topBitmap.width < bottomBitmap.width) topBitmap.width else bottomBitmap.width
            }
            var tempBitmapT: Bitmap = topBitmap
            var tempBitmapB: Bitmap = bottomBitmap
            if (topBitmap.width != width) {
                tempBitmapT =
                    Bitmap.createScaledBitmap(topBitmap, width, (topBitmap.height * 1f / topBitmap.width * width).toInt(), false)
            } else if (bottomBitmap.width != width) {
                tempBitmapB = Bitmap.createScaledBitmap(
                    bottomBitmap,
                    width,
                    (bottomBitmap.height * 1f / bottomBitmap.width * width).toInt(),
                    false
                )
            }
            val height = tempBitmapT.height + tempBitmapB.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val topRect = Rect(0, 0, tempBitmapT.width, tempBitmapT.height)
            val bottomRect = Rect(0, 0, tempBitmapB.width, tempBitmapB.height)
            val bottomRectT = Rect(0, tempBitmapT.height, width, height)
            canvas.drawBitmap(tempBitmapT, topRect, topRect, null)
            canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * @param color
     * @param orginBitmap
     * @return
     */
    fun drawBgBitmap(orginBitmap: Bitmap?, color: Int): Bitmap? {
        if (orginBitmap == null) {
            return null
        }
        val paint = Paint()
        paint.color = color
        val bitmap = Bitmap.createBitmap(
            orginBitmap.width,
            orginBitmap.height, orginBitmap.config
        )
        val canvas = Canvas(bitmap)
        canvas.drawRect(0f, 0f, orginBitmap.width.toFloat(), orginBitmap.height.toFloat(), paint)
        canvas.drawBitmap(orginBitmap, 0f, 0f, paint)
        return bitmap
    }

    /**
     * 释放bitmap
     *
     * @param bitmap
     */
    fun recycle(bitmap: Bitmap?) {
        try {
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}