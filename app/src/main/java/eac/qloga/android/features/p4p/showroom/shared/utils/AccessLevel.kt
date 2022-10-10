package eac.qloga.android.features.p4p.showroom.shared.utils

sealed class AccessLevel(
    val label: String
){
    object Private: AccessLevel("Private")
    object Shared: AccessLevel("Shared")
    object Public : AccessLevel("Public")

    companion object{
        val list by lazy{ listOf( Private, Shared, Public)}
    }
}