package com.h.android.utils.number

import androidx.annotation.CheckResult
import java.lang.reflect.Array
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * 2020/12/5
 *
 * @author zhangxiaohui
 * @describe
 */
object NumberUtils {
    /**
     * 向上摄入
     * 5.5->6
     *
     * @param number
     * @param minFractionDigits
     * @param maxFractionDigits
     * @return
     */
    fun formatRoundUp(
        number: Any,
        minFractionDigits: Int,
        maxFractionDigits: Int
    ): String? {
        return format(number, minFractionDigits, maxFractionDigits, RoundingMode.UP)
    }

    /**
     * 向内取整模式
     * 5.5->5
     *
     * @param number
     * @param minFractionDigits
     * @param maxFractionDigits
     * @return
     */
    fun formatRoundDown(
        number: Any,
        minFractionDigits: Int,
        maxFractionDigits: Int
    ): String? {
        return format(number, minFractionDigits, maxFractionDigits, RoundingMode.DOWN)
    }

    /**
     * 四舍五入
     * 5.5->6
     * 5.1->5
     *
     * @param number
     * @param minFractionDigits
     * @param maxFractionDigits
     * @return
     */
    fun formatRoundHalfUp(
        number: Any,
        minFractionDigits: Int,
        maxFractionDigits: Int
    ): String? {
        return format(number, minFractionDigits, maxFractionDigits, RoundingMode.HALF_UP)
    }

    /**
     * 格式化数字
     *
     * @param number
     * @param minFractionDigits 小数位最小长度
     * @param maxFractionDigits 小数位最大长度
     * @param roundingMode      舍入模式模式
     * @return
     */
    fun format(
        number: Any,
        minFractionDigits: Int,
        maxFractionDigits: Int,
        roundingMode: RoundingMode?
    ): String? {
        return format(
            number,
            minFractionDigits,
            maxFractionDigits,
            roundingMode, Int.MAX_VALUE
        )
    }

    /**
     * @param number
     * @param minFractionDigits 小数位最小长度
     * @param maxFractionDigits 小数位最大长度
     * @param roundingMode      舍入模式模式
     * @param maxLength         最大长度 字符串截取 比如设置5 10023787834.10->10023
     * @return
     */
    fun format(
        number: Any,
        minFractionDigits: Int,
        maxFractionDigits: Int,
        roundingMode: RoundingMode?,
        maxLength: Int
    ): String? {
        return format(
            number,
            minFractionDigits,
            maxFractionDigits,
            roundingMode,
            maxLength,
            false, 0.toString()
        )
    }


    /**
     * @param number
     * @param minFractionDigits 小数位最小长度
     * @param maxFractionDigits 小数位最大长度
     * @param roundingMode      舍入模式模式
     * @param sign              是否转换有符号数
     * @return
     */
    fun format(
        number: Any,
        minFractionDigits: Int,
        maxFractionDigits: Int,
        roundingMode: RoundingMode?,
        sign: Boolean
    ): String? {
        return format(
            number,
            minFractionDigits,
            maxFractionDigits,
            roundingMode, Int.MAX_VALUE,
            sign, 0.toString()
        )
    }


