package eac.qloga.android.business.util

import android.graphics.Color.parseColor
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Extensions {


    val String.color
        get() = Color(parseColor(this))

    fun Modifier.advancedShadow(
        color: Color = Color.Black,
        alpha: Float = 0f,
        cornersRadius: Dp = 0.dp,
        shadowBlurRadius: Dp = 0.dp,
        offsetY: Dp = 0.dp,
        offsetX: Dp = 0.dp
    ) = drawBehind {
        val shadowColor = color.copy(alpha = alpha).toArgb()
        val transparentColor = color.copy(alpha = 0f).toArgb()
        drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor
            frameworkPaint.setShadowLayer(
                shadowBlurRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                cornersRadius.toPx(),
                cornersRadius.toPx(),
                paint
            )
        }
    }

    fun View.isKeyboardOpen(): Boolean {
        val rect = Rect()
        getWindowVisibleDisplayFrame(rect);
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rect.bottom;
        return keypadHeight > screenHeight * 0.15
    }

    @Composable
    fun rememberIsKeyboardOpen(): State<Boolean> {
        val view = LocalView.current
        return produceState(initialValue = view.isKeyboardOpen()) {
            val viewTreeObserver = view.viewTreeObserver
            val listener = ViewTreeObserver.OnGlobalLayoutListener { value = view.isKeyboardOpen() }
            viewTreeObserver.addOnGlobalLayoutListener(listener)
            awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener) }
        }
    }

    fun Modifier.clearFocusOnKeyboardDismiss(): Modifier = composed {
        var isFocused by remember { mutableStateOf(false) }
        var keyboardAppearedSinceLastFocused by remember { mutableStateOf(false) }

        if (isFocused) {
            val isKeyboardOpen by rememberIsKeyboardOpen()
            val focusManager = LocalFocusManager.current
            LaunchedEffect(isKeyboardOpen) {
                if (isKeyboardOpen) {
                    keyboardAppearedSinceLastFocused = true
                } else if (keyboardAppearedSinceLastFocused) {
                    focusManager.clearFocus()
                }
            }
        }
        onFocusEvent {
            if (isFocused != it.isFocused) {
                isFocused = it.isFocused
                if (isFocused) {
                    keyboardAppearedSinceLastFocused = false
                }
            }
        }
    }

    fun Float.roundToRating( ): Float{
        return when(this){
            in 1f..1.2f -> 1f
            in 1.3f..1.6f -> 1.5f
            in 1.7f..2.2f -> 2f
            in 2.3f..2.6f -> 2.5f
            in 2.7f..3.3f -> 3f
            in 3.4f..3.7f -> 3.5f
            in 3.6f..4.3f -> 4f
            in 4.4f..4.7f -> 4.5f
            in 4.8f..5.3f -> 5f
            else -> 0f
        }
    }
}