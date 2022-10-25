package eac.qloga.android.core.shared.utils

sealed class UiEvent {
    data class ShowSnackBar(val msg: String,val actionLabel: String? = null ): UiEvent()
    data class ShowToast(val msg: String ): UiEvent()
    object NavigateBack: UiEvent()
}