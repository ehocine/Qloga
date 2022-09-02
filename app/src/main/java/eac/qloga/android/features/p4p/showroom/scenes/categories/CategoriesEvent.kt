package eac.qloga.android.features.p4p.showroom.scenes.categories

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.features.p4p.showroom.shared.utils.ServiceCategory
import eac.qloga.android.features.p4p.showroom.shared.utils.FilterTypes

sealed class CategoriesEvent {
    data class EnterText(val text: String): CategoriesEvent()
    object Search: CategoriesEvent()
    object ClearInput: CategoriesEvent()
    object ToggleOpenMap: CategoriesEvent()
    data class FocusInput(val focusState: FocusState): CategoriesEvent()
    data class NavItemClick(val navItem: ServiceCategory): CategoriesEvent()
    data class OnChangeSliderFilterState(val type: FilterTypes, val value: Int): CategoriesEvent()
}