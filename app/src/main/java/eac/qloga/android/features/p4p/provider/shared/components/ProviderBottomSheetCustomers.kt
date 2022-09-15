package eac.qloga.android.features.p4p.provider.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.business.util.Extensions.roundToRating
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.intro.presentation.components.SliderCard
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine

@Composable
fun ProviderBottomSheetCustomers(
    modifier: Modifier = Modifier,
    selectedVerificationIndexes: List<Int>,
    onSelectVerificationOption: (Int) -> Unit
) {
    val categoriesCheckBox = remember { mutableStateOf(false) }
    val servicesCheckBox = remember { mutableStateOf(true) }
    val minimumSumSlider = remember{ mutableStateOf(0)}
    val distanceSlider = remember{ mutableStateOf(0)}
    val minimumStartRatingSlider = remember{ mutableStateOf(0f)}
    val scrollState = rememberScrollState()

    val ratingSliderValue = remember{ mutableStateOf(1f) }

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp)
                    ,
                    text = "MATCH BY",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
                Cards.ContainerBorderedCard(
                    cornerRadius = 12.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Categories",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            Switch(
                                modifier = Modifier.height(24.dp),
                                checked = categoriesCheckBox.value,
                                onCheckedChange = { categoriesCheckBox.value = !categoriesCheckBox.value },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Services",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            Switch(
                                modifier = Modifier.height(24.dp),
                                checked = servicesCheckBox.value,
                                onCheckedChange = { servicesCheckBox.value = !servicesCheckBox.value },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    SliderCard(
                        label = "MINIMUM SUM(£): "  + minimumSumSlider.value,
                        /*** Convert the value into (0-100)% range to show in slider */
                        value = ( minimumSumSlider.value.toFloat()/500)*100, //maxValue = 500
                        onValueChange = {
                            /***** Convert value from percentage(0-100) to normal *********/
                            minimumSumSlider.value = ((it/100) * 500).toInt()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SliderCard(
                        label = "Distance(miles): "  + distanceSlider.value,
                        value = ( distanceSlider.value.toFloat()/200)*100, //maxValue = 200
                        onValueChange = {
                            distanceSlider.value = ((it/100) * 200).toInt()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
                    CustomerVerificationOptionCard(
                        label = "VERIFICATION TYPES",
                        onSelect = { onSelectVerificationOption(it) },
                        selectedOption = selectedVerificationIndexes
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}