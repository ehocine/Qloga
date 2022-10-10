package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.DividerLine

@Composable
fun FAQItem(
    modifier: Modifier = Modifier,
    iconId: Int? = null ,
    question: String,
    expandable: Boolean = true,
) {
    val iconSize = 24.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        iconId?.let {
            Box(modifier = Modifier.padding(end = 16.dp)){
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = question,
                    style = MaterialTheme.typography.titleMedium
                )

                if(expandable){
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Rounded.ArrowForwardIos,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            DividerLine(Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Preview
@Composable
fun PreviwewFAQItem() {
    FAQItem(iconId = R.drawable.ic_ql_prv_faq, question = "Provider F.A.Q.")
}