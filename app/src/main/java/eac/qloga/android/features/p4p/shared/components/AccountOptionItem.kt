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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun AccountOptionItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String = "",
    iconId: Int,
    onClick : () -> Unit
){
    val arrowIconSize = 18.dp

    ContainerBorderedCard(
        cornerRadius = 12.dp
    ) {
        Column(
            modifier = modifier
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, top = 14.dp, bottom = 14.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = painterResource(id = iconId),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 8.dp),
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = value,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.W400,
                        color = gray30,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Box(modifier = Modifier.width(arrowIconSize)){
                        Icon(
                            modifier = Modifier
                                .size(arrowIconSize)
                                .clip(CircleShape)
                            ,
                            imageVector = Icons.Rounded.ArrowForwardIos,
                            contentDescription = "forward arrow",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileList() {
    Box(modifier = Modifier.fillMaxSize()) {
        AccountOptionItem(title = "Rating",value =  "ID Address, Phone",iconId=  R.drawable.ic_rating_star){}
    }
}