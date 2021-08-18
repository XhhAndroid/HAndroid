/*
 * *******************************************************************
 *   @项目名称: BHex Android
 *   @文件名称: SpringScaleInterpolator.java
 *   @Date: 19-4-18 下午8:17
 *   @Author: ppzhao
 *   @Description:
 *   @Copyright（C）: 2019 BlueHelix Inc.   All rights reserved.
 *   注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的.
 *  *******************************************************************
 */
package com.h.android.utils

import android.view.animation.Interpolator

class SpringScaleInterpolator(  //弹性因数
    private val factor: Float
) : Interpolator {
    override fun getInterpolation(input: Float): Float {
        return (Math.pow(
            2.0,
            (-10 * input).toDouble()
        ) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1).toFloat()
    }
}