package eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.signUp

import androidx.compose.ui.focus.FocusState
import eac.qloga.bare.enums.Gender
import java.time.LocalDate

sealed class SignupEvents {
    data class EnterFirstName(val firstName: String): SignupEvents()
    data class EnterFamilyName(val familyName: String): SignupEvents()
    data class EnterEmailAddress(val emailAddress: String): SignupEvents()
    data class EnterBirthday(val birthday: String): SignupEvents()
    data class EnterLocalDateBirthday(val localDateBirthday: LocalDate): SignupEvents()
    data class EnterGender(val gender: Gender): SignupEvents()
    data class FocusFirstName(val focusState: FocusState): SignupEvents()
    data class FocusFamilyName(val focusState: FocusState): SignupEvents()
    data class FocusEmailAddress(val focusState: FocusState): SignupEvents()
}