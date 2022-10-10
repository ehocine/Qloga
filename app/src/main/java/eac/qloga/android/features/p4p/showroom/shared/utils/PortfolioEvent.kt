package eac.qloga.android.features.p4p.showroom.shared.utils

import androidx.compose.ui.focus.FocusState
import eac.qloga.bare.enums.AccessLevel

sealed class PortfolioEvent {
    data class SelectImage(val id: String): PortfolioEvent()
    object SelectFolderAll: PortfolioEvent()
    data class EnterAlbumName(val name: String): PortfolioEvent()
    data class FocusAlbumName(val focusState: FocusState): PortfolioEvent()
    data class EnterAlbumDesc(val desc: String): PortfolioEvent()
    data class FocusAlbumDesc(val focusState: FocusState): PortfolioEvent()
    data class ChangeAccessLevel(val access: AccessLevel): PortfolioEvent()
    object CloseSelectable: PortfolioEvent()
    object CreateAlbum: PortfolioEvent()
    object DeleteAlbum: PortfolioEvent()
}