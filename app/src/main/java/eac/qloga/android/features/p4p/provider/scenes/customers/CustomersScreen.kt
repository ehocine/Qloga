package eac.qloga.android.features.p4p.provider.scenes.customers

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Buttons.FilterButton
import eac.qloga.android.core.shared.components.Buttons.MapButton
import eac.qloga.android.core.shared.components.Buttons.ProviderUserButton
import eac.qloga.android.core.shared.components.Customer
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.*
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.Extensions.rememberScrollContext
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.convertPrice
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.customer.scenes.customerProfile.CustomerProfileViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.components.CustomersMapView
import eac.qloga.android.features.p4p.provider.shared.components.ProviderBottomSheetCustomers
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderDashboardViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.IconStatus
import eac.qloga.bare.enums.Parking
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CustomersScreen(
    navController: NavController,
    hideNavBar: (Boolean) -> Unit = {},
    viewModel: ProviderDashboardViewModel = hiltViewModel()
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val isMapShown = viewModel.isShownMap.value
    val imageId = R.drawable.ql_cst_avtr_acc
    val lazyColumnState = rememberLazyListState()
    val scrollContext = rememberScrollContext(lazyColumnState)
    var getCustomersLoading by remember { mutableStateOf(false) }
    val getCustomersLoadingState by viewModel.getCustomersLoadingState.collectAsState()
    val avatarImageState by viewModel.avatarImageState.collectAsState()

    var filtersApplied by remember { mutableStateOf(false) }
    var filtersOpened by remember { mutableStateOf(false) }
    var loadAvatars by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = scrollContext.isBottom) {
        if (!viewModel.customersLastPage.value) {
            if (getCustomersLoadingState == LoadingState.LOADED || getCustomersLoadingState == LoadingState.IDLE) {
                getCustomersLoading = true
                loadAvatars = true
                viewModel.pageNumber.value += 1
                Log.d("Tag", "Current: ${viewModel.pageNumber.value}")
                viewModel.getCustomers()
            }
        }
    }
    val requestsList by viewModel.requestsList

    val dfRating = DecimalFormat("#.0")
    dfRating.roundingMode = RoundingMode.DOWN

    val dfDistance = DecimalFormat("#.#")
    dfDistance.roundingMode = RoundingMode.DOWN

    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    when (modalBottomSheetState.currentValue) {
        ModalBottomSheetValue.Expanded -> {
            viewModel.customersLastPage.value = false
            filtersApplied = true
        }
        ModalBottomSheetValue.Hidden -> {
            LaunchedEffect(key1 = Unit) {
//                viewModel.getCustomersLoadingState.emit(LoadingState.IDLE)
                // Scroll to the first item
                lazyColumnState.scrollToItem(index = 0)
                delay(300)
                filtersOpened = false
            }
            if (filtersApplied) {
                loadAvatars = false
                viewModel.requestsList.value = mutableListOf()
                viewModel.pageNumber.value = 1
                viewModel.getCustomers()
                filtersApplied = false

            }
        }
        else -> {}
    }
    LaunchedEffect(key1 = getCustomersLoadingState == LoadingState.LOADED) {
        if (requestsList.isNotEmpty()) {
            if (loadAvatars) {
                viewModel.getCustomersResponse.value.content?.let {
                    it.forEach { request ->
                        if (request.request.cstProfile.avatarId != null) {
                            viewModel.getCustomerAvatar(request.request.cstProfile.avatarId)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(modalBottomSheetState.isVisible) {
        hideNavBar(!modalBottomSheetState.isVisible)
    }

    BackHandler {
        navController.popBackStack(route = P4pShowroomScreens.Enrolled.route, inclusive = false)
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ProviderBottomSheetCustomers(
                selectedVerificationIndexes = viewModel.selectedCustomerVerificationOption.value,
                onSelectVerificationOption = { viewModel.onChangeCustomerVerificationOption(it) }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pProviderScreens.Customers.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    showBackPressButton = false,
                    leadingActions = {
                        MapButton(
                            onClick = {
                                viewModel.getCustomersForMap()
                                viewModel.toggleMapShow()
                            },
                            color = if (isMapShown) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.onBackground
                        )
                    },
                    actions = {
                        FilterButton(
                            onClick = {
                                filtersOpened = true
                                hideNavBar(!modalBottomSheetState.isVisible)
                                scope.launch {
                                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            },
                            Color.Black
                        )
                        ProviderUserButton(
                            onClick = {
                                scope.launch {
                                    /*navController.navigate(
                                        Screen.Account.route+"?$ACCOUNT_TYPE_KEY=${AccountType.PROVIDER.label}" +
                                                "&$PARENT_ROUTE_KEY=${Screen.ProviderNavContainer.route}"
                                    )
                                     */
                                }
                            },
                            color = info_sky
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
                    when (isMapShown) {
                        true -> {
                            CustomersMapView(listOfCustomers = viewModel.customersCoordinates.value)
                        }
                        else -> {
                            when {
                                getCustomersLoadingState == LoadingState.LOADING && requestsList.isEmpty() -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(color = green1)
                                    }
                                }
                                getCustomersLoadingState == LoadingState.LOADED -> {
                                    getCustomersLoading = false
                                }
                                getCustomersLoadingState == LoadingState.ERROR -> {
                                    Box(
                                        Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ZeroStateImage(
                                            modifier = Modifier.size(400.dp)
                                        ) {

                                        }
                                    }
                                }
                            }
                            if (requestsList.isNotEmpty()) {
                                LazyColumn(
                                    state = lazyColumnState,
                                    contentPadding = PaddingValues(
                                        vertical = 8.dp,
                                        horizontal = 24.dp
                                    )
                                ) {
                                    items(requestsList) { request ->
                                        Customer(
                                            title = request.request.cstProfile.fullName,
                                            rating = if (request.request.cstProfile.rating != null && request.request.cstProfile.rating != 0) {
                                                dfRating.format(request.request.cstProfile.rating.toDouble() / 1000)
                                            } else {
                                                "0.0"
                                            },
                                            imageUrl = if (request.request.cstProfile.avatarId != null) {
                                                when (avatarImageState) {
                                                    LoadingState.LOADED -> {
                                                        viewModel.listOfAvatars.value.firstOrNull {
                                                            it.containsKey(request.request.cstProfile.avatarId)
                                                        }
                                                            ?.get(request.request.cstProfile.avatarId)
                                                            ?: imageId
                                                    }
                                                    else -> imageId
                                                }
                                            } else imageId,
                                            description = if (request.request.notes != null) request.request.notes else "",
                                            visitsCount = request.request.visits,
                                            onClickItem = {
                                                scope.launch {
                                                    //Go to customer profile
                                                    CustomerProfileViewModel.customerId.value = request.request.cstProfile.id
                                                    CustomerProfileViewModel.providerId.value = ApiViewModel.userProfile.value.id
                                                    CustomerProfileViewModel.customerProfile.value = request.request.cstProfile
                                                    scope.launch {
                                                        navController.navigate(P4pCustomerScreens.CustomerProfile.route)
                                                    }
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
                                                            label = "${
                                                                dfDistance.format(
                                                                    request.distance
                                                                )
                                                            } miles",
                                                            iconId = R.drawable.ic_ql_miles
                                                        )

                                                        Column(
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .padding(horizontal = 4.dp),
                                                            horizontalAlignment = Alignment.CenterHorizontally,
                                                            verticalArrangement = Arrangement.SpaceBetween
                                                        ) {
                                                            Icon(
                                                                modifier = Modifier.size(22.dp),
                                                                painter = painterResource(id = R.drawable.ic_ql_free),
                                                                contentDescription = null,
                                                                tint = gray30
                                                            )
                                                            Spacer(Modifier.height(4.dp))
                                                            Text(
                                                                text = if (request.request.address.parking != null) {
                                                                    when (request.request.address.parking) {
                                                                        Parking.FREE -> "Free"
                                                                        Parking.PAID -> "Paid"
                                                                        Parking.UNSET -> "Unspecified"
                                                                    }
                                                                } else "Unspecified",
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis,
                                                                style = MaterialTheme.typography.labelMedium,
                                                                textAlign = TextAlign.Center,
                                                                fontWeight = FontWeight.W600,
                                                                color = if (request.request.address.parking != null) {
                                                                    when (request.request.address.parking) {
                                                                        Parking.PAID -> Red10
                                                                        Parking.FREE -> green1
                                                                        Parking.UNSET -> Color.Unspecified
                                                                    }
                                                                } else {
                                                                    Color.Unspecified
                                                                }
                                                            )
                                                        }
                                                        IconStatus(
                                                            modifier = Modifier.weight(1f),
                                                            label = "£${convertPrice(request.request.offeredSum)}",
                                                            iconId = R.drawable.ic_ql_money_bag,
                                                            fontWeight = FontWeight.W600,
                                                            textColor = Color.Black
                                                        )
                                                    }
                                                }
                                            }
                                        )
                                        Spacer(Modifier.height(16.dp))
                                    }
                                }
                            } else {
                                when (getCustomersLoadingState) {
                                    LoadingState.LOADED -> {
                                        if (!filtersOpened) {
                                            Box(
                                                Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                ZeroStateImage(
                                                    modifier = Modifier.size(400.dp)
                                                ) {

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(paddingValues.calculateBottomPadding() + 32.dp))
                }
            }
        }
    }
}

@Composable
fun ZeroStateImage(modifier: Modifier = Modifier, onClick: () -> Unit) {
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
//        Image(
//            painter = rememberAsyncImagePainter(R.drawable.ic_no_request),
//            modifier = Modifier
//                .align(Alignment.Center)
//                .fillMaxSize(),
//            contentDescription = null,
//            contentScale = ContentScale.Fit
//        )
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You can't find any customer\n until no service is defined",
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
                text = "Choose a service",
                color = blueTextColor,
                style = MaterialTheme.typography.titleLarge
            )
            Buttons.PersSettingsButton(
                onClick = { onClick() },
                color = orange1
            )
            Text(
                text = "to provide",
                color = blueTextColor,
                style = MaterialTheme.typography.titleLarge
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
        ZeroStateImage(modifier = Modifier.size(400.dp)) {

        }
    }
}