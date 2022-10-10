package eac.qloga.android.features.p4p.showroom.scenes.providerServices

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.shared.models.ServicesWithConditions
import eac.qloga.android.data.shared.models.conditions.ConditionsResponse
import eac.qloga.p4p.lookups.dto.QService
import eac.qloga.p4p.lookups.dto.ServiceCategory
import eac.qloga.p4p.lookups.dto.ServiceCondition
import eac.qloga.p4p.prv.dto.ProviderService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderServicesViewModel @Inject constructor(): ViewModel() {

    companion object{
        val providerServices = mutableStateOf<List<ProviderService>>(emptyList())
    }

    val categories = ApiViewModel.categories.value
    val qServices = ApiViewModel.qServices.value
    val conditions = ApiViewModel.conditions.value

    var servicesWithConditions by mutableStateOf<List<ServicesWithConditions>>(emptyList())
        private set

    var servicesWithCategories by mutableStateOf<Map<ServiceCategory, List<ServicesWithConditions?>>>(emptyMap())
        private set

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
            listOf(servicesWithConditions.find { it.service == service })
        )
        val previousServices = servicesWithCategories[category]

        previousServices?.let {
            newServiceWithConditions.addAll(previousServices)
        }
        val setResult = newServiceWithConditions.toSet() //to remove duplication

        if(category != null){
            servicesWithCategories = servicesWithCategories.plus(
                mapOf(Pair(category,setResult.toList()))
            )
        }
    }

    private fun concatServiceConditions(pService: ProviderService){
        val sConditions: ArrayList<ServiceCondition> = arrayListOf()
        pService.conditions.forEach { condId ->
            val condition = conditions.find { it.id == condId }
            condition?.let { sConditions.add(condition) }
        }
        val service = qServices.find { it.id == pService.qServiceId }

        service?.let {
            val cloned = ArrayList(servicesWithConditions)
            cloned.add(ServicesWithConditions(service,pService.unitCost,sConditions))
            servicesWithConditions = cloned
        }
    }
}