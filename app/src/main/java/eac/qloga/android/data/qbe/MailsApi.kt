package eac.qloga.android.data.qbe


import eac.qloga.bare.dto.person.Person
import retrofit2.http.*

interface MailsApi {

    @GET("qbe/mails")
    suspend fun searchEmails(
        @Query("page") page: Int,
        @Query("psize") psize: Int,
        @Query("filter") filter: String
    ): List<Person>

    @GET("qbe/mails")
    suspend fun getTextBody(
        @Query("eid") eid: Long,
        @Query("html") html: Boolean
    ): List<Person>

}
