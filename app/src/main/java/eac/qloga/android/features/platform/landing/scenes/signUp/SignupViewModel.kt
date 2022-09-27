package eac.qloga.android.features.platform.landing.scenes.signUp

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.DateConverter
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.QTAG
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
        const val TAG = "${QTAG}-SignupViewModel"
    }

    var firstName by mutableStateOf(InputFieldState(hint = "First Name"))
        private  set

    var familyName by mutableStateOf(InputFieldState(hint = "Family Name"))
        private set

    var emailAddress by mutableStateOf(InputFieldState(hint = "E-mail address"))
        private set

    var birthday by mutableStateOf<String?>(null)
        private set

    var gender by mutableStateOf<Gender?>(null)
        private set

    fun onTriggerEvent(event: SignupEvents) {
        try {
            viewModelScope.launch {
                when (event) {
                    is SignupEvents.EnterFirstName -> {
                        firstName = firstName.copy(text = event.firstName)
                    }
                    is SignupEvents.EnterFamilyName -> {
                        familyName = familyName.copy(text = event.familyName)
                    }
                    is SignupEvents.EnterEmailAddress -> {
                        emailAddress = emailAddress.copy(text = event.emailAddress)
                    }
                    is SignupEvents.EnterBirthday -> {
                        birthday = event.birthday
                    }
                    is SignupEvents.EnterGender -> {
                        gender = event.gender
                    }
                    is SignupEvents.FocusEmailAddress -> {
                        emailAddress =
                            emailAddress.copy(isFocused = event.focusState.isFocused)
                    }
                    is SignupEvents.FocusFamilyName -> {
                        familyName =
                            familyName.copy(isFocused = event.focusState.isFocused)
                    }
                    is SignupEvents.FocusFirstName -> {
                        firstName =
                            firstName.copy(isFocused = event.focusState.isFocused)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onEventTrigger: ${e.printStackTrace()}")
        }
    }

    var signUpLoadingState = MutableStateFlow(LoadingState.IDLE)

    @RequiresApi(Build.VERSION_CODES.O)
    fun signUpApply() {
        viewModelScope.launch {
            try {
                val registrationRequest: RegistrationRequest
                if (
                    firstName.text.isNotEmpty() &&
                    familyName.text.isNotEmpty() &&
                    emailAddress.text.isNotEmpty() &&
                    birthday != null &&
                    gender != null
                ) {
                    registrationRequest = RegistrationRequest(
                        firstName.text,
                        familyName.text,
                        DateConverter.stringToLocalDate(birthday),
                        emailAddress.text,
                        gender
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
                        getApplication(),
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