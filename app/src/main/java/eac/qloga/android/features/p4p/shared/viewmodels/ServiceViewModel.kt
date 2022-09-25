package eac.qloga.android.features.p4p.shared.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.features.p4p.shared.utils.QuoteBottomBtnState
import eac.qloga.android.features.p4p.shared.utils.ServiceEvent
import eac.qloga.android.features.p4p.shared.utils.VisitInfo
import eac.qloga.android.features.p4p.shared.utils.VisitedTime
import eac.qloga.android.features.p4p.showroom.shared.utils.CleaningServiceCategory
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "ServiceViewModel"

@HiltViewModel
class ServiceViewModel @Inject constructor(): ViewModel(){

    val timeTabContains = mutableStateOf(listOf("30 min", "1 hr"))

    private val timeSlots1 = listOf(
        "00:30-01:30","01:30-02:30","02:30-03:30","03:30-04:30",
        "04:30-05:30","05:30-06:30","06:30-07:30","07:30-08:30",
        "08:30-09:30","09:30-10:30","10:30-11:30","11:30-12:30",
        "12:30-13:30","13:30-14:30","14:30-15:30","15:30-16:30",
        "16:300-17:30","17:30-18:30","18:30-19:30","19:30-20:30",
        "20:30-21:30","21:30-22:30","22:30-23:30","23:30-00:30"
    )

    private val timeSlots2 = listOf(
        "00:00-01:00","01:00-02:00","02:00-03:00","03:00-04:00",
        "04:00-05:00","05:00-06:00","06:00-07:00","07:00-08:00",
        "08:00-09:00","09:00-10:00","10:00-11:00","11:00-12:00",
        "12:00-13:00","13:00-14:00","14:00-15:00","15:00-16:00",
        "16:00-17:00","17:00-18:00","18:00-19:00","19:00-20:00",
        "20:00-21:00","21:00-22:00","22:00-23:00","23:00-00:00"
    )

    private val _checked = mutableStateOf(false)
    val checked: State<Boolean> = _checked

    private val _selectedVisitedDate = mutableStateOf<VisitInfo?>(null)
    val selectedVisitedDate: State<VisitInfo?> = _selectedVisitedDate

    private val _visitedOneWeekData = mutableStateOf<List<VisitInfo>>(emptyList())
    val visitedOneWeekData: State<List<VisitInfo>> = _visitedOneWeekData

    private val _visitedTwoWeekData = mutableStateOf<List<VisitInfo>>(emptyList())
    val visitedTwoWeekData: State<List<VisitInfo>> = _visitedTwoWeekData

    private val _listOfTimeSlots = mutableStateOf(timeSlots2)
    val listOfTimeSlots: State<List<String>> = _listOfTimeSlots

    // This is the list of available time slots for first visits
    private val _timeSlotAvailableList = mutableStateOf<List<String>>(emptyList())
    val timeSlotAvailableList: State<List<String>> = _timeSlotAvailableList

    private val _selectedTimeSlotList = mutableStateOf<List<String>>(emptyList())
    val selectedTimeSlotList: State<List<String>> = _selectedTimeSlotList

    private val _windowCleanCount = mutableStateOf(CleaningServiceCategory(title="Window Cleaning"))
    val windowCleanCount: State<CleaningServiceCategory> = _windowCleanCount

    private val _kitchenCleanCount = mutableStateOf(CleaningServiceCategory(title="Kitchen Cleaning"))
    val kitchenCleanCount: State<CleaningServiceCategory> = _kitchenCleanCount

    private val _bedRoomCleanCount = mutableStateOf(CleaningServiceCategory(title="Bed or living room Cleaning"))
    val bedRoomCleanCount: State<CleaningServiceCategory> = _bedRoomCleanCount

    private val _completeHomeCleanCount = mutableStateOf(CleaningServiceCategory(title="Complete home Cleaning"))
    val completeHomeCleanCount: State<CleaningServiceCategory> = _completeHomeCleanCount

    private val _completeCleanCount = mutableStateOf(CleaningServiceCategory(title="Complete Cleaning"))
    val completeCleanCount: State<CleaningServiceCategory> = _completeCleanCount

    private val _selectedTimeTabRowIndex = mutableStateOf(1)
    val selectedTimeTabRowIndex: State<Int> = _selectedTimeTabRowIndex

