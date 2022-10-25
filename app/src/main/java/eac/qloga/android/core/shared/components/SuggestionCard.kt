package eac.qloga.android.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SuggestionCard(
    modifier: Modifier = Modifier,
    width: Dp,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(0.dp),
    expanded: Boolean = false,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    if(expanded) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .shadow(8.dp,roundedCornerShape, spotColor = Color.Gray, ambientColor = Color.Gray)
                    .padding(vertical = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(width)
                        .clip(roundedCornerShape)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column {
                        content()
                    }
                }
            }
        }
    }
}