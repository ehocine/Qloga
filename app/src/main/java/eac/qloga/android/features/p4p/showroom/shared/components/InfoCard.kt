package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine
import eac.qloga.android.features.p4p.showroom.shared.utils.AccessLevel

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    access: String,
    name: String,
    label: String,
    owner: String? = null ,
) {
    val containerHorizontalPadding = 24.dp
    val accessLevel = remember(access){
        mutableStateOf(
            when(access){
                AccessLevel.Private.label -> AccessLevel.Private
                AccessLevel.Public.label -> AccessLevel.Public
                AccessLevel.Shared.label -> AccessLevel.Shared
                else -> {
                    AccessLevel.Private
                }
            }
        )
    }

    val color = when(accessLevel.value){
        AccessLevel.Private -> { MaterialTheme.colorScheme.primary}
        AccessLevel.Public -> { Color.Red }
        AccessLevel.Shared -> { info_sky }
    }
    val desc: String =  when(accessLevel.value){
        AccessLevel.Private -> "only you can see it"
        AccessLevel.Public -> "anyone can see it"
        AccessLevel.Shared -> "you and your family can see it"
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        BottomSheetDashLine()
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = containerHorizontalPadding)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                text = accessLevel.value.label + " $label",
                style = MaterialTheme.typography.bodyMedium,
                color = color
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .alpha(.65f)
                ,
                text = "($desc)",
                style = MaterialTheme.typography.titleSmall,
                color = gray30
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_ql_key),
                contentDescription = null,
                tint = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        DividerLine()
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = containerHorizontalPadding),
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        owner?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = containerHorizontalPadding),
                text = "Owner: $owner",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}