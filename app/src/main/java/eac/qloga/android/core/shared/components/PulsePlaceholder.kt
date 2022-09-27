package eac.qloga.android.core.shared.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun PulsePlaceholder(
    modifier: Modifier = Modifier,
    initialColor: Color = Color(0xFFE5E5E5),
    targetColor: Color = Color(0xFFF6F6F6),
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    transitionDelay: Int = 800
) {

    val infiniteTransition = rememberInfiniteTransition()

    val color by infiniteTransition.animateColor(
        initialValue = initialColor,
        targetValue = targetColor,
        animationSpec =  infiniteRepeatable(
            animation = tween(transitionDelay),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .size(200.dp)
            .clip(roundedCornerShape)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPulsePlaceholder() {
    PulsePlaceholder()
}