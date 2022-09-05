package eac.qloga.android.data.p4p

import eac.qloga.android.di.QLOGAApiService
import javax.inject.Inject

class P4pRepository @Inject constructor(@QLOGAApiService private val apiService: P4pApi) {

    //Enrolls
    suspend fun enrolls() = apiService.enrolls()
}