package eac.qloga.android.features.p4p.shared.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.p4p.shared.utils.OrderCategory
import eac.qloga.android.features.p4p.shared.utils.OrderNoteType
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(): ViewModel() {

    private val _orderCategory = mutableStateOf<OrderCategory>(OrderCategory.FundsReservation)
    val orderCategory: State<OrderCategory> = _orderCategory

    private val _selectedOrderNoteType = mutableStateOf<OrderNoteType?>(null)
    val selectedOrderNoteType: State<OrderNoteType?> = _selectedOrderNoteType

    fun orderCategory(category: OrderCategory){
        _orderCategory.value = category
    }

    fun orderNoteType(type: OrderNoteType){
        _selectedOrderNoteType.value = type
    }
}