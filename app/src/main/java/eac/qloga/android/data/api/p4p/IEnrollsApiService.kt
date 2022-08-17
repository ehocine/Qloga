package eac.qloga.android.data.api.p4p

import eac.qloga.android.data.model.ResponseEnrollsModel
import retrofit2.Response
import retrofit2.http.GET

interface IEnrollsApiService {

    @GET("p4p/")
    suspend fun enrolls(): Response<ResponseEnrollsModel>
}