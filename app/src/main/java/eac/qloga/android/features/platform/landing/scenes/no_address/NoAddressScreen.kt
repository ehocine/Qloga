package eac.qloga.android.features.platform.landing.scenes.no_address

import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.data.shared.models.address_suggestions.Suggestion
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.SearchBar
import eac.qloga.android.features.p4p.showroom.shared.viewModels.AddressViewModel

@Composable
fun NoAddressScreen(
    navController: NavController,
    viewModel: NoAddressViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
) {

    val searchBarValue = viewModel.inputFieldState.value.text
    val searchBarFocusRequester = remember { FocusRequester() }

    val addressSuggestionsLoadingState by addressViewModel.getAddressSuggestionsLoadingState.collectAsState()
    val addressSuggestions by addressViewModel.addressSuggestionsList

    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }


    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp), 
        contentAlignment = Alignment.Center) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                parentSize = it.size
            }) {
            SearchBar(
                value = searchBarValue,
                isFocused = viewModel.inputFieldState.value.isFocused,
                hint = viewModel.inputFieldState.value.hint,
                focusRequester = searchBarFocusRequester,
                onValueChange = {
                    viewModel.onTriggerEvent(NoAddressEvent.EnterText(it))
                    addressViewModel.getAddressSuggestions(
                        term = it
                    )
                },
                onSubmit = {
                    if (viewModel.inputFieldState.value.text.isNotEmpty()) {
                        AddressViewModel.selectedAddressSuggestion.value =
                            Suggestion(searchBarValue, "", "")
                        navController.navigate(P4pShowroomScreens.AddAddress.route)
                    }

                },
                onClear = { viewModel.onTriggerEvent(NoAddressEvent.ClearInput) },
                onFocusedChanged = { viewModel.onTriggerEvent(NoAddressEvent.FocusInput(it)) }
            )

            when (addressSuggestionsLoadingState) {
                LoadingState.LOADED -> {
                    expanded = true
                    LaunchedEffect(key1 = true) {
                        addressViewModel.getAddressSuggestionsLoadingState.value =
                            LoadingState.IDLE
                    }
                }
            }

            if (addressSuggestions.isNotEmpty()) {

                DropdownMenu(
                    modifier = Modifier
                        .width(with(LocalDensity.current) { parentSize.width.toDp() }),
                    expanded = expanded,
                    properties = PopupProperties(focusable = false),
                    onDismissRequest = { expanded = false }
                ) {
                    addressSuggestions.forEach { addressSuggestion ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                viewModel.onTriggerEvent(
                                    NoAddressEvent.AddressChosen(
                                        addressSuggestion.address
                                    )
                                )
                                AddressViewModel.selectedAddressSuggestion.value =
                                    addressSuggestion
                                navController.navigate(P4pShowroomScreens.AddAddress.route)
                            }
                        ) {
                            Text(text = addressSuggestion.address)
                        }
                    }
                }
            }
        }

    }

}