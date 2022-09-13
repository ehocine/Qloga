package eac.qloga.android.data.shared.models.address_suggestions

import com.fasterxml.jackson.annotation.JsonProperty

data class AddressSuggestionsResponse(
    @JsonProperty("suggestions")
    val suggestions: List<Suggestion>
)
