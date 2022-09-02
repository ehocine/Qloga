package eac.qloga.android.features.platform.landing.scenes.signUp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.bare.enums.Gender
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(): ViewModel(){

    companion object {
        const val TAG = "SignupViewModel"
    }

    private val _firstName = mutableStateOf(InputFieldState(hint = "First Name"))
    val firstName: State<InputFieldState> = _firstName

    private val _familyName = mutableStateOf(InputFieldState(hint = "Family Name"))
    val familyName: State<InputFieldState> = _familyName

    private val _emailAddress = mutableStateOf(InputFieldState(hint = "E-mail address"))
    val emailAddress: State<InputFieldState> = _emailAddress

    private val _birthday = mutableStateOf<String?>(null)
    val birthday: State<String?> = _birthday

    private val _gender = mutableStateOf<Gender?>(null)
    val gender: State<Gender?> = _gender

    fun onTriggerEvent(event: SignupEvents){
        try {
            viewModelScope.launch {
                when(event){
                    is SignupEvents.EnterFirstName -> { _firstName.value = _firstName.value.copy(text = event.firstName)}
                    is SignupEvents.EnterFamilyName -> { _familyName.value = _familyName.value.copy(text = event.familyName)}
                    is SignupEvents.EnterEmailAddress -> { _emailAddress.value = _emailAddress.value.copy(text = event.emailAddress)}
                    is SignupEvents.EnterBirthday -> { _birthday.value  = event.birthday}
                    is SignupEvents.EnterGender -> { _gender.value = event.gender}
                    is SignupEvents.FocusEmailAddress -> { _emailAddress.value = _emailAddress.value.copy(isFocused = event.focusState.isFocused)}
                    is SignupEvents.FocusFamilyName -> {_familyName.value = _familyName.value.copy(isFocused = event.focusState.isFocused)}
                    is SignupEvents.FocusFirstName -> { _firstName.value = _firstName.value.copy(isFocused = event.focusState.isFocused)}
                }
            }
        }catch (e: Exception){
            Log.d(TAG, "onEventTrigger: ${e.printStackTrace()}")
        }
    }

    fun signUpApply(): Boolean{
        if(
            _firstName.value.text.isNotEmpty() &&
            _familyName.value.text.isNotEmpty() &&
            _emailAddress.value.text.isNotEmpty() &&
            !_birthday.value.isNullOrEmpty() &&
            _gender.value != null
        ) return true

        return false
    }
}