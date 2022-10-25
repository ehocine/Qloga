package eac.qloga.android.data.p4p.lookups

import eac.qloga.android.data.shared.models.categories.CategoriesResponse
import eac.qloga.android.data.shared.models.conditions.ConditionsResponse
import eac.qloga.android.data.shared.models.faq.FAQResponse
import eac.qloga.android.data.shared.models.faq.FaqGroup
import eac.qloga.android.data.shared.models.q_services.QServicesResponse
import eac.qloga.android.data.shared.models.responses.CountryResponse
import eac.qloga.android.data.shared.models.responses.LanguageResponse
import eac.qloga.bare.dto.lookups.Country
import retrofit2.Response
import retrofit2.http.GET

interface LookupsApi {

    @GET("/lookups/p4p/categories.json")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("lookups/p4p/conditions.json")
    suspend fun getConditions(): Response<ConditionsResponse>

    @GET("lookups/p4p/services.json")
    suspend fun getServices(): Response<QServicesResponse>

    @GET("help/faq.json")
    suspend fun getFaQuestions(): Response<FAQResponse>

    @GET("lookups/qbe/countries.json")
    suspend fun getCountries(): Response<CountryResponse>

    @GET("lookups/qbe/langs.json")
    suspend fun getLanguages(): Response<LanguageResponse>

}