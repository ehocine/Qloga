package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.data.shared.models.Language
import eac.qloga.android.features.p4p.shared.utils.SpokenLanguage

@Composable
fun LanguageOptions(
    spokenLanguageState: List<Language?>,
    languagesOptions: List<Language?>,
    onSelect: (Language) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Cards.ContainerBorderedCard {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    languagesOptions.forEach {
                        OptionItem(
                            label = it?.descr ?: "",
                            isSelected  = it in spokenLanguageState
                        ) {
                            if (it != null) {
                                onSelect(it)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionItem(
    label: String,
    isSelected: Boolean = true,
    onSelect: () -> Unit
){
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onSelect()
            }
            .padding(vertical = 4.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            text = label,
            color = if(isSelected) MaterialTheme.colorScheme.primary else gray30,
            style = MaterialTheme.typography.bodyMedium
        )

        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .border(
                    1.dp,
                    if (isSelected) MaterialTheme.colorScheme.primary else gray1,
                    CircleShape
                )
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Rounded.Check,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}