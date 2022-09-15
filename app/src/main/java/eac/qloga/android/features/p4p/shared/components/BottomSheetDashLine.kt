package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun BottomSheetDashLine() {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 12.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .width(70.dp)
                .clip(CircleShape)
                .background(gray1)
        )
    }
}