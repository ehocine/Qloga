package eac.qloga.android.features.platform.landing.scenes.noAddress

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.features.p4p.showroom.scenes.notEnrolled.NotEnrolledViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoAddressViewModel @Inject constructor(
) : ViewModel() {


    private var _inputFieldState = mutableStateOf(
        InputFieldState(
            text = "",
            hint = "Enter new address"
        )
    )
    var inputFieldState: MutableState<InputFieldState> = _inputFieldState


    fun onTriggerEvent(event: NoAddressEvent) {
        try {
            viewModelScope.launch {
                when (event) {
                    is NoAddressEvent.EnterText -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = event.text
                        )
                    }

                    is NoAddressEvent.ClearInput -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = ""
                        )
                    }

                    is NoAddressEvent.FocusInput -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            isFocused = event.focusState.isFocused
                        )
                    }
                    is NoAddressEvent.ToggleOpenMap -> {
                        /*
                        if(_isShownMap.value){
                            _providersScreenType.value = ProvidersScreenType.MAP
                        }else{
                            _providersScreenType.value = ProvidersScreenType.PROVIDERS
                        }

                         */
                    }
                    is NoAddressEvent.AddressChosen -> {
                        _inputFieldState.value = inputFieldState.value.copy(
                            text = event.text
                        )
                    }
                    is NoAddressEvent.Search -> {
//                        onSearch()
                    }
                    is NoAddressEvent.NavItemClick -> {

                    }
                    is NoAddressEvent.OnChangeSliderFilterState -> {

                    }
                }
            }
        } catch (e: Exception) {
            Log.e(NotEnrolledViewModel.TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

}