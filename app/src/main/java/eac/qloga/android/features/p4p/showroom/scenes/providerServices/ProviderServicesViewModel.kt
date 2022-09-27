package eac.qloga.android.features.p4p.showroom.scenes.providerServices

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.PROVIDER_ID
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.p4p.lookups.dto.QService
import eac.qloga.p4p.lookups.dto.ServiceCategory
import eac.qloga.p4p.prv.dto.Provider
import eac.qloga.p4p.prv.dto.ProviderService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProviderServicesViewModel @Inject constructor(): ViewModel() {

//    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
//        throwable.printStackTrace()
//    }

    companion object{
        val prvId = mutableStateOf<Long?>(null)
        val providerServices = mutableStateOf<List<ProviderService>>(emptyList())
    }

    val categories = ApiViewModel.categories.value
    val qServices = ApiViewModel.qServices.value
    val conditions = ApiViewModel.conditions.value

    private val _servicesWithConditions = mutableStateOf<List<ServicesWithCondition>>(emptyList())

    private val _servicesWithCategories = mutableStateOf<Map<ServiceCategory, List<ServicesWithCondition?>>>(emptyMap())
    val serviceWithCategories: State<Map<ServiceCategory, List<ServicesWithCondition?>>> = _servicesWithCategories

    fun loadServices(){
        viewModelScope.launch {
            try {
                providerServices.value.forEach { pService ->
                    concatServiceConditions(pService)
                    concatServiceCategories(pService)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun getCategory(service: QService): ServiceCategory{
        var result = ServiceCategory()
        categories.forEach { category ->
            if(service in category.services){
                result = category
                return@forEach
            }
        }
        return result
    }

    private fun concatServiceCategories(pService: ProviderService) {
        val service = qServices.find { it.id == pService.qServiceId }
        val category = service?.let { getCategory(it) }
        val newServiceWithConditions = ArrayList(
            listOf(_servicesWithConditions.value.find { it.service == service })
        )

        val previousServices = _servicesWithCategories.value[category]
        previousServices?.let {
            newServiceWithConditions.addAll(previousServices)
        }

        if(category != null){
            _servicesWithCategories.value = _servicesWithCategories.value.plus(
                mapOf(Pair(category,newServiceWithConditions))
            )
        }
    }

    private fun concatServiceConditions(pService: ProviderService){
        val sConditions: ArrayList<String> = arrayListOf()
        pService.conditions.forEach { condId ->
            val condition = conditions.find { it.id == condId }
            condition?.let { sConditions.add(condition.name) }
        }
        val service = qServices.find { it.id == pService.qServiceId }

        service?.let {
            val cloned = ArrayList(_servicesWithConditions.value)
            cloned.add(ServicesWithCondition(service,pService.unitCost,sConditions))
            _servicesWithConditions.value = cloned
        }
    }
}

data class ServicesWithCondition(
    val service: QService,
    val unitPrice: Long,
    val conditions: List<String>
)