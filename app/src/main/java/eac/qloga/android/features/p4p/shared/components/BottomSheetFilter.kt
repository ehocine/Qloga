package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.business.util.Extensions.roundToRating
import eac.qloga.android.features.intro.presentation.components.*
import eac.qloga.android.features.p4p.shared.utils.*

@Composable
fun BottomSheetFilter(
    modifier: Modifier = Modifier,
    getFilterSliderState: (FilterTypes) -> Array<Int>,
    onValueChange: (FilterTypes, Float) -> Unit,
    sliderTypeFilterList: List<FilterTypes>,
    onSelectProvidersType: (Int) -> Unit,
    selectedProvidersTypeOption: List<Int>,
    onSelectProvidersVerification: (Int) -> Unit,
    selectedProvidersVerificationOption: List<Int>,
    onSelectProvidersAdminVerification: (Int) -> Unit,
    selectedProvidersAdminVerificationsOptions: List<Int>,
    onSelectClearanceCertificates: ( Int ) -> Unit,
    selectedClearanceCertificationsOptions: List<Int>
) {
    val scrollState = rememberScrollState()
    val slidedValue = remember{ mutableStateOf(0f) }
    val minimumStartRatingSlider = remember{ mutableStateOf(0f)}

    Column(modifier = Modifier.fillMaxWidth()) {
        BottomSheetDashLine()
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
            ,
            text = "Search filter",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
        ) {
            sliderTypeFilterList.forEach { filterType ->
                val units = if(filterType == FilterTypes.ReturnRate) " %" else ""
                val sliderValue = (getFilterSliderState(filterType)[0].toFloat()/getFilterSliderState(filterType)[1])*100
                val value = getFilterSliderState(filterType)[0]

                SliderCard(
                    /// getFilterSwipeState has two int values as array
                    /// First is value and second is max
                    label = filterType.label + value + units,
                    /*** Convert the value into (0-100)% range to show in slider */
                    value = sliderValue,
                    onValueChange = {
                        /***** Convert value from percentage(0-100) to normal *********/
                        slidedValue.value = (it/100) * getFilterSliderState(filterType)[1]
                        onValueChange(filterType, slidedValue.value)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            SliderCard(
                label = "MINIMUM START RATING: "  + minimumStartRatingSlider.value.roundToRating(),
                value = minimumStartRatingSlider.value, //maxValue = 5
                valueRange = 1f..5f,
                steps = 7,
                onValueChange = {
                    minimumStartRatingSlider.value = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectOptionCard(
                label = FilterTypes.ProvidersType.label,
                onSelect = { onSelectProvidersType(it) },
                options = ProvidersTypeOptions.listValue.map { it.label },
                selected = selectedProvidersTypeOption
            )
            Spacer(modifier = Modifier.height(16.dp))

            SelectOptionCard(
                label = FilterTypes.ProviderVerifications.label,
                onSelect = { onSelectProvidersVerification(it) },
                options = ProvidersVerificationOptions.listValue.map { it.label },
                selected = selectedProvidersVerificationOption
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectOptionCard(
                label = FilterTypes.ProviderAdminVerifications.label,
                onSelect = { onSelectProvidersAdminVerification(it) },
                options = ProvidersAdminVerificationsOptions.listValue.map { it.label },
                selected = selectedProvidersAdminVerificationsOptions
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectOptionCard(
                label = FilterTypes.ClearanceCertificates.label,
                onSelect = { onSelectClearanceCertificates(it) },
                options = ClearanceCertificationsOptions.listValue.map { it.label },
                selected = selectedClearanceCertificationsOptions
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}