package eac.qloga.android.data.get_address

import eac.qloga.android.data.shared.models.FullAddress
import eac.qloga.android.data.shared.models.address_suggestions.AddressSuggestionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetAddressApi {

    @GET("/autocomplete/{term}")
    suspend fun getAddressSuggestions(
        @Path("term") term: String,
        @Query("api-key") apiKey: String
    ): Response<AddressSuggestionsResponse>

    @GET("/get/{id}")
    suspend fun getFullAddress(
        @Path("id") id: String,
        @Query("api-key") apiKey: String
    ): Response<FullAddress>

}