package eac.qloga.android.core.shared.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1

object Cards{
    @Composable
    fun ContainerBorderedCard(
        modifier: Modifier = Modifier,
        borderWidth: Dp = 1.5.dp,
        borderColor: Color = gray1,
        cornerRadius: Dp = 16.dp,
        content: @Composable (() -> Unit)
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .border(borderWidth, borderColor.copy(.5f), shape = RoundedCornerShape(cornerRadius))
        ) {
            content()
        }
    }
}