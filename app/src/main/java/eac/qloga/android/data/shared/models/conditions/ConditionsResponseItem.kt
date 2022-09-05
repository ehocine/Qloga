package eac.qloga.android.data.shared.models.conditions


import com.fasterxml.jackson.annotation.JsonProperty

data class ConditionsResponseItem(
    @JsonProperty("descr")
    val descr: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("serviceCatId")
    val serviceCatId: Int,
    @JsonProperty("sortOrder")
    val sortOrder: Int
)