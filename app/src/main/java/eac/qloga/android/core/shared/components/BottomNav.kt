package eac.qloga.android.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.utils.Extensions.advancedShadow

@Composable
fun BottomNav(
    modifier: Modifier = Modifier,
    showBottomBorder: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    items: @Composable (() -> Unit) = {}
) {
    Box(
        modifier = Modifier
            .advancedShadow(
                color = if (showBottomBorder) Color.Gray else Color.Transparent,
            .8f,
                0.dp,
                if (showBottomBorder) 1.dp else 0.dp
            )
            .background(backgroundColor)
    ){
        Row(
            modifier = modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNav() {
    BottomNav()
}