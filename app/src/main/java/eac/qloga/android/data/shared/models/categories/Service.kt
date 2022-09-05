package eac.qloga.android.data.shared.models.categories


import com.fasterxml.jackson.annotation.JsonProperty

data class Service(
    @JsonProperty("avatarId")
    val avatarId: Int,
    @JsonProperty("avatarUrl")
    val avatarUrl: String,
    @JsonProperty("contractBareHTML")
    val contractBareHTML: String,
    @JsonProperty("contractUrl")
    val contractUrl: String,
    @JsonProperty("descr")
    val descr: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("sortOrder")
    val sortOrder: Int,
    @JsonProperty("timeNorm")
    val timeNorm: Int,
    @JsonProperty("unit")
    val unit: String,
    @JsonProperty("unitDescr")
    val unitDescr: String
)