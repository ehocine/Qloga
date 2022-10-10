package eac.qloga.android.data.shared.models

import com.google.gson.annotations.SerializedName

data class FaQuestion(
    @SerializedName("question")
    val question: String?,

    @SerializedName("answer")
    val answer: String?
)