    private val _selectedCleaningCategories = mutableStateOf(
        listOf(_windowCleanCount, _kitchenCleanCount, _bedRoomCleanCount,_completeHomeCleanCount,_completeCleanCount)
    )
    val selectedCleaningCategories: State<List<MutableState<CleaningServiceCategory>>> = _selectedCleaningCategories

    // selected services are stored with integer of its order
    private val _selectedServiceId = mutableStateOf<Int?>(null)
    val selectedServiceId: State<Int?> = _selectedServiceId

    private val _quoteBottomBtnState = mutableStateOf(QuoteBottomBtnState.EMPTY_SERVICE)
    val quoteBottomBtnState: State<QuoteBottomBtnState> = _quoteBottomBtnState

    init {
        getThisWeekVisitedData()
        getTwoWeekVisitedData()
        getListOfTimeSlots()
        getAvailableTimeSlots()
    }

    fun onTriggerEvent(event: ServiceEvent){
         try {
            viewModelScope.launch {
              when(event){
                  is ServiceEvent.ToggleSwitch -> { onToggleSwitch() }
                  is ServiceEvent.ChangeTimeTabRow -> { onChangeTabRow(event.index)}
                  is ServiceEvent.SubCompleteHomeClean -> {
                      if(_completeHomeCleanCount.value.count > 0){
                          _completeHomeCleanCount.value = _completeHomeCleanCount.value.copy(
                              count = _completeHomeCleanCount.value.count - 1
                          )
                      }
                  }
                  is ServiceEvent.AddCompleteClean -> {
                      _completeCleanCount.value = _completeCleanCount.value.copy(
                          count =  _completeCleanCount.value.count + 1
                      )
                  }
                  is ServiceEvent.AddCompleteHomeClean -> {
                      _completeHomeCleanCount.value = _completeHomeCleanCount.value.copy(
                          count =  _completeHomeCleanCount.value.count + 1
                      )
                  }
                  is ServiceEvent.AddBedroomClean -> {
                      _bedRoomCleanCount.value = _bedRoomCleanCount.value.copy(
                          count = _bedRoomCleanCount.value.count + 1
                      )
                  }
                  is ServiceEvent.AddKitchenClean -> {
                      _kitchenCleanCount.value = _kitchenCleanCount.value.copy(
                          count = _kitchenCleanCount.value.count + 1
                      )
                  }
                  is ServiceEvent.AddWindowClean -> {
                      _windowCleanCount.value = _windowCleanCount.value.copy(
                          count = _windowCleanCount.value.count + 1
                      )
                  }
                  is ServiceEvent.SubCompleteClean -> {
                      if(_completeCleanCount.value.count > 0){
                          _completeCleanCount.value = _completeCleanCount.value.copy(
                              count = _completeCleanCount.value.count - 1
                          )
                      }
                  }
                  is ServiceEvent.SubBedroomClean -> {
                      if(_bedRoomCleanCount.value.count > 0){
                          _bedRoomCleanCount.value = _bedRoomCleanCount.value.copy(
                              count = _bedRoomCleanCount.value.count - 1
                          )
                      }
                  }
                  is ServiceEvent.SubKitchenClean -> {
                      if(_kitchenCleanCount.value.count > 0){
                          _kitchenCleanCount.value = _kitchenCleanCount.value.copy(
                              count = _kitchenCleanCount.value.count - 1
                          )
                      }
                  }
                  is ServiceEvent.SubWindowClean -> {
                      if(_windowCleanCount.value.count > 0){
                          _windowCleanCount.value = _windowCleanCount.value.copy(
                              count = _windowCleanCount.value.count - 1
                          )
                      }
                  }
                  is ServiceEvent.AddSelectedServiceCount -> {
                      _selectedCleaningCategories.value[selectedServiceId.value!!].value =
                          _selectedCleaningCategories.value[selectedServiceId.value!!].value.copy(
                              count = _selectedCleaningCategories.value[selectedServiceId.value!!].value.count + 1
                          )
                  }
                  is ServiceEvent.SubSelectedServiceCount -> {
                      if(_selectedCleaningCategories.value[selectedServiceId.value!!].value.count > 0){
                          _selectedCleaningCategories.value[selectedServiceId.value!!].value =
                              _selectedCleaningCategories.value[selectedServiceId.value!!].value.copy(
                                  count = _selectedCleaningCategories.value[selectedServiceId.value!!].value.count - 1
                              )
                      }
                  }
              }
            }
         }catch (e: Exception){
             Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
         }
    }

