package eac.qloga.android.data.landing

import eac.qloga.android.di.PublicQLOGAApiService
import eac.qloga.bare.dto.RegistrationRequest

class LandingRepository(@PublicQLOGAApiService private val landingApi: LandingApi) {
    suspend fun register(body: RegistrationRequest) = landingApi.register(body)
}