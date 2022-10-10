package eac.qloga.android.features.p4p.provider.scenes.providerOrderFilter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.Buttons.ByStatusButton
import eac.qloga.android.core.shared.components.InputFields.NumberInputField
import eac.qloga.android.core.shared.components.InputFields.TextInputField
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.lightGrayBackground
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.components.CheckableListItem
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import eac.qloga.p4p.order.enums.OrderStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProviderOrderFilterScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
) {
    val containerHorizontalPadding = 24.dp
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val interactionSource = remember{ MutableInteractionSource() }
    val orderNumber = remember{ mutableStateOf(InputFieldState())}
    val dateFrom = remember{ mutableStateOf(InputFieldState())}
    val dateTo = remember{ mutableStateOf(InputFieldState())}
    val priceFrom = remember{ mutableStateOf(InputFieldState())}
    val priceTo = remember{ mutableStateOf(InputFieldState())}
    val selectedOrderStatus = remember{ mutableStateOf<List<OrderStatus>>(emptyList())}

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                BottomSheetDashLine()
                OrderStatus.values().forEach { status ->
                    CheckableListItem(
                        label = status.display,
                        selectedColor = info_sky,
                        unSelectedColor = gray30,
                        isSelected = status in selectedOrderStatus.value,
                        onClick = {
                            if(status in selectedOrderStatus.value){
                                selectedOrderStatus.value = selectedOrderStatus.value.minus(status)
                            }else{
                                selectedOrderStatus.value = selectedOrderStatus.value.plus(status)
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pProviderScreens.ProviderOrderFilter.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                    actions = {
                        ByStatusButton(
                            onClick = {
                                scope.launch {
                                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }
                        )
                    }
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValues ->
            val topPadding = paddingValues.calculateTopPadding()

            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(topPadding))
                    OrderTypeTitleItem(text = "Sort by")
                    NumberInputField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onValueChange = { orderNumber.value = orderNumber.value.copy(text = it) },
                        value = orderNumber.value.text,
                        hint = "Order number",
                        onFocusedChanged = { orderNumber.value = orderNumber.value.copy(isFocused = it.isFocused)},
                    )
                    OrderTypeTitleItem(text = "Date")
                    TextInputField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onValueChange = { dateFrom.value = dateFrom.value.copy(text = it)},
                        value = dateFrom.value.text,
                        hint = "From",
                        onFocusedChanged = { dateFrom.value = dateFrom.value.copy(isFocused = it.isFocused)},
                    )
                    TextInputField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onValueChange = { dateTo.value = dateTo.value.copy(text = it)},
                        value = dateTo.value.text,
                        hint = "To",
                        onFocusedChanged = { dateTo.value = dateTo.value.copy(isFocused = it.isFocused)},
                    )
                    OrderTypeTitleItem(text = "Price")
                    TextInputField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onValueChange = { priceFrom.value = priceFrom.value.copy(text = it)},
                        value = priceFrom.value.text,
                        hint = "From",
                        onFocusedChanged = { priceFrom.value = priceFrom.value.copy(isFocused = it.isFocused)},
                    )
                    TextInputField(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onValueChange = { priceTo.value = priceTo.value.copy(text = it)},
                        value = priceTo.value.text,
                        hint = "To",
                        onFocusedChanged = { priceTo.value = priceTo.value.copy(isFocused = it.isFocused)},
                    )
                    OrderTypeTitleItem(text = "")
                    Spacer(modifier = Modifier.height(120.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    dateFrom.value = dateFrom.value.copy(text = "")
                                    dateTo.value = dateTo.value.copy(text = "")
                                    priceFrom.value = priceFrom.value.copy(text = "")
                                    priceTo.value = priceTo.value.copy(text = "")
                                    orderNumber.value = orderNumber.value.copy(text = "")
                                },
                            text = "Reset all",
                            style = MaterialTheme.typography.titleMedium,
                            color = orange1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderTypeTitleItem(
    modifier: Modifier = Modifier,
    height: Dp = 54.dp,
    text: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(lightGrayBackground)
            .padding(horizontal = 24.dp)
    ){
        Text(
            modifier = Modifier.align(Alignment.BottomStart),
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}