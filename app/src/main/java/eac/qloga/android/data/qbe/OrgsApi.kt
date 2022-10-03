package eac.qloga.android.data.qbe

import eac.qloga.bare.dto.OffTime
import eac.qloga.bare.dto.Org
import eac.qloga.bare.dto.WorkHours
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface OrgsApi {

    @GET("qbe/orgs")
    suspend fun getOrgs(): Response<List<Org>>

    @PUT("qbe/orgs")
    suspend fun update(
        @Body org: Org
    ): Org

    @POST("qbe/orgs")
    suspend fun add(
        @Body org: Org
    ): Org

    @DELETE("qbe/orgs/{oid}")
    suspend fun deleteOrg(
        @Path("oid") oid: Long
    ): Response<Unit>

    @GET("qbe/orgs/{oid}")
    suspend fun getOrgById(
        @Path("oid") oid: Long
    ): Org

    @POST("qbe/orgs/{oid}/off-time")
    suspend fun setOffTimes(
        @Path("oid") oid: Long,
        @Path("times") times: List<OffTime>
    ): Response<Unit>

    @PUT("qbe/orgs/{oid}/off-time")
    suspend fun addOffTime(
        @Path("oid") oid: Long,
        @Path("record") record: OffTime,
    ): Response<Unit>

    @DELETE("qbe/orgs/{oid}")
    suspend fun deleteOffTime(
        @Path("oid") oid: Long,
        @Path("rid") rid: Long,
    ): Response<Unit>

    @POST("qbe/orgs/{oid}/work-hours")
    suspend fun setWorkingHours(
        @Path("oid") oid: Long,
        @Path("hrs") hrs: List<WorkHours>
    ): Response<Unit>

    //0
    @GET("qbe/orgs/{oid}/phone")
    suspend fun getPhoneVerificationCode(
        @Query("oid") oid: Long
    ): Long

    //0
    @PUT("qbe/orgs/{oid}/phone")
    suspend fun verifyPhone(
        @Path("oid") oid: Long,
        @Path("code") code: Long,
    ): Response<Unit>
}