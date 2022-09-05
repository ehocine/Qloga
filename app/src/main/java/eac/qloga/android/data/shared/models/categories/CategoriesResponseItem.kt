package eac.qloga.android.data.shared.models.categories


import com.fasterxml.jackson.annotation.JsonProperty

data class CategoriesResponseItem(
    @JsonProperty("avatarUrl")
    val avatarUrl: String,
    @JsonProperty("catGroupBgColour")
    val catGroupBgColour: String,
    @JsonProperty("catGroupColour")
    val catGroupColour: String,
    @JsonProperty("catGroupId")
    val catGroupId: Int,
    @JsonProperty("catGroupOrder")
    val catGroupOrder: Int,
    @JsonProperty("descr")
    val descr: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("mapUrl")
    val mapUrl: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("services")
    val services: List<Service>,
    @JsonProperty("sortOrder")
    val sortOrder: Int
)