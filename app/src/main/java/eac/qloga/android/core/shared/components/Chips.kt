package eac.qloga.android.core.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1

object Chips {
    @Composable
    fun FullRoundedChip(
        modifier: Modifier = Modifier,
        label: String,
        count : Int = 0,
        countColor: Color = MaterialTheme.colorScheme.primary,
        borderColor: Color = gray30,
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colorScheme.onBackground,
    ){
        Row(
            modifier = modifier
                .clip(CircleShape)
                .padding(top = 2.dp, bottom = 2.dp, end = 8.dp)
                .border(1.2.dp, borderColor, CircleShape)
                .background(backgroundColor)
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor.copy(alpha = .8f),
                fontWeight = FontWeight.W600,
            )
            if(count > 0){
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "$count",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.W600,
                    color = countColor.copy(alpha = .8f)
                )
            }
        }
    }


    @Composable
    fun SelectedServiceChip(
        modifier: Modifier = Modifier,
        label: String,
        count: Int,
        borderColor: Color = gray30,
        showCrossBtn: Boolean = false,
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = MaterialTheme.colorScheme.onBackground,
        onClick: () -> Unit,
        onClear: () -> Unit = {}
    ){
        val visible = remember{ mutableStateOf(false) }
        LaunchedEffect(Unit){
            visible.value = true
        }

        AnimatedVisibility(
            visible = visible.value && count > 0,
            enter = fadeIn(animationSpec = tween(600)),
            exit = fadeOut(animationSpec = tween(600))
        ) {
            Row(
                modifier = modifier
                    .padding(vertical = 2.dp, horizontal = 4.dp)
                    .clip(CircleShape)
                    .border(1.dp, borderColor, CircleShape)
                    .clickable { onClick() }
                    .background(backgroundColor)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = contentColor.copy(alpha = .5f),
                    fontWeight = FontWeight.W600,
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "$count",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.W600,
                    color = info_sky.copy(alpha = .5f)
                )

                if(count > 0 && showCrossBtn){
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(CircleShape)
                            .clickable { onClear() }
                    ){
                        Icon(
                            modifier = Modifier.size(14.dp),
                            painter = painterResource(id = R.drawable.ic_ql_cross),
                            contentDescription = null,
                            tint = orange1
                        )
                    }
                }
            }
        }
    }
}