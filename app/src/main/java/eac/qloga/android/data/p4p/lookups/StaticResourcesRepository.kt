package eac.qloga.android.data.p4p.lookups

import eac.qloga.android.data.shared.utils.apiHandler
import eac.qloga.android.di.PrivateQLOGAApiService

class StaticResourcesRepository(@PrivateQLOGAApiService private val lookupsApi: LookupsApi) {

    suspend fun getFaQuestions() = lookupsApi.getFaQuestions()
    suspend fun getCountries() = apiHandler { lookupsApi.getCountries() }
    suspend fun getLanguages() = apiHandler { lookupsApi.getLanguages() }
}