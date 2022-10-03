package eac.qloga.android.data.qbe

import eac.qloga.android.data.shared.models.VrfPhone
import eac.qloga.android.di.QLOGAApiService
import eac.qloga.bare.dto.person.Person
import eac.qloga.bare.enums.SettingsScope

class PlatformRepository(@QLOGAApiService private val apiService: PlatformApi) {
    //platform
    suspend fun getSettings(scope: SettingsScope, orgId: Long?, set: Boolean?) =
        apiService.getSettings(scope, orgId, set)

    suspend fun setSettings(settings: HashMap<String, String>, scope: SettingsScope, orgId: Long?) =
        apiService.setSettings(settings, scope, orgId)

    suspend fun getUserProfile() = apiService.getUserProfile()
    suspend fun deleteUser() = apiService.deleteUser()
    suspend fun singOutUser() = apiService.singOutUser()
    suspend fun updateUser(person: Person) = apiService.updateUser(person)
    suspend fun getPublicProfile(pid: Long) = apiService.getPublicProfile(pid)
    suspend fun getFamilyProfile() = apiService.getFamilyProfile()
    suspend fun deleteVerification(id: Long) = apiService.deleteVerification(id)
    suspend fun getVerifications() = apiService.getVerifications()
    suspend fun getPhoneVerificationCode() = apiService.getPhoneVerificationCode()
    suspend fun verifyPhone(code: VrfPhone) = apiService.verifyPhone(code)
}
