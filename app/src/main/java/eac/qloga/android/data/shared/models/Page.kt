package eac.qloga.android.data.shared.models

data class Page<out T>(val content: List<T>? = null,
                       val message: String? = null,
                       val empty: Boolean ?= null,
                       val first: Boolean? = null,
                       val last: Boolean? = null,
                       val number: Int? = null,
                       val numberOfElements: Int? = null,
                       val pageable: Any? = null,
                       val size: Int? = null,
                       val sort: Any? = null,
                       val totalElements: Int? = null,
                       val totalPages: Int? = null) {
    companion object {
        fun <T> success(data: List<T>?): Page<T> =
            Page( content = data, message = null)

        fun <T> error(data: List<T>?, message: String): Page<T> =
            Page( content = data, message = message)

        fun <T> loading(data: List<T>?): Page<T> =
            Page( content = data, message = null)
    }
}
