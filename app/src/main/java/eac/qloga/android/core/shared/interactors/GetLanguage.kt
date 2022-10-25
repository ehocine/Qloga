package eac.qloga.android.core.shared.interactors

import android.util.Log
import eac.qloga.android.data.p4p.lookups.StaticResourcesRepository
import eac.qloga.android.data.shared.models.Language
import eac.qloga.android.data.shared.utils.NetworkResult
import eac.qloga.bare.dto.lookups.Country

private const val TAG = "GetCountry"

class GetLanguage(
    private val staticResourcesRepository: StaticResourcesRepository
){
    suspend operator fun invoke(): List<Language>{
        return when(val response = staticResourcesRepository.getLanguages()){
            is NetworkResult.Success -> {
                response.data
            }
            is NetworkResult.Exception -> {
                response.e.printStackTrace()
                emptyList()
            }
            is NetworkResult.Error -> {
                Log.e(TAG, "invoke: code = ${response.code}, error = ${response.message}" )
                emptyList()
            }
        }
    }
}
