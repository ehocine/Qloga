package eac.qloga.android.data.p4p.lookups

import eac.qloga.android.data.shared.utils.apiHandler
import eac.qloga.android.di.PrivateQLOGAApiService
import javax.inject.Inject

class LookupsRepository(@PrivateQLOGAApiService private val lookupsApi: LookupsApi) {

    //Get categories
    suspend fun getCategories() = lookupsApi.getCategories()

    //Get conditions
    suspend fun getConditions() = lookupsApi.getConditions()

    //Get QServices
    suspend fun getServices() = apiHandler { lookupsApi.getServices() }

}