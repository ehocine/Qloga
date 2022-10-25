package eac.qloga.android.features.p4p.provider.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.lightGrayBackground

@Composable
fun DateTimePickerItem(
    modifier: Modifier = Modifier,
    time: String = "",
    date: String = "",
    label: String,
    isActive: Boolean = true,
    onSelectDate: () -> Unit,
    onSelectTime: () -> Unit,
) {
    val dateChipWidth = 124.dp
    val timeChipWidth = 70.dp
    val cornerRadius = 8.dp
    val chipPadding = 6.dp

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                ,
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = if(isActive) MaterialTheme.colorScheme.onBackground else gray30.copy(.5f)
            )
            Box(
                modifier = Modifier
                    .widthIn(min = dateChipWidth)
                    .clip(RoundedCornerShape(cornerRadius))
                    .clickable { onSelectDate() }
                    .background(if(isActive) lightGrayBackground else Color.Transparent)
                    .padding(chipPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.ifEmpty { "22 Feb 2022" },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isActive) MaterialTheme.colorScheme.onBackground else gray30.copy(.5f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .widthIn(min = timeChipWidth)
                    .clip(RoundedCornerShape(cornerRadius))
                    .clickable { onSelectTime() }
                    .background(if(isActive) lightGrayBackground else Color.Transparent)
                    .padding(chipPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = time.ifEmpty { "11:00" },
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isActive) MaterialTheme.colorScheme.onBackground else gray30.copy(.5f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateTimePicker() {
    DateTimePickerItem(label = "from", onSelectDate = { /*TODO*/ }) {}
}