package eac.qloga.android.features.intro.presentation.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.features.shared.presentation.components.DividerLines.DividerLine
import eac.qloga.android.ui.theme.gray30
import eac.qloga.android.ui.theme.orange1

@Composable
fun CategoryList(
    modifier: Modifier = Modifier,
    title: String,
    showDivider: Boolean = true,
    summery: String,
    onClickI: () -> Unit,
    onShowProviders : () -> Unit,
){
    val buttonHeight = 40.dp
    val infoHeight = 32.dp
    val expanded = remember { mutableStateOf(false) }
    val animatedFloat = animateFloatAsState(targetValue = if(expanded.value) 90f else 0f)

    Column(
        modifier = modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 600
                )
            )
            .clickable { expanded.value = !expanded.value }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
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

        if(expanded.value){
            Column(modifier = Modifier.padding(start  = 16.dp, end = 16.dp)) {
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
                        .fillMaxWidth()
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.6f)
                            .height(buttonHeight)
                            .clip(CircleShape)
                            .clickable { onShowProviders() }
                            .background(MaterialTheme.colorScheme.primary)
                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Show providers",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(modifier = Modifier.clip(CircleShape).clickable { onClickI() }){
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
        if(showDivider){
            DividerLine(Modifier.padding(start = 64.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewList() {
    CategoryList(title="Complete Home Cleaning ", summery = "", onShowProviders = {}, onClickI = {})
}