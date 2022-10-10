package eac.qloga.android.features.p4p.showroom.shared.utils

import androidx.compose.ui.focus.FocusState
import eac.qloga.bare.dto.media.MediaAlbum
import eac.qloga.bare.dto.media.MediaMeta
import eac.qloga.bare.enums.AccessLevel

sealed class MediaEvent {
    object LongPressImage: MediaEvent()
    data class SelectImage(val images: List<MediaMeta>): MediaEvent()
    object SelectAllImages: MediaEvent()
    data class EnterImageName(val name: String): MediaEvent()
    data class EnterMediaFolderName(val name: String): MediaEvent()
    data class FocusImageName(val focusState: FocusState): MediaEvent()
    data class FocusMediaFolderName(val focusState: FocusState): MediaEvent()
    data class EnterImageDesc(val desc: String): MediaEvent()
    data class EnterMediaFolderDesc(val desc: String): MediaEvent()
    data class FocusImageDesc(val focusState: FocusState): MediaEvent()
    data class FocusMediaFolderDesc(val focusState: FocusState): MediaEvent()
    data class ChangeMediaFolderAccessLevel(val access: AccessLevel): MediaEvent()
    data class ChangeImageAccessLevel(val access: AccessLevel): MediaEvent()
    object CloseSelectable: MediaEvent()
    object DeleteImage: MediaEvent()
    data class UpdateImage(val imageId: Long ): MediaEvent()
    object UpdateMediaFolder: MediaEvent()
    data class SelectFolderToMove(val folder: MediaAlbum): MediaEvent()
    object Move: MediaEvent()
}