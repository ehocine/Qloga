package eac.qloga.android.core.shared.utils

data class InputFieldState(
    var text: String = "",
    val hint: String = "",
    val isFocused: Boolean = false
)