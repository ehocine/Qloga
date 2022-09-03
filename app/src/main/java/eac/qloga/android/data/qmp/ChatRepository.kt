package eac.qloga.android.data.qmp

import eac.qloga.android.di.QLOGAApiService
import eac.qloga.bare.dto.chat.ChatGroup
import eac.qloga.bare.dto.chat.ChatMessage
import eac.qloga.bare.dto.chat.ChatMessageUpdateRequest
import eac.qloga.bare.dto.chat.Chatter

class ChatRepository(@QLOGAApiService private val apiService: ChatApi) {

    suspend fun getP2PChatters() = apiService.getP2PChatters()
    suspend fun addP2PMessage(pid: Long, message: ChatMessage) =
        apiService.addP2PMessage(pid, message)

    suspend fun getGroups() = apiService.getGroups()
    suspend fun addGroup(group: ChatGroup) = apiService.addGroup(group)
    suspend fun updateGroup(group: ChatGroup) = apiService.updateGroup(group)
    suspend fun deleteGroup(id: Long) = apiService.deleteGroup(id)
    suspend fun getGroup(id: Long) = apiService.getGroup(id)
    suspend fun getGroupChatters(id: Long) = apiService.getGroupChatters(id)
    suspend fun addChatter(chatter: Chatter) = apiService.addChatter(chatter)
    suspend fun updateChatter(chatter: Chatter) = apiService.updateChatter(chatter)
    suspend fun deleteChatter(id: Long) = apiService.deleteChatter(id)
    suspend fun getChatter(id: Long) = apiService.getChatter(id)
    suspend fun getGroupMessages(id: Long, page: Long, psize: Long) =
        apiService.getGroupMessages(id, page, psize)

    suspend fun addMessage(message: ChatMessage) = apiService.addMessage(message)
    suspend fun updateMessage(request: ChatMessageUpdateRequest) = apiService.updateMessage(request)
    suspend fun deleteMessage(id: Long) = apiService.deleteMessage(id)

}