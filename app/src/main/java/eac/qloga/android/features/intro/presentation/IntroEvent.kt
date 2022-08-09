package eac.qloga.android.features.intro.presentation

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.features.intro.util.ServiceCategory
import eac.qloga.android.features.intro.util.FilterTypes

sealed class IntroEvent {
    data class EnterText(val text: String): IntroEvent()
    object Search: IntroEvent()
    object ClearInput: IntroEvent()
    object ToggleOpenMap: IntroEvent()
    data class FocusInput(val focusState: FocusState): IntroEvent()
    data class NavItemClick(val navItem: ServiceCategory): IntroEvent()
    data class OnChangeSliderFilterState(val type: FilterTypes, val value: Int): IntroEvent()
}