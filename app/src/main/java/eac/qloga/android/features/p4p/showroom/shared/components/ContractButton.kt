package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun ContractButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Cards.ContainerBorderedCard() {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
            ){
                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Full service contract",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Rounded.ArrowForwardIos,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}