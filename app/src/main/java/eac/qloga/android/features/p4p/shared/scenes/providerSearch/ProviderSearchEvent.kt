package eac.qloga.android.features.p4p.shared.scenes.providerSearch

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.features.p4p.shared.utils.FilterTypes
import eac.qloga.p4p.lookups.dto.ServiceCategory

sealed class ProviderSearchEvent {
    data class EnterText(val text: String): ProviderSearchEvent()
    object Search: ProviderSearchEvent()
    object ClearInput: ProviderSearchEvent()
    object ToggleOpenMap: ProviderSearchEvent()
    data class FocusInput(val focusState: FocusState): ProviderSearchEvent()
    data class NavItemClick(val navItem: ServiceCategory): ProviderSearchEvent()
    data class OnChangeSliderFilterState(val type: FilterTypes, val value: Int): ProviderSearchEvent()
}