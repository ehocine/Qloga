package eac.qloga.android.features.p4p.customer.shared.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class CustomerDashboardViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {

    companion object {
        var selectedNavItem: MutableState<CustomerBottomNavItems> =
            mutableStateOf(CustomerBottomNavItems.listOfItems[0])
        var alreadyShownProfileInfoDialog by mutableStateOf(false)
    }


    var selectedServiceCategory by mutableStateOf(ServiceCategory())
        private set

    var notes by mutableStateOf(InputFieldState(hint = "Your notes..."))
        private set

    var searchFilterState by mutableStateOf(SearchFilterState())
        private set

    var selectedStatus by mutableStateOf<List<Int>>(emptyList())
        private set

    var showEmptyStateOpenRequest by mutableStateOf(true)
        private set

    var showProfileInfoDialog by mutableStateOf(false)
        private set

    var showProfileInfoDialogCheck by mutableStateOf(true)
        private set

    init {
        observePreferences()
    }

    fun onShowEmptyStateOpenRequest(value: Boolean) {
        showEmptyStateOpenRequest = value
    }

    private fun observePreferences() {
        viewModelScope.launch {
            datastoreRepository.getCustomerProfileInfoDialogState().onEach {
                showProfileInfoDialog = it
            }.launchIn(viewModelScope)
        }
    }

    fun onSelectStatusOption(index: Int) {
        val copyList = ArrayList(selectedStatus)
        if (index in copyList) {
            copyList.remove(index)
        } else {
            copyList.add(index)
        }
        selectedStatus = copyList
    }

    fun onAlreadyShowProfileInfoDialog(value: Boolean) {
        alreadyShownProfileInfoDialog = value
    }

    fun onDismissInfoDialog() {
        alreadyShownProfileInfoDialog = true
        if (showProfileInfoDialogCheck != showProfileInfoDialog) {
            toggleProfileInfoDialogCheck()
        }
    }

    fun onProfileInfoDialogCheck() {
        showProfileInfoDialogCheck = !showProfileInfoDialogCheck
    }

    fun toggleProfileInfoDialogCheck() {
        viewModelScope.launch {
            datastoreRepository.toggleCustomerProfileInfoDialog()
        }
    }

    fun onSelectNavItem(navItems: CustomerBottomNavItems) {
        selectedNavItem.value = navItems
    }

    fun onSelectServiceCategory(serviceCategory: ServiceCategory) {
        selectedServiceCategory = serviceCategory
    }

    fun onEnterNotes(value: String) {
        notes = notes.copy(text = value)
    }

    fun onFocusNotes(focusState: FocusState) {
        notes = notes.copy(isFocused = focusState.isFocused)
    }

    fun onChangeSliderFilter(type: FilterTypes, value: Int) {
        when (type) {
            is FilterTypes.Distance -> {
                searchFilterState = searchFilterState.copy(
                    distance = Distance(value = value)
                )
            }

            is FilterTypes.ReturnRate -> {
                searchFilterState = searchFilterState.copy(
                    returnRate = ReturnRate(value = value)
                )
            }

            is FilterTypes.MinStartRating -> {
                searchFilterState = searchFilterState.copy(
                    minStartRating = MinStartRating(value = value)
                )
            }

            is FilterTypes.OrdersDelivered -> {
                searchFilterState = searchFilterState.copy(
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

        return when (filterTypes) {

            is FilterTypes.Distance -> {
                arrayOf(searchFilterState.distance.value, searchFilterState.distance.max)
            }

            is FilterTypes.ReturnRate -> {
                arrayOf(searchFilterState.returnRate.value, searchFilterState.returnRate.max)
            }

            is FilterTypes.MinStartRating -> {
                arrayOf(
                    searchFilterState.minStartRating.value,
                    searchFilterState.minStartRating.max
                )
            }

            is FilterTypes.OrdersDelivered -> {
                arrayOf(
                    searchFilterState.ordersDelivered.value,
                    searchFilterState.ordersDelivered.max
                )
            }

            else -> {
                arrayOf(0, 0)
            }
        }
    }

    fun onSelectProvidersType(index: Int) {
        val selectedIndexes = ArrayList(searchFilterState.providersType)

        if (index in searchFilterState.providersType) {
            selectedIndexes.remove(index)
        } else {
            selectedIndexes.add(index)
        }
        searchFilterState = searchFilterState.copy(
            providersType = selectedIndexes
        )
    }

    fun onSelectProvidersVerification(index: Int) {
        val selectedIndexes = ArrayList(searchFilterState.providersVerifications)

        if (index in searchFilterState.providersVerifications) {
            selectedIndexes.remove(index)
        } else {
            selectedIndexes.add(index)
        }
        searchFilterState = searchFilterState.copy(
            providersVerifications = selectedIndexes
        )
    }

    fun onSelectProvidersAdminVerification(index: Int) {
        val selectedIndexes = ArrayList(searchFilterState.providersAdminVerifications)

        if (index in searchFilterState.providersAdminVerifications) {
            selectedIndexes.remove(index)
        } else {
            selectedIndexes.add(index)
        }
        searchFilterState = searchFilterState.copy(
            providersAdminVerifications = selectedIndexes
        )
    }

    fun onSelectClearanceCertificates(index: Int) {
        val selectedIndexes = ArrayList(searchFilterState.clearanceCertifications)

        if (index in searchFilterState.clearanceCertifications) {
            selectedIndexes.remove(index)
        } else {
            selectedIndexes.add(index)
        }
        searchFilterState = searchFilterState.copy(
            clearanceCertifications = selectedIndexes
        )
    }
}