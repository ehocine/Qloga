package eac.qloga.android.features.p4p.provider.shared.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.datastore.DatastoreRepository
import eac.qloga.android.core.shared.utils.CustomMarkerState
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderBottomNavItems
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderDashboardViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
): ViewModel() {

    companion object{
        var alreadyShownProfileInfoDialog by mutableStateOf(false)
    }

    private val _selectNavItem = mutableStateOf(ProviderBottomNavItems.listOfItems[0])
    val selectedNavItem: State<ProviderBottomNavItems> = _selectNavItem

    private val _isShownMap = mutableStateOf(false)
    val isShownMap: State<Boolean> =_isShownMap

    private val _customersCoordinates = mutableStateOf<List<CustomMarkerState>>(emptyList())
    val customersCoordinates: State<List<CustomMarkerState>> =_customersCoordinates

    private val _selectedCustomerVerificationOption = mutableStateOf<List<Int>>(emptyList())
    val selectedCustomerVerificationOption: State<List<Int>> = _selectedCustomerVerificationOption

    private val _selectedStatus = mutableStateOf<List<Int>>(emptyList())
    val selectedStatus: State<List<Int>> = _selectedStatus

    var showProviderInfoDialog by mutableStateOf(false)
        private set

    var showProviderInfoDialogCheck by mutableStateOf(true)
        private set

    fun onSelectNavItem(navItems: ProviderBottomNavItems){
        _selectNavItem.value = navItems
    }

    init {
        getCustomersCoordinates()
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            datastoreRepository.getProviderProfileInfoDialogState().onEach {
                showProviderInfoDialog = it
            }.launchIn(viewModelScope)
        }
    }

    fun onClickDialogCheck(){
        showProviderInfoDialogCheck = !showProviderInfoDialogCheck
    }

    fun onDismissInfoDialog(){
        if(showProviderInfoDialogCheck != showProviderInfoDialog){
            onToggleShowProviderInfoDialog()
        }
    }

    private fun onToggleShowProviderInfoDialog(){
        viewModelScope.launch {
            datastoreRepository.toggleProviderProfileInfoDialog()
        }
    }

    fun onChangeCustomerVerificationOption(index: Int){
        val listCopy = ArrayList(_selectedCustomerVerificationOption.value)
        if(index in listCopy){
            listCopy.remove(index)
        }else{
            listCopy.add(index)
        }
        _selectedCustomerVerificationOption.value = listCopy
    }

    fun toggleMapShow(){
        _isShownMap.value = !_isShownMap.value
    }

    private fun getCustomersCoordinates(){
        _customersCoordinates.value = listOf(
            CustomMarkerState(54.9715, -1.6123, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.9745, -1.6423, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.9775, -1.6523, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.9115, -1.6923, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.8715, -1.5123, "Kathmandu, Nepal Street 3340")
        )
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
}