package eac.qloga.android.features.service.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.HorizontalRule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.grayTextColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CountingButton(
    onSub: () -> Unit,
    onAdd: () -> Unit,
    count: Int
) {
    val interactionSource = remember{ MutableInteractionSource() }
    val btnWidth = 154
    val buttonHeight = 34.dp
    val buttonWidth = btnWidth.dp
    val verticalDividerWidth = 1.5.dp
    val subBtnWidth = (btnWidth/3).dp
    val addBtnWidth = (btnWidth/3).dp
    val countTextWidth = (btnWidth/3 - 8).dp

    Row(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(CircleShape)
            .border(verticalDividerWidth, gray1, shape = CircleShape),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier
                .width(subBtnWidth)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onSub() }
            ,
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier
                    .size(30.dp)
                ,
                imageVector = Icons.Rounded.HorizontalRule,
                contentDescription = "",
                tint = grayTextColor,
            )
        }

        //vertical divider line
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(verticalDividerWidth)
                .border(
                    verticalDividerWidth,
                    gray1,
                )
            ,
        )

        Box(
            modifier = Modifier.width(countTextWidth),
            contentAlignment = Alignment.Center
        ){
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    if(targetState > initialState){
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    }else{
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()

                    }.using( SizeTransform(clip = false) )
                }
            ) { targetCount ->
                Text(
                    text = "$targetCount",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        //vertical divider line
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(verticalDividerWidth)
                .border(
                    verticalDividerWidth,
                    gray1,
                )
            ,
        )

        Box(
            modifier = Modifier
                .width(addBtnWidth)
                .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onAdd() },
            contentAlignment = Alignment.Center,
        ){
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview
@Composable
fun PreviewCountingButton() {
    CountingButton(onSub = { /*TODO*/ }, onAdd = { /*TODO*/ }, count = 0)
}