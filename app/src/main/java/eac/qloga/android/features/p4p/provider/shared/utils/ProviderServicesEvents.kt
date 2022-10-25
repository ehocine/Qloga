package eac.qloga.android.features.p4p.provider.shared.utils

import androidx.compose.ui.focus.FocusState

sealed class ProviderServicesEvents {
    data class EnterPrice(val price: String): ProviderServicesEvents()
    data class FocusPrice(val focusState: FocusState): ProviderServicesEvents()
    data class EnterTimeNorm(val timeNorm: String): ProviderServicesEvents()
    data class FocusTimeNorm(val focusState: FocusState): ProviderServicesEvents()
    object ToggleNotify: ProviderServicesEvents()
}