package eac.qloga.android.features.p4p.shared.utils

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(

    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("is_selected")
    val isSelected: Boolean
)