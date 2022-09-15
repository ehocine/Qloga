package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CountText(
    count: Int
) {
    val visible = remember{ mutableStateOf(false) }

    LaunchedEffect( Unit ){
        visible.value = true
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = scaleIn(animationSpec = tween(
            durationMillis = 1000
        )),
        exit = scaleOut(animationSpec = tween(
            durationMillis = 1000
        ))
    ) {
        Box(
            modifier = Modifier ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$count",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}