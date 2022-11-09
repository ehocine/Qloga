package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.orange1

@Composable
fun ServicesListItem(
    modifier: Modifier = Modifier,
    title: String,
    showDivider: Boolean = true,
    summery: String,
    value: String? = null,
    isSelected : Boolean,
    onClick: () -> Unit,
    onShowProviders: () -> Unit,
    expandable: Boolean = true,
    onClickItem: () -> Unit = {},
    catChanged: Boolean
) {
    val buttonHeight = 40.dp
    val infoHeight = 32.dp
    var expanded by remember { mutableStateOf(false) }
    if (catChanged) expanded = false
    val animatedFloat = animateFloatAsState(targetValue = if (expanded) 90f else 0f)

    Column(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 600
                )
            )
            .clickable {
                if(expandable){
                    expanded = !expanded
                }else{
                    onClickItem()
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(9f),
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = if (isSelected) FontWeight.W600 else FontWeight.W500
                ),
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(!value.isNullOrEmpty()){
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = "$value",
                        style = MaterialTheme.typography.titleSmall,
                        color = gray30
                    )
                }
                Icon(
                    modifier = Modifier
                        .rotate(animatedFloat.value)
                        .size(17.dp)
                        .clip(CircleShape)
                    ,
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "forward arrow",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (expanded) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Text(
                    text = summery,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 15.sp
                    ),
                    color = gray30
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .height(buttonHeight)
                            .clip(CircleShape)
                            .clickable { onShowProviders() }
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Select & search providers",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onClick() }) {
                        Icon(
                            modifier = Modifier.size(infoHeight),
                            painter = painterResource(id = R.drawable.ic_info),
                            contentDescription = null,
                            tint = orange1
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (showDivider) {
            DividerLine(Modifier.padding(start = 64.dp))
        }
    }
}