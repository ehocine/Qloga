package eac.qloga.android.features.p4p.provider.scenes.servicesConditions

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.QTAG
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.p4p.lookups.dto.QService
import eac.qloga.p4p.lookups.dto.ServiceCategory
import eac.qloga.p4p.prv.dto.ProviderService
import javax.inject.Inject

const val TAG = "$QTAG SvrCndViewModel"
@HiltViewModel
class ServicesConditionsViewModel @Inject constructor(): ViewModel() {

    companion object{
        var selectNavItem by mutableStateOf<ServiceCategory?>(null)
    }

    var serviceCount by mutableStateOf<Map<String,Int>>(emptyMap())
        private set

    fun onChangeCategory(category: ServiceCategory){
        selectNavItem = category
    }

    private fun getCategory(service: QService): ServiceCategory{
        var result = ServiceCategory()
        ApiViewModel.categories.value.forEach { category ->
            if(service in category.services){
                result = category
                return@forEach
            }
        }
        return result
    }

    fun groupServiceCount(services: List<ProviderService>){
        val categories = mutableListOf<ServiceCategory>()
        services.forEach {
            categories.add(getCategory(getQService(it.qServiceId)))
        }

        val groupCount = categories.groupingBy { it.name }.eachCount()
        serviceCount = groupCount
        Log.d(TAG, "groupServiceCount: $groupCount")
        Log.d(TAG, "groupServiceCount: $serviceCount")
    }

    private fun getQService(id: Long): QService{
        return ApiViewModel.qServices.value.find { it.id == id }!!
    }
}