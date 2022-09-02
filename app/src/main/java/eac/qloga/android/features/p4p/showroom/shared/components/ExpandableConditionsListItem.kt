package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun ExpandableConditionsListItem(
    modifier: Modifier = Modifier,
    label: String,
    description: String,
) {
    val expanded = remember{ mutableStateOf(false) }
    val animatedExpandIconAngle  = animateFloatAsState(targetValue = if(expanded.value) -90f else 90f)
    val interactionSource = remember{ MutableInteractionSource() }

    Column( modifier = modifier.fillMaxWidth() ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { expanded.value = !expanded.value }
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircleDot()
            Spacer(Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f)
                ,
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = gray30,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )

            // expand arrow button
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { expanded.value = !expanded.value }
                    .padding(8.dp)
                    .rotate(animatedExpandIconAngle.value)
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                    ,
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        AnimatedVisibility(visible = expanded.value,) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = description,
                style = MaterialTheme.typography.titleSmall,
                color = gray30
            )
        }
    }
}