package eac.qloga.android.features.p4p.showroom.scenes.providerWorkingSchedule

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.features.p4p.shared.scenes.P4pSharedScreens
import eac.qloga.bare.dto.OffTime
import eac.qloga.bare.dto.WorkHours
import eac.qloga.p4p.core.dto.Rating
import eac.qloga.p4p.order.dto.OrderReview
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
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