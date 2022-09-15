package eac.qloga.android.features.intro.presentation.components

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
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun SliderCard(
    label: String,
    value : Float,
    valueRange: ClosedFloatingPointRange<Float> = 1f..100f,
    steps: Int = 0,
    onValueChange: (Float) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 4.dp)
            ,
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = gray30
        )

        ContainerBorderedCard(
            modifier = Modifier.fillMaxWidth()
                .height(44.dp),
            cornerRadius = 12.dp
        ) {
            Slider(
                modifier = Modifier.padding(horizontal = 8.dp),
                value = value,
                onValueChange = { onValueChange(it) },
                enabled = true,
                valueRange = valueRange,
                steps = steps,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTickColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    }
}