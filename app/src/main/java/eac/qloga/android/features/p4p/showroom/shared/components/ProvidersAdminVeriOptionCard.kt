package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.features.p4p.shared.utils.ProvidersAdminVerificationsOptions

@Composable
fun ProvidersAdminVeriOptionCard(
    label: String,
    selectedOption: ProvidersAdminVerificationsOptions,
    onSelect: (selectedOptions: ProvidersAdminVerificationsOptions) -> Unit
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
            cornerRadius = 12.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                ProvidersAdminVerificationsOptions.listValue.forEach {
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