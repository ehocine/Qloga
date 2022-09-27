package eac.qloga.android.core.shared.utils

import android.util.Patterns


object EmailValidator {
    fun isValidEmail(target: CharSequence?): Boolean {
        if(target.isNullOrEmpty()) return false
        return Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}