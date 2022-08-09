package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import eac.qloga.android.features.intro.util.ProvidersTypeOptions
import eac.qloga.android.features.shared.presentation.components.Cards.ContainerBorderedCard
import eac.qloga.android.ui.theme.grayTextColor

@Composable
fun ProvidersTypeOptionCard(
    label: String,
    selectedOption: ProvidersTypeOptions,
    onSelect: (selectedOptions: ProvidersTypeOptions) -> Unit
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
            modifier = Modifier
                .fillMaxWidth()
            ,
            cornerRadius = 12.dp
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                ProvidersTypeOptions.listValue.forEach {
                    OptionItem(
                        label = it.label,
                        isSelected  = selectedOption == it
                    ) {
                        onSelect(it)
                    }
                }
            }
        }
    }
}