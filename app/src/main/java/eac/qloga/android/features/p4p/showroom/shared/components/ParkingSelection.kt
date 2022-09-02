package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.utils.ParkingType

/**
 *  Composable with list of parking type
 * **/
@Composable
fun ParkingSelection(
    onSelect: (ParkingType) -> Unit,
    selected: ParkingType = ParkingType.FreeType
){
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth(.15f)
                    .clip(CircleShape)
                    .background(gray1),
            )
        }

        ParkingType.listOfParkingType.forEach { type ->
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onSelect(type)
                    }
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = type.label,
                    color = if(selected == type) MaterialTheme.colorScheme.primary else grayTextColor,
                    style = MaterialTheme.typography.titleMedium
                )
                AnimatedVisibility(
                    visible = selected == type,
                    enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 600))
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewParkingSelection(){
    ParkingSelection(onSelect = {})
}