    fun onChangeQuoteBottomBtnState(btnState: QuoteBottomBtnState){
        _quoteBottomBtnState.value = btnState
    }

    fun removeCleaningCategory(index: Int){
        _selectedCleaningCategories.value = _selectedCleaningCategories.value.mapIndexed { i, service ->
            if(index == i){
                service.value = service.value.copy(count = 0)
            }
            service
        }
    }

    fun setSelectedServiceId(id: Int){
        _selectedServiceId.value = id
    }

    private fun onChangeTabRow(index: Int) {
        _selectedTimeTabRowIndex.value = index
        getListOfTimeSlots()
        getAvailableTimeSlots()
    }

    private fun onToggleSwitch(){
        _checked.value = !checked.value
    }

    fun resetCleaningServices(){
        _windowCleanCount.value = _windowCleanCount.value.copy(count = 0)
        _kitchenCleanCount.value = _kitchenCleanCount.value.copy(count = 0)
        _completeCleanCount.value = _completeCleanCount.value.copy(count = 0)
        _completeHomeCleanCount.value = _completeHomeCleanCount.value.copy(count = 0)
        _bedRoomCleanCount.value = _bedRoomCleanCount.value.copy(count = 0)
    }

    fun getNextWeekVisitedData(){
        val thisWeek: List<VisitInfo> = listOf(
            VisitInfo(date = "14/10/2022", "Monday", listOf(VisitedTime("12:00", "13:00"))),
            VisitInfo(date = "15/10/2022", "Tuesday", listOf(VisitedTime("14:00", "18:00"))),
            VisitInfo(date = "16/10/2022", "Wednesday", ),
            VisitInfo(date = "17/10/2022", "Thursday", listOf(VisitedTime("12:00", "19:00"))),
            VisitInfo(date = "18/10/2022", "Friday",  ),
            VisitInfo(date = "19/10/2022", "Saturday", ),
            VisitInfo(date = "20/10/2022", "Sunday", listOf(VisitedTime("12:00", "13:00"))),
        )
        _visitedOneWeekData.value = thisWeek
    }

    fun clearAllVisits(){
        /** Simulating the raw data not actual * */
        val thisTwoWeek: List<VisitInfo> = listOf(
            VisitInfo(date = "07/10/2022", "Monday",),
            VisitInfo(date = "08/10/2022", "Tuesday", ),
            VisitInfo(date = "09/10/2022", "Wednesday", ),
            VisitInfo(date = "10/10/2022", "Thursday",),
            VisitInfo(date = "11/10/2022", "F",  ),
            VisitInfo(date = "12/10/2022", "S", ),
            VisitInfo(date = "13/10/2022", "S",),
            VisitInfo(date = "07/10/2022", "Monday",),
            VisitInfo(date = "08/10/2022", "T", ),
            VisitInfo(date = "09/10/2022", "W", ),
            VisitInfo(date = "10/10/2022", "T",),
            VisitInfo(date = "11/10/2022", "F",),
            VisitInfo(date = "12/10/2022", "S",),
            VisitInfo(date = "13/10/2022", "S", ),
        )
        val thisWeek: List<VisitInfo> = listOf(
            VisitInfo(date = "07/10/2022", "Monday", ),
            VisitInfo( date = "08/10/2022", "Tuesday",),
            VisitInfo(date = "09/10/2022", "Wednesday", ),
            VisitInfo(date = "10/10/2022", "Thursday",),
            VisitInfo(date = "11/10/2022", "F",  ),
            VisitInfo(date = "12/10/2022", "S", ),
            VisitInfo(date = "13/10/2022", "S",),
        )
        _visitedOneWeekData.value = thisWeek
        _visitedTwoWeekData.value = thisTwoWeek
        _selectedTimeSlotList.value = emptyList()
    }

