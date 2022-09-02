package eac.qloga.android.data.landing

import eac.qloga.bare.dto.RegistrationRequest
import javax.inject.Inject

class LandingRepository @Inject constructor(private val apiService: LandingApi) {
    suspend fun register(body: RegistrationRequest) = apiService.register(body)
}