package eac.qloga.android.data.shared.models.faq

import com.google.gson.annotations.SerializedName

data class FaqGroup(
    @SerializedName("name")
    val name: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("questions")
    val questions: ArrayList<FaQuestion>
)