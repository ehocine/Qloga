package eac.qloga.android.data.p4p

import eac.qloga.android.data.shared.models.ResponseEnrollsModel
import retrofit2.Response
import retrofit2.http.GET

interface P4pApi {

    @GET("p4p/")
    suspend fun enrolls(): Response<ResponseEnrollsModel>
}