package eac.qloga.android.features.platform.landing.scenes.no_address

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.features.p4p.showroom.shared.utils.FilterTypes
import eac.qloga.p4p.lookups.dto.ServiceCategory


sealed class NoAddressEvent {
    data class EnterText(val text: String) : NoAddressEvent()
    data class AddressChosen(val text: String) : NoAddressEvent()
    object Search : NoAddressEvent()
    object ClearInput : NoAddressEvent()
    object ToggleOpenMap : NoAddressEvent()
    data class FocusInput(val focusState: FocusState) : NoAddressEvent()
    data class NavItemClick(val navItem: ServiceCategory) : NoAddressEvent()
    data class OnChangeSliderFilterState(val type: FilterTypes, val value: Int) : NoAddressEvent()
}
