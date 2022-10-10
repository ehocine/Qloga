package eac.qloga.android.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun TimeChip(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean = false,
    isAvailable: Boolean = true,
    onClick : () -> Unit
){
    val heightTimeChip = 54.dp
    val cornerRadius = 12.dp

    val backgroundColor = remember{
        derivedStateOf {
            if(!isAvailable) gray1 else Color.Transparent
        }
    }

    Box(
        modifier = modifier
            .height(heightTimeChip)
            .padding(horizontal = 2.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(
                enabled = isAvailable
            ) { onClick() }
            .background(if(isSelected) MaterialTheme.colorScheme.primary else backgroundColor.value)
            .border(
                1.dp,
                if(isSelected) MaterialTheme.colorScheme.primary else gray1.copy(alpha = .5f),
                RoundedCornerShape(cornerRadius)
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if(!isAvailable || isSelected) Color.White  else gray30,
            fontWeight = if(isSelected) FontWeight.W600 else FontWeight.W400
        )
    }
}
