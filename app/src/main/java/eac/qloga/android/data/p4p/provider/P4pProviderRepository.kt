package eac.qloga.android.data.p4p.provider

import eac.qloga.android.data.shared.utils.listToString
import eac.qloga.android.data.shared.utils.toJsonString
import eac.qloga.android.data.shared.utils.toJsonStringWithDate
import javax.inject.Inject
import eac.qloga.p4p.core.dto.AssignFieldsSelector
import eac.qloga.p4p.core.dto.OrderFieldsSelector
import eac.qloga.p4p.core.dto.RqFieldsSelector
import eac.qloga.p4p.order.dto.*
import eac.qloga.p4p.order.enums.PrvActionEnum
import eac.qloga.p4p.prv.dto.Provider
import eac.qloga.p4p.prv.dto.ProviderService
import eac.qloga.p4p.prv.dto.ProviderServiceCondition
import eac.qloga.p4p.prv.enums.PrvFields
import eac.qloga.p4p.search_filters.AssignSearchFilter
import eac.qloga.p4p.search_filters.OrderFilter
import eac.qloga.p4p.search_filters.PrvRequestSearchFilter

class P4pProviderRepository @Inject constructor(private val apiService: P4pProviderApi){

    //Provider
    suspend fun get(fields: List<PrvFields>) = apiService.get(fields.listToString())
    suspend fun create(body: Provider) = apiService.create(body)
    suspend fun update(body: Provider) = apiService.update(body)
    suspend fun getCustomers(prvId: Long, page: Long, psize: Long, filter: PrvRequestSearchFilter?, selector: RqFieldsSelector?) = apiService.getCustomers(prvId, page, psize, filter?.toJsonString(),selector?.toJsonString())
    suspend fun delete(prvId: Long) = apiService.delete(prvId)
    suspend fun deactivate(prvId: Long) = apiService.deactivate(prvId)
    suspend fun getProvider(prvId: Long) = apiService.getProvider(prvId)
    suspend fun getPrvReviews(prvId: Long) = apiService.getPrvReviews(prvId)
    suspend fun getCustomerInfo(prvId: Long,cstId: Long) = apiService.getCustomerInfo(prvId,cstId)
    suspend fun getPrvRequestById(prvId: Long,rqid: Long) = apiService.getPrvRequestById(prvId,rqid)
    suspend fun getCustomerReviews(prvId: Long,cstId: Long) = apiService.getCustomerReviews(prvId, cstId)
    suspend fun getOrders(prvId: Long, page: Long, psize: Long, filter: OrderFilter?, selector: OrderFieldsSelector?) = apiService.getOrders(prvId, page, psize, filter?.toJsonString(), selector?.toJsonString())

    suspend fun workWithOrder(prvId: Long, requestBody: Order, action: PrvActionEnum, note: String, adata: OrderReview) = apiService.workWithOrder(prvId, requestBody, action, note, adata.toJsonString())
    suspend fun workWithOrder(prvId: Long, requestBody: Order, action: PrvActionEnum, note: String, adata: MeetUp) = apiService.workWithOrder(prvId, requestBody, action, note, adata.toJsonString())
    suspend fun workWithOrder(prvId: Long, requestBody: Order, action: PrvActionEnum, note: String, adata: VisitMeetUp) = apiService.workWithOrder(prvId, requestBody, action, note, adata.toJsonString())
    suspend fun workWithOrder(prvId: Long, requestBody: Order, action: PrvActionEnum, note: String, adata: VisitActionData) = apiService.workWithOrder(prvId, requestBody, action, note, adata.toJsonString())
    suspend fun workWithOrder(prvId: Long, requestBody: Order, action: PrvActionEnum, note: String) = apiService.workWithOrder(prvId, requestBody, action, note,null)


    suspend fun getOrder(prvId: Long,orderId: Long) = apiService.getOrder(prvId, orderId)
    suspend fun getAssigns(prvId: Long, page: Long, psize: Long, filter: AssignSearchFilter?, selector: AssignFieldsSelector?) = apiService.getAssigns(prvId, page, psize, filter?.toJsonStringWithDate(), selector?.toJsonString())
    suspend fun getServices(prvId: Long) = apiService.getServices(prvId)
    suspend fun addServiceConditions(prvId: Long, linkParam: Long,bodyParam: List<ProviderServiceCondition>) = apiService.addServiceConditions(prvId, linkParam, bodyParam)
    suspend fun updateServices(prvId: Long,bodyParam: List<ProviderService>) = apiService.updateServices(prvId, bodyParam)
    suspend fun deleteServiceConditions(prvId: Long,bodyParam: List<ProviderService>) = apiService.deleteServiceConditions(prvId, bodyParam)
    suspend fun getFavCustomers(prvId: Long) = apiService.getFavCustomers(prvId)
    suspend fun addFavCustomers(prvId: Long,idNewFavsCst: Long) = apiService.addFavCustomers(prvId, idNewFavsCst)
    suspend fun deleteFavCustomers(prvId: Long,cstIdForDel: Long) = apiService.deleteFavCustomers(prvId, cstIdForDel)
    suspend fun updateFavCustomers(prvId: Long,listCstIdForUpdate: List<Long>) = apiService.updateFavCustomers(prvId, listCstIdForUpdate)

}