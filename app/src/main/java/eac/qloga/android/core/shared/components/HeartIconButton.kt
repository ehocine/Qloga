package eac.qloga.android.core.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.purple_heart

@Composable
fun HeartIconButton(
    onClick: () -> Unit,
    isSelected: Boolean,
){
    val interactionSource = MutableInteractionSource()

    Box(
        Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
    ) {
        AnimatedVisibility(
            visible = !isSelected,
            enter = fadeIn(animationSpec = tween(700)),
            exit = fadeOut(animationSpec = tween(700))
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_ql_heart) ,
                contentDescription = "",
                tint = purple_heart
            )
        }
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(700)),
            exit = fadeOut(animationSpec = tween(700))
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_ql_filled_heart) ,
                contentDescription = "",
                tint = purple_heart
            )
        }
    }
}