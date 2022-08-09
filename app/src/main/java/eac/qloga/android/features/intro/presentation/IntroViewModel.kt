package eac.qloga.android.features.intro.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.intro.util.*
import eac.qloga.android.features.shared.util.InputFieldState
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "IntroViewModel"

@HiltViewModel
class IntroViewModel @Inject constructor(): ViewModel(){

    private val _inputFieldState = mutableStateOf(InputFieldState(hint = "Enter new address"))
    val inputFieldState: State<InputFieldState> = _inputFieldState

    private val _selectedNav = mutableStateOf<ServiceCategory?>(null)
    val selectedNav: State<ServiceCategory?> = _selectedNav

    private val _providersList = mutableStateOf<List<Providers>>(emptyList())
    val providersList: State<List<Providers>> =_providersList

    private val _isShownMap = mutableStateOf(false)
    val isShownMap: State<Boolean> =_isShownMap

    private val _searchFilterState = mutableStateOf(SearchFilterState())
    val searchFilterState: State<SearchFilterState> =_searchFilterState


    init {
        getProvidersList()
    }

    fun onTriggerEvent(event: IntroEvent){
         try {
            viewModelScope.launch {
              when(event){
                  is IntroEvent.EnterText -> {
                      _inputFieldState.value = inputFieldState.value.copy(
                          text = event.text
                      )
                  }

                  is IntroEvent.ClearInput -> {
                      _inputFieldState.value = inputFieldState.value.copy(
                          text = ""
                      )
                  }

                  is IntroEvent.FocusInput -> {
                      _inputFieldState.value = inputFieldState.value.copy(
                          isFocused = event.focusState.isFocused
                      )
                  }

                  is IntroEvent.Search -> {
                      onSearch()
                  }

                  is IntroEvent.NavItemClick -> {
                      onNavClick(event.navItem)
                  }

                  is IntroEvent.ToggleOpenMap -> {
                      _isShownMap.value = !isShownMap.value
                  }

                  is IntroEvent.OnChangeSliderFilterState -> {
                      onChangeSliderFilter(event.type, event.value)
                  }
              }
            }

         }catch (e: Exception){
             Log.d(TAG, "onTriggerEvent: ${e.printStackTrace()}")
         }
    }

    private fun onSearch() {
        _inputFieldState.value = inputFieldState.value.copy(text = "")
    }

    private fun onNavClick(navItem: ServiceCategory){
        _selectedNav.value = navItem
    }

    private fun onChangeSliderFilter(type: FilterTypes, value: Int){

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

    fun onSelectProvidersType(selectedOptions: ProvidersTypeOptions){
        _searchFilterState.value = searchFilterState.value.copy(
            providersType = ProvidersType(value = selectedOptions)
        )
    }

    fun onSelectProvidersVerification(selectedOptions: ProvidersVerificationOptions){
        _searchFilterState.value = searchFilterState.value.copy(
            providersVerifications = ProvidersVerifications(value = selectedOptions)
        )
    }

    fun onSelectProvidersAdminVerification(selectedOptions: ProvidersAdminVerificationsOptions){
        _searchFilterState.value = searchFilterState.value.copy(
            providersAdminVerifications = ProvidersAdminVerifications(value = selectedOptions)
        )
    }

    fun onSelectClearanceCertificates(selectedOptions: ClearanceCertificationsOptions){
        _searchFilterState.value = searchFilterState.value.copy(
            clearanceCertifications = ClearanceCertifications(value = selectedOptions)
        )
    }

    private fun getProvidersList(){
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

}

data class Providers(
    val name: String,
    val others: String
)