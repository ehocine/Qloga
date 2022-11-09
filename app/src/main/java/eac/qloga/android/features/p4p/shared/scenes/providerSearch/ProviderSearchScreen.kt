package eac.qloga.android.features.p4p.shared.scenes.providerSearch

import P4pCustomerScreens
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Buttons.FilterButton
import eac.qloga.android.core.shared.components.Buttons.MapButton
import eac.qloga.android.core.shared.components.Buttons.UserButton
import eac.qloga.android.core.shared.components.Provider
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.blueTextColor
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.theme.orange2
import eac.qloga.android.core.shared.utils.Extensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.convertPrice
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.customer.shared.components.CustomerBottomNavItems
import eac.qloga.android.features.p4p.customer.shared.viewModels.CustomerDashboardViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.scenes.providerProfile.ProviderProfileViewModel
import eac.qloga.android.features.p4p.shared.components.BottomSheetFilter
import eac.qloga.android.features.p4p.shared.components.TwoSwitchTabRow
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import eac.qloga.android.features.p4p.shared.viewmodels.ProviderSearchViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.IconStatus
import eac.qloga.android.features.p4p.showroom.shared.components.ProvidersMapView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

enum class ProvidersTabItems(
    val label: String
) {
    MATCH_REQUEST("Match requests"),
    SELECT_SERVICES("Select service"),
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProviderSearchScreen(
    navController: NavController,
    parentRoute: String? = null,
    hideNavBar: (Boolean) -> Unit = {},
    viewModel: ProviderSearchViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val isMapShown = viewModel.isShownMap.value
    val providersCoordinates = viewModel.providersCoordinates.value
    val screenType = viewModel.providersScreenType.value
    val selectedTapIndex =
        ProvidersTabItems.values()
            .indexOf(ProviderSearchViewModel.selectedProvidersTab.value) //[0,1]
    val isFromCustomerNavContainer = remember { mutableStateOf(false) }
    val imageId = R.drawable.pvr_profile_ava

    val lazyColumnState = rememberLazyListState()
    val scrollContext = Extensions.rememberScrollContext(lazyColumnState)
    var getProvidersLoading by remember { mutableStateOf(false) }
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val getProvidersLoadingState by viewModel.getProvidersLoadingState.collectAsState()
    val avatarImageState by viewModel.avatarImageState.collectAsState()
    val getFirstRqsState by ProviderSearchViewModel.getFirstRqsState.collectAsState()

    var filtersApplied by remember { mutableStateOf(false) }
    var loadAvatars by remember { mutableStateOf(true) }
    var serviceChanged by remember { mutableStateOf(false) }
    val servicesList by ProviderSearchViewModel.servicesList
    val providersFirstSearch = ProviderSearchViewModel.providersFirstSearch.value
    val singleServiceFirstSearch = ProviderSearchViewModel.singleServiceFirstSearch.value
    var filtersOpened by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        if (providersFirstSearch && !singleServiceFirstSearch) {
            getProvidersLoading = true
            loadAvatars = true
            ProviderSearchViewModel.providersFirstSearch.value = false
            viewModel.getFirstRqPage()
        }
    }

    LaunchedEffect(key1 = ProviderSearchViewModel.singleService.value) {
        if (ProviderSearchViewModel.singleService.value != null) {
            getProvidersLoading = true
            loadAvatars = true
            viewModel.pageNumber.value = 1
            Log.d("Tag", "Current: ${viewModel.pageNumber.value}")
            viewModel.getProviders()
            ProviderSearchViewModel.singleServiceFirstSearch.value = false
        }
    }

    LaunchedEffect(key1 = scrollContext.isBottom) {
        Log.d(
            "Tag",
            "Cond: ${servicesList.isNotEmpty()} ${!providersFirstSearch} ${!viewModel.providersLastPage.value}"
        )
        if (servicesList.isNotEmpty() && !providersFirstSearch && !viewModel.providersLastPage.value) {
            if (getProvidersLoadingState == LoadingState.LOADED || getProvidersLoadingState == LoadingState.IDLE) {
                getProvidersLoading = true
                loadAvatars = true
                viewModel.pageNumber.value += 1
                Log.d("Tag", "Current: ${viewModel.pageNumber.value}")
                viewModel.getProviders()
            }
        }
    }

    LaunchedEffect(key1 = scrollContext.isBottom) {
        if (ProviderSearchViewModel.singleService.value != null && !singleServiceFirstSearch && !viewModel.providersLastPage.value) {
            if (getProvidersLoadingState == LoadingState.LOADED || getProvidersLoadingState == LoadingState.IDLE) {
                getProvidersLoading = true
                loadAvatars = true
                viewModel.pageNumber.value += 1
                Log.d("Tag", "Current: ${viewModel.pageNumber.value}")
                viewModel.getProviders()
            }
        }
    }

    LaunchedEffect(key1 = serviceChanged) {
        if (serviceChanged) {
            getProvidersLoading = true
            loadAvatars = true
            viewModel.pageNumber.value = 1
            Log.d("Tag", "Current: ${viewModel.pageNumber.value}")
            viewModel.getProviders()
            serviceChanged = false
            lazyColumnState.scrollToItem(index = 0)
        }
    }
    val providersList by ProviderSearchViewModel.providersList

    val dfRating = DecimalFormat("#.0")
    dfRating.roundingMode = RoundingMode.DOWN

    val dfDistance = DecimalFormat("#.#")
    dfDistance.roundingMode = RoundingMode.DOWN

    when (modalBottomSheetState.currentValue) {
        ModalBottomSheetValue.Expanded -> {
            viewModel.providersLastPage.value = false
            filtersApplied = true
        }
        ModalBottomSheetValue.Hidden -> {
            LaunchedEffect(key1 = Unit) {
//                viewModel.getProvidersLoadingState.emit(LoadingState.IDLE)
                // Scroll to the first item
                lazyColumnState.scrollToItem(index = 0)
                delay(300)
                filtersOpened = false
            }
            if (filtersApplied) {
                loadAvatars = false
                ProviderSearchViewModel.providersList.value = mutableListOf()
                viewModel.pageNumber.value = 1
                viewModel.getProviders()
                filtersApplied = false
            }
        }
        else -> {}
    }

    LaunchedEffect(key1 = getProvidersLoadingState == LoadingState.LOADED) {
        if (providersList.isNotEmpty()) {
            if (loadAvatars) {
                viewModel.getProvidersResponse.value.content?.let {
                    it.forEach { provider ->
                        if (provider.prv.org.avatarId != null) {
                            viewModel.getProviderAvatar(provider.prv.org.avatarId)
                        }
                    }
                }
            }
        }
    }


    LaunchedEffect(modalBottomSheetState.isVisible) {
        hideNavBar(modalBottomSheetState.isVisible)
        isFromCustomerNavContainer.value = parentRoute == P4pCustomerScreens.CustomerDashboard.route
    }

    BackHandler {
        if (isFromCustomerNavContainer.value) {
            // navController.popBackStack(route = Screen.OrderListPrv.route, inclusive = false)
        } else {
            navController.navigateUp()
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            BottomSheetFilter(
                onSelectProvidersVerification = { viewModel.onSelectProvidersVerification(it) },
                selectedProvidersVerificationOption = viewModel.searchFilterState.value.providersVerifications,
                onSelectProvidersAdminVerification = {
                    viewModel.onSelectProvidersAdminVerification(
                        it
                    )
                },
                selectedProvidersAdminVerificationsOptions = viewModel.searchFilterState.value.providersAdminVerifications,
                onSelectClearanceCertificates = { viewModel.onSelectClearanceCertificates(it) },
                selectedClearanceCertificationsOptions = viewModel.searchFilterState.value.clearanceCertifications
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.ProviderSearch.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
//                    showBackPressButton = !isFromCustomerNavContainer.value,
                    showBackPressButton = false,
                    leadingActions = {
                        MapButton(
                            onClick = {
                                if (!isMapShown) {
                                    viewModel.getProvidersForMap()
                                }
                                viewModel.onTriggerEvent(ProviderSearchEvent.ToggleOpenMap)
                            },
                            color = if (isMapShown) MaterialTheme.colorScheme.primary else Color.Black
                        )
                    },
                    actions = {
                        FilterButton(
                            onClick = {
                                filtersOpened = true
                                scope.launch {
                                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }, Color.Black
                        )
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

                    when (screenType) {
                        ProvidersScreenType.MAP -> {
                            ProvidersMapView(providersCoordinates)
                        }
                        ProvidersScreenType.ZERO_STATE -> {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Spacer(Modifier.padding(top = 16.dp))
                                if (EnrollmentViewModel.currentEnrollmentType.value != null) {
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
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
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
                                if (EnrollmentViewModel.currentEnrollmentType.value != null) {
                                    Spacer(Modifier.padding(top = 16.dp))
                                    TwoSwitchTabRow(
                                        modifier = Modifier.padding(horizontal = 24.dp),
                                        height = 32.dp,
                                        selectedColor = MaterialTheme.colorScheme.primary,
                                        tabsContent = ProvidersTabItems.values().map { it.label },
                                        selectedTapIndex = ProvidersTabItems.values()
                                            .indexOf(ProviderSearchViewModel.selectedProvidersTab.value),
                                        onSelect = { index ->
                                            if (index == 1) {
                                                ProviderSearchViewModel.providersList.value =
                                                    mutableListOf()
                                                ProviderSearchViewModel.servicesList.value =
                                                    mutableListOf()
                                                viewModel.pageNumber.value = 1
                                                scope.launch {
                                                    lazyColumnState.scrollToItem(index = 0)
                                                    navController.navigate(
                                                        P4pShowroomScreens.Categories.route
                                                    ) {
                                                        launchSingleTop = true
                                                    }
                                                }
                                            } else {
                                                if (ProviderSearchViewModel.selectedProvidersTab.value == ProvidersTabItems.SELECT_SERVICES) {// we switch back from the second tab
                                                    ProviderSearchViewModel.providersList.value =
                                                        mutableListOf()
                                                    ProviderSearchViewModel.servicesList.value =
                                                        mutableListOf()
                                                    viewModel.providersLastPage.value = false
                                                    viewModel.pageNumber.value = 1
                                                    viewModel.getFirstRqPage()
                                                    ProviderSearchViewModel.providersFirstSearch.value =
                                                        false
                                                    ProviderSearchViewModel.singleServiceFirstSearch.value =
                                                        false
                                                    ProviderSearchViewModel.singleService.value =
                                                        null
                                                    scope.launch {
                                                        lazyColumnState.scrollToItem(index = 0)
                                                    }
                                                }
                                            }
                                            viewModel.onSelectProvidersTab(ProvidersTabItems.values()[index])
                                        }
                                    )
                                }
                                Spacer(Modifier.padding(top = 16.dp))
                                Column(Modifier.fillMaxWidth()) {
                                    when (getFirstRqsState) {
                                        LoadingState.LOADING -> {
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                CircularProgressIndicator(color = green1)
                                            }
                                        }
                                        LoadingState.LOADED -> {
                                            when (ProviderSearchViewModel.selectedProvidersTab.value) {
                                                ProvidersTabItems.MATCH_REQUEST -> {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(if (servicesList.size <= 1) .2f else 1f)
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .padding(horizontal = 48.dp)
                                                                .verticalScroll(
                                                                    rememberScrollState()
                                                                )
                                                        ) {
                                                            servicesList.forEach { service ->
                                                                SelectServiceRadioOption(
                                                                    label = ApiViewModel.qServices.value.first { it.id == service }.name,
                                                                    isSelected = ProviderSearchViewModel.selectedServiceId.value == service,
                                                                    onSelect = {
                                                                        viewModel.providersLastPage.value =
                                                                            false
                                                                        ProviderSearchViewModel.providersList.value =
                                                                            mutableListOf()
                                                                        serviceChanged = true
                                                                        ProviderSearchViewModel.selectedServiceId.value =
                                                                            service
                                                                    }
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                                ProvidersTabItems.SELECT_SERVICES -> {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(.2f)
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .padding(horizontal = 48.dp)
                                                        ) {
                                                            if (ProviderSearchViewModel.singleService.value != null) {
                                                                SelectServiceRadioOption(
                                                                    label = ApiViewModel.qServices.value.first { it.id == ProviderSearchViewModel.singleService.value!!.qServiceId }.name,
                                                                    isSelected = ProviderSearchViewModel.selectedServiceId.value == ProviderSearchViewModel.singleService.value!!.qServiceId,
                                                                    onSelect = {
                                                                        ProviderSearchViewModel.providersList.value =
                                                                            mutableListOf()
                                                                        serviceChanged = true
                                                                        viewModel.providersLastPage.value =
                                                                            false
                                                                        ProviderSearchViewModel.selectedServiceId.value =
                                                                            ProviderSearchViewModel.singleService.value!!.qServiceId
                                                                    }
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            Spacer(Modifier.padding(top = 16.dp))
                                            if (servicesList.isNotEmpty() || ProviderSearchViewModel.singleService.value != null) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(4f)
                                                ) {

                                                    when {
                                                        getProvidersLoadingState == LoadingState.LOADING && providersList.isEmpty() -> {
                                                            Box(
                                                                modifier = Modifier.fillMaxSize(),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                CircularProgressIndicator(color = green1)
                                                            }
                                                        }
                                                        getProvidersLoadingState == LoadingState.LOADED -> {
                                                            getProvidersLoading = false
                                                        }
                                                        getProvidersLoadingState == LoadingState.ERROR -> {
                                                            Box(
                                                                modifier = Modifier
                                                                    .fillMaxSize(),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                ZeroStateImage(
                                                                    modifier = Modifier
                                                                        .size(400.dp),
                                                                    navController = navController,
                                                                    scope = coroutineScope
                                                                )
                                                            }
                                                        }
                                                    }
                                                    if (providersList.isNotEmpty()) {
                                                        LazyColumn(
                                                            state = lazyColumnState,
                                                            contentPadding = PaddingValues(
                                                                vertical = 8.dp,
                                                                horizontal = 24.dp
                                                            )
                                                        ) {
                                                            items(providersList) { provider ->
                                                                Provider(
                                                                    title = provider.prv.org.name,
                                                                    rating = if (provider.prv.rating != null && provider.prv.rating != 0) {
                                                                        dfRating.format(provider.prv.rating.toDouble() / 1000)
                                                                    } else {
                                                                        "0.0"
                                                                    },
//                                                                    imageId = imageId,
                                                                    imageId = if (provider.prv.org.avatarId != null) {
                                                                        when (avatarImageState) {
                                                                            LoadingState.LOADED -> {
                                                                                viewModel.listOfAvatars.value.firstOrNull {
                                                                                    it.containsKey(
                                                                                        provider.prv.org.avatarId
                                                                                    )
                                                                                }
                                                                                    ?.get(provider.prv.org.avatarId)
                                                                                    ?: imageId
                                                                            }
                                                                            else -> imageId
                                                                        }
                                                                    } else {
                                                                        imageId
                                                                    },
                                                                    description = if (provider.prv.org.descr != null) provider.prv.org.descr else "",
                                                                    onClickItem = {
                                                                        scope.launch {
                                                                            // Go to Provider profile
                                                                            ProviderProfileViewModel.providerId = provider.prv.id
                                                                            ProviderProfileViewModel.provider.emit(provider.prv)
                                                                            scope.launch {
                                                                                navController.navigate(
                                                                                    P4pProviderScreens.ProviderProfile.route
                                                                                )
                                                                            }
                                                                        }
                                                                    },
                                                                    statusCompose = {
                                                                        Column(
                                                                            Modifier.fillMaxHeight(),
                                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                                            verticalArrangement = Arrangement.SpaceBetween
                                                                        ) {
                                                                            Row(
                                                                                modifier = Modifier
                                                                                    .fillMaxWidth()
                                                                                    .padding(top = 8.dp),
                                                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                                                verticalAlignment = Alignment.Top
                                                                            ) {
                                                                                IconStatus(
                                                                                    modifier = Modifier.weight(
                                                                                        1f
                                                                                    ),
                                                                                    label = "${
                                                                                        dfDistance.format(
                                                                                            provider.distance
                                                                                        )
                                                                                    } miles",
                                                                                    iconId = R.drawable.ic_ql_miles
                                                                                )

                                                                                Column(
                                                                                    modifier = Modifier
                                                                                        .padding(
                                                                                            horizontal = 4.dp
                                                                                        )
                                                                                        .weight(
                                                                                            1f
                                                                                        ),
                                                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                                                    verticalArrangement = Arrangement.SpaceBetween
                                                                                ) {
                                                                                    if (!getProvidersLoading) {
                                                                                        val service =
                                                                                            provider.prv.services.first {
                                                                                                it.qServiceId == ProviderSearchViewModel.selectedServiceId.value
                                                                                            }
                                                                                        Text(
                                                                                            text = "£${
                                                                                                convertPrice(
                                                                                                    service.unitCost
                                                                                                )
                                                                                            }",
                                                                                            overflow = TextOverflow.Ellipsis,
                                                                                            style = MaterialTheme.typography.labelMedium,
                                                                                            textAlign = TextAlign.Center,
                                                                                            fontWeight = FontWeight.W800,
                                                                                            color = Color.Black
                                                                                        )
                                                                                        Text(
                                                                                            text = ApiViewModel.qServices.value.first { it.id == service.qServiceId }.unitDescr,
                                                                                            maxLines = 1,
                                                                                            overflow = TextOverflow.Ellipsis,
                                                                                            style = MaterialTheme.typography.labelMedium,
                                                                                            textAlign = TextAlign.Center,
                                                                                            fontWeight = FontWeight.W600
                                                                                        )
                                                                                        if (!service.unitTimed) {
                                                                                            Text(
                                                                                                text = "${service.timeNorm} min",
                                                                                                overflow = TextOverflow.Ellipsis,
                                                                                                style = MaterialTheme.typography.labelMedium,
                                                                                                textAlign = TextAlign.Center,
                                                                                                fontWeight = FontWeight.W600
                                                                                            )
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            Spacer(
                                                                                modifier = Modifier.height(
                                                                                    5.dp
                                                                                )
                                                                            )
                                                                            Row(
                                                                                modifier = Modifier.fillMaxWidth(),
                                                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                                                verticalAlignment = Alignment.Bottom
                                                                            ) {
                                                                                Column(
                                                                                    modifier = Modifier
                                                                                        .weight(1f)
                                                                                        .padding(
                                                                                            horizontal = 4.dp
                                                                                        ),
                                                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                                                    verticalArrangement = Arrangement.SpaceBetween
                                                                                ) {
                                                                                    if (provider.prv.calloutCharge) {
                                                                                        Text(
                                                                                            text = "Callout\nCharge",
                                                                                            maxLines = 2,
                                                                                            overflow = TextOverflow.Ellipsis,
                                                                                            style = MaterialTheme.typography.labelMedium,
                                                                                            textAlign = TextAlign.Center,
                                                                                            color = orange2,
                                                                                            fontWeight = FontWeight.W600
                                                                                        )
                                                                                    }
                                                                                }
                                                                                Column(
                                                                                    modifier = Modifier
                                                                                        .weight(1f)
                                                                                        .padding(
                                                                                            horizontal = 4.dp
                                                                                        ),
                                                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                                                    verticalArrangement = Arrangement.SpaceBetween
                                                                                ) {
                                                                                    if (provider.prv.cancelHrs != null) {
                                                                                        if (provider.prv.cancelHrs != 0) {
                                                                                            Text(
                                                                                                text = "Cancelation",
                                                                                                maxLines = 1,
                                                                                                overflow = TextOverflow.Ellipsis,
                                                                                                style = MaterialTheme.typography.labelMedium,
                                                                                                textAlign = TextAlign.Center,
                                                                                                color = orange2,
                                                                                                fontWeight = FontWeight.W600
                                                                                            )
                                                                                            Text(
                                                                                                text = "${provider.prv.cancelHrs} hours",
                                                                                                maxLines = 1,
                                                                                                overflow = TextOverflow.Ellipsis,
                                                                                                style = MaterialTheme.typography.labelMedium,
                                                                                                textAlign = TextAlign.Center,
                                                                                                color = orange2,
                                                                                                fontWeight = FontWeight.W600
                                                                                            )
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                )
                                                                Spacer(Modifier.height(16.dp))
                                                            }
                                                        }
                                                    } else {
                                                        when (getProvidersLoadingState) {
                                                            LoadingState.LOADED -> {
                                                                if (!filtersOpened) {
                                                                    Box(
                                                                        modifier = Modifier
                                                                            .fillMaxSize(),
                                                                        contentAlignment = Alignment.Center
                                                                    ) {
                                                                        ZeroStateImage(
                                                                            modifier = Modifier
                                                                                .size(400.dp),
                                                                            navController = navController,
                                                                            scope = coroutineScope
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    ZeroStateImage(
                                                        modifier = Modifier
                                                            .size(400.dp),
                                                        navController = navController,
                                                        scope = coroutineScope
                                                    )
                                                }
                                            }
                                        }
                                        LoadingState.ERROR -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                ZeroStateImage(
                                                    modifier = Modifier
                                                        .size(400.dp),
                                                    navController = navController,
                                                    scope = coroutineScope
                                                )
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
fun ZeroStateImage(
    modifier: Modifier = Modifier,
    navController: NavController,
    scope: CoroutineScope
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_request),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You can't find any\n provider until no open\n request is defined",
                textAlign = TextAlign.Center,
                color = blueTextColor,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create an open request",
                color = blueTextColor,
                style = MaterialTheme.typography.titleLarge
            )
            Buttons.WavingHandButton(
                onClick = {
                    scope.launch {
                        CustomerDashboardViewModel.selectedNavItem.value =
                            CustomerBottomNavItems.REQUESTS
                        navController.navigate(P4pCustomerScreens.CustomerDashboard.route) {
                            launchSingleTop = true
                        }
                    }
                },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
@Preview
fun ImagePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ZeroStateImage(
            modifier = Modifier
                .size(400.dp),
            navController = rememberNavController(),
            scope = rememberCoroutineScope()
        )
    }
}
