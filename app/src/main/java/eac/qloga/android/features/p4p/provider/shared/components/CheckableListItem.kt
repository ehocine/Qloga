package eac.qloga.android.features.p4p.provider.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R

@Composable
fun CheckableListItem(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean = false,
    selectedColor: Color,
    unSelectedColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember{ MutableInteractionSource()}
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
            .padding(vertical = 8.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
            color = if(isSelected) selectedColor else unSelectedColor
        )
        if(isSelected){
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.ic_ql_check_mark),
                contentDescription = null,
                tint = selectedColor
            )
        }
    }
}