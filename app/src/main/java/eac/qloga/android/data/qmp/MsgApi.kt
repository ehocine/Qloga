package eac.qloga.android.data.qmp

import eac.qloga.bare.dto.OnlineUser
import eac.qloga.bare.messages.NotificationChannel
import eac.qloga.bare.messages.NotificationType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MsgApi {

    @GET("qmp/all")
    suspend fun getOnlineAll(): Map<Long, Set<OnlineUser>>

    @GET("qmp/online")
    suspend fun getOnlineRelatives(): Set<OnlineUser>

}