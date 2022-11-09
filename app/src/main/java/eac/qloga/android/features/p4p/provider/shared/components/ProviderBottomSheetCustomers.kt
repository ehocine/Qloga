package eac.qloga.android.features.p4p.provider.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderDashboardViewModel
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine
import eac.qloga.android.features.p4p.showroom.shared.components.SliderCard
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderBottomSheetCustomers(
    modifier: Modifier = Modifier,
    selectedVerificationIndexes: List<Int>,
    onSelectVerificationOption: (Int) -> Unit,
    viewModel: ProviderDashboardViewModel = hiltViewModel()
) {
    val matchByOption by remember {
        mutableStateOf(viewModel.matchServices)
    }
    val minimumSumSlider by remember { mutableStateOf(viewModel.minOfferSum) }
    val distanceSlider by remember { mutableStateOf(viewModel.proximity) }
    val minimumStartRatingSlider by remember { mutableStateOf(viewModel.rating) }
    val scrollState = rememberScrollState()

    val ratingSliderValue = remember { mutableStateOf(1f) }

    Column(modifier = Modifier.fillMaxWidth()) {
        BottomSheetDashLine()
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp),
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
                        .padding(start = 8.dp, bottom = 4.dp),
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
                            RadioButton(
                                modifier = Modifier.height(24.dp),
                                selected = !matchByOption.value,
                                onClick = { viewModel.matchServices.value = false },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = gray1
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
                            RadioButton(
                                modifier = Modifier.height(24.dp),
                                selected = matchByOption.value,
                                onClick = { viewModel.matchServices.value = true },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = gray1
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
                        label = "MINIMUM BUDGET(£): " + minimumSumSlider.value / 100,
                        /*** Convert the value into (0-100)% range to show in slider */
                        value = (minimumSumSlider.value.toFloat() / 500), //maxValue = 500
                        onValueChange = {
                            /***** Convert value from percentage(0-100) to normal *********/
                            viewModel.minOfferSum.value = ((it / 100) * 500 * 100).toLong()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SliderCard(
                        label = "DISTANCE (miles): " + distanceSlider.value,
                        value = (distanceSlider.value.toFloat() / 200) * 100, //maxValue = 200
                        onValueChange = {
                            viewModel.proximity.value = ((it / 100) * 200).toLong()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SliderCard(
                        label = "MINIMUM START RATING: " + minimumStartRatingSlider.value / 1000,
                        value = (minimumStartRatingSlider.value.toFloat()) / 1000, //maxValue = 5
                        valueRange = 0f..5f,
                        steps = 4,
                        onValueChange = {
                            viewModel.rating.value = it.roundToInt() * 1000
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