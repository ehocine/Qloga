package eac.qloga.android.features.p4p.showroom.scenes.providerWorkingSchedule

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.bare.dto.OffTime
import eac.qloga.bare.dto.WorkHours
import javax.inject.Inject

@HiltViewModel
class PrvWorkingScheduleViewModel @Inject constructor(
    application: Application,
): AndroidViewModel(application) {

    companion object{
        const val TAG = "PrvWorkingViewModel"
        val workingHours = mutableStateOf<List<WorkHours>>(emptyList())
        val offTime = mutableStateOf<List<OffTime>>(emptyList())
    }

    private val _groupedWorkingHours = mutableStateOf<Map<String,List<WorkHours>>>(emptyMap())
    val groupedWorkingHours: State<Map<String,List<WorkHours>>> = _groupedWorkingHours

    fun groupWorkHours(){
        val result = workingHours.value.groupBy {
            it.weekDay.toString()
        }
        _groupedWorkingHours.value = result
    }
}