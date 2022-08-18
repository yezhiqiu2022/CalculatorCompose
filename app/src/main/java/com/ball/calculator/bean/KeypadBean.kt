package com.ball.calculator.bean

import androidx.compose.ui.graphics.Color
import com.ball.calculator.vm.CalculatorViewModel.Companion.MORE_TYPE

/**
 *Created  by .husy on 2022/8/9/009.
 * @link
 * @description:
 */
data class KeypadBean(
    val keypadName: String, val keypadTextColor: Color, val type: Int
) {
    constructor(keypadName: String, keypadTextColor: Color) : this(
        keypadName, keypadTextColor, MORE_TYPE
    )
}
