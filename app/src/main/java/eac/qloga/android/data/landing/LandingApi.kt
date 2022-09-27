package eac.qloga.android.data.landing

import eac.qloga.bare.dto.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LandingApi {

    @POST("a/signup/")
    suspend fun register(
        @Body body: RegistrationRequest
    ): Response<Unit>
}