    /**
     * 1、ROUND_UP：向远离零的方向舍入。
     * 若舍入位为非零，则对舍入部分的前一位数字加1；若舍入位为零，则直接舍弃。即为向外取整模式。
     *
     *
     * 2、ROUND_DOWN：向接近零的方向舍入。
     * 不论舍入位是否为零，都直接舍弃。即为向内取整模式。
     *
     *
     * 3、ROUND_CEILING：向正无穷大的方向舍入。
     * 若 BigDecimal 为正，则舍入行为与 ROUND_UP 相同；若为负，则舍入行为与 ROUND_DOWN 相同。即为向上取整模式。
     *
     *
     * 4、ROUND_FLOOR：向负无穷大的方向舍入。
     * 若 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同；若为负，则舍入行为与 ROUND_UP 相同。即为向下取整模式。
     *
     *
     * 5、ROUND_HALF_UP：向“最接近的”整数舍入。
     * 若舍入位大于等于5，则对舍入部分的前一位数字加1；若舍入位小于5，则直接舍弃。即为四舍五入模式。
     *
     *
     * 6、ROUND_HALF_DOWN：向“最接近的”整数舍入。
     * 若舍入位大于5，则对舍入部分的前一位数字加1；若舍入位小于等于5，则直接舍弃。即为五舍六入模式。
     *
     *
     * 7、ROUND_HALF_EVEN：向“最接近的”整数舍入。
     * 若（舍入位大于5）或者（舍入位等于5且前一位为奇数），则对舍入部分的前一位数字加1；
     * 若（舍入位小于5）或者（舍入位等于5且前一位为偶数），则直接舍弃。即为银行家舍入模式。
     *
     *
     * 8、ROUND_UNNECESSARY
     * 断言请求的操作具有精确的结果，因此不需要舍入。
     * 如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException。
     *
     * @param number
     * @param minFractionDigits 小数位最小长度
     * @param maxFractionDigits 小数位最大长度
     * @param roundingMode      舍入模式模式
     * @param maxLength         最大长度 字符串截取 比如设置5 10023787834.10->10023
     * @param sign              是否转换有符号数
     * @param defaultValue      异常默认值
     * @return
     */
    fun format(
        number: Any,
        minFractionDigits: Int,
        maxFractionDigits: Int,
        roundingMode: RoundingMode?,
        maxLength: Int,
        sign: Boolean,
        defaultValue: String?
    ): String? {
        try {
            val numberInstance = NumberFormat.getNumberInstance(Locale.CHINA)
            numberInstance.isGroupingUsed = false
            numberInstance.minimumFractionDigits = minFractionDigits
            numberInstance.maximumFractionDigits = maxFractionDigits
            numberInstance.roundingMode = roundingMode
            if (sign) {
                try {
                    val decimalFormat = numberInstance as DecimalFormat
                    decimalFormat.positivePrefix = "+"
                    decimalFormat.negativePrefix = "-"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            /**
             * 需要用BigDecimal 来接收否则有精度损失问题
             */
            var format = numberInstance.format(innerConvertDecimal(number))
            if (format.length > maxLength) {
                format = format.substring(0, maxLength)
                if (format.endsWith(".")) {
                    format = format.substring(0, format.length - 1)
                }
            }
            return format
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 万能转换Decimal  double转string会有精度丢失问题 且BigDecimal构造函数必须是具体类型
     *
     * @param number
     * @return
     */
    @Throws(NumberFormatException::class, ArithmeticException::class)
    private fun innerConvertDecimal(number: Any): BigDecimal {
        if ((number is Long || number is Int ||
                    number is Short || number is Byte ||
                    number is AtomicInteger ||
                    number is AtomicLong ||
                    number is BigInteger &&
                    number.bitLength() < 64)
        ) {
            return BigDecimal((number as Number).toLong())
        } else if (number is BigDecimal) {
            return number
        } else if (number is BigInteger) {
            return BigDecimal(number)
        } else if (number is Float) {
            /**
             * 直接转有精度丢失 系统16位截取
             */
            val bigDecimal = BigDecimal(number.toString())
            return if (bigDecimal.scale() < 16) {
                bigDecimal
            } else BigDecimal(number.toString())
            /**
             * 直接构造函数又有 四舍五入的问题
             * 如:1.9->1.89999997
             */
        } else if (number is Double) {
            /**
             * 直接转有精度丢失 系统16位截取
             */
            val bigDecimal = BigDecimal(number.toString())
            return if (bigDecimal.scale() < 16) {
                bigDecimal
            } else BigDecimal(number)
            /**
             * 直接构造函数又有 四舍五入的问题
             * 如:1.9->1.89999997
             */
        } else return if (number is String) {
            BigDecimal(number)
        } else {
            BigDecimal(number.toString())
        }
    }

    fun add(b1: Any, b2: Any): BigDecimal {
        return (add(b1, b2, BigDecimal(0)))!!
    }

    /**
     * 加法
     *
     * @param b1
     * @param b2
     * @return
     */
    @CheckResult
    fun add(b1: Any, b2: Any, defaultValue: BigDecimal?): BigDecimal? {
        try {
            val bc1 = innerConvertDecimal(b1)
            val bc2 = innerConvertDecimal(b2)
            return bc1.add(bc2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 减法
     *
     * @param b1
     * @param b2
     * @return
     */
    fun subtract(b1: Any, b2: Any): BigDecimal {
        return (subtract(b1, b2, BigDecimal(0)))!!
    }

    /**
     * 减法
     *
     * @param b1
     * @param b2
     * @param defaultValue
     * @return
     */
    @CheckResult
    fun subtract(b1: Any, b2: Any, defaultValue: BigDecimal?): BigDecimal? {
        try {
            val bc1 = innerConvertDecimal(b1)
            val bc2 = innerConvertDecimal(b2)
            return bc1.subtract(bc2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 乘法
     *
     * @param b1
     * @param b2
     * @return
     */
    fun multiply(b1: Any, b2: Any): BigDecimal {
        return (multiply(b1, b2, BigDecimal(0)))!!
    }

    /**
     * 乘法
     *
     * @param b1
     * @param b2
     * @param defaultValue
     * @return
     */
    @CheckResult
    fun multiply(b1: Any, b2: Any, defaultValue: BigDecimal?): BigDecimal? {
        try {
            val bc1 = innerConvertDecimal(b1)
            val bc2 = innerConvertDecimal(b2)
            return bc1.multiply(bc2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 除法
     * 会出现 : Non-terminating decimal expansion; no exact representable decimal result.
     * 因为BigDecimal的divide方法出现了无限循环小数
     * 默认最多16位小数,且舍入模式为FLOOR 正数等同于down 负数则更小  5.5->5    5.5->-6
     *
     * @param b1
     * @param b2
     * @return
     */
    fun divide(b1: Any, b2: Any): BigDecimal {
        return divide(b1, b2, 16)
    }

    /**
     * 除法
     *
     * @param b1
     * @param b2
     * @param scale 保留小数位 默认舍入模式为FLOOR 正数等同于down 负数则更小  5.5->5    5.5->-6
     * @return
     */
    fun divide(b1: Any, b2: Any, scale: Int): BigDecimal {
        return (divide(b1, b2, scale, RoundingMode.FLOOR))!!
    }

    /**
     * @param b1
     * @param b2
     * @param scale        保留小数位
     * @param roundingMode
     * @return
     */
    fun divide(b1: Any, b2: Any, scale: Int, roundingMode: RoundingMode?): BigDecimal? {
        return divide(b1, b2, scale, roundingMode, BigDecimal(0))
    }

    /**
     * 除法
     *
     * @param b1
     * @param b2
     * @param scale        保留小数位
     * @param roundingMode 舍入模式
     * @param defaultValue
     * @return
     */
    @CheckResult
    fun divide(b1: Any, b2: Any, scale: Int, roundingMode: RoundingMode?, defaultValue: BigDecimal?): BigDecimal? {
        try {
            val bc1 = innerConvertDecimal(b1)
            val bc2 = innerConvertDecimal(b2)
            return bc1.divide(bc2, scale, roundingMode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 最小值
     *
     * @param b1
     * @param b2
     * @return
     */
    fun min(b1: BigDecimal?, b2: BigDecimal?): BigDecimal {
        return if (b1 == null || b2 == null) {
            BigDecimal(0)
        } else b1.min(b2)
    }

    fun min(vararg array: BigDecimal): BigDecimal {
        try {
            if (array != null && array.size > 0) {
                var min = array[0]
                for (bigDecimal: BigDecimal in array) {
                    if (bigDecimal.compareTo(min) < 0) {
                        min = bigDecimal
                    }
                }
                return min
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return BigDecimal(Int.MIN_VALUE)
    }

    /**
     * 最大值
     *
     * @param array 数字
     * @return 最大值
     */
    fun max(vararg array: BigDecimal): BigDecimal {
        try {
            if (array != null && array.size > 0) {
                var max = array[0]
                for (bigDecimal: BigDecimal in array) {
                    if (bigDecimal.compareTo(max) > 0) {
                        max = bigDecimal
                    }
                }
                return max
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return BigDecimal(Int.MAX_VALUE)
    }

    /**
     * 是否在闭区间内eg.[3,5]
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    fun inClosedRange(value: Any, min: Any, max: Any): Boolean {
        try {
            val bdValue = innerConvertDecimal(value)
            val bdMin = innerConvertDecimal(min)
            val bdMax = innerConvertDecimal(max)
            return inClosedRange(bdValue, bdMin, bdMax)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 是否在闭区间内eg.[3,5]
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    fun inClosedRange(value: BigDecimal, min: BigDecimal, max: BigDecimal): Boolean {
        return if ((value == null) || (min == null) || (max == null)) {
            false
        } else (max.compareTo(min) > 0) && (value.compareTo(min) >= 0) && (value.compareTo(max) <= 0)
    }

    /**
     * 是否在开区间内eg.(3,5)
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    fun inOpenedRange(value: Any, min: Any, max: Any): Boolean {
        try {
            val bdValue = innerConvertDecimal(value)
            val bdMin = innerConvertDecimal(min)
            val bdMax = innerConvertDecimal(max)
            return inOpenedRange(bdValue, bdMin, bdMax)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 是否在开区间内eg.(3,5)
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    fun inOpenedRange(value: BigDecimal, min: BigDecimal, max: BigDecimal): Boolean {
        return if ((value == null) || (min == null) || (max == null)) {
            false
        } else (max.compareTo(min) > 0) && (value.compareTo(min) > 0) && (value.compareTo(max) < 0)
    }

    //-----------------------------------------------------------------------
    /**
     *
     * Convert a `String` to an `int`, returning
     * `zero` if the conversion fails.
     *
     *
     * If the string is `null`, `zero` is returned.
     *
     * <pre>
     * NumberUtils.toInt(null) = 0
     * NumberUtils.toInt("")   = 0
     * NumberUtils.toInt("1")  = 1
    </pre> *
     *
     * @param str the string to convert, may be null
     * @return the int represented by the string, or `zero` if
     * conversion fails
     * @since 2.1
     */
    fun toInt(str: String?): Int {
        return toInt(str, 0)
    }

    /**
     *
     * Convert a `String` to an `int`, returning a
     * default value if the conversion fails.
     *
     *
     * If the string is `null`, the default value is returned.
     *
     * <pre>
     * NumberUtils.toInt(null, 1) = 1
     * NumberUtils.toInt("", 1)   = 1
     * NumberUtils.toInt("1", 0)  = 1
    </pre> *
     *
     * @param str          the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion fails
     * @since 2.1
     */
    fun toInt(str: String?, defaultValue: Int): Int {
        if (str == null) {
            return defaultValue
        }
        try {
            return str.toInt()
        } catch (nfe: NumberFormatException) {
            return defaultValue
        }
    }

    /**
     *
     * Convert a `String` to a `long`, returning
     * `zero` if the conversion fails.
     *
     *
     * If the string is `null`, `zero` is returned.
     *
     * <pre>
     * NumberUtils.toLong(null) = 0L
     * NumberUtils.toLong("")   = 0L
     * NumberUtils.toLong("1")  = 1L
    </pre> *
     *
     * @param str the string to convert, may be null
     * @return the long represented by the string, or `0` if
     * conversion fails
     * @since 2.1
     */
    fun toLong(str: String?): Long {
        return toLong(str, 0L)
    }

    /**
     *
     * Convert a `String` to a `long`, returning a
     * default value if the conversion fails.
     *
     *
     * If the string is `null`, the default value is returned.
     *
     * <pre>
     * NumberUtils.toLong(null, 1L) = 1L
     * NumberUtils.toLong("", 1L)   = 1L
     * NumberUtils.toLong("1", 0L)  = 1L
    </pre> *
     *
     * @param str          the string to convert, may be null
     * @param defaultValue the default value
     * @return the long represented by the string, or the default if conversion fails
     * @since 2.1
     */
    fun toLong(str: String?, defaultValue: Long): Long {
        if (str == null) {
            return defaultValue
        }
        try {
            return str.toLong()
        } catch (nfe: NumberFormatException) {
            return defaultValue
        }
    }

    /**
     *
     * Convert a `String` to a `float`, returning
     * `0.0f` if the conversion fails.
     *
     *
     * If the string `str` is `null`,
     * `0.0f` is returned.
     *
     * <pre>
     * NumberUtils.toFloat(null)   = 0.0f
     * NumberUtils.toFloat("")     = 0.0f
     * NumberUtils.toFloat("1.5")  = 1.5f
    </pre> *
     *
     * @param str the string to convert, may be `null`
     * @return the float represented by the string, or `0.0f`
     * if conversion fails
     * @since 2.1
     */
    fun toFloat(str: String?): Float {
        return toFloat(str, 0.0f)
    }

    /**
     *
     * Convert a `String` to a `float`, returning a
     * default value if the conversion fails.
     *
     *
     * If the string `str` is `null`, the default
     * value is returned.
     *
     * <pre>
     * NumberUtils.toFloat(null, 1.1f)   = 1.0f
     * NumberUtils.toFloat("", 1.1f)     = 1.1f
     * NumberUtils.toFloat("1.5", 0.0f)  = 1.5f
    </pre> *
     *
     * @param str          the string to convert, may be `null`
     * @param defaultValue the default value
     * @return the float represented by the string, or defaultValue
     * if conversion fails
     * @since 2.1
     */
    fun toFloat(str: String?, defaultValue: Float): Float {
        if (str == null) {
            return defaultValue
        }
        try {
            return str.toFloat()
        } catch (nfe: NumberFormatException) {
            return defaultValue
        }
    }

    /**
     *
     * Convert a `String` to a `double`, returning
     * `0.0d` if the conversion fails.
     *
     *
     * If the string `str` is `null`,
     * `0.0d` is returned.
     *
     * <pre>
     * NumberUtils.toDouble(null)   = 0.0d
     * NumberUtils.toDouble("")     = 0.0d
     * NumberUtils.toDouble("1.5")  = 1.5d
    </pre> *
     *
     * @param str the string to convert, may be `null`
     * @return the double represented by the string, or `0.0d`
     * if conversion fails
     * @since 2.1
     */
    fun toDouble(str: String?): Double {
        return toDouble(str, 0.0)
    }

    /**
     *
     * Convert a `String` to a `double`, returning a
     * default value if the conversion fails.
     *
     *
     * If the string `str` is `null`, the default
     * value is returned.
     *
     * <pre>
     * NumberUtils.toDouble(null, 1.1d)   = 1.1d
     * NumberUtils.toDouble("", 1.1d)     = 1.1d
     * NumberUtils.toDouble("1.5", 0.0d)  = 1.5d
    </pre> *
     *
     * @param str          the string to convert, may be `null`
     * @param defaultValue the default value
     * @return the double represented by the string, or defaultValue
     * if conversion fails
     * @since 2.1
     */
    fun toDouble(str: String?, defaultValue: Double): Double {
        if (str == null) {
            return defaultValue
        }
        try {
            return str.toDouble()
        } catch (nfe: NumberFormatException) {
            return defaultValue
        }
    }

    //-----------------------------------------------------------------------

    //-----------------------------------------------------------------------
    /**
     *
     * Convert a `String` to a `byte`, returning
     * `zero` if the conversion fails.
     *
     *
     * If the string is `null`, `zero` is returned.
     *
     * <pre>
     * NumberUtils.toByte(null) = 0
     * NumberUtils.toByte("")   = 0
     * NumberUtils.toByte("1")  = 1
    </pre> *
     *
     * @param str the string to convert, may be null
     * @return the byte represented by the string, or `zero` if
     * conversion fails
     * @since 2.5
     */
    fun toByte(str: String?): Byte {
        return toByte(str, 0.toByte())
    }

    /**
     *
     * Convert a `String` to a `byte`, returning a
     * default value if the conversion fails.
     *
     *
     * If the string is `null`, the default value is returned.
     *
     * <pre>
     * NumberUtils.toByte(null, 1) = 1
     * NumberUtils.toByte("", 1)   = 1
     * NumberUtils.toByte("1", 0)  = 1
    </pre> *
     *
     * @param str          the string to convert, may be null
     * @param defaultValue the default value
     * @return the byte represented by the string, or the default if conversion fails
     * @since 2.5
     */
    fun toByte(str: String?, defaultValue: Byte): Byte {
        if (str == null) {
            return defaultValue
        }
        try {
            return str.toByte()
        } catch (nfe: NumberFormatException) {
            return defaultValue
        }
    }

    /**
     *
     * Convert a `String` to a `short`, returning
     * `zero` if the conversion fails.
     *
     *
     * If the string is `null`, `zero` is returned.
     *
     * <pre>
     * NumberUtils.toShort(null) = 0
     * NumberUtils.toShort("")   = 0
     * NumberUtils.toShort("1")  = 1
    </pre> *
     *
     * @param str the string to convert, may be null
     * @return the short represented by the string, or `zero` if
     * conversion fails
     * @since 2.5
     */
    fun toShort(str: String?): Short {
        return toShort(str, 0.toShort())
    }

    /**
     *
     * Convert a `String` to an `short`, returning a
     * default value if the conversion fails.
     *
     *
     * If the string is `null`, the default value is returned.
     *
     * <pre>
     * NumberUtils.toShort(null, 1) = 1
     * NumberUtils.toShort("", 1)   = 1
     * NumberUtils.toShort("1", 0)  = 1
    </pre> *
     *
     * @param str          the string to convert, may be null
     * @param defaultValue the default value
     * @return the short represented by the string, or the default if conversion fails
     * @since 2.5
     */
    fun toShort(str: String?, defaultValue: Short): Short {
        if (str == null) {
            return defaultValue
        }
        try {
            return str.toShort()
        } catch (nfe: NumberFormatException) {
            return defaultValue
        }
    }

    /**
     * 转换成 货币运算
     *
     * @param str
     * @return
     */
    fun toBigDecimal(str: String?): BigDecimal {
        return (toBigDecimal(str, BigDecimal(0)))!!
    }

    /**
     * 转换成 货币运算
     *
     * @param str
     * @param defaultValue 默认值
     * @return
     */
    fun toBigDecimal(str: String?, defaultValue: BigDecimal?): BigDecimal? {
        try {
            return BigDecimal(str)
        } catch (e: Exception) {
            e.printStackTrace()
            return defaultValue
        }
    }

    //-----------------------------------------------------------------------
    // must handle Long, Float, Integer, Float, Short,
    //                  BigDecimal, BigInteger and Byte
    // useful methods:
    // Byte.decode(String)
    // Byte.valueOf(String,int radix)
    // Byte.valueOf(String)
    // Double.valueOf(String)
    // Float.valueOf(String)
    // Float.valueOf(String)
    // Integer.valueOf(String,int radix)
    // Integer.valueOf(String)
    // Integer.decode(String)
    // Integer.getInteger(String)
    // Integer.getInteger(String,int val)
    // Integer.getInteger(String,Integer val)
    // Integer.valueOf(String)
    // Double.valueOf(String)
    // new Byte(String)
    // Long.valueOf(String)
    // Long.getLong(String)
    // Long.getLong(String,int)
    // Long.getLong(String,Integer)
    // Long.valueOf(String,int)
    // Long.valueOf(String)
    // Short.valueOf(String)
    // Short.decode(String)
    // Short.valueOf(String,int)
    // Short.valueOf(String)
    // new BigDecimal(String)
    // new BigInteger(String)
    // new BigInteger(String,int radix)
    // Possible inputs:
    // 45 45.5 45E7 4.5E7 Hex Oct Binary xxxF xxxD xxxf xxxd
    // plus minus everything. Prolly more. A lot are not separable.

    //-----------------------------------------------------------------------
    // must handle Long, Float, Integer, Float, Short,
    //                  BigDecimal, BigInteger and Byte
    // useful methods:
    // Byte.decode(String)
    // Byte.valueOf(String,int radix)
    // Byte.valueOf(String)
    // Double.valueOf(String)
    // Float.valueOf(String)
    // Float.valueOf(String)
    // Integer.valueOf(String,int radix)
    // Integer.valueOf(String)
    // Integer.decode(String)
    // Integer.getInteger(String)
    // Integer.getInteger(String,int val)
    // Integer.getInteger(String,Integer val)
    // Integer.valueOf(String)
    // Double.valueOf(String)
    // new Byte(String)
    // Long.valueOf(String)
    // Long.getLong(String)
    // Long.getLong(String,int)
    // Long.getLong(String,Integer)
    // Long.valueOf(String,int)
    // Long.valueOf(String)
    // Short.valueOf(String)
    // Short.decode(String)
    // Short.valueOf(String,int)
    // Short.valueOf(String)
    // new BigDecimal(String)
    // new BigInteger(String)
    // new BigInteger(String,int radix)
    // Possible inputs:
    // 45 45.5 45E7 4.5E7 Hex Oct Binary xxxF xxxD xxxf xxxd
    // plus minus everything. Prolly more. A lot are not separable.
    /**
     *
     * Turns a string value into a java.lang.Number.
     *
     *
     * If the string starts with `0x` or `-0x` (lower or upper case) or `#` or `-#`, it
     * will be interpreted as a hexadecimal Integer - or Long, if the number of digits after the
     * prefix is more than 8 - or BigInteger if there are more than 16 digits.
     *
     *
     * Then, the value is examined for a type qualifier on the end, i.e. one of
     * `'f','F','d','D','l','L'`.  If it is found, it starts
     * trying to create successively larger types from the type specified
     * until one is found that can represent the value.
     *
     *
     * If a type specifier is not found, it will check for a decimal point
     * and then try successively larger types from `Integer` to
     * `BigInteger` and from `Float` to
     * `BigDecimal`.
     *
     *
     *
     * Integral values with a leading `0` will be interpreted as octal; the returned number will
     * be Integer, Long or BigDecimal as appropriate.
     *
     *
     *
     * Returns `null` if the string is `null`.
     *
     *
     * This method does not trim the input string, i.e., strings with leading
     * or trailing spaces will generate NumberFormatExceptions.
     *
     * @param str String containing a number, may be null
     * @return Number created from the string (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    @Throws(NumberFormatException::class)
    fun createNumber(str: String?): Number? {
        if (str == null) {
            return null
        }
        if (str.isBlank()) {
            throw NumberFormatException("A blank string is not a valid number")
        }
        // Need to deal with all possible hex prefixes here
        val hex_prefixes = arrayOf("0x", "0X", "-0x", "-0X", "#", "-#")
        var pfxLen = 0
        for (pfx: String in hex_prefixes) {
            if (str.startsWith(pfx)) {
                pfxLen += pfx.length
                break
            }
        }
        if (pfxLen > 0) { // we have a hex number
            var firstSigDigit = 0.toChar() // strip leading zeroes
            for (i in pfxLen until str.length) {
                firstSigDigit = str[i]
                if (firstSigDigit == '0') { // count leading zeroes
                    pfxLen++
                } else {
                    break
                }
            }
            val hexDigits = str.length - pfxLen
            if (hexDigits > 16 || (hexDigits == 16 && firstSigDigit > '7')) { // too many for Long
                return createBigInteger(str)
            }
            return if (hexDigits > 8 || (hexDigits == 8 && firstSigDigit > '7')) { // too many for an int
                createLong(str)
            } else createInteger(str)
        }
        val lastChar = str[str.length - 1]
        val mant: String
        val dec: String?
        val exp: String?
        val decPos = str.indexOf('.')
        val expPos = str.indexOf('e') + str.indexOf('E') + 1 // assumes both not present
        // if both e and E are present, this is caught by the checks on expPos (which prevent IOOBE)
        // and the parsing which will detect if e or E appear in a number due to using the wrong offset
        var numDecimals = 0 // Check required precision (LANG-693)
        if (decPos > -1) { // there is a decimal point
            if (expPos > -1) { // there is an exponent
                if (expPos < decPos || expPos > str.length) { // prevents double exponent causing IOOBE
                    throw NumberFormatException("$str is not a valid number.")
                }
                dec = str.substring(decPos + 1, expPos)
            } else {
                dec = str.substring(decPos + 1)
            }
            mant = getMantissa(str, decPos)
            numDecimals =
                dec.length + decPos + 1 // gets number of digits past the decimal to ensure no loss of precision for floating point numbers.
        } else {
            if (expPos > -1) {
                if (expPos > str.length) { // prevents double exponent causing IOOBE
                    throw NumberFormatException("$str is not a valid number.")
                }
                mant = getMantissa(str, expPos)
            } else {
                mant = getMantissa(str)
            }
            dec = null
        }
        if (!Character.isDigit(lastChar) && lastChar != '.') {
            if (expPos > -1 && expPos < str.length - 1) {
                exp = str.substring(expPos + 1, str.length - 1)
            } else {
                exp = null
            }
            //Requesting a specific type..
            val numeric = str.substring(0, str.length - 1)
            val allZeros = isAllZeros(mant) && isAllZeros(exp)
            when (lastChar) {
                'l', 'L' -> {
                    if ((dec == null
                                ) && (exp == null
                                ) && (numeric[0] == '-' && isDigits(numeric.substring(1)) || isDigits(numeric))
                    ) {
                        try {
                            return createLong(numeric)
                        } catch (nfe: NumberFormatException) { // NOPMD
                            // Too big for a long
                        }
                        return createBigInteger(numeric)
                    }
                    throw NumberFormatException("$str is not a valid number.")
                }
                'f', 'F' -> {
                    try {
                        val f = createFloat(numeric)
                        if (!(f!!.isInfinite() || (f.toFloat() == 0.0f && !allZeros))) {
                            //If it's too big for a float or the float value = 0 and the string
                            //has non-zeros in it, then float does not have the precision we want
                            return f
                        }
                    } catch (nfe: NumberFormatException) { // NOPMD
                        // ignore the bad number
                    }
                    try {
                        val d = createDouble(numeric)
                        if (!(d!!.isInfinite() || (d.toFloat().toDouble() == 0.0 && !allZeros))) {
                            return d
                        }
                    } catch (nfe: NumberFormatException) { // NOPMD
                        // ignore the bad number
                    }
                    try {
                        return createBigDecimal(numeric)
                    } catch (e: NumberFormatException) { // NOPMD
                        // ignore the bad number
                    }
                    throw NumberFormatException("$str is not a valid number.")
                }
                'd', 'D' -> {
                    try {
                        val d = createDouble(numeric)
                        if (!(d!!.isInfinite() || (d.toFloat().toDouble() == 0.0 && !allZeros))) {
                            return d
                        }
                    } catch (nfe: NumberFormatException) {
                    }
                    try {
                        return createBigDecimal(numeric)
                    } catch (e: NumberFormatException) {
                    }
                    throw NumberFormatException("$str is not a valid number.")
                }
                else -> throw NumberFormatException("$str is not a valid number.")
            }
        }
        //User doesn't have a preference on the return type, so let's start
        //small and go from there...
        if (expPos > -1 && expPos < str.length - 1) {
            exp = str.substring(expPos + 1, str.length)
        } else {
            exp = null
        }
        if (dec == null && exp == null) { // no decimal point and no exponent
            //Must be an Integer, Long, Biginteger
            try {
                return createInteger(str)
            } catch (nfe: NumberFormatException) { // NOPMD
                // ignore the bad number
            }
            try {
                return createLong(str)
            } catch (nfe: NumberFormatException) { // NOPMD
                // ignore the bad number
            }
            return createBigInteger(str)
        }

        //Must be a Float, Double, BigDecimal
        val allZeros = isAllZeros(mant) && isAllZeros(exp)
        try {
            if (numDecimals <= 7) { // If number has 7 or fewer digits past the decimal point then make it a float
                val f = createFloat(str)
                if (!(f!!.isInfinite() || (f.toFloat() == 0.0f && !allZeros))) {
                    return f
                }
            }
        } catch (nfe: NumberFormatException) { // NOPMD
            // ignore the bad number
        }
        try {
            if (numDecimals <= 16) { // If number has between 8 and 16 digits past the decimal point then make it a double
                val d = createDouble(str)
                if (!(d!!.isInfinite() || (d.toDouble() == 0.0 && !allZeros))) {
                    return d
                }
            }
        } catch (nfe: NumberFormatException) { // NOPMD
            // ignore the bad number
        }
        return createBigDecimal(str)
    }

    /**
     *
     * Utility method for [.createNumber].
     *
     *
     * Returns mantissa of the given number.
     *
     * @param str the string representation of the number
     * @return mantissa of the given number
     */
    private fun getMantissa(str: String): String {
        return getMantissa(str, str.length)
    }

    /**
     *
     * Utility method for [.createNumber].
     *
     *
     * Returns mantissa of the given number.
     *
     * @param str     the string representation of the number
     * @param stopPos the position of the exponent or decimal point
     * @return mantissa of the given number
     */
    private fun getMantissa(str: String, stopPos: Int): String {
        val firstChar = str[0]
        val hasSign = (firstChar == '-' || firstChar == '+')
        return if (hasSign) str.substring(1, stopPos) else str.substring(0, stopPos)
    }

    /**
     *
     * Utility method for [.createNumber].
     *
     *
     * Returns `true` if s is `null`.
     *
     * @param str the String to check
     * @return if it is all zeros or `null`
     */
    private fun isAllZeros(str: String?): Boolean {
        if (str == null) {
            return true
        }
        for (i in str.length - 1 downTo 0) {
            if (str[i] != '0') {
                return false
            }
        }
        return str.length > 0
    }

    //-----------------------------------------------------------------------

    //-----------------------------------------------------------------------
    /**
     *
     * Convert a `String` to a `Float`.
     *
     *
     * Returns `null` if the string is `null`.
     *
     * @param str a `String` to convert, may be null
     * @return converted `Float` (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    fun createFloat(str: String?): Float? {
        return if (str == null) {
            null
        } else java.lang.Float.valueOf(str)
    }

    /**
     *
     * Convert a `String` to a `Double`.
     *
     *
     * Returns `null` if the string is `null`.
     *
     * @param str a `String` to convert, may be null
     * @return converted `Double` (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    fun createDouble(str: String?): Double? {
        return if (str == null) {
            null
        } else java.lang.Double.valueOf(str)
    }

    /**
     *
     * Convert a `String` to a `Integer`, handling
     * hex (0xhhhh) and octal (0dddd) notations.
     * N.B. a leading zero means octal; spaces are not trimmed.
     *
     *
     * Returns `null` if the string is `null`.
     *
     * @param str a `String` to convert, may be null
     * @return converted `Integer` (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    fun createInteger(str: String?): Int? {
        return if (str == null) {
            null
        } else Integer.decode(str)
        // decode() handles 0xAABD and 0777 (hex and octal) as well.
    }

    /**
     *
     * Convert a `String` to a `Long`;
     * since 3.1 it handles hex (0Xhhhh) and octal (0ddd) notations.
     * N.B. a leading zero means octal; spaces are not trimmed.
     *
     *
     * Returns `null` if the string is `null`.
     *
     * @param str a `String` to convert, may be null
     * @return converted `Long` (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    fun createLong(str: String?): Long? {
        return if (str == null) {
            null
        } else java.lang.Long.decode(str)
    }

    /**
     *
     * Convert a `String` to a `BigInteger`;
     * since 3.2 it handles hex (0x or #) and octal (0) notations.
     *
     *
     * Returns `null` if the string is `null`.
     *
     * @param str a `String` to convert, may be null
     * @return converted `BigInteger` (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    fun createBigInteger(str: String?): BigInteger? {
        if (str == null) {
            return null
        }
        var pos = 0 // offset within string
        var radix = 10
        var negate = false // need to negate later?
        if (str.startsWith("-")) {
            negate = true
            pos = 1
        }
        if (str.startsWith("0x", pos) || str.startsWith("0X", pos)) { // hex
            radix = 16
            pos += 2
        } else if (str.startsWith("#", pos)) { // alternative hex (allowed by Long/Integer)
            radix = 16
            pos++
        } else if (str.startsWith("0", pos) && str.length > pos + 1) { // octal; so long as there are additional digits
            radix = 8
            pos++
        } // default is to treat as decimal
        val value = BigInteger(str.substring(pos), radix)
        return if (negate) value.negate() else value
    }

    /**
     *
     * Convert a `String` to a `BigDecimal`.
     *
     *
     * Returns `null` if the string is `null`.
     *
     * @param str a `String` to convert, may be null
     * @return converted `BigDecimal` (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    fun createBigDecimal(str: String?): BigDecimal? {
        if (str == null) {
            return null
        }
        // handle JDK1.3.1 bug where "" throws IndexOutOfBoundsException
        if (str.isBlank()) {
            throw NumberFormatException("A blank string is not a valid number")
        }
        if (str.trim { it <= ' ' }.startsWith("--")) {
            // this is protection for poorness in java.lang.BigDecimal.
            // it accepts this as a legal value, but it does not appear
            // to be in specification of class. OS X Java parses it to
            // a wrong value.
            throw NumberFormatException("$str is not a valid number.")
        }
        return BigDecimal(str)
    }

    private fun validateArray(array: Any) {
        requireNotNull(array) { "The Array must not be null" }
        isTrue(Array.getLength(array) != 0, "Array cannot be empty.")
    }

    fun isTrue(expression: Boolean, message: String?, vararg values: Any?) {
        require(expression != false) { String.format(message!!, *values) }
    }

    // Min in array
    //--------------------------------------------------------------------

    // Min in array
    //--------------------------------------------------------------------
    /**
     *
     * Returns the minimum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from min(long[]) to min(long...)
     */
    fun min(vararg array: Long): Long {
        // Validates input
        validateArray(array)

        // Finds and returns min
        var min = array[0]
        for (i in 1 until array.size) {
            if (array[i] < min) {
                min = array[i]
            }
        }
        return min
    }

    /**
     *
     * Returns the minimum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from min(int[]) to min(int...)
     */
    fun min(vararg array: Int): Int {
        // Validates input
        validateArray(array)

        // Finds and returns min
        var min = array[0]
        for (j in 1 until array.size) {
            if (array[j] < min) {
                min = array[j]
            }
        }
        return min
    }

    /**
     *
     * Returns the minimum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from min(short[]) to min(short...)
     */
    fun min(vararg array: Short): Short {
        // Validates input
        validateArray(array)

        // Finds and returns min
        var min = array[0]
        for (i in 1 until array.size) {
            if (array[i] < min) {
                min = array[i]
            }
        }
        return min
    }

    /**
     *
     * Returns the minimum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from min(byte[]) to min(byte...)
     */
    fun min(vararg array: Byte): Byte {
        // Validates input
        validateArray(array)

        // Finds and returns min
        var min = array[0]
        for (i in 1 until array.size) {
            if (array[i] < min) {
                min = array[i]
            }
        }
        return min
    }

    /**
     *
     * Returns the minimum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @see "IEEE754rUtils.min
     * @since 3.4 Changed signature from min(double[]) to min(double...)
     */
    fun min(vararg array: Double): Double {
        // Validates input
        validateArray(array)

        // Finds and returns min
        var min = array[0]
        for (i in 1 until array.size) {
            if (java.lang.Double.isNaN(array[i])) {
                return java.lang.Double.NaN
            }
            if (array[i] < min) {
                min = array[i]
            }
        }
        return min
    }

    /**
     *
     * Returns the minimum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the minimum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @see "IEEE754rUtils.min
     * @since 3.4 Changed signature from min(float[]) to min(float...)
     */
    fun min(vararg array: Float): Float {
        // Validates input
        validateArray(array)

        // Finds and returns min
        var min = array[0]
        for (i in 1 until array.size) {
            if (java.lang.Float.isNaN(array[i])) {
                return Float.NaN
            }
            if (array[i] < min) {
                min = array[i]
            }
        }
        return min
    }

    // Max in array
    //--------------------------------------------------------------------

    // Max in array
    //--------------------------------------------------------------------
    /**
     *
     * Returns the maximum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the maximum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from max(long[]) to max(long...)
     */
    fun max(vararg array: Long): Long {
        // Validates input
        validateArray(array)

        // Finds and returns max
        var max = array[0]
        for (j in 1 until array.size) {
            if (array[j] > max) {
                max = array[j]
            }
        }
        return max
    }

    /**
     *
     * Returns the maximum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the maximum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from max(int[]) to max(int...)
     */
    fun max(vararg array: Int): Int {
        // Validates input
        validateArray(array)

        // Finds and returns max
        var max = array[0]
        for (j in 1 until array.size) {
            if (array[j] > max) {
                max = array[j]
            }
        }
        return max
    }

    /**
     *
     * Returns the maximum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the maximum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from max(short[]) to max(short...)
     */
    fun max(vararg array: Short): Short {
        // Validates input
        validateArray(array)

        // Finds and returns max
        var max = array[0]
        for (i in 1 until array.size) {
            if (array[i] > max) {
                max = array[i]
            }
        }
        return max
    }

    /**
     *
     * Returns the maximum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the maximum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @since 3.4 Changed signature from max(byte[]) to max(byte...)
     */
    fun max(vararg array: Byte): Byte {
        // Validates input
        validateArray(array)

        // Finds and returns max
        var max = array[0]
        for (i in 1 until array.size) {
            if (array[i] > max) {
                max = array[i]
            }
        }
        return max
    }

    /**
     *
     * Returns the maximum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the maximum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @see "IEEE754rUtils.max
     * @since 3.4 Changed signature from max(double[]) to max(double...)
     */
    fun max(vararg array: Double): Double {
        // Validates input
        validateArray(array)

        // Finds and returns max
        var max = array[0]
        for (j in 1 until array.size) {
            if (java.lang.Double.isNaN(array[j])) {
                return Double.NaN
            }
            if (array[j] > max) {
                max = array[j]
            }
        }
        return max
    }

    /**
     *
     * Returns the maximum value in an array.
     *
     * @param array an array, must not be null or empty
     * @return the maximum value in the array
     * @throws IllegalArgumentException if `array` is `null`
     * @throws IllegalArgumentException if `array` is empty
     * @see "IEEE754rUtils.max
     * @since 3.4 Changed signature from max(float[]) to max(float...)
     */
    fun max(vararg array: Float): Float {
        // Validates input
        validateArray(array)

        // Finds and returns max
        var max = array[0]
        for (j in 1 until array.size) {
            if (java.lang.Float.isNaN(array[j])) {
                return Float.NaN
            }
            if (array[j] > max) {
                max = array[j]
            }
        }
        return max
    }

    // 3 param min
    //-----------------------------------------------------------------------

    // 3 param min
    //-----------------------------------------------------------------------
    /**
     *
     * Gets the minimum of three `long` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the smallest of the values
     */
    fun min(a: Long, b: Long, c: Long): Long {
        var a = a
        if (b < a) {
            a = b
        }
        if (c < a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the minimum of three `int` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the smallest of the values
     */
    fun min(a: Int, b: Int, c: Int): Int {
        var a = a
        if (b < a) {
            a = b
        }
        if (c < a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the minimum of three `short` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the smallest of the values
     */
    fun min(a: Short, b: Short, c: Short): Short {
        var a = a
        if (b < a) {
            a = b
        }
        if (c < a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the minimum of three `byte` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the smallest of the values
     */
    fun min(a: Byte, b: Byte, c: Byte): Byte {
        var a = a
        if (b < a) {
            a = b
        }
        if (c < a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the minimum of three `double` values.
     *
     *
     * If any value is `NaN`, `NaN` is
     * returned. Infinity is handled.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the smallest of the values
     * @see "IEEE754rUtils.min
     */
    fun min(a: Double, b: Double, c: Double): Double {
        return Math.min(Math.min(a, b), c)
    }

    /**
     *
     * Gets the minimum of three `float` values.
     *
     *
     * If any value is `NaN`, `NaN` is
     * returned. Infinity is handled.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the smallest of the values
     * @see "IEEE754rUtils.min
     */
    fun min(a: Float, b: Float, c: Float): Float {
        return Math.min(Math.min(a, b), c)
    }

    // 3 param max
    //-----------------------------------------------------------------------

    // 3 param max
    //-----------------------------------------------------------------------
    /**
     *
     * Gets the maximum of three `long` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    fun max(a: Long, b: Long, c: Long): Long {
        var a = a
        if (b > a) {
            a = b
        }
        if (c > a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the maximum of three `int` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    fun max(a: Int, b: Int, c: Int): Int {
        var a = a
        if (b > a) {
            a = b
        }
        if (c > a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the maximum of three `short` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    fun max(a: Short, b: Short, c: Short): Short {
        var a = a
        if (b > a) {
            a = b
        }
        if (c > a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the maximum of three `byte` values.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     */
    fun max(a: Byte, b: Byte, c: Byte): Byte {
        var a = a
        if (b > a) {
            a = b
        }
        if (c > a) {
            a = c
        }
        return a
    }

    /**
     *
     * Gets the maximum of three `double` values.
     *
     *
     * If any value is `NaN`, `NaN` is
     * returned. Infinity is handled.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     * @see "IEEE754rUtils.max
     */
    fun max(a: Double, b: Double, c: Double): Double {
        return Math.max(Math.max(a, b), c)
    }

    /**
     *
     * Gets the maximum of three `float` values.
     *
     *
     * If any value is `NaN`, `NaN` is
     * returned. Infinity is handled.
     *
     * @param a value 1
     * @param b value 2
     * @param c value 3
     * @return the largest of the values
     * @see "IEEE754rUtils.max
     */
    fun max(a: Float, b: Float, c: Float): Float {
        return Math.max(Math.max(a, b), c)
    }

    //-----------------------------------------------------------------------

    //-----------------------------------------------------------------------
    /**
     *
     * Checks whether the `String` contains only
     * digit characters.
     *
     *
     * `Null` and empty String will return
     * `false`.
     *
     * @param str the `String` to check
     * @return `true` if str contains only Unicode numeric
     */
    fun isDigits(str: String): Boolean {
        if (str.isBlank()) {
            return false
        }
        for (i in 0 until str.length) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }

    /**
     *
     * Checks whether the String a valid Java number.
     *
     *
     * Valid numbers include hexadecimal marked with the `0x` or
     * `0X` qualifier, octal numbers, scientific notation and numbers
     * marked with a type qualifier (e.g. 123L).
     *
     *
     * Non-hexadecimal strings beginning with a leading zero are
     * treated as octal values. Thus the string `09` will return
     * `false`, since `9` is not a valid octal value.
     * However, numbers beginning with `0.` are treated as decimal.
     *
     *
     * `null` and empty/blank `String` will return
     * `false`.
     *
     * @param str the `String` to check
     * @return `true` if the string is a correctly formatted number
     * @since 3.3 the code supports hex `0Xhhh` and octal `0ddd` validation
     */
    fun isNumber(str: String): Boolean {
        if (str.isBlank()) {
            return false
        }
        val chars = str.toCharArray()
        var sz = chars.size
        var hasExp = false
        var hasDecPoint = false
        var allowSigns = false
        var foundDigit = false
        // deal with any possible sign up front
        val start = if ((chars[0] == '-')) 1 else 0
        if (sz > start + 1 && chars[start] == '0') { // leading 0
            if ((chars[start + 1] == 'x') ||
                (chars[start + 1] == 'X')
            ) { // leading 0x/0X
                var i = start + 2
                if (i == sz) {
                    return false // str == "0x"
                }
                // checking hex (it can't be anything else)
                while (i < chars.size) {
                    if (((chars[i] < '0' || chars[i] > '9')
                                && (chars[i] < 'a' || chars[i] > 'f')
                                && (chars[i] < 'A' || chars[i] > 'F'))
                    ) {
                        return false
                    }
                    i++
                }
                return true
            } else if (Character.isDigit(chars[start + 1])) {
                // leading 0, but not hex, must be octal
                var i = start + 1
                while (i < chars.size) {
                    if (chars[i] < '0' || chars[i] > '7') {
                        return false
                    }
                    i++
                }
                return true
            }
        }
        sz-- // don't want to loop to the last char, check it afterwords
        // for type qualifiers
        var i = start
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || ((i < sz + 1) && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true
                allowSigns = false
            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false
                }
                hasDecPoint = true
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false
                }
                if (!foundDigit) {
                    return false
                }
                hasExp = true
                allowSigns = true
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false
                }
                allowSigns = false
                foundDigit = false // we need a digit after the E
            } else {
                return false
            }
            i++
        }
        if (i < chars.size) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false
            }
            if (chars[i] == '.') {
                return if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    false
                } else foundDigit
                // single trailing decimal point after non-exponent is ok
            }
            if ((!allowSigns
                        && ((chars[i] == 'd'
                        ) || (chars[i] == 'D'
                        ) || (chars[i] == 'f'
                        ) || (chars[i] == 'F')))
            ) {
                return foundDigit
            }
            return if ((chars.get(i) == 'l'
                        || chars.get(i) == 'L')
            ) {
                // not allowing L with an exponent or decimal point
                foundDigit && !hasExp && !hasDecPoint
            } else false
            // last character is illegal
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit
    }

    /**
     *
     * Compares two `int` values numerically. This is the same functionality as provided in Java 7.
     *
     * @param x the first `int` to compare
     * @param y the second `int` to compare
     * @return the value `0` if `x == y`;
     * a value less than `0` if `x < y`; and
     * a value greater than `0` if `x > y`
     * @since 3.4
     */
    fun compare(x: Int, y: Int): Int {
        if (x == y) {
            return 0
        }
        return if (x < y) {
            -1
        } else {
            1
        }
    }

    /**
     *
     * Compares to `long` values numerically. This is the same functionality as provided in Java 7.
     *
     * @param x the first `long` to compare
     * @param y the second `long` to compare
     * @return the value `0` if `x == y`;
     * a value less than `0` if `x < y`; and
     * a value greater than `0` if `x > y`
     * @since 3.4
     */
    fun compare(x: Long, y: Long): Int {
        if (x == y) {
            return 0
        }
        return if (x < y) {
            -1
        } else {
            1
        }
    }

    /**
     *
     * Compares to `short` values numerically. This is the same functionality as provided in Java 7.
     *
     * @param x the first `short` to compare
     * @param y the second `short` to compare
     * @return the value `0` if `x == y`;
     * a value less than `0` if `x < y`; and
     * a value greater than `0` if `x > y`
     * @since 3.4
     */
    fun compare(x: Short, y: Short): Int {
        if (x == y) {
            return 0
        }
        return if (x < y) {
            -1
        } else {
            1
        }
    }

    /**
     *
     * Compares two `byte` values numerically. This is the same functionality as provided in Java 7.
     *
     * @param x the first `byte` to compare
     * @param y the second `byte` to compare
     * @return the value `0` if `x == y`;
     * a value less than `0` if `x < y`; and
     * a value greater than `0` if `x > y`
     * @since 3.4
     */
    fun compare(x: Byte, y: Byte): Int {
        return x - y
    }

    /**
     * 比较任意数字大小
     *
     * @param x
     * @param y
     * @return
     */
    fun compare(x: Any, y: Any): Int {
        try {
            val xBd = innerConvertDecimal(x)
            val yBd = innerConvertDecimal(y)
            return xBd.compareTo(yBd)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 判断该数字是否为0
     *
     * @param x
     * @return
     */
    fun isZero(x: Any): Boolean {
        val xBd = innerConvertDecimal(x)
        return xBd.compareTo(BigDecimal.ZERO) == 0
    }

    /**
     * 判断该数字为正数（大于0）
     *
     * @param x
     * @return
     */
    fun isPositiveNumber(x: Any): Boolean {
        val xBd = innerConvertDecimal(x)
        return xBd.compareTo(BigDecimal.ZERO) > 0
    }

    /**
     * 判断负数(小于0)
     * @param x
     * @return
     */
    fun isNegativeNumber(x: Any): Boolean {
        val xBd = innerConvertDecimal(x)
        return xBd.compareTo(BigDecimal.ZERO) < 0
    }

}