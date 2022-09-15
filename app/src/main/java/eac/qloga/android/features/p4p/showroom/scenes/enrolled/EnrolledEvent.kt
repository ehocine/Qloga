package eac.qloga.android.features.p4p.showroom.scenes.enrolled

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.features.p4p.shared.utils.FilterTypes
import eac.qloga.p4p.lookups.dto.ServiceCategory

sealed class EnrolledEvent{
    data class EnterText(val text: String) : EnrolledEvent()
    data class AddressChosen(val text: String) : EnrolledEvent()
    object Search : EnrolledEvent()
    object ClearInput : EnrolledEvent()
    object ToggleOpenMap : EnrolledEvent()
    data class FocusInput(val focusState: FocusState) : EnrolledEvent()
    data class NavItemClick(val navItem: ServiceCategory) : EnrolledEvent()
    data class OnChangeSliderFilterState(val type: FilterTypes, val value: Int) : EnrolledEvent()
}
