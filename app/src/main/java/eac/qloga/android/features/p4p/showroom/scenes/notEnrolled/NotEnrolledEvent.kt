package eac.qloga.android.features.p4p.showroom.scenes.notEnrolled

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.features.p4p.showroom.shared.utils.FilterTypes
import eac.qloga.p4p.lookups.dto.ServiceCategory

sealed class NotEnrolledEvent {
    data class EnterText(val text: String) : NotEnrolledEvent()
    data class AddressChosen(val text: String) : NotEnrolledEvent()
    object Search : NotEnrolledEvent()
    object ClearInput : NotEnrolledEvent()
    object ToggleOpenMap : NotEnrolledEvent()
    data class FocusInput(val focusState: FocusState) : NotEnrolledEvent()
    data class NavItemClick(val navItem: ServiceCategory) : NotEnrolledEvent()
    data class OnChangeSliderFilterState(val type: FilterTypes, val value: Int) : NotEnrolledEvent()
}