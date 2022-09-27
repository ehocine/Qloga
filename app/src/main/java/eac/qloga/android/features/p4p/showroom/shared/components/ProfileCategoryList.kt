package eac.qloga.android.features.p4p.showroom.shared.components

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun ProfileCategoryList(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    iconId: Int,
    showDivider: Boolean = true,
    onExpand : () -> Unit
){
    // Not enough room for long text so remove some
    val valueText = if(value.length > 29) value.substring(0,28) else value

    Column(
        modifier = modifier.clickable { onExpand() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, top = 14.dp, bottom = 14.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = iconId),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W400
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Row(
                    modifier = Modifier.weight(1f)
                        .padding(end = 4.dp, start = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = valueText,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.W400,
                        color = gray30,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                    ,
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "forward arrow",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        if(showDivider){
            LightDividerLine(Modifier.padding(start = 64.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileList() {
    Box(modifier = Modifier.fillMaxSize()) {
        ProfileCategoryList(title = "Rating",value =  "ID Address, ",iconId=  R.drawable.ic_rating_star){}
    }
}