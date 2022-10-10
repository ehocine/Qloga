package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.IOSArrowForwardButton
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun OrderedServicesCard(
    modifier: Modifier = Modifier,
    onClickService: () -> Unit
) {
    Cards.ContainerBorderedCard {
        Column(
            modifier = modifier
        ) {
            CardItem(label = "Classic plumbing and modern plumbing", price = 84.00){ onClickService() }
            CardItem(label = "Drain and sewer repair", price = 24.00, showBottomLine = false){ onClickService() }
        }
    }
}

@Composable
private fun CardItem(
    modifier: Modifier = Modifier,
    label: String,
    price: Double,
    showBottomLine: Boolean = true,
    onClickArrow: () -> Unit
){
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClickArrow() }
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.padding(8.dp)){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ql_tap),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "£$price",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = gray30
                )
                IOSArrowForwardButton( onClick = { onClickArrow() })
            }
        }
        if(showBottomLine){
            DividerLines.LightDividerLine(Modifier.padding(start = 60.dp))
        }
    }
}

@Preview
@Composable
fun PreviewOrderedServicesCard() {
    OrderedServicesCard(onClickService = {})
}