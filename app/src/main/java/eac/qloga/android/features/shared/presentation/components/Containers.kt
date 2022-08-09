package eac.qloga.android.features.shared.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object Containers {
    @Composable
    fun BottomButtonContainer(
        modifier: Modifier = Modifier,
        content: @Composable (() -> Unit),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp, top = 8.dp),
            contentAlignment = Alignment.Center
        ){
            content()
        }
    }
}