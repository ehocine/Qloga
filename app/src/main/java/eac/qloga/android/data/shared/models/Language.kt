package eac.qloga.android.data.shared.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Language(
    @JsonProperty("code")
    val code: String? = null,

    @JsonProperty("descr")
    val descr: String? = null
)