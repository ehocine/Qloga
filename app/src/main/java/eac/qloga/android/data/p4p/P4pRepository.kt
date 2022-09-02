package eac.qloga.android.data.p4p

import eac.qloga.android.data.shared.models.ResponseEnrollsModel
import retrofit2.Response
import javax.inject.Inject

class P4pRepository @Inject constructor(private val apiService: P4pApi) {

    //Enrolls
    suspend fun enrolls(): Response<ResponseEnrollsModel> = apiService.enrolls()
}