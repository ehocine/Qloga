package eac.qloga.android.features.p4p.shared.components

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
import eac.qloga.android.features.p4p.shared.utils.ClearanceCertificationsOptions
import eac.qloga.android.features.p4p.shared.utils.FilterTypes
import eac.qloga.android.features.p4p.shared.utils.ProvidersAdminVerificationsOptions
import eac.qloga.android.features.p4p.shared.utils.ProvidersVerificationOptions
import eac.qloga.android.features.p4p.shared.viewmodels.ProviderSearchViewModel
import eac.qloga.android.features.p4p.showroom.shared.components.SliderCard
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFilter(
    modifier: Modifier = Modifier,
    onSelectProvidersVerification: (Int) -> Unit,
    selectedProvidersVerificationOption: List<Int>,
    onSelectProvidersAdminVerification: (Int) -> Unit,
    selectedProvidersAdminVerificationsOptions: List<Int>,
    onSelectClearanceCertificates: (Int) -> Unit,
    selectedClearanceCertificationsOptions: List<Int>,
    viewModel: ProviderSearchViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val slidedValue = remember { mutableStateOf(0f) }

    val distanceSlider by remember { mutableStateOf(viewModel.proximity) }
    val returnRateSlider by remember { mutableStateOf(viewModel.repeatedCustomerRate) }
    val minimumStartRatingSlider by remember { mutableStateOf(viewModel.rating) }
    val ordersQtySlider by remember { mutableStateOf(viewModel.ordersQty) }
    val providerType by remember {
        mutableStateOf(viewModel.individual)
    }
    val clearanceTypeId by remember {
        viewModel.clearanceTypeId
    }

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


            SliderCard(
                label = "${FilterTypes.Distance.label} " + distanceSlider.value,
                value = (distanceSlider.value.toFloat() / 200) * 100, //maxValue = 200
                onValueChange = {
                    viewModel.proximity.value = ((it / 100) * 200).toLong()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SliderCard(
                label = "${FilterTypes.ReturnRate.label} ${returnRateSlider.value}%",
                value = returnRateSlider.value.toFloat(),
                onValueChange = {
                    viewModel.repeatedCustomerRate.value = it.toLong()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SliderCard(
                label = "${FilterTypes.MinStartRating.label} " + minimumStartRatingSlider.value / 1000,
                value = (minimumStartRatingSlider.value.toFloat()) / 1000, //maxValue = 5
                valueRange = 0f..5f,
                steps = 4,
                onValueChange = {
                    viewModel.rating.value = it.roundToInt() * 1000
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SliderCard(
                label = "${FilterTypes.OrdersDelivered.label} ${ordersQtySlider.value}",
                value = (ordersQtySlider.value.toFloat() / 500) * 100,
                onValueChange = {
                    viewModel.ordersQty.value = ((it * 500) / 100).toLong()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp),
                    text = FilterTypes.ProvidersType.label,
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
                                text = "All",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp),
                                selected = providerType.value == null,
                                onClick = { viewModel.individual.value = null },
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
                                text = "Individual",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp),
                                selected = providerType.value == true,
                                onClick = { viewModel.individual.value = true },
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
                                text = "Agency",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp),
                                selected = providerType.value == false,
                                onClick = { viewModel.individual.value = false },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = gray1
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            SelectOptionCard(
                label = FilterTypes.ProviderAdminVerifications.label,
                onSelect = { onSelectProvidersAdminVerification(it) },
                options = ProvidersAdminVerificationsOptions.listValue.map { it.label },
                selected = selectedProvidersAdminVerificationsOptions
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectOptionCard(
                label = FilterTypes.ProviderVerifications.label,
                onSelect = { onSelectProvidersVerification(it) },
                options = ProvidersVerificationOptions.listValue.map { it.label },
                selected = selectedProvidersVerificationOption
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp),
                    text = FilterTypes.ClearanceCertificates.label,
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
                                text = ClearanceCertificationsOptions.listValue[0].label,
                                modifier = Modifier.weight(9f),
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp).weight(1f),
                                selected = clearanceTypeId == 1L,
                                onClick = { onSelectClearanceCertificates(0) },
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
                                text = ClearanceCertificationsOptions.listValue[1].label,
                                modifier = Modifier.weight(9f),
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp).weight(1f),
                                selected = clearanceTypeId == 2L,
                                onClick = { onSelectClearanceCertificates(1) },
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
                                text = ClearanceCertificationsOptions.listValue[2].label,
                                modifier = Modifier.weight(9f),
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp).weight(1f),
                                selected = clearanceTypeId == 3L,
                                onClick = { onSelectClearanceCertificates(2) },
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
                                text = ClearanceCertificationsOptions.listValue[3].label,
                                modifier = Modifier.weight(9f),
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp).weight(1f),
                                selected = clearanceTypeId == 4L,
                                onClick = { onSelectClearanceCertificates(3) },
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
                                text = ClearanceCertificationsOptions.listValue[4].label,
                                modifier = Modifier.weight(9f),
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            RadioButton(
                                modifier = Modifier.height(24.dp).weight(1f),
                                selected = clearanceTypeId == null,
                                onClick = { onSelectClearanceCertificates(4) },
                                colors = RadioButtonDefaults.colors(
                                    unselectedColor = gray1
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}