package eac.qloga.android.features.p4p.shared.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.service.presentation.components.CountingButton

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ServicesItem(
    modifier: Modifier = Modifier,
    title: String,
    additionalInfo: String,
    description: String,
    count: Int,
    showBottomDivider: Boolean = true,
    price: String = "0",
    showPrice: Boolean = true,
    onSub: () -> Unit,
    onAdd: () -> Unit,
    onClickInfo: () -> Unit
) {
    val infoHeight = 28.dp
    val expanded = remember{ mutableStateOf(false) }
    val animatedExpandIconAngle  = animateFloatAsState(targetValue = if(expanded.value) -90f else 90f)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    // expand arrow button
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { expanded.value = !expanded.value }
                            .padding(8.dp)
                            .rotate(animatedExpandIconAngle.value)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .alpha(.5f)
                            ,
                            imageVector = Icons.Rounded.ArrowForwardIos,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = additionalInfo,
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )

                AnimatedVisibility(visible = expanded.value,) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        text = description,
                        style = MaterialTheme.typography.titleSmall,
                        color = gray30
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    CountingButton(onSub = { onSub() }, onAdd = { onAdd() }, count = count)
                    Box(modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                    ){
                        androidx.compose.animation.AnimatedVisibility(
                            visible = expanded.value,
                            enter = slideInVertically(tween())+ fadeIn(),
                            exit = slideOutVertically(tween())+ fadeOut()
                        ) {
                            Box(modifier = Modifier
                                .clip(CircleShape)
                                .clickable { onClickInfo() }
                            ){
                                Icon(
                                    modifier = Modifier.size(infoHeight),
                                    painter = painterResource(id = R.drawable.ic_info),
                                    contentDescription = null,
                                    tint = orange1
                                )
                            }
                        }
                    }

                    if(showPrice){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = 12.dp),
                            text = "£$price",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        if(showBottomDivider){
            LightDividerLine(modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewServicesItem(){
    ServicesItem(
        title = "Windows cleaning",
        additionalInfo = "Time norm: 60 min/room (recomended)",
        description = "Professional will come to your house and try " +
                "theirhardest to fix your boiler as soon as possible.",
        onSub = {},
        onAdd = {},
        price = "450",
        count = 4,
        onClickInfo = {}
    )
}