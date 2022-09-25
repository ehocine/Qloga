package eac.qloga.android.features.p4p.shared.scenes.providerSearch

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FilterButton
import eac.qloga.android.core.shared.components.Buttons.MapButton
import eac.qloga.android.core.shared.components.Buttons.UserButton
import eac.qloga.android.core.shared.components.Provider
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.shared.components.BottomSheetFilter
import eac.qloga.android.features.p4p.shared.components.TwoSwitchTabRow
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.FilterTypes
import eac.qloga.android.features.p4p.shared.viewmodels.ProviderSearchViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.IconStatus
import eac.qloga.android.features.p4p.showroom.shared.components.ProvidersMapView
import kotlinx.coroutines.launch

enum class ProvidersTabItems(
    val label: String
){
    MATCH_REQUEST("Match requests"),
    SELECT_SERVICES("Select services"),
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProviderSearchScreen(
    navController: NavController,
    parentRoute: String? = null,
    hideNavBar: (Boolean) -> Unit = {},
    viewModel: ProviderSearchViewModel = hiltViewModel()
) {
    val isMapShown = viewModel.isShownMap.value
    val providersCoordinates = viewModel.providersCoordinates.value
    val screenType = viewModel.providersScreenType.value
    val selectedTapIndex = ProvidersTabItems.values().indexOf(viewModel.selectedProvidersTab.value) //[0,1]
    val selectedRadioButton = viewModel.selectedServiceRadioOption.value
    val isFromCustomerNavContainer = remember{ mutableStateOf(false) }
    val title = "Kai's Elderly care (Edinburgh)"
    val rating = 4.2
    val imageId = R.drawable.pvr_profile_ava
    val description = "Residential cleaning services " +
            "for all parts of your home. With tasks" +
            " covering dish Residential cleaning " +
            "services for all parts of your home." +
            " With tasks covering dish  Residential " +
            "cleaning services for all parts of your home." +
            " With tasks covering dish Residential cleaning" +
            " services for all parts of your home. With tasks " +
            "covering."

    val lazyColumnState = rememberLazyListState()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    LaunchedEffect(modalBottomSheetState.isVisible){
        hideNavBar(modalBottomSheetState.isVisible)
            isFromCustomerNavContainer.value = parentRoute == P4pCustomerScreens.CustomerDashboard.route
    }

    BackHandler {
        if(isFromCustomerNavContainer.value){
            // navController.popBackStack(route = Screen.OrderListPrv.route, inclusive = false)
        }else{
            navController.navigateUp()
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            BottomSheetFilter(
                getFilterSliderState = { viewModel.getFilterSliderState(it) },
                sliderTypeFilterList = FilterTypes.sliderTypeFilterList,
                onValueChange = { type, value ->
                    viewModel.onTriggerEvent(ProviderSearchEvent.OnChangeSliderFilterState(type, value.toInt()))
                },
                onSelectProvidersType = { viewModel.onSelectProvidersType(it)},
                selectedProvidersTypeOption = viewModel.searchFilterState.value.providersType,
                onSelectProvidersVerification = { viewModel.onSelectProvidersVerification(it)},
                selectedProvidersVerificationOption = viewModel.searchFilterState.value.providersVerifications,
                onSelectProvidersAdminVerification = { viewModel.onSelectProvidersAdminVerification(it)},
                selectedProvidersAdminVerificationsOptions = viewModel.searchFilterState.value.providersAdminVerifications,
                onSelectClearanceCertificates = { viewModel.onSelectClearanceCertificates(it)},
                selectedClearanceCertificationsOptions = viewModel.searchFilterState.value.clearanceCertifications
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.ProviderSearch.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    showBackPressButton = !isFromCustomerNavContainer.value,
                    actions = {
                        FilterButton( onClick = {
                                    scope.launch {
                                        modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }, Color.Black
                        )
                        if(isFromCustomerNavContainer.value){
                            UserButton(
                                onClick = {
                                    scope.launch {
                                       //navController.navigate(
                                       // Screen.Account.route+"?$ACCOUNT_TYPE_KEY=${AccountType.CUSTOMER.label}" +
                                       //     "&$PARENT_ROUTE_KEY=${Screen.CustomerNavContainer.route}"
                                       // )
                                    }
                                },
                                color = MaterialTheme.colorScheme.primary
                            )
                        }else{
                            MapButton(
                                onClick = { viewModel.onTriggerEvent(ProviderSearchEvent.ToggleOpenMap) },
                                color = if (isMapShown) MaterialTheme.colorScheme.primary else Color.Black
                            )
                        }
                    }
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValues ->
            val topPadding = paddingValues.calculateTopPadding()

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(topPadding))

                    when(screenType){
                        ProvidersScreenType.MAP -> {
                            ProvidersMapView( providersCoordinates )
                        }
                        ProvidersScreenType.ZERO_STATE -> {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Spacer(Modifier.padding(top = 16.dp))
                                TwoSwitchTabRow(
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                    height = 32.dp,
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    tabsContent = ProvidersTabItems.values().map { it.label },
                                    selectedTapIndex = selectedTapIndex,
                                    onSelect = {
                                        viewModel.onSelectProvidersTab(ProvidersTabItems.values()[it])
                                    },
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(32.dp)
                                    ,
                                    contentAlignment = Alignment.Center
                                ) {
                                    //TODO SVG
                                    Image(
                                        painter = rememberAsyncImagePainter(R.drawable.ql_openreq),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                        ProvidersScreenType.PROVIDERS -> {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Spacer(Modifier.padding(top = 16.dp))
                                TwoSwitchTabRow(
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                    height = 32.dp,
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    tabsContent = ProvidersTabItems.values().map { it.label },
                                    selectedTapIndex = ProvidersTabItems.values().indexOf(viewModel.selectedProvidersTab.value),
                                    onSelect = { index ->
                                        if(index == 1){
                                            scope.launch {
                                                navController.navigate(
                                                    P4pShowroomScreens.Categories.route // TODO: +"?$PARENT_ROUTE_KEY=${Screen.CustomerNavContainer.route}"
                                                ){
                                                    launchSingleTop = true
                                                }
                                            }
                                        }
                                        viewModel.onSelectProvidersTab(ProvidersTabItems.values()[index])
                                    },
                                )
                                Spacer(Modifier.padding(top = 16.dp))
                                Column(modifier = Modifier.padding(horizontal = 48.dp)) {
                                    if(selectedTapIndex == 0){
                                        SelectServiceRadioOption(
                                            label = "Complete home cleaning",
                                            isSelected = selectedRadioButton == 1,
                                            onSelect = { viewModel.onSelectServiceRadioOption(1) }
                                        )
                                        SelectServiceRadioOption(
                                            label = "Changeover cleaning",
                                            isSelected = selectedRadioButton == 2,
                                            onSelect = { viewModel.onSelectServiceRadioOption(2) }
                                        )
                                        SelectServiceRadioOption(
                                            label = "Stair cleaning",
                                            isSelected = selectedRadioButton == 3,
                                            onSelect = { viewModel.onSelectServiceRadioOption(3) }
                                        )
                                    }else{
                                        when(selectedRadioButton){
                                            1 -> {
                                                SelectServiceRadioOption(
                                                    label = "Complete home cleaning",
                                                    isSelected = selectedRadioButton == 1,
                                                    onSelect = { viewModel.onSelectServiceRadioOption(1) }
                                                )
                                            }
                                            2-> {
                                                SelectServiceRadioOption(
                                                    label = "Changeover cleaning",
                                                    isSelected = selectedRadioButton == 2,
                                                    onSelect = { viewModel.onSelectServiceRadioOption(2) }
                                                )
                                            }
                                            3 -> {
                                                SelectServiceRadioOption(
                                                    label = "Stair cleaning",
                                                    isSelected = selectedRadioButton == 3,
                                                    onSelect = { viewModel.onSelectServiceRadioOption(3) }
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(Modifier.padding(top = 16.dp))

                                LazyColumn(
                                    state = lazyColumnState,
                                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 24.dp)
                                ){
                                    items(viewModel.providersList.value){ _ ->
                                        Provider(
                                            title = title,
                                            rating = rating,
                                            imageId = imageId,
                                            description = description,
                                            onClickItem = {
                                                scope.launch {
                                                    navController.navigate(P4pShowroomScreens.ProviderDetails.route)
                                                }
                                            },
                                            statusCompose = {
                                                Column(modifier = Modifier.fillMaxWidth()) {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        IconStatus(
                                                            modifier = Modifier.weight(1f),
                                                            label = "3.5 miles",
                                                            iconId = R.drawable.ic_ql_miles
                                                        )
                                                        IconStatus(
                                                            modifier = Modifier.weight(1f),
                                                            label = "0 hour",
                                                            iconId = R.drawable.ic_no_shake_hnd
                                                        )
                                                    }
                                                    Spacer(Modifier.height(12.dp))
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        StatusCallOutFee(modifier= Modifier.weight(1f))

                                                        Column(
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .padding(horizontal = 4.dp),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.SpaceBetween
                                                        ) {
                                                            Icon(
                                                                modifier = Modifier.size(22.dp),
                                                                painter = painterResource(id = R.drawable.ic_ql_money_bag),
                                                                contentDescription = null,
                                                                tint = gray30
                                                            )
                                                            Spacer(Modifier.height(4.dp))
                                                            Text(
                                                                text = "£75.00/",
                                                                style = MaterialTheme.typography.labelMedium,
                                                                textAlign = TextAlign.Center,
                                                                fontWeight = FontWeight.W600
                                                            )
                                                            Text(
                                                                text = "hours",
                                                                style = MaterialTheme.typography.labelMedium,
                                                                textAlign = TextAlign.Center,
                                                                fontWeight = FontWeight.W600,
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        )
                                        Spacer(Modifier.height(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectServiceRadioOption(
    isSelected: Boolean,
    label: String,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(12.dp),
        ) {
            RadioButton(
                selected = isSelected,
                onClick = { onSelect() },
                colors = RadioButtonDefaults.colors(
                    unselectedColor = gray1,
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun StatusCallOutFee(
    modifier: Modifier = Modifier
) {
    val iconSize = 18.dp

    Column(
        modifier = modifier
            .padding(start = 4.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Callout \nfee",
            style = MaterialTheme.typography.labelMedium,
            color = gray30,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = Icons.Rounded.Check,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
