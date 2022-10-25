package eac.qloga.android.data.landing

import eac.qloga.android.di.PublicQLOGAApiService
import eac.qloga.bare.dto.SignUpRequest

class LandingRepository(@PublicQLOGAApiService private val landingApi: LandingApi) {
    suspend fun register(body: SignUpRequest) = landingApi.register(body)
}