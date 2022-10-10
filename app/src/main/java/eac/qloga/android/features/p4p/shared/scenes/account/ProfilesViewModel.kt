package eac.qloga.android.features.p4p.shared.scenes.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.ACCOUNT_TYPE_KEY
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.data.shared.models.FaQuestion
import eac.qloga.android.features.p4p.shared.utils.AccountType
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "ProfilesViewModel"

@HiltViewModel
class ProfilesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _paramsAccountType = mutableStateOf<String?>(savedStateHandle[ACCOUNT_TYPE_KEY])
    
//    private val _workingHourState = mutableStateOf(WorkingHoursState())
//    val workingHoursState: State<WorkingHoursState> = _workingHourState
//
//    private val _timeOffList = mutableStateOf<List<TimeOffState>>(emptyList())
//    val timeOffList: State<List<TimeOffState>> = _timeOffList
//
//    private val _selectedNav = mutableStateOf<ServiceCategory?>(null)
//    val selectedNav: State<ServiceCategory?> = _selectedNav
//
    private val _priceInputField = mutableStateOf(InputFieldState(text = "15.00"))
    val priceInputField: State<InputFieldState> = _priceInputField

    private val _timeNormInputField = mutableStateOf(InputFieldState(text = "180"))
    val timeNormInputField: State<InputFieldState> = _timeNormInputField

    private val _providesCleaningSwitch = mutableStateOf(true)
    val providesCleaningSwitch: State<Boolean> = _providesCleaningSwitch

    private val _noCarpetCleaningSwitch = mutableStateOf(false)
    val noCarpetCleaningSwitch: State<Boolean> = _noCarpetCleaningSwitch

    private val _noSideWindowsCleaningSwitch = mutableStateOf(true)
    val noSideWindowsCleaningSwitch: State<Boolean> = _noSideWindowsCleaningSwitch

    private val _conditionsCount = mutableStateOf(0)
    val conditionsCount: State<Int> = _conditionsCount

    private val _showGotoProfileDialog = mutableStateOf(false)
    val showGotoProfileDialog: State<Boolean> = _showGotoProfileDialog

    private val _showGotoDialogAgain = mutableStateOf(true)
    val showGotoDialogAgain: State<Boolean> = _showGotoDialogAgain

    var faQuestions by mutableStateOf<List<FaQuestion>>(emptyList())
        private set

    var accountType by mutableStateOf(AccountType.PROVIDER)
        private set

    init{
        getConditionsSwitchCount()
        getGeneralQuestions()
        _paramsAccountType.value?.let {
            getAccountTypeByParam(it)?.let { type -> accountType = type }
        }
    }

    fun onTriggerEvent(event: ProfilesEvent){
        try {
            viewModelScope.launch {
                when(event){
//                    is ProfilesEvent.SwitchAccountType -> {
//                        _accountType.value = event.accountType
//                        Log.d(TAG, ": ${_paramsAccountType.value}")
//                        Log.d(TAG, ": ${accountType.value}")
//                    }
//                    is ProfilesEvent.SelectWorkingTime -> { onSelectWorkingHours(event.workingHoursType, event.workingHourTime, event.index)}
//                    is ProfilesEvent.SelectTimeOff -> { onSelectTimeOff(event.timeOffState, event.index)}
//                    is ProfilesEvent.SelectTopNavItem -> { onTopNavClick(event.navItem) }
//                    is ProfilesEvent.EnterPrice -> { onPrice(event.price) }
//                    is ProfilesEvent.EnterTimeNorm -> { onTimeNorm(event.timeNorm) }
//                    is ProfilesEvent.FocusPrice -> { onFocusPrice(event.focusState) }
//                    is ProfilesEvent.FocusTimeNorm -> { onFocusTimeNorm(event.focusState) }
//                    is ProfilesEvent.AddTimeOff -> { onAddTimeOff() }
//                    is ProfilesEvent.AddWorkingHour -> { onAddWorkingHour(event.type)}
//                    is ProfilesEvent.RemoveWorkingHour -> { onRemoveWorkingHour(event.type, event.index)}
//                    is ProfilesEvent.SwitchProvidesCleaning -> {
//                        _providesCleaningSwitch.value = !providesCleaningSwitch.value
//                        getConditionsSwitchCount()
//                    }
//                    is ProfilesEvent.SwitchNoCarpetCleaning -> {
//                        _noCarpetCleaningSwitch.value = !noCarpetCleaningSwitch.value
//                        getConditionsSwitchCount()
//                    }
//                    is ProfilesEvent.SwitchNoSideWindowsCleaning -> {
//                        _noSideWindowsCleaningSwitch.value = !noSideWindowsCleaningSwitch.value
//                        getConditionsSwitchCount()
//                    }
//                    is ProfilesEvent.RemoveTimeOff -> { onRemoveTimeOff(event.index)}
                }
            }
        }catch (e: Exception){
            Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    private fun onRemoveTimeOff(index: Int) {
//        val timeOff = ArrayList<TimeOffState>(_timeOffList.value)
//        timeOff.removeAt(index)
//        _timeOffList.value = timeOff
    }

    private fun onFocusTimeNorm(focusState: FocusState) {
//        _timeNormInputField.value = timeNormInputField.value.copy(
//            isFocused = focusState.isFocused
//        )
    }

    private fun onTimeNorm(timeNorm: String) {
//        _timeNormInputField.value = timeNormInputField.value.copy(
//            text = timeNorm
//        )
    }

    private fun onFocusPrice(focusState: FocusState) {
//        _priceInputField.value = priceInputField.value.copy(
//            isFocused =  focusState.isFocused
//        )
    }

    private fun onPrice(price: String) {
//        _priceInputField.value = priceInputField.value.copy(
//            text = price
//        )
    }

    private fun onAddTimeOff(){
//        val timeOff = ArrayList<TimeOffState>(_timeOffList.value)
//        timeOff.add(TimeOffState("","","",""))
//        _timeOffList.value = timeOff
    }

    fun onGotoProfileDialog(value: Boolean){
        _showGotoProfileDialog.value = value
    }

    fun onShowGotoDialogAgain(value: Boolean){
        _showGotoDialogAgain.value = value
    }

//    private fun onRemoveWorkingHour(type: WorkingHoursType, index: Int) {
//        when(type){
//            WorkingHoursType.SUN -> {
//                val sundayList = ArrayList(_workingHourState.value.SUN)
//                sundayList.removeAt(index)
//                _workingHourState.value = _workingHourState.value.copy(
//                    SUN = sundayList
//                )
//            }
//
//            WorkingHoursType.MON -> {
//                val monList = ArrayList(_workingHourState.value.MON)
//                monList.removeAt(index)
//                _workingHourState.value = _workingHourState.value.copy(
//                    MON = monList
//                )
//            }
//
//            WorkingHoursType.TUE -> {
//                val tueList = ArrayList(_workingHourState.value.TUE)
//                tueList.removeAt(index)
//                _workingHourState.value = _workingHourState.value.copy(
//                    TUE = tueList
//                )
//            }
//
//            WorkingHoursType.WED -> {
//                val wedList = ArrayList(_workingHourState.value.WED)
//                wedList.removeAt(index)
//                _workingHourState.value = _workingHourState.value.copy(
//                    WED = wedList
//                )
//            }
//
//            WorkingHoursType.THU -> {
//                val thuList = ArrayList(_workingHourState.value.THU)
//                thuList.removeAt(index)
//                _workingHourState.value = _workingHourState.value.copy(
//                    THU = thuList
//                )
//            }
//
//            WorkingHoursType.FRI -> {
//                val friList = ArrayList(_workingHourState.value.FRI)
//                friList.removeAt(index)
//                _workingHourState.value = _workingHourState.value.copy(
//                    FRI = friList
//                )
//            }
//
//            WorkingHoursType.SAT -> {
//                val satList = ArrayList(_workingHourState.value.SAT)
//                satList.removeAt(index)
//                _workingHourState.value = _workingHourState.value.copy(
//                    SAT = satList
//                )
//            }
//        }
//    }

//    private fun onAddWorkingHour(type: WorkingHoursType) {
//        //workingHourState.value = _workingHourState.value
//        when(type){
//            WorkingHoursType.SUN -> {
//                val sundayList = ArrayList(_workingHourState.value.SUN)
//                sundayList.add(WorkingHourTime())
//                _workingHourState.value = _workingHourState.value.copy(
//                    SUN = sundayList
//                )
//            }
//
//            WorkingHoursType.MON -> {
//                val monList = ArrayList(_workingHourState.value.MON)
//                monList.add(WorkingHourTime())
//                _workingHourState.value = _workingHourState.value.copy(
//                    MON = monList
//                )
//            }
//
//            WorkingHoursType.TUE -> {
//                val tueList = ArrayList(_workingHourState.value.TUE)
//                tueList.add(WorkingHourTime())
//                _workingHourState.value = _workingHourState.value.copy(
//                    TUE = tueList
//                )
//            }
//
//            WorkingHoursType.WED -> {
//                val wedList = ArrayList(_workingHourState.value.WED)
//                wedList.add(WorkingHourTime())
//                _workingHourState.value = _workingHourState.value.copy(
//                    WED = wedList
//                )
//            }
//
//            WorkingHoursType.THU -> {
//                val thuList = ArrayList(_workingHourState.value.THU)
//                thuList.add(WorkingHourTime())
//                _workingHourState.value = _workingHourState.value.copy(
//                    THU = thuList
//                )
//            }
//
//            WorkingHoursType.FRI -> {
//                val friList = ArrayList(_workingHourState.value.FRI)
//                friList.add(WorkingHourTime())
//                _workingHourState.value = _workingHourState.value.copy(
//                    FRI = friList
//                )
//            }
//
//            WorkingHoursType.SAT -> {
//                val satList = ArrayList(_workingHourState.value.SAT)
//                satList.add(WorkingHourTime())
//                _workingHourState.value = _workingHourState.value.copy(
//                    SAT = satList
//                )
//            }
//        }
//    }

//    private fun onSelectWorkingHours(type: WorkingHoursType, workingHourTime: WorkingHourTime, index: Int){
//        /***
//         *  @selectedTime is for to know exactly which time picker button
//         *  is clicked as which time data we are getting like SUN.FROM one type etc.
//         *  @time is actual time that use picked up as working hour
//         * ***/
//        when(type){
//            // is week is sunday type
//             is WorkingHoursType.SUN -> { _workingHourState.value = workingHoursState.value.copy(
//                 SUN = _workingHourState.value.SUN.mapIndexed { mIndex, mWorkingHourTime ->
//                     if(index == mIndex){
//                         WorkingHourTime(
//                             from = workingHourTime.from.ifEmpty { mWorkingHourTime.from },
//                             to = workingHourTime.to.ifEmpty { mWorkingHourTime.to }
//                            )
//                        }else{
//                            mWorkingHourTime
//                        }
//                    }
//                )
//             }
//
//            // is week is monday type
//            is WorkingHoursType.MON -> { _workingHourState.value = workingHoursState.value.copy(
//                MON = _workingHourState.value.MON.mapIndexed { mIndex, mWorkingHourTime ->
//                    if(index == mIndex){
//                        WorkingHourTime(
//                            from = workingHourTime.from.ifEmpty { mWorkingHourTime.from },
//                            to = workingHourTime.to.ifEmpty { mWorkingHourTime.to }
//                        )
//                    }else{
//                        mWorkingHourTime
//                    }
//                }
//            ) }
//
//            is WorkingHoursType.TUE -> { _workingHourState.value = workingHoursState.value.copy(
//                TUE = _workingHourState.value.TUE.mapIndexed { mIndex, mWorkingHourTime ->
//                    if(index == mIndex){
//                        WorkingHourTime(
//                            from = workingHourTime.from.ifEmpty { mWorkingHourTime.from },
//                            to = workingHourTime.to.ifEmpty { mWorkingHourTime.to }
//                        )
//                    }else{
//                        mWorkingHourTime
//                    }
//                }
//            ) }
//
//            is WorkingHoursType.WED -> { _workingHourState.value = workingHoursState.value.copy(
//                WED = _workingHourState.value.WED.mapIndexed { mIndex, mWorkingHourTime ->
//                    if(index == mIndex){
//                        WorkingHourTime(
//                            from = workingHourTime.from.ifEmpty { mWorkingHourTime.from },
//                            to = workingHourTime.to.ifEmpty { mWorkingHourTime.to }
//                        )
//                    }else{
//                        mWorkingHourTime
//                    }
//                }
//            ) }
//
//            is WorkingHoursType.THU -> { _workingHourState.value = workingHoursState.value.copy(
//                THU = _workingHourState.value.THU.mapIndexed { mIndex, mWorkingHourTime ->
//                    if(index == mIndex){
//                        WorkingHourTime(
//                            from = workingHourTime.from.ifEmpty { mWorkingHourTime.from },
//                            to = workingHourTime.to.ifEmpty { mWorkingHourTime.to }
//                        )
//                    }else{
//                        mWorkingHourTime
//                    }
//                }
//            ) }
//
//            is WorkingHoursType.FRI -> { _workingHourState.value = workingHoursState.value.copy(
//                FRI = _workingHourState.value.FRI.mapIndexed { mIndex, mWorkingHourTime ->
//                    if(index == mIndex){
//                        WorkingHourTime(
//                            from = workingHourTime.from.ifEmpty { mWorkingHourTime.from },
//                            to = workingHourTime.to.ifEmpty { mWorkingHourTime.to }
//                        )
//                    }else{
//                        mWorkingHourTime
//                    }
//                }
//            ) }
//
//            is WorkingHoursType.SAT -> { _workingHourState.value = workingHoursState.value.copy(
//                SAT = _workingHourState.value.SAT.mapIndexed { mIndex, mWorkingHourTime ->
//                    if(index == mIndex){
//                        WorkingHourTime(
//                            from = workingHourTime.from.ifEmpty { mWorkingHourTime.from },
//                            to = workingHourTime.to.ifEmpty { mWorkingHourTime.to }
//                        )
//                    }else{
//                        mWorkingHourTime
//                    }
//                }
//            ) }
//        }
//    }

//    private fun onSelectTimeOff(timeOffState: TimeOffState, index: Int){
//        // index is used to identify the item to be updated
//        _timeOffList.value = _timeOffList.value.mapIndexed { mIndex, mTimeOffState ->
//            if(index == mIndex){
//                TimeOffState(
//                    // if the value received as empty i.e no update else update
//                    fromDate = timeOffState.fromDate.ifEmpty { mTimeOffState.fromDate },
//                    toDate = timeOffState.toDate.ifEmpty { mTimeOffState.toDate },
//                    fromTime = timeOffState.fromTime.ifEmpty { mTimeOffState.fromTime },
//                    toTime = timeOffState.toTime.ifEmpty { mTimeOffState.toTime },
//                )
//            }else{
//                mTimeOffState
//            }
//        }
//    }

//    private fun onTopNavClick(navItem: ServiceCategory){
//        _selectedNav.value = navItem
//    }

    private fun getConditionsSwitchCount(){
        val listOfConditionsSwitch = listOf(
            _providesCleaningSwitch.value,
            _noCarpetCleaningSwitch.value,
            _noSideWindowsCleaningSwitch.value
        )
        val count = listOfConditionsSwitch.count { it }
        _conditionsCount.value = count
    }

//    fun onSelectedNav(selectedNavItem: ServiceCategory){
//        _selectedNav.value = selectedNavItem
//    }

    private fun getGeneralQuestions(){
        // simulate api calls here to get general questions list
        faQuestions = listOf(
            FaQuestion(
                question = "How will QLOGA use my data?",
                answer = "QLOGA will never share any user data " +
                        "with third parties that are not directly related " +
                        "to QLOGA provided services. QLOGA will never share or " +
                        "sell any user data with third parties without" +
                        " explicit consent from the users."
            ),
            FaQuestion(
                question = "What if I don't add any relatives?",
                answer = "QLOGA will never share any user data " +
                        "with third parties that are not directly related " +
                        "to QLOGA provided services. QLOGA will never share or " +
                        "sell any user data with third parties without" +
                        " explicit consent from the users."
            )
        )
    }

    fun getAccountTypeByParam(accTypeString: String): AccountType?{
        Log.d(TAG, "getAccountTypeByParam: $accTypeString")
        if(accTypeString == AccountType.CUSTOMER.label) return AccountType.CUSTOMER
        if(accTypeString == AccountType.PROVIDER.label) return AccountType.PROVIDER
        return null
    }
}