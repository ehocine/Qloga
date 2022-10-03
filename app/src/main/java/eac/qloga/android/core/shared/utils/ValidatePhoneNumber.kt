package eac.qloga.android.core.shared.utils

fun validatePhoneNumber(phoneNumber: String): Boolean {
    return phoneNumber.startsWith("0") && phoneNumber.length >= 10
}