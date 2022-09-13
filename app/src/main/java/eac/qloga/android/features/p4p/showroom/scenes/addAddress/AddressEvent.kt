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
    data class EnterTown(val town: String) : AddressEvent()
    data class FocusTown(val focusState: FocusState) : AddressEvent()
    data class EnterStreet(val street: String) : AddressEvent()
    data class FocusStreet(val focusState: FocusState) : AddressEvent()
    data class EnterBuilding(val building: String) : AddressEvent()
    data class FocusBuilding(val focusState: FocusState) : AddressEvent()
    data class EnterApartments(val apartments: String) : AddressEvent()
    data class FocusApartments(val focusState: FocusState) : AddressEvent()
    data class ClickMap(val latLng: LatLng) : AddressEvent()
}