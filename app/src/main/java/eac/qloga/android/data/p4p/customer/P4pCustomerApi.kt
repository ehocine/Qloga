package eac.qloga.android.data.p4p.customer

import eac.qloga.android.data.shared.models.Page
import eac.qloga.p4p.cst.dto.CstPublicProfile
import eac.qloga.p4p.cst.dto.Customer
import eac.qloga.p4p.cst.dto.PrvSearchResult
import eac.qloga.p4p.order.dto.Order
import eac.qloga.p4p.order.dto.OrderActionResult
import eac.qloga.p4p.order.dto.OrderReview
import eac.qloga.p4p.order.enums.CstActionEnum
import eac.qloga.p4p.prv.dto.Provider
import eac.qloga.p4p.rq.dto.Rq
import eac.qloga.p4p.rq.dto.RqActionResult
import eac.qloga.p4p.rq.dto.RqService
import eac.qloga.p4p.rq.enums.RqAction
import retrofit2.Response
import retrofit2.http.*

@JvmSuppressWildcards
interface P4pCustomerApi {

    @POST("p4p/cst")
    suspend fun create(
    ): Response<Customer>

    @GET("p4p/cst")
    suspend fun get(): Response<Customer>

    @PATCH("p4p/cst")
    suspend fun update(
        @Body customer: Customer
    ): Customer

    @DELETE("p4p/cst")
    suspend fun delete(
    ): Response<Unit>

    @GET("p4p/cst/reviews")
    suspend fun getReviews(): List<OrderReview>

    @GET("p4p/cst/pubprofile")
    suspend fun getPreview(): CstPublicProfile

    @POST("p4p/cst/favs")
    suspend fun addFavPrv(
        @Query("prvid") prvid: Long
    ): Response<Unit>

    @PUT("p4p/cst/favs")
    suspend fun updateFavPrvs(
        @Body favPrvs: List<Long>
    ): Response<Unit>

    @GET("p4p/cst/favs")
    suspend fun getFavPrvs(
        @Query("fields") fields: List<String>?
    ): Response<List<Provider>>

    @DELETE("p4p/cst/favs")
    suspend fun deleteFavPrv(
        @Query("prvid") prvid: Long
    ): Response<Unit>

    @GET("p4p/cst/orders")
    suspend fun getOrderList(
        @Query("page") page: Long,
        @Query("psize") psize: Long,
        @Query("filter") filter: String?,
        @Query("selector") selector: String?
    ): Page<Order>

    @PATCH("p4p/cst/orders")
    suspend fun workWithOrder(
        @Body bodyParams: Order,
        @Query("action") action: CstActionEnum = CstActionEnum.PROPOSE,
        @Query("note") note: String,
        @Query("adata") adata: String?
    ): OrderActionResult

    @GET("p4p/cst/orders/{orderId}")
    suspend fun getOrder(
        @Path("orderId") orderId: Long
    ): Order

    @GET("p4p/cst/requests")
    suspend fun getRequestList(
        @Query("page") page: Long,
        @Query("psize") psize: Long,
        @Query("filter") filter: String?,
        @Query("selector") selector: String?
    ): Page<Rq>

    @PATCH("p4p/cst/requests")
    suspend fun workWithRequest(
        @Body bodyParam: Rq,
        @Query("action") action: RqAction = RqAction.CREATE,
        @Query("note") note: String = ""
    ): RqActionResult


    //=
    @DELETE("p4p/cst/requests")
    suspend fun deleteRequest(
        @Query("requestId") requestId: Long
    ): Rq

    @GET("p4p/cst/requests/{requestId}")
    suspend fun getRequestById(
        @Path("requestId") requestId: Long
    ): Rq

    @GET("p4p/cst/requests/{requestId}/services")
    suspend fun getRequestServices(
        @Path("requestId") requestId: Long
    ): List<RqService>

    @POST("p4p/cst/requests/{requestId}/services")
    suspend fun addServiceRequest(
        @Path("requestId") requestId: Long,
        @Body rqService: List<RqService>
    ): List<RqService>

    //=
    @DELETE("p4p/cst/requests/{requestId}/services")
    suspend fun delRequestService(
        @Path("requestId") requestId: Long,
        @Body rqService: List<RqService>
    ): Response<Unit>

    @PATCH("p4p/cst/reviews/{requestId}/services")
    suspend fun updRequestService(
        @Path("requestId") requestId: Long,
        @Body bodyParam: List<RqService>
    ): List<RqService>

    @GET("p4p/cst/providers")
    suspend fun getProviders(
        @Query("page") page: Long,
        @Query("psize") psize: Long,
        @Query("filter") filter: String?,
        @Query("fields") fields: List<String>?
    ): Page<PrvSearchResult>

    @GET("p4p/cst/providers/{prvId}")
    suspend fun getProviderInfo(
        @Path("prvId") prvId: Long
    ): Response<Provider>

    @GET("p4p/cst/providers/{prvId}/reviews")
    suspend fun getProviderReviews(
        @Path("prvId") prvId: Long
    ): Response<List<OrderReview>>

}