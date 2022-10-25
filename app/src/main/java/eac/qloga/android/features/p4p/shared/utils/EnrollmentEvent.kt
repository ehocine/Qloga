package eac.qloga.android.features.p4p.shared.utils

import androidx.compose.ui.focus.FocusState
import com.google.android.gms.maps.model.LatLng
import eac.qloga.bare.dto.lookups.Country

sealed class EnrollmentEvent {
    data class SelectCountryCode(val countryCode: Country) : EnrollmentEvent()
    data class EnterNumber(val number: String) : EnrollmentEvent()
    data class EnterCode(val code: String) : EnrollmentEvent()
    data class EnterAddress(val address: String) : EnrollmentEvent()
    data class FocusNumberInput(val focusState: FocusState) : EnrollmentEvent()
    data class FocusCodeInput(val focusState: FocusState) : EnrollmentEvent()
    data class FocusAddressInput(val focusState: FocusState) : EnrollmentEvent()
    object ClearAddress : EnrollmentEvent()
    object SendCode : EnrollmentEvent()
    object SubmitCode : EnrollmentEvent()
    object ToggleCheckTermsConditions : EnrollmentEvent()
    data class ClickMap(val latLng: LatLng) : EnrollmentEvent()
}