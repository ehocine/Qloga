package eac.qloga.android.data.qmp

import eac.qloga.android.di.QLOGAApiService

class MsgRepository(@QLOGAApiService private val apiService: MsgApi) {

    suspend fun getOnlineAll() = apiService.getOnlineAll()
    suspend fun getOnlineRelatives() = apiService.getOnlineRelatives()

}