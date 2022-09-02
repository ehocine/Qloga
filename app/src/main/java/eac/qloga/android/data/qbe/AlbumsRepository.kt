package eac.qloga.android.data.qbe

import eac.qloga.bare.dto.media.MediaAlbum

class AlbumsRepository (private val apiService: AlbumsApi){
    suspend fun getAlbums(page:Long,psize:Long) = apiService.getAlbums(page, psize)
    suspend fun add(meta: MediaAlbum) = apiService.add(meta)
    suspend fun update(meta: MediaAlbum) = apiService.update(meta)
    suspend fun moveMediaBetweenAlbums(mid: Long,aid: Long) = apiService.moveMediaBetweenAlbums(mid, aid)
    suspend fun getAlbumById(aid: Long) = apiService.getAlbumById(aid)
    suspend fun deleteMediaAlbum(aid: Long) = apiService.deleteMediaAlbum(aid)
}