package com.ball.calculator.ui

import android.content.pm.ActivityInfo
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ball.calculator.R
import com.ball.calculator.base.BaseActivity
import com.ball.calculator.bean.KeypadBean
import com.ball.calculator.vm.CalculatorViewModel
import com.ball.calculator.vm.CalculatorViewModel.Companion.MORE_TYPE
import com.ball.calculator.vm.CalculatorViewModel.Companion.SINGLE_TYPE
import com.ball.calculator.vm.CalculatorViewModel.Companion.SPECIAL_TYPE


class MainActivity : BaseActivity() {

    private lateinit var mInputValue: MutableState<String>
    private lateinit var mViewModel: CalculatorViewModel

    //清除还是继续追加
    private lateinit var isClear: MutableState<Boolean>

    @Composable
    override fun AddView() {
        super.AddView()
        CalculatorView()
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun CalculatorView(viewModel: CalculatorViewModel = viewModel()) {

        isClear = remember { mutableStateOf(false) }
        mViewModel = viewModel
        mInputValue = remember { viewModel.mResultLiveData }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            //添加动画
            AnimatedContent(targetState = mInputValue.value, transitionSpec = {
                if (isClear.value) {
                    slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
                } else {
                    fadeIn(
                        animationSpec = tween(
                            180, delayMillis = 90
                        )
                    ) + scaleIn(
                        initialScale = 1f, animationSpec = tween(180, delayMillis = 90)
                    ) with fadeOut(animationSpec = tween(90))
                }.using(
                    SizeTransform(clip = true)
                )
            }) { targetCount ->
                Text(
                    targetCount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.dp_32),
                            end = dimensionResource(R.dimen.dp_32)
                        ),
                    textAlign = TextAlign.End,
                    fontSize = fontDimensionResource(
                        R.dimen.textSize36
                    ),
                    color = Color.Black,
                )
            }
            Divider(
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.dp_18),
                    end = dimensionResource(R.dimen.dp_18),
                    top = dimensionResource(R.dimen.dp_10)
                ), thickness = dimensionResource(R.dimen.dp_05)
            )
            if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                val menuList = getLandscapeMenuList()
                LandscapeKeypadViews(menuList)
            } else {
                val menuList = getPortraitMenuList()
                PortraitKeypadViews(menuList)
            }
        }
    }

    /**
     * 竖屏
     */
    @Composable
    fun getPortraitMenuList() = with(mutableListOf<KeypadBean>()) {
        add(KeypadBean("AC", colorResource(R.color.bg_orange), CalculatorViewModel.NO_NEED_TYPE))
        add(KeypadBean("←", colorResource(R.color.bg_orange), CalculatorViewModel.NO_NEED_TYPE))
        add(KeypadBean(CalculatorViewModel.PERCENT, colorResource(R.color.bg_orange)))
        add(KeypadBean(CalculatorViewModel.DIVIDE, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(KeypadBean("7", Color.DarkGray))
        add(KeypadBean("8", Color.DarkGray))
        add(KeypadBean("9", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.MULTIPLY, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(KeypadBean("4", Color.DarkGray))
        add(KeypadBean("5", Color.DarkGray))
        add(KeypadBean("6", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.SUBTRACT, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(KeypadBean("1", Color.DarkGray))
        add(KeypadBean("2", Color.DarkGray))
        add(KeypadBean("3", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.ADD, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(
            KeypadBean(
                CalculatorViewModel.SWITCH,
                colorResource(R.color.bg_orange),
                CalculatorViewModel.NO_NEED_TYPE
            )
        )
        add(KeypadBean("0", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.POINT, Color.DarkGray, SPECIAL_TYPE))
        add(KeypadBean("=", colorResource(R.color.bg_orange), CalculatorViewModel.NO_NEED_TYPE))
        this
    }

    /**
     * 横屏
     */
    @Composable
    fun getLandscapeMenuList() = with(mutableListOf<KeypadBean>()) {
        add(KeypadBean("AC", colorResource(R.color.bg_orange), CalculatorViewModel.NO_NEED_TYPE))
        add(KeypadBean("1", Color.DarkGray))
        add(KeypadBean("2", Color.DarkGray))
        add(KeypadBean("3", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.ADD, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(KeypadBean("←", colorResource(R.color.bg_orange), CalculatorViewModel.NO_NEED_TYPE))
        add(KeypadBean("4", Color.DarkGray))
        add(KeypadBean("5", Color.DarkGray))
        add(KeypadBean("6", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.SUBTRACT, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(KeypadBean(CalculatorViewModel.PERCENT, colorResource(R.color.bg_orange)))
        add(KeypadBean("7", Color.DarkGray))
        add(KeypadBean("8", Color.DarkGray))
        add(KeypadBean("9", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.MULTIPLY, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(
            KeypadBean(
                CalculatorViewModel.SWITCH,
                colorResource(R.color.bg_orange),
                CalculatorViewModel.NO_NEED_TYPE
            )
        )
        add(KeypadBean("0", Color.DarkGray))
        add(KeypadBean(CalculatorViewModel.POINT, Color.DarkGray, SPECIAL_TYPE))
        add(KeypadBean(CalculatorViewModel.DIVIDE, colorResource(R.color.bg_orange), SINGLE_TYPE))
        add(KeypadBean("=", colorResource(R.color.bg_orange), CalculatorViewModel.NO_NEED_TYPE))
        this
    }

    /**
     * 竖屏
     */
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PortraitKeypadViews(keypad: MutableList<KeypadBean>) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
        ) {
            itemsIndexed(keypad) { _, item ->
                KeypadItemView(item, dimensionResource(R.dimen.dp_80))
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun LandscapeKeypadViews(keypad: MutableList<KeypadBean>) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(5),
        ) {
            itemsIndexed(keypad) { _, item ->
                KeypadItemView(item, dimensionResource(R.dimen.dp_68))
            }
        }
    }

    @Composable
    fun KeypadItemView(item: KeypadBean, height: Dp) {
        Column(
            modifier = Modifier.height(height),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.keypadName, fontSize = fontDimensionResource(
                    R.dimen.textSize32
                ), modifier = Modifier.clickable(
                    //改变点击状态、大小和颜色
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false, color = Color.LightGray, radius = dimensionResource(
                            R.dimen.dp_38
                        )
                    )
                ) {
                    keypadClick(item)
                }, color = item.keypadTextColor
            )
        }
    }

    private fun keypadClick(item: KeypadBean) {
        item.apply {
            mInputValue.let {
                when (type) {
                    MORE_TYPE -> {
                        if (isClear.value && keypadName != CalculatorViewModel.PERCENT) {
                            it.value = keypadName
                        } else {
                            it.value += keypadName
                        }
                        isClear.value = false
                    }
                    SINGLE_TYPE -> {
                        if (!it.value.contains(keypadName)) {
                            it.value += keypadName
                        }
                        isClear.value = false
                    }
                    SPECIAL_TYPE -> {
                        //判断有没有运算符(第一个数a添加的时候)
                        if (!it.value.contains(CalculatorViewModel.ADD) && !it.value.contains(
                                CalculatorViewModel.SUBTRACT
                            ) && !it.value.contains(CalculatorViewModel.MULTIPLY) && !it.value.contains(
                                CalculatorViewModel.DIVIDE
                            )
                        ) {
                            if (!it.value.contains(keypadName)) {
                                it.value += keypadName
                            }
                        }
                        //第二个数b添加的时候
                        else {
                            if (mViewModel.isShowKeypad(
                                    it.value, CalculatorViewModel.ADD, keypadName
                                )
                            ) {
                                it.value += keypadName
                            } else if (mViewModel.isShowKeypad(
                                    it.value, CalculatorViewModel.SUBTRACT, keypadName
                                )
                            ) {
                                it.value += keypadName
                            } else if (mViewModel.isShowKeypad(
                                    it.value, CalculatorViewModel.MULTIPLY, keypadName
                                )
                            ) {
                                it.value += keypadName
                            } else if (mViewModel.isShowKeypad(
                                    it.value, CalculatorViewModel.DIVIDE, keypadName
                                )
                            ) {
                                it.value += keypadName
                            }
                        }
                        isClear.value = false
                    }
                    else -> when (keypadName) {
                        "AC" -> {
                            it.value = ""
                            isClear.value = false
                        }
                        "←" -> {
                            if (it.value.isNotEmpty() && !isClear.value) {
                                it.value = it.value.dropLast(1)

                            } else {
                                it.value = ""
                            }
                            isClear.value = false
                        }
                        "=" -> {
                            isClear.value = true
                            mViewModel.getResult(it.value)
                        }
                        //切换横竖屏
                        CalculatorViewModel.SWITCH -> {
                            requestedOrientation =
                                if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                } else {
                                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                                }
                        }
                    }
                }
            }
        }
    }
}