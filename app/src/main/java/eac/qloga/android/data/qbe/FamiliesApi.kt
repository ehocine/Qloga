package eac.qloga.android.data.qbe


import eac.qloga.bare.dto.RegistrationRequest
import eac.qloga.bare.dto.person.Address
import eac.qloga.bare.dto.person.Family
import eac.qloga.bare.dto.person.Person
import eac.qloga.bare.dto.person.Relative
import eac.qloga.bare.enums.FamilyRole
import retrofit2.Response
import retrofit2.http.*


interface FamiliesApi {

    @GET("qbe/families")
    suspend fun getFamilies(): List<Family>

    @PUT("qbe/families")
    suspend fun update(
        @Body family: Family
    ): Family

    @DELETE("qbe/families")
    suspend fun deleteFamily(
        @Query("id") id: Long
    ): Response<Unit>

    @POST("qbe/families")
    suspend fun add(
        @Body family: Family
    ): Family

    //-----------------------------------------

    @GET("qbe/families/addresses")
    suspend fun getAddresses(
        @Query("fid") fid: Long?
    ): Response<List<Address>>

    @PUT("qbe/families/addresses")
    suspend fun updateAddress(
        @Body address: Address
    ): Response<Address>

    @DELETE("qbe/families/addresses/{aid}")
    suspend fun deleteAddress(
        @Path("aid") aid: Long
    ): Response<Unit>

    @POST("qbe/families/addresses")
    suspend fun addAddress(
        @Body address: Address
    ): Response<Address>

    //0
    @PATCH("qbe/families/addresses/{aid}")
    suspend fun switchToAddress(
        @Path("aid") aid: Long
    ): Response<Unit>

    //-----------------------------------------

    @GET("qbe/families/relatives")
    suspend fun getFamilyMembers(
    ): List<Person>

    //0
    @PATCH("qbe/families/relatives")
    suspend fun updateRelative(
        @Query("kin") kin: FamilyRole,
        @Query("startDate") startDate: String,
        @Body person: Person
    ): Person

    @POST("qbe/families/relatives")
    suspend fun registerRelative(
        @Query("kin") kin: FamilyRole,
        @Query("startDate") startDate: String,
        @Body registrationRequest: RegistrationRequest
    ): Person

    @DELETE("qbe/families/relatives/{pid}")
    suspend fun deleteRelative(
        @Path("pid") aid: Long
    ): Response<Unit>

    @GET("qbe/families/relatives/{pid}")
    suspend fun getPersonInfo(
        @Path("pid") pid: Long
    ): Person

    @GET("qbe/families/relatives/kin")
    suspend fun getRelatives(
    ): List<Relative>

    @DELETE("qbe/families/relatives/kin/{pid}")
    suspend fun deleteKinWithPerson(
        @Path("pid") pid: Long
    ): Response<Unit>

}