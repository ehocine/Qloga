package eac.qloga.android.features.p4p.customer.scenes.request

import P4pCustomerScreens
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.business.util.Extensions.rememberIsKeyboardOpen
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.Containers.BottomButtonContainer
import eac.qloga.android.core.shared.components.DividerLines
import eac.qloga.android.core.shared.components.EditTextInputField
import eac.qloga.android.core.shared.components.Items.RequestItem
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine
import eac.qloga.android.features.p4p.shared.viewmodels.ServiceViewModel
import eac.qloga.android.features.p4p.shared.viewmodels.AddressViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun RequestScreen(
    navController: NavController,
    //viewModel: CustomerNegotiationViewModel,
    serviceViewModel: ServiceViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel()
) {
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerVerticalPadding = Dimensions.ScreenTopPadding.dp
    val bottomSheetCornerRadius  = 16.dp
    val budget = remember{ mutableStateOf(InputFieldState(text = "100.0")) }
    val valid = remember{ mutableStateOf(InputFieldState(text = "11")) }
    val visit = remember{ mutableStateOf(InputFieldState(text = "2")) }

    val keyBoardOpenState by  rememberIsKeyboardOpen()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = bottomSheetCornerRadius, topEnd = bottomSheetCornerRadius),
        sheetContent ={
            BottomSheetDetails(
                placed = "13/01/2022 11:00",
                untill = "24/02/2022 01:23",
                looked = 34,
                searched = 14
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pCustomerScreens.Request.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    onBackPress = { navController.navigateUp() }
                )
            }
        ) { paddingValues ->
            val titleBarHeight = paddingValues.calculateTopPadding()

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(scrollState)
                        .padding(horizontal = horizontalContentPadding)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //space for title bar
                    Spacer(modifier = Modifier.height(titleBarHeight))
                    Spacer(modifier = Modifier.height(containerVerticalPadding))

                    ContainerBorderedCard {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            serviceViewModel.selectedCleaningCategories.value.forEachIndexed { index, cleaningType ->
                                if(cleaningType.value.count > 0){
                                    RequestItem(
                                        label = cleaningType.value.title,
                                        value = "${cleaningType.value.count}",
                                        showForwardArrow = true,
                                        isItemClickable = true,
                                        onClickArrowForward = {
                                            scope.launch {
//                                                navController.navigate(Screen.SelectedService.route+"?$ID_KEY=$index"){
//                                                    launchSingleTop = true
//                                                }
                                            }
                                        },
                                        onClickItem = {
                                            scope.launch {
//                                                navController.navigate(Screen.SelectedService.route+"?$ID_KEY=$index"){
//                                                    launchSingleTop = true
//                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ContainerBorderedCard {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            RequestItem(
                                label = "Ordered: ",
                                isItemClickable = false,
                                extraTexts = {
                                    Text(
                                        text = "22/06/2022",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.W500
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "9:00",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.W600
                                    )
                                }
                            )
                            RequestEditableItem(
                                value = budget.value.text,
                                label = "Budget(£): ",
                                showDividerLine = false,
                                onChangeValue = { budget.value = budget.value.copy(text = it)},
                                onFocusChange = { budget.value = budget.value.copy(isFocused = it.isFocused)},
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    FullRoundedButton(
                        buttonText = "Add services",
                        showBorder = true,
                        borderColor = MaterialTheme.colorScheme.primary,
                        textColor = MaterialTheme.colorScheme.primary,
                        backgroundColor = MaterialTheme.colorScheme.background
                    ) {
                        scope.launch {
//                            navController.navigate(Screen.Services.route){
//                                launchSingleTop = true
//                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ContainerBorderedCard {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            RequestItem(
                                leadingIcon = { Icon(
                                    painter = painterResource(id = R.drawable.ic_ql_home),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )},
                                label = "Address",
                                value = addressViewModel.selectedAddress.value,
                                isItemClickable = true,
                                showForwardArrow = true,
                                onClickArrowForward = {
//                                    navController.navigate(Screen.Address.route){
//                                        launchSingleTop = true
//                                    }
                                },
                                onClickItem = {
//                                    navController.navigate(Screen.Address.route){
//                                        launchSingleTop = true
//                                    }
                                }
                            )
                            RequestEditableItem(
                                value = visit.value.text,
                                label = "Visits",
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_ql_flag),
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = null
                                    )
                                },
                                showDividerLine = true,
                                onChangeValue = { visit.value = visit.value.copy(text = it)},
                                onFocusChange = {
                                    scope.launch {
                                        visit.value = visit.value.copy(isFocused = it.isFocused)
                                        delay(600)
                                        if(keyBoardOpenState){
                                            scrollState.animateScrollTo(scrollState.maxValue)
                                        }
                                    }
                                },
                            )
                            RequestItem(
                                leadingIcon = { Icon(
                                    painter = painterResource(id = R.drawable.ic_info),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )},
                                label = "Details",
                                value = "Placed & Until",
                                isItemClickable = true,
                                showForwardArrow = true,
                                onClickItem = {
                                    scope.launch {
                                        modalBottomSheetState.animateTo( ModalBottomSheetValue.Expanded )
                                    }
                                },
                                onClickArrowForward = {
                                    scope.launch {
                                        modalBottomSheetState.animateTo( ModalBottomSheetValue.Expanded )
                                    }
                                }
                            )
                            RequestItem(
                                leadingIcon = { Icon(
                                    painter = painterResource(id = R.drawable.ic_ql_note),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )},
                                label = "Notes",
                                isItemClickable = true,
                                showForwardArrow = true,
                                onClickArrowForward = {
//                                    navController.navigate(Screen.Notes.route){
//                                        launchSingleTop = true
//                                    }
                                },
                                onClickItem = {
//                                    navController.navigate(Screen.Notes.route){
//                                        launchSingleTop = true
//                                    }
                                }
                            )
                            RequestItem(
                                leadingIcon = { Icon(
                                    painter = painterResource(id = R.drawable.ic_ql_foot),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null
                                )},
                                label = "Tracking",
                                isItemClickable = true,
                                showForwardArrow = true,
                                onClickItem = {
//                                    navController.navigate(Screen.Tracking.route+"?$PARENT_ROUTE_KEY=${Screen.Request.route}"){
//                                        launchSingleTop = true
//                                    }
                                },
                                onClickArrowForward = {
//                                    navController.navigate(Screen.Tracking.route){
//                                        launchSingleTop = true
//                                    }
                                }
                            )
                            RequestEditableItem(
                                value = valid.value.text,
                                label = "Valid(weeks)",
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_ql_cal_timer),
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = null
                                    )
                                },
                                showDividerLine = false,
                                onChangeValue = { valid.value = valid.value.copy(text = it)},
                                onFocusChange = {
                                    scope.launch {
                                        valid.value = valid.value.copy(isFocused = it.isFocused)
                                        delay(300)
                                        if(keyBoardOpenState){
                                            scrollState.animateScrollTo(scrollState.maxValue)
                                        }
                                    }
                                },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(76.dp)) //bottom space
                }

                BottomButtonContainer(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    FullRoundedButton(
                        buttonText = "Create Open Request",
                        onClick = {
                            scope.launch{
//                                navController.navigate(Screen.OpenRequestList.route){
//                                    launchSingleTop = true
//                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomSheetDetails(
    placed: String,
    untill: String,
    looked: Int,
    searched: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSheetDashLine()
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Details", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Placed", style = MaterialTheme.typography.titleMedium, color = gray30)
            Text(text = placed, style = MaterialTheme.typography.titleMedium, color = gray30)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Untill", style = MaterialTheme.typography.titleMedium, color = gray30)
            Text(text = untill, style = MaterialTheme.typography.titleMedium, color = gray30)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Looked", style = MaterialTheme.typography.titleMedium, color = gray30)
            Text(text = "$looked", style = MaterialTheme.typography.titleMedium, color = gray30)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Searched", style = MaterialTheme.typography.titleMedium, color = gray30)
            Text(text = "$searched", style = MaterialTheme.typography.titleMedium, color = gray30)
        }
    }
}

@Composable
fun RequestEditableItem(
    value : String,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    showDividerLine: Boolean = false,
    iconSpacer: Dp = 16.dp,
    onChangeValue: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(leadingIcon != null){
                leadingIcon()
                Spacer(modifier = Modifier.width(iconSpacer))
            }

            Text(text = label, style = MaterialTheme.typography.titleMedium)
            Box(modifier = Modifier.weight(1f)){
                EditTextInputField(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.End
                    ),
                    value = value,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { onChangeValue(it) },
                    onSubmit = { },
                    onFocusChange ={ onFocusChange(it) }
                )
            }
        }
        if(showDividerLine){
            DividerLines.LightDividerLine(Modifier.padding(start = 64.dp))
        }
    }
}