package eac.qloga.android.data.p4p.lookups

import eac.qloga.android.data.shared.models.categories.CategoriesResponse
import eac.qloga.android.data.shared.models.conditions.ConditionsResponse
import retrofit2.Response
import retrofit2.http.GET

interface LookupsApi {

    @GET("/lookups/p4p/categories.json")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("lookups/p4p/conditions.json")
    suspend fun getConditions(): Response<ConditionsResponse>
}