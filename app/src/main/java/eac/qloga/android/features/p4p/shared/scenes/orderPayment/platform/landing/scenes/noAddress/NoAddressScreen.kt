package eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.noAddress

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.SuggestionCard
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.Padding
import eac.qloga.android.data.shared.models.address_suggestions.Suggestion
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NoAddressScreen(
    navController: NavController,
    viewModel: NoAddressViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as Activity
    val searchBarValue = viewModel.inputFieldState.value.text
    val searchBarFocusRequester = remember { FocusRequester() }
    val screenHeightDp = remember { mutableStateOf(0) }

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val addressSuggestionsLoadingState by addressViewModel.getAddressSuggestionsLoadingState.collectAsState()
    val addressSuggestions by addressViewModel.addressSuggestionsList

    var expanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    // to avoid resize the layout automatically
    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

    DisposableEffect(true) {
        onDispose {
            //make adjustResize to rest of screen
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    if (scrollState.isScrollInProgress) {
        keyboardController?.hide()
    }

    with(LocalDensity.current) {
        screenHeightDp.value = LocalConfiguration.current.screenHeightDp
    }

    Scaffold { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            expanded = false
                            focusManager.clearFocus()
                        }
                    )
                }
                .padding(horizontal = Padding.containerHorizontalPadding.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = R.drawable.curvy_back_15),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Image(
                        painter = rememberAsyncImagePainter(model = R.drawable.ql_home),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

            ConstraintLayout {
                val (searchBar, suggestionCard) = createRefs()

                Box(
                    modifier = Modifier
                        .constrainAs(searchBar) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .onGloballyPositioned {
                            parentSize = it.size
                        },
                ) {
                    SearchBar(
                        value = searchBarValue,
                        isFocused = viewModel.inputFieldState.value.isFocused,
                        hint = viewModel.inputFieldState.value.hint,
                        background = MaterialTheme.colorScheme.background,
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
                }

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
                    SuggestionCard(
                        modifier = Modifier
                            .constrainAs(suggestionCard) {
                                bottom.linkTo(searchBar.top, margin = 8.dp)
                            }
                            .verticalScroll(scrollState),
                        width = with(LocalDensity.current) { parentSize.width.toDp() },
                        roundedCornerShape = RoundedCornerShape(8.dp),
                        expanded = expanded,
                    ) {
                        addressSuggestions.forEach { addressSuggestion ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        expanded = false
                                        viewModel.onTriggerEvent(
                                            NoAddressEvent.AddressChosen(
                                                addressSuggestion.address
                                            )
                                        )
                                        AddressViewModel.selectedAddressSuggestion.value =
                                            addressSuggestion
                                        AddressViewModel.searchAddress.value = true
                                        navController.navigate(P4pShowroomScreens.AddAddress.route)
                                    }
                                    .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
                            ) {
                                Text(
                                    modifier = Modifier,
                                    text = addressSuggestion.address,
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}