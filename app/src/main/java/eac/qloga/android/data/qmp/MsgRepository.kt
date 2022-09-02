package eac.qloga.android.data.qmp

class MsgRepository(private val apiService: MsgApi) {

    suspend fun getOnlineAll() = apiService.getOnlineAll()
    suspend fun getOnlineRelatives() = apiService.getOnlineRelatives()

}