package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectedTitleBarItem(
    modifier: Modifier = Modifier,
    iconId: Int,
    iconSize: Dp = 32.dp,
    label: String? = null,
    onClick: () -> Unit,
    iconColor : Color = MaterialTheme.colorScheme.primary
){
    val visible = remember{ mutableStateOf(false) }
    val animationDuration = 700

    LaunchedEffect(Unit){
        visible.value = true
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = scaleIn(
            animationSpec = tween(durationMillis = animationDuration)
        ),
        exit = scaleOut(animationSpec = tween(durationMillis = animationDuration))
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onClick() }
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = iconColor
                )
            }
            label?.let {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true )
@Composable
fun PreviewSelectedItem() {
    SelectedTitleBarItem(iconId = R.drawable.ic_ql_heart, onClick = {})
}