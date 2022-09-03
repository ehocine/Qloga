package eac.qloga.android.features.p4p.showroom.scenes.notEnrolled

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.CustomMarkerState
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.core.shared.viewmodels.SettingsViewModel
import eac.qloga.android.data.get_address.GetAddressRepository
import eac.qloga.android.features.p4p.showroom.shared.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotEnrolledViewModel @Inject constructor(
    private val getAddressRepository: GetAddressRepository,
    private val settingsViewModel: SettingsViewModel
) : ViewModel() {

    companion object {
        const val TAG = "NotEnrolledViewModel"
    }

    private val _inputFieldState = mutableStateOf(
        InputFieldState(
            text = ApiViewModel.userProfile.value.contacts.address.shortAddress,
            hint = "Enter new address"
        )
    )
    val inputFieldState: State<InputFieldState> = _inputFieldState

    private val _selectedNav = mutableStateOf<ServiceCategory?>(null)
    val selectedNav: State<ServiceCategory?> = _selectedNav

    private val _providersList = mutableStateOf<List<Providers>>(emptyList())
    val providersList: State<List<Providers>> = _providersList

    private val _isShownMap = mutableStateOf(false)
    val isShownMap: State<Boolean> = _isShownMap

    private val _providersCoordinates = mutableStateOf<List<CustomMarkerState>>(emptyList())
    val providersCoordinates: State<List<CustomMarkerState>> = _providersCoordinates

    private val _searchFilterState = mutableStateOf(SearchFilterState())
    val searchFilterState: State<SearchFilterState> = _searchFilterState

    /*    private val _providersScreenType = mutableStateOf(ProvidersScreenType.PROVIDERS)
        val providersScreenType: State<ProvidersScreenType> = _providersScreenType

        private val _selectedProvidersTab = mutableStateOf(ProvidersTabItems.MATCH_REQUEST)
        val selectedProvidersTab: State<ProvidersTabItems> = _selectedProvidersTab
    */
    private val _selectedServiceRadioOption = mutableStateOf(1)
    val selectedServiceRadioOption: State<Int> = _selectedServiceRadioOption

//    val addressSuggestionsList: MutableState<List<Suggestion>> = mutableStateOf(listOf())
//    val selectedAddressSuggestion: MutableState<Suggestion> = mutableStateOf(Suggestion())
//
//    var getAddressSuggestionsLoadingState = MutableStateFlow(LoadingState.IDLE)

    init {
        getProvidersList()
        getProvidersCoordinates()
    }

    fun onTriggerEvent(event: NotEnrolledEvent) {
        try {
            viewModelScope.launch {
                when (event) {
                    is NotEnrolledEvent.EnterText -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = event.text
                        )
                    }

                    is NotEnrolledEvent.ClearInput -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = ""
                        )
                    }

                    is NotEnrolledEvent.FocusInput -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            isFocused = event.focusState.isFocused
                        )
                    }
                    is NotEnrolledEvent.ToggleOpenMap -> {
                        _isShownMap.value = !isShownMap.value
                        /*
                        if(_isShownMap.value){
                            _providersScreenType.value = ProvidersScreenType.MAP
                        }else{
                            _providersScreenType.value = ProvidersScreenType.PROVIDERS
                        }

                         */
                    }
                    is NotEnrolledEvent.AddressChosen -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = event.text
                        )
                    }
                    is NotEnrolledEvent.Search -> {
                        onSearch()
                    }
                    is NotEnrolledEvent.NavItemClick -> {
                        onNavClick(event.navItem)
                    }
                    is NotEnrolledEvent.OnChangeSliderFilterState -> {
                        onChangeSliderFilter(event.type, event.value)
                    }
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

//    fun getAddressSuggestions(term: String) {
//        viewModelScope.launch {
//            getAddressSuggestionsLoadingState.emit(LoadingState.LOADING)
//            val response = settingsViewModel.settings.value.get("GETADDRESS_KEY")?.let {
//                getAddressRepository.getAddressSuggestions(
//                    term = term,
//                    apikey = it
//                )
//            }
//            if (response != null) {
//                if (response.isSuccessful) {
//                    addressSuggestionsList.value = response.body()!!.suggestions
//                    getAddressSuggestionsLoadingState.emit(LoadingState.LOADED)
//                } else {
//                    getAddressSuggestionsLoadingState.emit(LoadingState.ERROR)
//                }
//            }
//        }
//    }

    fun onSelectServiceRadioOption(index: Int) {
        _selectedServiceRadioOption.value = index
    }

    /*
        fun onSelectProvidersTab(item: ProvidersTabItems){
            _selectedProvidersTab.value = item
        }
    */
    private fun onSearch() {
        _inputFieldState.value = inputFieldState.value.copy(text = "")
    }

    fun onNavClick(navItem: ServiceCategory?) {
        _selectedNav.value = navItem
    }

    private fun onChangeSliderFilter(type: FilterTypes, value: Int) {
        when (type) {
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

        return when (filterTypes) {

            is FilterTypes.Distance -> {
                arrayOf(
                    searchFilterState.value.distance.value,
                    searchFilterState.value.distance.max
                )
            }

            is FilterTypes.ReturnRate -> {
                arrayOf(
                    searchFilterState.value.returnRate.value,
                    searchFilterState.value.returnRate.max
                )
            }

            is FilterTypes.MinStartRating -> {
                arrayOf(
                    searchFilterState.value.minStartRating.value,
                    searchFilterState.value.minStartRating.max
                )
            }

            is FilterTypes.OrdersDelivered -> {
                arrayOf(
                    searchFilterState.value.ordersDelivered.value,
                    searchFilterState.value.ordersDelivered.max
                )
            }

            else -> {
                arrayOf(0, 0)
            }
        }
    }

    fun onSelectProvidersType(index: Int) {
        /*
        val selectedIndexes = ArrayList(_searchFilterState.value.providersType)

        if( index in _searchFilterState.value.providersType){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersType = selectedIndexes
        )

         */
    }

    fun onSelectProvidersVerification(index: Int) {
        /*
        val selectedIndexes = ArrayList(_searchFilterState.value.providersVerifications)

        if( index in _searchFilterState.value.providersVerifications){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersVerifications = selectedIndexes
        )

         */
    }

    fun onSelectProvidersAdminVerification(index: Int) {
        /*
        val selectedIndexes = ArrayList(_searchFilterState.value.providersAdminVerifications)

        if( index in _searchFilterState.value. providersAdminVerifications){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            providersAdminVerifications = selectedIndexes
        )

         */
    }

    fun onSelectClearanceCertificates(index: Int) {
        /*
        val selectedIndexes = ArrayList(_searchFilterState.value.clearanceCertifications)

        if( index in _searchFilterState.value.clearanceCertifications){
            selectedIndexes.remove(index)
        }else{
            selectedIndexes.add(index)
        }
        _searchFilterState.value = _searchFilterState.value.copy(
            clearanceCertifications = selectedIndexes
        )

         */
    }

    private fun getProvidersList() {
        _providersList.value = listOf(
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
            Providers("name 1", "others"),
        )
    }

    private fun getProvidersCoordinates() {
        _providersCoordinates.value = listOf(
            CustomMarkerState(54.9715, -1.6123, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.9745, -1.6423, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.9775, -1.6523, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.9115, -1.6923, "Kathmandu, Nepal Street 3340"),
            CustomMarkerState(54.8715, -1.5123, "Kathmandu, Nepal Street 3340")
        )
    }
}

data class Providers(
    val name: String,
    val others: String
)