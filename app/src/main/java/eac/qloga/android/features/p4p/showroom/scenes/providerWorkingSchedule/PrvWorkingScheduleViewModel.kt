package eac.qloga.android.features.p4p.showroom.scenes.providerWorkingSchedule

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.UiEvent
import eac.qloga.android.core.shared.utils.WeekDays
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.qbe.OrgsRepository
import eac.qloga.bare.dto.OffTime
import eac.qloga.bare.dto.WorkHours
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class PrvWorkingScheduleViewModel @Inject constructor(
    application: Application,
    private val orgsRepository: OrgsRepository
): AndroidViewModel(application) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object{
        const val TAG = "PrvWorkingViewModel"
        val workingHours: MutableState<List<WorkHours>> = mutableStateOf(emptyList())
        var offTimes by mutableStateOf<List<OffTime>>(emptyList())
    }

    var groupedWorkingHours by mutableStateOf<Map<String,List<WorkHours>>>(emptyMap())
        private set

    private val _savingState = MutableStateFlow(LoadingState.IDLE)
    val savingState = _savingState.asStateFlow()

    private val _eventFlow  = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        preLoadCalls()
    }

    fun preLoadCalls(){
        getWorkingHoursOffTime()
        groupWorkHours()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addOffTime(){
        val cloned = ArrayList(offTimes)
        cloned.add(OffTime(ZonedDateTime.now(), ZonedDateTime.now()))
        offTimes = cloned
    }

    fun removeOffTime(index: Int){
        val cloned = ArrayList(offTimes)
        cloned.removeAt(index)
        offTimes = cloned
    }

    fun removeWorkingHour(weekDays: WeekDays, index: Int){
        val filtered = mutableMapOf<String,List<WorkHours>>()
        groupedWorkingHours.forEach{ (weekDay, workHours) ->
            var result = workHours
            if(weekDay == weekDays.name) {
                val hw = ArrayList(workHours)
                hw.removeAt(index)
                result = hw
            }
            filtered[weekDay] = result
        }
        groupedWorkingHours = filtered
    }

    fun addWorkingHour(weekDays: WeekDays, newWorkHours: WorkHours){
        val filtered = mutableMapOf<String,List<WorkHours>>()
        groupedWorkingHours.forEach{ (weekDay, workHours) ->
            var result = workHours
            if(weekDay == weekDays.name) {
                val hw = ArrayList(workHours)
                hw.add(newWorkHours)
                result = hw
            }
            filtered[weekDay] = result
        }
        groupedWorkingHours = filtered
        Log.d(TAG, "addWorkingHour: ${groupedWorkingHours.values}")
    }

    fun updateWorkingHour(weekDays: WeekDays, newWorkHours: WorkHours, index: Int){
        val newGrouped = mutableMapOf<String,List<WorkHours>>()
        groupedWorkingHours.forEach{ (weekDay, workHours) ->
            var result = workHours
            if(weekDay == weekDays.name) {
                result = workHours.mapIndexed { i,w ->
                    var result2 = w
                    if( i == index) result2 = newWorkHours
                    result2
                }
            }
            newGrouped[weekDay] = result
        }
        groupedWorkingHours = newGrouped
    }

    fun updateOffTime(newOffTime: OffTime, index: Int){
        val newOffTimes = offTimes.mapIndexed { i,offTime  ->
            val result = if(index == i) newOffTime else offTime
            result
        }
        offTimes = emptyList()
        offTimes = newOffTimes
    }

    fun saveWorkingHours(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _savingState.emit(LoadingState.LOADING)
            val oid = ApiViewModel.orgs[0].id
            val flatWorkingHours = groupedWorkingHours.flatMap { it.value }
            val response = orgsRepository.setWorkingHours(oid, flatWorkingHours)
            if(response.isSuccessful){
                _savingState.emit(LoadingState.LOADED)
            }else{
                _eventFlow.emit(UiEvent.ShowToast("Fails to save working hours!"))
                _savingState.emit(LoadingState.ERROR)
            }
        }    
    }
    
    fun saveOffTime(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            try {
                _savingState.emit(LoadingState.LOADING)
                val oid = ApiViewModel.orgs[0].id
                val result = orgsRepository.setOffTimes(oid, offTimes)
                if(result.isSuccessful){
                    _eventFlow.emit(UiEvent.ShowToast("Saved successfully!"))
                    _savingState.emit(LoadingState.LOADED)
                }else{
                    _eventFlow.emit(UiEvent.ShowToast("Failed to save!"))
                    _savingState.emit(LoadingState.ERROR)
                }
            }catch (e: IOException){
                Log.e(TAG, "updateOffTime: ${e.cause}",)
                e.printStackTrace()
                _savingState.emit(LoadingState.ERROR)
            }
        }
    }

    private fun getWorkingHoursOffTime(){
        try {
            workingHours.value = ApiViewModel.orgs[0].workingHours
            offTimes = ApiViewModel.orgs[0].offTime.sortedBy { it.id }
        }catch (e: Exception){
            Log.e(TAG, "getWorkingHours: ${e.cause}")
            e.printStackTrace()
            throw Exception("Error occurred")
        }
    }

    fun groupWorkHours(){
        val result = workingHours.value.groupBy {
            it.weekDay.toString()
        }

        val workingHoursWithWeeks = mutableMapOf<String,List<WorkHours>>()

        WeekDays.values().forEach { day ->
            val dayResult = result[day.name] ?: emptyList()
            workingHoursWithWeeks[day.name] =  dayResult
        }
        groupedWorkingHours = workingHoursWithWeeks
    }
}