    private fun getThisWeekVisitedData(){
        /** Simulating the raw data not actual* */
        val thisWeek: List<VisitInfo> = listOf(
            VisitInfo(date = "07/10/2022", "Monday", listOf(VisitedTime("12:00", "13:00"), VisitedTime("14:00", "15:00"))),
            VisitInfo(
                date = "08/10/2022",
                "Tuesday",
                listOf(
                    VisitedTime("14:00", "18:00"),
                    VisitedTime("18:00", "19:00"),
                    VisitedTime("19:00", "20:00")
                )
            ),
            VisitInfo(date = "09/10/2022", "Wednesday", ),
            VisitInfo(date = "10/10/2022", "Thursday", listOf(VisitedTime("12:00", "19:00"))),
            VisitInfo(date = "11/10/2022", "F",  ),
            VisitInfo(date = "12/10/2022", "S", ),
            VisitInfo(date = "13/10/2022", "S", listOf(VisitedTime("12:00", "13:00"))),
        )
        _visitedOneWeekData.value = thisWeek
    }

    private fun getTwoWeekVisitedData(){
        /** Simulating the raw data not actual * */
        val thisTwoWeek: List<VisitInfo> = listOf(
            VisitInfo(date = "07/10/2022", "Monday", listOf(VisitedTime("12:00", "13:00"))),
            VisitInfo(date = "08/10/2022", "Tuesday", listOf(VisitedTime("14:00", "18:00"))),
            VisitInfo(date = "09/10/2022", "Wednesday", ),
            VisitInfo(date = "10/10/2022", "Thursday", listOf(VisitedTime("12:00", "19:00"))),
            VisitInfo(date = "11/10/2022", "F",  ),
            VisitInfo(date = "12/10/2022", "S", ),
            VisitInfo(date = "13/10/2022", "S", listOf(VisitedTime("12:00", "13:00"))),
            VisitInfo(date = "07/10/2022", "Monday", listOf(VisitedTime("12:00", "13:00"))),
            VisitInfo(date = "08/10/2022", "T", listOf(VisitedTime("14:00", "18:00"))),
            VisitInfo(date = "09/10/2022", "W", ),
            VisitInfo(date = "10/10/2022", "T", listOf(VisitedTime("12:00", "19:00"))),
            VisitInfo(date = "11/10/2022", "F",  ),
            VisitInfo(date = "12/10/2022", "S", ),
            VisitInfo(date = "13/10/2022", "S", listOf(VisitedTime("12:00", "13:00"))),
        )
        _visitedTwoWeekData.value = thisTwoWeek
    }

    fun setSelectedDate(visitedDate: VisitInfo){
        _selectedVisitedDate.value = visitedDate
    }

    private fun getListOfTimeSlots(){
        val selectedIndexes = _selectedTimeSlotList.value.map {
            listOfTimeSlots.value.indexOf(it)
        }

        if(selectedTimeTabRowIndex.value == 0){
            //30 min
            _listOfTimeSlots.value = _listOfTimeSlots.value.mapIndexed { index, timeSlot ->
                if(index in selectedIndexes){
                    timeSlot
                }else{
                    timeSlots1[index]
                }
            }
            return
        }
        // 1hr
        _listOfTimeSlots.value = _listOfTimeSlots.value.mapIndexed{ index, timeSlot ->
            if(index in selectedIndexes){
                timeSlot
            }else{
                timeSlots2[index]
            }
        }
    }

    private fun getAvailableTimeSlots(){
        _timeSlotAvailableList.value = listOf(
            _listOfTimeSlots.value[4],
            _listOfTimeSlots.value[5],
            _listOfTimeSlots.value[6],
            _listOfTimeSlots.value[7],
            _listOfTimeSlots.value[8],
            _listOfTimeSlots.value[9]
        )
    }

    fun toggleSelectedTimeSlot(timeSlot: String){
        if( timeSlot in selectedTimeSlotList.value){
            // remove already exits
            _selectedTimeSlotList.value = selectedTimeSlotList.value.minus(timeSlot)
        }else{
            // only 3 slots can be selected at max
            if(selectedTimeSlotList.value.size > 2) return
            //add
            _selectedTimeSlotList.value = selectedTimeSlotList.value.plus(timeSlot)
        }

        val visitedTime:List<VisitedTime> = selectedTimeSlotList.value.map {
            VisitedTime(start = it.split("-")[0], end = it.split("-")[1])
        }

        _selectedVisitedDate.value = selectedVisitedDate.value?.copy(
            time = visitedTime
        )
    }
}
