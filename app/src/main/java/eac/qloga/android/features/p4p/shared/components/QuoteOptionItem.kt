package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun QuoteOptionItem(
    modifier: Modifier = Modifier,
    title : String ,
    value: String,
    showDivider: Boolean = true,
    enable: Boolean = true,
    iconId: Int? = null,
    onExpand: () -> Unit
) {
    val color = if(enable) MaterialTheme.colorScheme.onBackground else gray30
    val iconSize = 20.dp

    Column(
        modifier = modifier.clickable{ onExpand() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            iconId?.let{
                Box(
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = painterResource(id = it),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
            ){
                Text(
                    modifier = Modifier
                        .alpha(if (enable) 1f else .75f)
                        .wrapContentWidth()
                        .padding(end = 8.dp)
                    ,
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = color
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ){
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 4.dp)
                    ,
                    text = value,
                    style = MaterialTheme.typography.titleSmall,
                    color = gray30,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .wrapContentWidth()
                    .alpha(if (enable) 1f else .75f)
                    .clip(CircleShape)
                ,
                imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = "forward arrow",
                tint = if(enable) MaterialTheme.colorScheme.primary else gray30
            )
        }

        if(showDivider){
            LightDividerLine(Modifier.padding(start = 64.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomCardItem(){
    QuoteOptionItem(title = "Windows cleaning", value = "Rate ($/hour): 21.00", onExpand = {}, iconId = R.drawable.ic_ql_home)
}
