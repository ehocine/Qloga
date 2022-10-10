package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DottedLine
import eac.qloga.android.core.shared.theme.info_sky

@Composable
fun VerticalDottedLine(
    modifier: Modifier = Modifier,
    height: Dp = 24.dp,
    width : Dp = 48.dp ,
    color : Color = info_sky
) {
    Box(
        modifier = modifier.height(height).width(width),
        contentAlignment = Alignment.Center
    ) {
        DottedLine(arcStrokeColor = color, vertical = true)
    }
}