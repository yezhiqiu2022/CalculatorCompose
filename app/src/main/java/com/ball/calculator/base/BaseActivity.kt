package com.ball.calculator.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import com.ball.calculator.ui.theme.CalculatorApplicationTheme

/**
 * @link
 * @description:
 */
abstract class BaseActivity : ComponentActivity() {

    /**
     * 默认状态栏黑色字体
     * false=白色字体
     * true=黑色字体
     */
    var mStatusBarBlackFontColor = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorApplicationTheme { // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    AddView()
                }
            }
        }
    }

    @Composable
    open fun AddView() {
        ViewCompat.getWindowInsetsController(LocalView.current)?.isAppearanceLightStatusBars =
            mStatusBarBlackFontColor
    }

    /**
     * dp转字体大小sp
     */
    @Composable
    @ReadOnlyComposable
    fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id).value.sp
}