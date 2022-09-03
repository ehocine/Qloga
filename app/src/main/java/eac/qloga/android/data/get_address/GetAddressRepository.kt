package eac.qloga.android.data.get_address

import eac.qloga.android.di.GetAddressApiService
import javax.inject.Inject

class GetAddressRepository @Inject constructor(
    @GetAddressApiService private val getAddressApi: GetAddressApi
) {
    suspend fun getAddressSuggestions(apikey: String, term: String) =
        getAddressApi.getAddressSuggestions(term = term, apiKey = apikey)

    suspend fun getFullAddress(apikey: String, id: String) =
        getAddressApi.getFullAddress(id = id, apiKey = apikey)
}