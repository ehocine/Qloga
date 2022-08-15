package eac.qloga.android.data.api

import javax.inject.Inject

class APIHelper @Inject constructor(private val apiService: IApiService) {

    //Enrolls
    suspend fun enrolls() = apiService.enrolls()
}