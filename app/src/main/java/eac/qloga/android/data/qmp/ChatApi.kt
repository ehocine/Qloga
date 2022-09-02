package eac.qloga.android.data.qmp


import eac.qloga.android.data.shared.models.Page
import eac.qloga.bare.dto.chat.ChatGroup
import eac.qloga.bare.dto.chat.ChatMessage
import eac.qloga.bare.dto.chat.ChatMessageUpdateRequest
import eac.qloga.bare.dto.chat.Chatter
import retrofit2.Response
import retrofit2.http.*

interface ChatApi {

    @GET("qmp/p2p")
    suspend fun getP2PChatters(): List<Chatter>

    @POST("qmp/p2p/messages")
    suspend fun addP2PMessage(
        @Query("pid") pid: Long,
        @Body message: ChatMessage
    ): ChatMessage

    @GET("qmp/groups")
    suspend fun getGroups(): List<ChatGroup>

    @POST("qmp/groups")
    suspend fun addGroup(
        @Body group: ChatGroup
    ): ChatGroup

    @POST("qmp/groups")
    suspend fun updateGroup(
        @Body group: ChatGroup
    ): ChatGroup

    @DELETE("qmp/groups")
    suspend fun deleteGroup(
        @Query("id") id: Long
    ): Response<Unit>

    @GET("qmp/groups/{id}")
    suspend fun getGroup(
        @Path("id") id: Long
    ): ChatGroup

    @GET("qmp/groups/chatters")
    suspend fun getGroupChatters(
        @Query("id") id: Long
    ): List<Chatter>

    @POST("qmp/groups/chatters")
    suspend fun addChatter(
        @Body chatter: Chatter
    ): Chatter

    @PATCH("qmp/groups/chatters")
    suspend fun updateChatter(
        @Body chatter: Chatter
    ): Chatter

    @DELETE("qmp/groups/chatters")
    suspend fun deleteChatter(
        @Query("id") id: Long
    ): Response<Unit>

    @GET("qmp/groups/chatters/{id}")
    suspend fun getChatter(
        @Path("id") id: Long
    ): Chatter

    @GET("qmp/groups/messages")
    suspend fun getGroupMessages(
        @Query("id") id: Long,
        @Query("page") page: Long,
        @Query("psize") psize: Long
    ): Page<ChatMessage>

    @POST("qmp/groups/messages")
    suspend fun addMessage(
        @Body message: ChatMessage
    ): ChatMessage

    @PATCH("qmp/groups/messages")
    suspend fun updateMessage(
        @Body request: ChatMessageUpdateRequest
    ): ChatMessage

    @DELETE("qmp/groups/messages")
    suspend fun deleteMessage(
        @Query("id") id: Long
    ): Response<Unit>

}