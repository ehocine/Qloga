package eac.qloga.android.data.qbe


class MailsRepository(private val apiService: MailsApi){
    //mails
    suspend fun searchEmails(page:Int,psize:Int,filter: String) = apiService.searchEmails(page, psize, filter)
    suspend fun getTextBody(eid: Long,html: Boolean) = apiService.getTextBody(eid,html)
}