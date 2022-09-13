package eac.qloga.android.features.p4p.showroom.scenes.enrolled

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.p4p.showroom.scenes.categories.CategoriesViewModel
import eac.qloga.android.features.p4p.showroom.scenes.notEnrolled.NotEnrolledEvent
import eac.qloga.android.features.p4p.showroom.scenes.notEnrolled.NotEnrolledViewModel
import eac.qloga.p4p.lookups.dto.ServiceCategory
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnrolledViewModel @Inject constructor(
) : ViewModel() {


    private val _selectedNav = mutableStateOf<ServiceCategory?>(null)
    val selectedNav: State<ServiceCategory?> = _selectedNav


    fun onTriggerEvent(event: EnrolledEvent) {
        try {
            viewModelScope.launch {
                when (event) {
                    is EnrolledEvent.EnterText -> {

                    }

                    is EnrolledEvent.ClearInput -> {
                    }

                    is EnrolledEvent.FocusInput -> {

                    }
                    is EnrolledEvent.ToggleOpenMap -> {
                        /*
                        if(_isShownMap.value){
                            _providersScreenType.value = ProvidersScreenType.MAP
                        }else{
                            _providersScreenType.value = ProvidersScreenType.PROVIDERS
                        }

                         */
                    }
                    is EnrolledEvent.AddressChosen -> {

                    }
                    is EnrolledEvent.Search -> {

                    }
                    is EnrolledEvent.NavItemClick -> {
                        onNavClick(event.navItem)
                    }
                    is EnrolledEvent.OnChangeSliderFilterState -> {

                    }
                }
            }
        } catch (e: Exception) {
            Log.e(NotEnrolledViewModel.TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }



    fun onNavClick(navItem: ServiceCategory?) {
        _selectedNav.value = navItem
        CategoriesViewModel.selectedNav.value = navItem
    }

}