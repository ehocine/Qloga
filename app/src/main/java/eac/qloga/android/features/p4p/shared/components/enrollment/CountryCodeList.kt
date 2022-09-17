package eac.qloga.android.features.p4p.shared.components.enrollment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.CountryCode
import eac.qloga.android.core.shared.utils.CountryCodes

@Composable
fun CountryCodeList(
    modifier: Modifier = Modifier,
    selectCountryCode: CountryCode,
    countryCodes: CountryCodes,
    onSelectCountryCode: (CountryCode) -> Unit,
) {
    val containerHorizontalPadding = 20.dp
    val lazyColumnState = rememberLazyListState()

//    val materialDatePicker = MaterialDatePicker.Builder()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = containerHorizontalPadding, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(.15f)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(gray1)
            ,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            state = lazyColumnState,
        ){
            items(countryCodes.countryCodes, key = {it.code} ){ countryCode ->
                val isSelected = selectCountryCode == countryCode
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .clickable { onSelectCountryCode(countryCode) }
                        .padding(8.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = countryCode.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = countryCode.dialCode,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else orange1
                    )
                }
            }
        }
    }
}