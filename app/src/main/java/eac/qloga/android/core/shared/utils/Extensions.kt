package eac.qloga.android.core.shared.utils

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
            awaitDispose { viewTreeObserver.removeOnGlobalLayoutListener(listener)  }
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
}