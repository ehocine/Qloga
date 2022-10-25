package eac.qloga.android.data.qbe


import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.utils.apiHandler
import eac.qloga.android.di.QLOGAApiService
import eac.qloga.bare.dto.media.MediaMeta
import eac.qloga.bare.enums.AccessLevel
import eac.qloga.bare.enums.AvatarType

class MediaRepository(@QLOGAApiService private val apiService: MediaApi) {
    //Media
    suspend fun getMedias(page: Long, psize: Long) = apiService.getMedias(page, psize)
    suspend fun upload(fileData: String, access: AccessLevel, aid: Long?) =
        apiService.upload(fileData, access, aid)

    suspend fun updateMedia(meta: MediaMeta) = apiService.updateMedia(meta)
    suspend fun getOriginalImage(mid: Long) = apiService.getOriginalImage(mid)
    suspend fun getImageDataUrl(mid: Long, size: MediaSize?) = apiHandler { apiService.getImageDataUrl(mid, size?.size) }
    suspend fun deleteMedia(mid: Long) = apiService.deleteMedia(mid)
    suspend fun getImage(mid: Long, size: MediaSize) = apiService.getImage(size, mid)
    suspend fun getAvatarDataUrl(type: AvatarType, id: Long, size: MediaSize?) =
        apiService.getAvatarDataUrl(type, id, size)

    suspend fun getAvatar(otype: AvatarType, id: Long) = apiService.getAvatar(otype, id)
    suspend fun getAvatarPrescaled(otype: AvatarType, id: Long, ms: MediaSize) =
        apiService.getAvatarPrescaled(otype, id, ms)

}