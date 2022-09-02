package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.grayTextColor

@Composable
fun SliderCard(
    label: String,
    value : Float,
    onValueChange: (Float) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .alpha(.5f)
                .padding(start = 8.dp, bottom = 4.dp)
            ,
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = grayTextColor
        )

        ContainerBorderedCard(
            modifier = Modifier.fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 8.dp),
            cornerRadius = 12.dp
        ) {
            Slider(
                modifier = Modifier.padding(horizontal = 8.dp),
                value = value,
                onValueChange = { onValueChange(it) },
                enabled = true,
                valueRange = 1f..100f,
                steps = 0,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTickColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(44.dp)
//                .clip(RoundedCornerShape(12.dp))
//                .border(2.dp, gray1, RoundedCornerShape(12.dp))
//                .background(MaterialTheme.colorScheme.background)
//                .padding(horizontal = 8.dp)
//        ) {
//
//        }
    }
}