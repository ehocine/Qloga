package eac.qloga.android.features.p4p.shared.utils

sealed class ServiceEvent {
    object ToggleSwitch: ServiceEvent()
    object AddWindowClean: ServiceEvent()
    object AddKitchenClean: ServiceEvent()
    object AddBedroomClean: ServiceEvent()
    object AddCompleteHomeClean: ServiceEvent()
    object AddCompleteClean: ServiceEvent()
    object SubWindowClean: ServiceEvent()
    object SubKitchenClean: ServiceEvent()
    object SubBedroomClean: ServiceEvent()
    object SubCompleteHomeClean: ServiceEvent()
    object SubCompleteClean: ServiceEvent()
    object SubSelectedServiceCount: ServiceEvent()
    object AddSelectedServiceCount: ServiceEvent()
    data class ChangeTimeTabRow(val index: Int): ServiceEvent()
}