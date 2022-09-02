package eac.qloga.android.data.shared.models

data class User(
    val userName: String? = "",
    val email: String? = "",
    val tokenExpiration: Int? = 0
)
