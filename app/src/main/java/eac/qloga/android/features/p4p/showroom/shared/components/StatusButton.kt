package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.business.util.Extensions.advancedShadow
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun StatusButton(
    modifier: Modifier = Modifier,
    leadingIcon: Int? = null,
    trailingIcon: ImageVector? = null,
    label: String ,
    count: String,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    val height = 52.dp

    Box(modifier = modifier
        .clip(RoundedCornerShape(12.dp))
        .advancedShadow(color = gray1, .8f, 16.dp, 12.dp)
        .padding(1.5.dp)
        .clickable(enabled = clickable) { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 16.dp, end = 16.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Image(
                    modifier = Modifier
                        .padding(end = 12.dp),
                    painter = painterResource(id = it),
                    contentDescription = ""
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400
                )
            )
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = count,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W400
                ),
                color = gray30
            )
            trailingIcon?.let {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = trailingIcon,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}