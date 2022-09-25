package eac.qloga.android.features.p4p.customer.shared.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import eac.qloga.android.core.shared.components.BottomSheets
import eac.qloga.android.core.shared.utils.InputFieldState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomerOrdersSearchFilter(
    modifier: Modifier = Modifier,
    fromDate: String = "",
    toDate: String = "",
    orderNumberState: InputFieldState,
    onChangeOrderValue: (String) -> Unit,
    onFocusOrderValue: (FocusState) -> Unit,
    selectedStatus: List<Int>,
    onStatusSelect: (Int) -> Unit,
    onPickFromDate: (String) -> Unit,
    onPickToDate: (String) -> Unit,
) {
    BottomSheets.OrdersSearchFilterBottomSheet(
        modifier = modifier,
        orderNumberState = orderNumberState,
        selectedStatus = selectedStatus,
        fromDate = fromDate,
        toDate = toDate,
        onChangeOrderValue = { onChangeOrderValue(it) },
        onFocusOrderValue = { onFocusOrderValue(it) },
        onStatusSelect = { onStatusSelect(it) },
        onPickFromDate = { onPickFromDate(it) },
        onPickToDate = { onPickToDate(it) }
    )
}