package eac.qloga.android.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1

object DividerLines{
    @Composable
    fun DividerLine(
        modifier: Modifier = Modifier,
        color: Color = gray1
    ) {
        Divider(
            modifier
                .fillMaxWidth()
                .height(1.dp)
                .alpha(0.2f)
                .background(color)
        )
    }

    @Composable
    fun LightDividerLine(
        modifier: Modifier = Modifier,
        color: Color = gray1
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(1.4.dp)
            .background(color.copy(.2f))
        )
    }
}