package eac.qloga.android.data.api

import eac.qloga.android.data.model.ResponseEnrollsModel
import retrofit2.Response
import javax.inject.Inject

class APIHelper @Inject constructor(private val apiService: IApiService) {

    //Enrolls
    suspend fun enrolls(): Response<ResponseEnrollsModel> = apiService.enrolls()
}