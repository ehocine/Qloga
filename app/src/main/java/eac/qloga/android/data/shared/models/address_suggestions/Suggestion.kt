package eac.qloga.android.data.shared.models.address_suggestions

import com.fasterxml.jackson.annotation.JsonProperty

data class Suggestion(
    @JsonProperty("address")
    val address: String = "",
    @JsonProperty("id")
    val id: String = "",
    @JsonProperty("url")
    val url: String = ""
)
