package eac.qloga.android.core.shared.utils

import eac.qloga.bare.dto.person.Address

object AddressConverter {
    fun addressToString(
        address: Address?
    ): String{
        if(address == null) return ""
        val line1 = if(address.line1.isNullOrEmpty()) "" else address.line1
        val line2 = if(address.line2.isNullOrEmpty()) "" else " " + address.line2
        val line3 = if(address.line3.isNullOrEmpty()) "" else ", " + address.line3
        val line4 = if(address.line4.isNullOrEmpty()) "" else ", " + address.line4
        val town = if(address.town.isNullOrEmpty()) "" else ", " + address.town
        val country = if(address.country.isNullOrEmpty()) "" else ", " + address.country
        val parking = if(address.parking?.name.isNullOrEmpty()) "" else " ("+
                address.parking.name.lowercase().replaceFirstChar {it.uppercase() } + " parking)"

        return line1 + line2 + line3 + line4 + town + country + parking
    }
}