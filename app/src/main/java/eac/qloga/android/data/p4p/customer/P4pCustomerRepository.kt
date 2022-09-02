package eac.qloga.android.data.p4p.customer

import eac.qloga.android.data.shared.utils.listToString
import eac.qloga.android.data.shared.utils.toJsonString
import eac.qloga.p4p.core.dto.OrderFieldsSelector
import eac.qloga.p4p.core.dto.RqFieldsSelector
import eac.qloga.p4p.cst.dto.Customer
import eac.qloga.p4p.order.dto.Order
import eac.qloga.p4p.order.dto.OrderReview
import eac.qloga.p4p.order.dto.VisitActionData
import eac.qloga.p4p.order.enums.CstActionEnum
import eac.qloga.p4p.prv.enums.PrvFields
import eac.qloga.p4p.rq.dto.Rq
import eac.qloga.p4p.rq.dto.RqService
import eac.qloga.p4p.rq.enums.RqAction
import eac.qloga.p4p.search_filters.CstRequestSearchFilter
import eac.qloga.p4p.search_filters.OrderFilter
import eac.qloga.p4p.search_filters.PrvSearchFilter
import javax.inject.Inject

class P4pCustomerRepository @Inject constructor(private val apiService: P4pCustomerApi) {
    //Customer
    suspend fun create() = apiService.create()
    suspend fun get() = apiService.get()
    suspend fun update(customer: Customer) = apiService.update(customer)
    suspend fun delete() = apiService.delete()
    suspend fun getReviews() = apiService.getReviews()
    suspend fun getPreview() = apiService.getPreview()

    suspend fun addFavPrv(prvid: Long) = apiService.addFavPrv(prvid)
    suspend fun updateFavPrvs(favPrvs: List<Long>) = apiService.updateFavPrvs(favPrvs)
    suspend fun getFavPrvs(fields: List<PrvFields>) = apiService.getFavPrvs(fields.listToString())

    suspend fun deleteFavPrv(prvid: Long) = apiService.deleteFavPrv(prvid)
    suspend fun getOrderList(page: Long, psize: Long, filter: OrderFilter?, selector: OrderFieldsSelector?) = apiService.getOrderList(page, psize, filter?.toJsonString(), selector?.toJsonString())

    suspend fun workWithOrder(bodyParams: Order, action: CstActionEnum, note: String, adata: OrderReview?) = apiService.workWithOrder(bodyParams, action, note, adata?.toJsonString())
    suspend fun workWithOrder(bodyParams: Order, action: CstActionEnum, note: String, adata: VisitActionData?) = apiService.workWithOrder(bodyParams, action, note, adata?.toJsonString())
    suspend fun workWithOrder(bodyParams: Order, action: CstActionEnum, note: String) = apiService.workWithOrder(bodyParams, action, note,null)

    suspend fun getOrder(orderId: Long) = apiService.getOrder(orderId)
    suspend fun getRequestList(page: Long, psize: Long, filter: CstRequestSearchFilter?, selector: RqFieldsSelector?) = apiService.getRequestList(page, psize,filter?.toJsonString(), selector?.toJsonString())
    suspend fun workWithRequest(bodyParam: Rq, action: RqAction, note: String) = apiService.workWithRequest(bodyParam,action,note )
    suspend fun deleteRequest(requestId: Long ) = apiService.deleteRequest(requestId)
    suspend fun getRequestById(requestId: Long ) = apiService.getRequestById(requestId)

    suspend fun getRequestServices(requestId: Long ) = apiService.getRequestServices(requestId)
    suspend fun addServiceRequest(requestId: Long,rqService: List<RqService>) = apiService.addServiceRequest(requestId,rqService)
    suspend fun delRequestService(requestId: Long,rqService: List<RqService>) = apiService.delRequestService(requestId,rqService)
    suspend fun updRequestService(requestId: Long,rqService: List<RqService>) = apiService.updRequestService(requestId,rqService)

    suspend fun getProviders(page: Long, psize: Long, filter: PrvSearchFilter?, fields: List<PrvFields>?) = apiService.getProviders(page, psize, filter?.toJsonString(),fields?.listToString())
    suspend fun getProviderInfo(prvId:Long) = apiService.getProviderInfo(prvId)
    suspend fun getProviderReviews(prvId:Long) = apiService.getProviderReviews(prvId)

}