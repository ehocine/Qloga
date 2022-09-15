package eac.qloga.android.features.p4p.showroom.scenes.portfolioAlbums

import androidx.compose.ui.focus.FocusState
import eac.qloga.bare.dto.media.MediaAlbum
import eac.qloga.bare.enums.AccessLevel

sealed class AlbumEvent {
    object LongPressFolder: AlbumEvent()
    data class SelectFolder(val folders: List<MediaAlbum>): AlbumEvent()
    object SelectFolderAll: AlbumEvent()
    data class EnterAlbumName(val name: String): AlbumEvent()
    data class FocusAlbumName(val focusState: FocusState): AlbumEvent()
    data class EnterAlbumDesc(val desc: String): AlbumEvent()
    data class FocusAlbumDesc(val focusState: FocusState): AlbumEvent()
    data class ChangeAccessLevel(val access: AccessLevel): AlbumEvent()
    object CloseSelectable: AlbumEvent()
    object CreateAlbum: AlbumEvent()
    object DeleteAlbum: AlbumEvent()
    data class UpdateAlbum(val albumId: Long ): AlbumEvent()
}