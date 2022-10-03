package eac.qloga.android.data.p4p.provider

import eac.qloga.android.data.shared.models.Page
import eac.qloga.p4p.cst.dto.CstPublicProfile
import eac.qloga.p4p.hres.dto.Assign
import eac.qloga.p4p.order.dto.Order
import eac.qloga.p4p.order.dto.OrderActionResult
import eac.qloga.p4p.order.dto.OrderReview
import eac.qloga.p4p.order.enums.PrvActionEnum
import eac.qloga.p4p.prv.dto.Provider
import eac.qloga.p4p.prv.dto.ProviderService
import eac.qloga.p4p.prv.dto.ProviderServiceCondition
import eac.qloga.p4p.prv.dto.RqSearchResult
import eac.qloga.p4p.rq.dto.Rq
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface P4pProviderApi {

    @GET("p4p/prv")
    suspend fun get(
        @Query("fields") fields: List<String>?
    ): List<Provider>

    //=
    @POST("p4p/prv")
    suspend fun create(
        @Body body: Provider
    ): Response<Provider>

    @PATCH("p4p/prv")
    suspend fun update(
        @Body body: Provider
    ): Provider

    @GET("p4p/prv/{prvId}/customers")
    suspend fun getCustomers(
        @Path("prvId") prvId: Long,
        @Query("page") page: Long,
        @Query("psize") psize: Long,
        @Query("filter") filter: String?,
        @Query("selector") selector: String?
    ): Page<RqSearchResult>

    @DELETE("p4p/prv/{prvId}")
    suspend fun delete(
        @Path("prvId") prvId: Long,
    ): Response<Unit>

    @PATCH("p4p/prv/{prvId}")
    suspend fun deactivate(
        @Path("prvId") prvId: Long,
    ): Response<Unit>

    @GET("p4p/prv/{prvId}")
    suspend fun getProvider(
        @Path("prvId") prvId: Long
    ): Provider

    @GET("p4p/prv/{prvId}/reviews")
    suspend fun getPrvReviews(
        @Path("prvId") prvId: Long
    ): List<OrderReview>

    @GET("p4p/prv/{prvId}/customers/{cstId}")
    suspend fun getCustomerInfo(
        @Path("prvId") prvId: Long,
        @Path("cstId") cstId: Long
    ): CstPublicProfile

    @GET("p4p/prv/{prvId}/requests/{rqid}")
    suspend fun getPrvRequestById(
        @Path("prvId") prvId: Long,
        @Path("rqid") rqid: Long
    ): Rq

    @GET("p4p/prv/{prvId}/customers/{cstId}/reviews")
    suspend fun getCustomerReviews(
        @Path("prvId") prvId: Long,
        @Path("cstId") cstId: Long
    ): List<OrderReview>

    @GET("p4p/prv/{prvId}/orders")
    suspend fun getOrders(
        @Path("prvId") prvId: Long,
        @Query("page") page: Long,
        @Query("psize") psize: Long,
        @Query("filter") filter: String?,
        @Query("selector") selector: String?
    ): Page<Order>

    @PATCH("p4p/prv/{prvId}/orders")
    suspend fun workWithOrder(
        @Path("prvId") prvId: Long,
        @Body requestBody: Order,
        @Query("action") action: PrvActionEnum = PrvActionEnum.PROPOSE,
        @Query("note") note: String,
        @Query("adata") adata: String?
    ): OrderActionResult

    @GET("p4p/prv/{prvId}/orders/{orderId}")
    suspend fun getOrder(
        @Path("prvId") prvId: Long,
        @Path("orderId") orderId: Long
    ): Order

    @GET("p4p/prv/{prvId}/assigns")
    suspend fun getAssigns(
        @Path("prvId") prvId: Long,
        @Query("page") page: Long,
        @Query("psize") psize: Long,
        @Query("filter") filter: String?,
        @Query("selector") selector: String?
    ): Page<Assign>

    @GET("p4p/prv/{prvId}/services")
    suspend fun getServices(
        @Path("prvId") prvId: Long
    ): List<ProviderService>

    //=
    @POST("p4p/prv/{prvId}/services")
    suspend fun addServiceConditions(
        @Path("prvId") prvId: Long,
        @Query("prvsid") linkParam: Long,
        @Body bodyParam: List<ProviderServiceCondition>
    ): List<ProviderServiceCondition>


    @PATCH("p4p/prv/{prvId}/services")
    suspend fun updateServices(
        @Path("prvId") prvId: Long,
        @Body bodyParam: List<ProviderService>
    ): List<ProviderService>

    //=
    @DELETE("p4p/prv/{prvId}/services")
    suspend fun deleteServiceConditions(
        @Path("prvId") prvId: Long,
        @Body bodyParam: List<ProviderService>
    ): Response<Unit>

    @GET("p4p/prv/{prvId}/favs")
    suspend fun getFavCustomers(
        @Path("prvId") prvId: Long
    ): List<CstPublicProfile>

    @POST("p4p/prv/{prvId}/favs")
    suspend fun addFavCustomers(
        @Path("prvId") prvId: Long,
        @Query("cstid") idNewFavsCst: Long
    ): Response<Unit>

    @DELETE("p4p/prv/{prvId}/favs")
    suspend fun deleteFavCustomers(
        @Path("prvId") prvId: Long,
        @Query("cstid") cstIdForDel: Long
    ): Response<Unit>

    @PUT("p4p/prv/{prvId}/favs")
    suspend fun updateFavCustomers(
        @Path("prvId") prvId: Long,
        @Body listCstIdForUpdate: List<Long>
    ): List<CstPublicProfile>

}