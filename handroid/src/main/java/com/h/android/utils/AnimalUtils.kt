package com.h.android.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

/**
 *2020/11/20
 *@author zhangxiaohui
 *@describe
 */
class AnimalUtils {
    private val ANIM_PROPERTY_ROTATE_NAME = "rotation"
    private val ANIM_PROPERTY_SCALE_NAME = "scaleX"
    private val ANIM_PROPERTY_ALPHA_NAME = "alpha"
    private val ANIM_PROPERTY_TRANS_NAME = "translationX"


    fun rotateyAnimRun( view: View, start: Float, end: Float ) {
        val anim = ObjectAnimator
            .ofFloat(view, ANIM_PROPERTY_ROTATE_NAME, start, end)
            .setDuration(500)
        anim.start()
        anim.addUpdateListener { animation ->
            val cVal = animation.animatedValue as Float
            view.rotation = cVal
        }
    }

    fun scaleAnimRun(view: View,start: Float,end: Float) {
        val anim = ObjectAnimator
            .ofFloat(view, ANIM_PROPERTY_SCALE_NAME, start, end)
            .setDuration(500)
        anim.start()
        anim.addUpdateListener { animation ->
            val cVal = animation.animatedValue as Float
            view.scaleX = cVal
        }
    }

    fun scaleXYAnimRun(view: View,start: Float,end: Float) {
        val anim = ObjectAnimator
            .ofFloat(view, ANIM_PROPERTY_SCALE_NAME, start, end)
            .setDuration(500)
        anim.start()
        anim.addUpdateListener { animation ->
            val cVal = animation.animatedValue as Float
            view.scaleX = cVal
            view.scaleY = cVal
        }
    }

    fun alphaAnimRun(view: View,start: Float,end: Float) {
        val anim = ObjectAnimator
            .ofFloat(view, ANIM_PROPERTY_ALPHA_NAME, start, end)
            .setDuration(500)
        anim.start()
        anim.addUpdateListener { animation ->
            val cVal = animation.animatedValue as Float
            view.alpha = cVal
        }
    }


    fun transAnimRun(view: View,startX: Float,endX: Float) {
        val anim = ObjectAnimator
            .ofFloat(view, ANIM_PROPERTY_TRANS_NAME, startX, endX)
            .setDuration(500)
        anim.start()
        anim.addUpdateListener { animation ->
            val cVal = animation.animatedValue as Float
            view.translationX = cVal
        }
    }

    fun transAnimRun(view: View,startX: Float,endX: Float,startY: Float,endY: Float) {
        val anim = ObjectAnimator
            .ofFloat(view, ANIM_PROPERTY_TRANS_NAME, startY, endY)
            .setDuration(1000)
        anim.start()
        anim.addUpdateListener { animation ->
            val cVal = animation.animatedValue as Float
            //                view.setTranslationX(cVal);
            view.translationY = cVal
        }
    }

    /**
     * 缩放回弹动画
     * @param view
     */
    fun onScaleAnimation(view: View?) {
        val animatorX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1.0f)
        val animatorY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1.0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = SpringScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }
}