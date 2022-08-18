package com.ball.calculator.vm

import android.text.TextUtils
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.pow

/**
 *Created  by .husy on 2022/8/9/009.
 * @link
 * @description:
 */
@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {

    val mResultLiveData = mutableStateOf("")

    companion object {
        //显示多个
        const val MORE_TYPE = 0

        //显示一个
        const val SINGLE_TYPE = 1

        //至少显示一个
        const val SPECIAL_TYPE = 2

        //不需要显示
        const val NO_NEED_TYPE = 3

        const val ADD = "+"
        const val SUBTRACT = "-"
        const val MULTIPLY = "×"
        const val DIVIDE = "÷"
        const val POINT = "."
        const val PERCENT = "%"
        const val SWITCH = "∽"
    }

    /**
     * 判断SPECIAL_TYPE类型是否需要添加第二个
     */
    fun isShowKeypad(value: String, operator: String, keypadName: String): Boolean {
        return if (value.contains(operator)) {
            val arithmetics = value.split(operator)
            !arithmetics[1].contains(keypadName)
        } else {
            false
        }
    }

    fun getResult(value: String) {
        if (TextUtils.isEmpty(value)) {
            return
        }

        val resultStr = try {
            if (value.contains(ADD)) {
                calculate(value, ADD).toString()
            } else if (value.contains(SUBTRACT)) {
                calculate(value, SUBTRACT).toString()
            } else if (value.contains(MULTIPLY)) {
                calculate(value, MULTIPLY).toString()
            } else if (value.contains(DIVIDE)) {
                calculate(value, DIVIDE).toString()
            } else if (value.contains(PERCENT) || value.contains(POINT)) {
                dealPercentAndPoint(value)
            } else {
                value.toLong().toString()
            }
        } catch (e: Exception) {
            "error"
        }
        mResultLiveData.value = resultStr
    }

    private fun calculate(value: String, operator: String): Number {
        val arithmetics = value.split(operator)
        //判断 a+b 正常情况
        return if (arithmetics.size > 1 && !TextUtils.isEmpty(arithmetics[1])) {
            //判断是否有小数点和百分号%
            if (value.contains(POINT) || value.contains(PERCENT)) {
                //处理%
                val a = if (arithmetics[0].contains(PERCENT)) {
                    val firstPercent = getStrCount(arithmetics[0], PERCENT)
                    arithmetics[0].replace("%", "").toDouble() / 100.0.pow(firstPercent)
                } else {
                    arithmetics[0].toDouble()
                }

                val b = if (arithmetics[1].contains(PERCENT)) {
                    val secondPercent = getStrCount(arithmetics[1], PERCENT)
                    arithmetics[1].replace("%", "").toDouble() / 100.0.pow(secondPercent)
                } else {
                    arithmetics[1].toDouble()
                }

                when (operator) {
                    ADD -> dealDouble(a + b)
                    SUBTRACT -> dealDouble(a - b)
                    MULTIPLY -> dealDouble(a * b)
                    DIVIDE -> dealDouble(a / b)
                    else -> 0
                }
            } else {
                when (operator) {
                    ADD -> arithmetics[0].toLong() + arithmetics[1].toLong()
                    SUBTRACT -> arithmetics[0].toLong() - arithmetics[1].toLong()
                    MULTIPLY -> arithmetics[0].toLong() * arithmetics[1].toLong()
                    DIVIDE -> dealDouble(arithmetics[0].toDouble() / arithmetics[1].toDouble())
                    else -> 0
                }
            }
        } else {
            //a+ -等情况
            arithmetics[0].toLong()
        }
    }

    /**
     * 处理%和.
     */
    private fun dealPercentAndPoint(value: String): String {
        var result = ""
        if (value.contains(PERCENT)) {
            val arithmetics = value.split(PERCENT)
            val firstPercent = getStrCount(value, PERCENT)
            result = dealDouble((arithmetics[0].toDouble() / (100.0.pow(firstPercent)))).toString()
        } else if (value.contains(POINT)) {
            val arithmetics = value.split(POINT)
            result = if (TextUtils.isEmpty(arithmetics[1]) || arithmetics[1].toLong() == 0L) {
                arithmetics[0]
            } else {
                arithmetics[0] + "." + (arithmetics[1].toLong())
            }
        }
        return result
    }

    /**
     * 处理整数带小数点的情况
     */
    private fun dealDouble(double: Double): Number {
        val doubleStr = double.toString()
        return if (doubleStr.contains(POINT)) {
            val doubles = doubleStr.split(POINT)
            if (doubles[1].toLong() > 0) {
                double
            } else {
                doubles[0].toLong()
            }
        } else {
            double
        }
    }

    /**
     * 判断某个字符出现的次数
     */
    private fun getStrCount(string: String, countStr: String): Int {
        val newStr = string.replace(countStr, "")
        return string.length - newStr.length
    }
}