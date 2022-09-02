package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.QLOGATheme
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    iconSize: Dp = 38.dp,
    iconId: Int,
    label: String,
    isSelected: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onClick: () -> Unit
) {
    val labelFontSize = 13.sp

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = if (isSelected) 2.dp else 1.5.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else gray1.copy(alpha = .6f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { onClick() }
                .background(backgroundColor)
            ,
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = iconId),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = labelFontSize,
                fontWeight = FontWeight.W600
            )
        )
    }
}

@Preview
@Composable
fun PreviewNav(){
    QLOGATheme(darkTheme = false) {
        NavItem(iconId = R.drawable.ic_clean, label = "Handyman"){}
    }
}