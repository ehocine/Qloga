package eac.qloga.android.data.shared.models.faq

import com.google.gson.annotations.SerializedName

data class FaQuestion(
    @SerializedName("q")
    val q: String?,

    @SerializedName("a")
    val a: String?
){
    override fun toString(): String{
        return "$q $a"
    }
}