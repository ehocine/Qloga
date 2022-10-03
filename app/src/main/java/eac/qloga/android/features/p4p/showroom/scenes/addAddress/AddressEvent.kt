package eac.qloga.android.features.p4p.showroom.scenes.addAddress

import androidx.compose.ui.focus.FocusState
import com.google.android.gms.maps.model.LatLng

sealed class AddressEvent {
    data class EnterText(val text: String) : AddressEvent()
    data class AddressChosen(val text: String) : AddressEvent()
    object Search : AddressEvent()
    object ClearInput : AddressEvent()
    data class FocusInput(val focusState: FocusState) : AddressEvent()
    data class EnterPostcode(val postCode: String) : AddressEvent()
    data class FocusPostcode(val focusState: FocusState) : AddressEvent()
    data class EnterCity(val city: String) : AddressEvent()
    data class FocusCity(val focusState: FocusState) : AddressEvent()
    data class EnterLine1(val line1: String) : AddressEvent()
    data class FocusLine1(val focusState: FocusState) : AddressEvent()
    data class EnterLine2(val line2: String) : AddressEvent()
    data class FocusLine2(val focusState: FocusState) : AddressEvent()
    data class EnterLine3(val line3: String) : AddressEvent()
    data class FocusLine3(val focusState: FocusState) : AddressEvent()
    data class ClickMap(val latLng: LatLng) : AddressEvent()
}