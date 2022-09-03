package eac.qloga.android.data.qbe

import eac.qloga.bare.dto.Verification
import eac.qloga.bare.dto.person.Family
import eac.qloga.bare.dto.person.Person
import eac.qloga.bare.dto.person.PersonPublicProfile
import eac.qloga.bare.enums.SettingsScope
import retrofit2.Response
import retrofit2.http.*

interface PlatformApi {


    @GET("qbe/settings")
    suspend fun getSettings(
        @Query("scope") scope: SettingsScope,
        @Query("orgId") orgId: Long?,
        @Query("set") set: Boolean?
    ): Response<HashMap<String, String>>

    @PUT("qbe/settings")
    suspend fun setSettings(
        @Body settings: HashMap<String, String>,
        @Query("scope") scope: SettingsScope,
        @Query("orgId") orgId: Long?
    ): Response<Unit>

    @GET("qbe/user")
    suspend fun getUserProfile(
    ): Response<Person>

    @DELETE("qbe/user")
    suspend fun deleteUser(
    ): Response<Unit>

    @PATCH("qbe/user")
    suspend fun singOutUser(
    ): Response<Unit>

    @PUT("qbe/user")
    suspend fun updateUser(
        @Body person: Person
    ): Person

    @GET("qbe/user/{pid}")
    suspend fun getPublicProfile(
        @Path("pid") pid: Long
    ): PersonPublicProfile

    @GET("qbe/user/family")
    suspend fun getFamilyProfile(
    ): Family

    @DELETE("qbe/user/vrfs")
    suspend fun deleteVerification(
        @Query("id") id: Long
    ): Response<Unit>

    @GET("qbe/user/vrfs")
    suspend fun getVerifications(
    ): ArrayList<Verification>

    @GET("qbe/user/phone")
    suspend fun getPhoneVerificationCode(
    ): Long

    @PUT("qbe/user/phone")
    suspend fun verifyPhone(
        @Body code: Long
    ): Object

}