package eac.qloga.android.core.shared.utils

import com.google.gson.annotations.SerializedName

data class CountryCode(
    @SerializedName("name")
    val name: String,
    @SerializedName("dial_code")
    val dialCode: String,
    @SerializedName("code")
    val code: String
)

data class CountryCodes(
    @SerializedName("country_codes")
    val countryCodes: List<CountryCode>
)