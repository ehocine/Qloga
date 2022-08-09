package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleDot(
    size: Dp = 8.dp,
    background: Color = MaterialTheme.colorScheme.primary
    ) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(background)
    )
}