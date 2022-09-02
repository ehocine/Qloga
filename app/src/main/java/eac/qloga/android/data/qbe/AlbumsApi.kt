package eac.qloga.android.data.qbe

import eac.qloga.android.data.shared.models.Page
import eac.qloga.bare.dto.media.MediaAlbum
import retrofit2.Response
import retrofit2.http.*

interface AlbumsApi {

    @GET("qbe/albums")
    suspend fun getAlbums(
        @Query("page") page: Long,
        @Query("psize") psize: Long
    ): Page<MediaAlbum>

    @POST("qbe/albums")
    suspend fun add(
        @Body meta: MediaAlbum,
    ): MediaAlbum

    @PUT("qbe/albums")
    suspend fun update(
        @Body meta: MediaAlbum,
    ): MediaAlbum


    /**
     * Moves a media from one album to another
     *    mid - media to move
     *    aid - album to move to
     */
    //0
    @PATCH("qbe/albums")
    suspend fun moveMediaBetweenAlbums(
        @Query("mid") mid: Long,
        @Query("aid") aid: Long
    ): Response<Unit>

    @GET("qbe/albums/{aid}")
    suspend fun getAlbumById(
        @Path("aid") aid: Long
    ): MediaAlbum

    @DELETE("qbe/albums/{aid}")
    suspend fun deleteMediaAlbum(
        @Path("aid") aid: Long
    ): Response<Unit>

}