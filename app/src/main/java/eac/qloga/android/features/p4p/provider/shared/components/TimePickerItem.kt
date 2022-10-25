package eac.qloga.android.features.p4p.provider.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.DoNotDisturbOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.lightGrayBackground
import eac.qloga.android.core.shared.theme.orange2
import eac.qloga.android.core.shared.utils.WeekDays
import java.time.DayOfWeek

@Composable
fun TimePickerItem(
    modifier: Modifier = Modifier,
    dayOfWeek: String,
    fromTime: String = "",
    toTime: String = "",
    isActive: Boolean = true,
    showBottomBorder: Boolean = true,
    onRemove: () -> Unit ,
    onAdd: () -> Unit,
    onSelectFrom: () -> Unit ,
    onSelectTo: () -> Unit,
) {
    val timePickerBoxWidth = 68.dp
    val timePickerChipCornerRadius = 8.dp
    val iconsSize = 24.dp
    val chipPadding = 6.dp

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(isActive){
                //remove circle btn
                Box(modifier = Modifier
                    .width(34.dp)
                    .padding(end = 8.dp)
                    .clip(CircleShape)
                    .clickable { onRemove()}
                ) {
                    Icon(
                        modifier = Modifier.size(iconsSize),
                        imageVector = Icons.Outlined.DoNotDisturbOn,
                        contentDescription = null,
                        tint = orange2
                    )
                }
            }else{
                Box(modifier = Modifier.width(34.dp))
            }

            val weekColor = if(isActive){
                if(dayOfWeek == WeekDays.SUNDAY.name || dayOfWeek ==WeekDays.SATURDAY.name){
                    info_sky
                } else MaterialTheme.colorScheme.onBackground
            } else gray30.copy(.65f)

            Text(
                modifier = Modifier.width(timePickerBoxWidth),
                text = if(dayOfWeek.length > 3) dayOfWeek.substring(0,3) else "",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W600
                ),
                color = weekColor
            )

            // from time picker chip
            Box(
                modifier = Modifier
                    .width(timePickerBoxWidth)
                    .clip(RoundedCornerShape(timePickerChipCornerRadius))
                    .clickable { onSelectFrom() }
                    .background(if(isActive) lightGrayBackground else Color.Transparent)
                    .padding(chipPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = fromTime.ifEmpty { if(isActive) "11:00" else "00:00" },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isActive) MaterialTheme.colorScheme.onBackground else gray30.copy(alpha = .65f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            //to time picker chip
            Box(
                modifier = Modifier
                    .width(timePickerBoxWidth)
                    .clip(RoundedCornerShape(timePickerChipCornerRadius))
                    .clickable { onSelectTo() }
                    .background(if(isActive) lightGrayBackground else Color.Transparent)
                    .padding(chipPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = toTime.ifEmpty { if(isActive) "11:00" else "00:00" },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isActive) MaterialTheme.colorScheme.onBackground else gray30.copy(alpha = .65f)
                )
            }
            // add btn
            Box(modifier = Modifier
                .padding(start = 8.dp)
                .clip(CircleShape)
                .clickable { onAdd() }) {
                Icon(
                    modifier = Modifier.size(iconsSize),
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        if(showBottomBorder){
            DividerLine()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimePicker() {
    TimePickerItem( dayOfWeek = "MON", onSelectFrom = {}, onSelectTo = {}, onAdd = {}, onRemove = {})
}