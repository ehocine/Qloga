package eac.qloga.android.features.p4p.shared.components.enrollment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun DocumentTypeItem(
    modifier: Modifier = Modifier,
    iconId: Int,
    label : String,
    onClick: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(painter = painterResource(id = iconId), contentDescription = "")

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                ,
                text = label,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )

        }

        Divider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .alpha(.1f)
                .height(1.dp)
                .background(gray1)
        )
    }
}