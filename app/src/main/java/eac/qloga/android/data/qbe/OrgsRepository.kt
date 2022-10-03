package eac.qloga.android.data.qbe


import eac.qloga.android.di.QLOGAApiService
import eac.qloga.bare.dto.OffTime
import eac.qloga.bare.dto.Org
import eac.qloga.bare.dto.WorkHours
import javax.inject.Inject

class OrgsRepository(@QLOGAApiService private val apiService: OrgsApi) {
    suspend fun getOrgs() = apiService.getOrgs()
    suspend fun update(org: Org) = apiService.update(org)
    suspend fun add(org: Org) = apiService.add(org)
    suspend fun deleteOrg(oid: Long) = apiService.deleteOrg(oid)
    suspend fun getOrgById(oid: Long) = apiService.getOrgById(oid)
    suspend fun setOffTimes(oid: Long, times: List<OffTime>) = apiService.setOffTimes(oid, times)
    suspend fun addOffTime(oid: Long, record: OffTime) = apiService.addOffTime(oid, record)
    suspend fun deleteOffTime(oid: Long, rid: Long) = apiService.deleteOffTime(oid, rid)
    suspend fun setWorkingHours(oid: Long, hrs: List<WorkHours>) =
        apiService.setWorkingHours(oid, hrs)

    suspend fun getPhoneVerificationCode(oid: Long) = apiService.getPhoneVerificationCode(oid)
    suspend fun verifyPhone(oid: Long, code: Long) = apiService.verifyPhone(oid, code)
}