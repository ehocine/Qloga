package eac.qloga.android.features.p4p.shared.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun ServiceConditionItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String? = null,
    showDivider: Boolean = true,
    onClick: () -> Unit
){
    val expanded = remember { mutableStateOf(false) }
    val animatedFloat = animateFloatAsState(targetValue = if(expanded.value) 90f else 0f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                value?.let {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
                        color = gray30
                    )
                }
                Icon(
                    modifier = Modifier
                        .rotate(animatedFloat.value)
                        .size(17.dp)
                        .clip(CircleShape)
                    ,
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "forward arrow",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        if(showDivider){
            DividerLine(Modifier.padding(start = 64.dp))
        }
    }
}