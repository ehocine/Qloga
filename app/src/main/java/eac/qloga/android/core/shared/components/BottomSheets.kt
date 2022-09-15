package eac.qloga.android.core.shared.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.PickerDialog
import eac.qloga.android.features.p4p.shared.components.BottomSheetDashLine
import kotlinx.coroutines.launch

object BottomSheets {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OrdersSearchFilterBottomSheet(
        modifier: Modifier = Modifier,
        orderNumberState: InputFieldState,
        fromDate: String,
        toDate: String,
        selectedStatus: List<Int>,
        onChangeOrderValue: (String) -> Unit,
        onFocusOrderValue: (FocusState) -> Unit,
        onStatusSelect: (Int) -> Unit,
        onPickFromDate: (String) -> Unit,
        onPickToDate: (String) -> Unit,
    ) {
        val scrollState = rememberScrollState()
        val itemHeight = 50.dp
        val chipWidth = 90.dp
        val context = LocalContext.current
        val priceRange = remember{ mutableStateOf(100f..400f) }
        val listOfRadioOptions = listOf(
            "Approved",
            "Paid",
            "Completed",
            "Arrived",
            "Accepted",
            "Unsatisfied",
            "Closed",
            "Dispute",
            "Cancelled"
        )

        val coroutineScope = rememberCoroutineScope()

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
                Cards.ContainerBorderedCard(cornerRadius = 12.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(itemHeight)
                            .padding(horizontal = 16.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Order number", style = MaterialTheme.typography.titleMedium)
                        InputFields.NumberInputField(
                            onValueChange = { onChangeOrderValue(it) },
                            value = orderNumberState.text,
                            hint = "",
                            textAlign = TextAlign.End,
                            onFocusedChanged = { onFocusOrderValue(it) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "DATE",
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30
                )
                Cards.ContainerBorderedCard(cornerRadius = 12.dp) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(itemHeight)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 16.dp),
                                text = "From",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Box(
                                modifier = Modifier
                                    .widthIn(min = chipWidth)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        coroutineScope.launch {
                                            PickerDialog.showDatePickerDialog(context,
                                                numberFormat = true,
                                                onSetDate = { onPickFromDate(it) }
                                            )
                                        }
                                    }
                                    .background(gray1)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = fromDate.ifEmpty { "11/2/22" },
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (fromDate.isEmpty()) Color.White else MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.padding(end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 16.dp),
                                text = "To",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Box(
                                modifier = Modifier
                                    .widthIn(min = chipWidth)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        coroutineScope.launch {
                                            PickerDialog.showDatePickerDialog(context,
                                                numberFormat = true,
                                                onSetDate = { onPickToDate(it) }
                                            )
                                        }
                                    }
                                    .background(gray1)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = toDate.ifEmpty { "11/2/22" },
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (toDate.isEmpty()) Color.White else MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "PRICE(£): ${priceRange.value.start.toInt()}-${priceRange.value.endInclusive.toInt()}",
                    style = MaterialTheme.typography.titleMedium, color = gray30
                )
                Cards.ContainerBorderedCard(cornerRadius = 12.dp) {
                    Box(
                        modifier = Modifier
                            .height(itemHeight)
                            .padding(horizontal = 8.dp)
                    ) {
                        RangeSlider(
                            values = priceRange.value,
                            onValueChange = { priceRange.value = it },
                            valueRange = 0f..500f
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                RadioOptionsCard(
                    label = "STATUS",
                    listOfOptions = listOfRadioOptions,
                    selectedOptionIndexList = selectedStatus,
                    onSelect = { onStatusSelect(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}