package eac.qloga.android.data.qbe

import android.os.Build
import androidx.annotation.RequiresApi
import eac.qloga.android.data.shared.utils.dateToString
import eac.qloga.bare.dto.RegistrationRequest
import eac.qloga.bare.dto.person.Address
import eac.qloga.bare.dto.person.Family
import eac.qloga.bare.dto.person.Person
import eac.qloga.bare.enums.FamilyRole
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)

class FamilliesRepository(private val apiService: FamiliesApi){
    //Families
    suspend fun getFamilies() = apiService.getFamilies()
    suspend fun update(family: Family) = apiService.update(family)
    suspend fun deleteFamily(id:Long) = apiService.deleteFamily(id)
    suspend fun add(family: Family) = apiService.add( family)

    //FamiliesAdress
    suspend fun getAddresses(fid:Long?) = apiService.getAddresses(fid)
    suspend fun updateAddress(address: Address) = apiService.updateAddress(address)
    suspend fun deleteAddress(id:Long) = apiService.deleteAddress(id)
    suspend fun addAddress(address: Address) = apiService.addAddress( address)
    suspend fun switchToAddress(aid: Long) = apiService.switchToAddress(aid)
    suspend fun getFamilyMembers() = apiService.getFamilyMembers()
    suspend fun updateRelative(kin: FamilyRole, startDate: ZonedDateTime, person: Person) = apiService.updateRelative(kin,startDate.dateToString(),person)
    suspend fun registerRelative(kin: FamilyRole, startDate: ZonedDateTime, registrationRequest: RegistrationRequest) = apiService.registerRelative(kin,startDate.dateToString(),registrationRequest)
    suspend fun deleteRelative(id:Long) = apiService.deleteRelative(id)
    suspend fun getPersonInfo(pid: Long) = apiService.getPersonInfo(pid)
    suspend fun getRelatives() = apiService.getRelatives()
    suspend fun deleteKinWithPerson(pid: Long) = apiService.deleteKinWithPerson(pid)
}