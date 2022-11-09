package eac.qloga.android.core.shared.utils

sealed class ParkingType(
    val label: String
) {
    object FreeType: ParkingType("Free")
    object PaidType: ParkingType("Paid")
    object UnspecifiedType: ParkingType("Unspecified")

    companion object{
        val listOfParkingType: List<ParkingType> by lazy{ listOf(FreeType, PaidType, UnspecifiedType)}
    }
}