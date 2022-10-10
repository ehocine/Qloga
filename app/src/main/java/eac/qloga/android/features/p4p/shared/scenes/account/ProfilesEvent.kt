package eac.qloga.android.features.p4p.shared.scenes.account

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.p4p.lookups.dto.ServiceCategory

sealed class ProfilesEvent {
    data class SwitchAccountType(val accountType: AccountType): ProfilesEvent()
//    data class SelectWorkingTime(
//        val workingHoursType: WorkingHoursType,
//        val workingHourTime: WorkingHourTime,
//        val index: Int
//    ): ProfilesEvent()
//    data class SelectTimeOff(val timeOffState: TimeOffState, val index: Int): ProfilesEvent()
    data class SelectTopNavItem(val navItem: ServiceCategory): ProfilesEvent()
    data class EnterPrice(val price: String): ProfilesEvent()
    data class EnterTimeNorm(val timeNorm: String): ProfilesEvent()
    data class FocusPrice(val focusState: FocusState): ProfilesEvent()
    data class FocusTimeNorm(val focusState: FocusState): ProfilesEvent()
    object SwitchProvidesCleaning: ProfilesEvent()
    object SwitchNoCarpetCleaning: ProfilesEvent()
    object SwitchNoSideWindowsCleaning: ProfilesEvent()
    object AddTimeOff: ProfilesEvent()
    data class RemoveTimeOff(val index: Int): ProfilesEvent()
//    data class AddWorkingHour(val type: WorkingHoursType): ProfilesEvent()
//    data class RemoveWorkingHour(val type: WorkingHoursType, val index: Int): ProfilesEvent()
}