package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eac.qloga.android.ui.theme.gray1
import eac.qloga.android.ui.theme.gray30
import eac.qloga.android.ui.theme.lightGrayBackground

@Composable
fun CrossTrailingIcon(
    color: Color = Color.White,
    onClick : () -> Unit,
    background: Color = lightGrayBackground
){
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(background)
            .padding(2.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(14.dp)
            ,
            imageVector = Icons.Rounded.Close,
            contentDescription = "Cross Icon",
            tint = color
        )
    }
}