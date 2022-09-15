package eac.qloga.android.core.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.showroom.shared.components.OptionItem

@Composable
fun RadioOptionsCard(
    label: String,
    listOfOptions: List<String>,
    selectedOptionIndexList: List<Int>,
    onSelect: (Int) -> Unit
) {
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
        Cards.ContainerBorderedCard(cornerRadius = 12.dp) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                listOfOptions.forEachIndexed { index, value ->
                    OptionItem(
                        label = value,
                        isSelected  =  index in selectedOptionIndexList
                    ) {
                        onSelect(index)
                    }
                }
            }
        }
    }
}