package eac.qloga.android.features.platform.landing.scenes.signUp

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.data.landing.LandingRepository
import eac.qloga.bare.dto.RegistrationRequest
import eac.qloga.bare.enums.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    application: Application,
    private val landingRepository: LandingRepository
) : AndroidViewModel(application) {

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

    private val _LocalDatebirthday = mutableStateOf<LocalDate?>(null)

    private val _gender = mutableStateOf<Gender?>(null)
    val gender: State<Gender?> = _gender

    fun onTriggerEvent(event: SignupEvents) {
        try {
            viewModelScope.launch {
                when (event) {
                    is SignupEvents.EnterFirstName -> {
                        _firstName.value = _firstName.value.copy(text = event.firstName)
                    }
                    is SignupEvents.EnterFamilyName -> {
                        _familyName.value = _familyName.value.copy(text = event.familyName)
                    }
                    is SignupEvents.EnterEmailAddress -> {
                        _emailAddress.value = _emailAddress.value.copy(text = event.emailAddress)
                    }
                    is SignupEvents.EnterBirthday -> {
                        _birthday.value = event.birthday
                    }
                    is SignupEvents.EnterLocalDateBirthday -> {
                        _LocalDatebirthday.value = event.localDateBirthday
                    }
                    is SignupEvents.EnterGender -> {
                        _gender.value = event.gender
                    }
                    is SignupEvents.FocusEmailAddress -> {
                        _emailAddress.value =
                            _emailAddress.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is SignupEvents.FocusFamilyName -> {
                        _familyName.value =
                            _familyName.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is SignupEvents.FocusFirstName -> {
                        _firstName.value =
                            _firstName.value.copy(isFocused = event.focusState.isFocused)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onEventTrigger: ${e.printStackTrace()}")
        }
    }

    var signUpLoadingState = MutableStateFlow(LoadingState.IDLE)

    fun signUpApply() {
        viewModelScope.launch {
            try {
                val registrationRequest: RegistrationRequest
                if (
                    _firstName.value.text.isNotEmpty() &&
                    _familyName.value.text.isNotEmpty() &&
                    _emailAddress.value.text.isNotEmpty() &&
                    !_birthday.value.isNullOrEmpty() &&
                    _gender.value != null
                ) {
                    registrationRequest = RegistrationRequest(
                        _firstName.value.text,
                        _familyName.value.text,
                        _LocalDatebirthday.value,
                        _emailAddress.value.text,
                        _gender.value

                    )
                    signUpLoadingState.emit(LoadingState.LOADING)
                    val response = landingRepository.register(registrationRequest)
                    if (response.isSuccessful) {
                        signUpLoadingState.emit(LoadingState.LOADED)
                    } else {
                        signUpLoadingState.emit(LoadingState.ERROR)
                    }

                } else {
                    Toast.makeText(
                        getApplication<Application>(),
                        "Fill out all the fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                signUpLoadingState.emit(LoadingState.ERROR)
            }
        }
    }
}