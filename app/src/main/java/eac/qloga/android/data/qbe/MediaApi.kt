package eac.qloga.android.data.qbe


import eac.qloga.android.data.shared.models.MediaSize
import eac.qloga.android.data.shared.models.Page
import eac.qloga.bare.dto.media.MediaMeta
import eac.qloga.bare.enums.AccessLevel
import eac.qloga.bare.enums.AvatarType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MediaApi {

    @GET("qbe/images")
    suspend fun getMedias(
        @Query("page") page: Long,
        @Query("psize") psize: Long
    ): Page<MediaMeta>

    @POST("qbe/images")
    suspend fun upload(
        @Body fileData: String,
        @Query("access") access: AccessLevel = AccessLevel.PRIVATE,
        @Query("aid") aid: Long?
    ): MediaMeta

    @PUT("qbe/images")
    suspend fun updateMedia(
        @Body mediaMeta: MediaMeta
    ): MediaMeta

    @GET("qbe/images/{mid}")
    suspend fun getOriginalImage(
        @Path("mid") mid: Long
    ): ResponseBody

    @GET("qbe/images/{mid}")
    suspend fun getImageDataUrl(
        @Path("mid") mid: Long,
        @Query("ms") size: Int?
    ): Response<ResponseBody>

    @DELETE("qbe/images/{mid}")
    suspend fun deleteMedia(
        @Path("mid") mid: Long
    ): Response<Unit>

    @GET("qbe/images/{ms}")
    suspend fun getImage(
        @Path("ms") size: MediaSize,
        @Query("mid") mid: Long
    ): ResponseBody

    @GET("qbe/avatars/{otype}/{id}")
    suspend fun getAvatarDataUrl(
        @Path("otype") otype: AvatarType,
        @Path("id") id: Long,
        @Query("ms") ms: MediaSize?
    ): ResponseBody

    @GET("qbe/avatars/{otype}/{id}")
    suspend fun getAvatar(
        @Path("otype") otype: AvatarType,
        @Path("id") id: Long
    ): ResponseBody

    @GET("qbe/avatars/{otype}/{id}")
    suspend fun getAvatarPrescaled(
        @Path("otype") otype: AvatarType,
        @Path("id") id: Long,
        @Query("ms") ms: MediaSize
    ): ResponseBody

}