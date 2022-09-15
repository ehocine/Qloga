package eac.qloga.android.core.shared.utils

sealed class BottomSheetType(val name: String){
    object Info: BottomSheetType("info")
    object Create: BottomSheetType("create")
    object Add: BottomSheetType("add")
    object Edit: BottomSheetType("edit")
    object ShowAlbum: BottomSheetType("showAlbum")
}