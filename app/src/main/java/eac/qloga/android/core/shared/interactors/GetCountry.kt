package eac.qloga.android.core.shared.interactors

import android.util.Log
import eac.qloga.android.data.p4p.lookups.StaticResourcesRepository
import eac.qloga.android.data.shared.utils.NetworkResult
import eac.qloga.bare.dto.lookups.Country

private const val TAG = "GetCountry"

class GetCountry(
    private val staticResourcesRepository: StaticResourcesRepository
){
    suspend operator fun invoke(): List<Country>{
        return when(val response = staticResourcesRepository.getCountries()){
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
