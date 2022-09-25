package eac.qloga.android.features.p4p.customer.shared.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.datastore.DatastoreRepository
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.customer.shared.components.CustomerBottomNavItems
import eac.qloga.android.features.p4p.shared.utils.*
import eac.qloga.p4p.lookups.dto.ServiceCategory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomerNegotiationViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
): ViewModel() {

    private val _selectNavItem = mutableStateOf(CustomerBottomNavItems.listOfItems[0])
    val selectedNavItem: State<CustomerBottomNavItems> = _selectNavItem

    private val _selectServiceCategory = mutableStateOf(ServiceCategory())
    val selectedServiceCategory: State<ServiceCategory> = _selectServiceCategory

    private val _notes = mutableStateOf(InputFieldState(hint = "Your notes..."))
    val notes: State<InputFieldState> = _notes

    private val _searchFilterState = mutableStateOf(SearchFilterState())
    val searchFilterState: State<SearchFilterState> =_searchFilterState

    private val _notShowAgainCustomerInfoDialog = mutableStateOf(false)
    val notShowAgainCustomerInfoDialog: State<Boolean> = _notShowAgainCustomerInfoDialog

    private val _selectedStatus = mutableStateOf<List<Int>>(emptyList())
    val selectedStatus: State<List<Int>> = _selectedStatus

    private val _showAccountSwitchInfoDialog = mutableStateOf(true)
    val showAccountSwitchInfoDialog: State<Boolean> = _showAccountSwitchInfoDialog

    private val _showAccountSwitchInfoDialogCount = mutableStateOf(0)
    val showAccountSwitchInfoDialogCount: State<Int> = _showAccountSwitchInfoDialogCount

    private val _showEmptyStateOpenRequest = mutableStateOf(true)
    val showEmptyStateOpenRequest: State<Boolean> = _showEmptyStateOpenRequest

    init {
        observePreferences()
    }

    fun onShowEmptyStateOpenRequest(value: Boolean){
        _showEmptyStateOpenRequest.value = value
    }

    private fun observePreferences() {
        viewModelScope.launch {
            datastoreRepository.getNotShowAgainCustomer().onEach {
                _notShowAgainCustomerInfoDialog.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun onAccountSwitchInfoDialogInc(){
        _showAccountSwitchInfoDialogCount.value = _showAccountSwitchInfoDialogCount.value + 1
    }

    fun onShowAccountSwitchInfoDialog(value: Boolean){
        _showAccountSwitchInfoDialog.value = value
    }

    fun onSelectStatusOption(index: Int){
        val copyList = ArrayList(_selectedStatus.value)
        if(index in copyList){
            copyList.remove(index)
        }else{
            copyList.add(index)
        }
        _selectedStatus.value = copyList
    }

    fun toggleNotShowAgainDialog(){
        viewModelScope.launch {
            datastoreRepository.toggleNotShowAgainCustomer()
        }
    }

    fun onSelectNavItem(navItems: CustomerBottomNavItems){
        _selectNavItem.value = navItems
    }

    fun onSelectServiceCategory(serviceCategory: ServiceCategory){
        _selectServiceCategory.value = serviceCategory
    }

    fun onEnterNotes(value: String){
        _notes.value = _notes.value.copy(
            text = value
        )
    }

    fun onFocusNotes(focusState: FocusState){
        _notes.value = _notes.value.copy(
            isFocused = focusState.isFocused
        )
    }

    fun onChangeSliderFilter(type: FilterTypes, value: Int){

        when(type){
            is FilterTypes.Distance -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    distance = Distance(value = value)
                )
            }

            is FilterTypes.ReturnRate -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    returnRate = ReturnRate(value = value)
                )
            }

            is FilterTypes.MinStartRating -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    minStartRating = MinStartRating(value = value)
                )
            }

            is FilterTypes.OrdersDelivered -> {
                _searchFilterState.value = searchFilterState.value.copy(
                    ordersDelivered = OrdersDelivered(value = value)
                )
            }
            else -> {}
        }
    }

    /**
     *  Return array of two integers, first is swiper value and
     *  second is swiper max value
     * **/
    fun getFilterSliderState(filterTypes: FilterTypes): Array<Int> {

        return when(filterTypes){

            is FilterTypes.Distance -> {
                arrayOf(searchFilterState.value.distance.value, searchFilterState.value.distance.max)
            }

            is FilterTypes.ReturnRate -> {
                arrayOf(searchFilterState.value.returnRate.value, searchFilterState.value.returnRate.max)
            }

            is FilterTypes.MinStartRating -> {
                arrayOf(searchFilterState.value.minStartRating.value, searchFilterState.value.minStartRating.max)
            }

            is FilterTypes.OrdersDelivered -> {
                arrayOf(searchFilterState.value.ordersDelivered.value, searchFilterState.value.ordersDelivered.max)
            }

            else -> { arrayOf(0,0)}
        }
    }

    fun onSelectProvidersType(index: Int){
        val selectedIndexes = ArrayList(_searchFilterState.value.providersType)

        if( index in _searchFilterState.value.providersType){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersType = selectedIndexes
        )
    }

    fun onSelectProvidersVerification(index: Int){
        val selectedIndexes = ArrayList(_searchFilterState.value.providersVerifications)

        if( index in _searchFilterState.value.providersVerifications){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersVerifications = selectedIndexes
        )
    }

    fun onSelectProvidersAdminVerification(index: Int){
        val selectedIndexes = ArrayList(_searchFilterState.value.providersAdminVerifications)

        if( index in _searchFilterState.value. providersAdminVerifications){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersAdminVerifications = selectedIndexes
        )
    }

    fun onSelectClearanceCertificates(index: Int){
        val selectedIndexes = ArrayList(_searchFilterState.value.clearanceCertifications)

        if( index in _searchFilterState.value.clearanceCertifications){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            clearanceCertifications = selectedIndexes
        )
    }
}