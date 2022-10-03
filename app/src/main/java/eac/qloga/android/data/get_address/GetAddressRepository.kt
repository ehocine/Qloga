package eac.qloga.android.data.get_address

import eac.qloga.android.di.GetAddressApiService
import javax.inject.Inject

class GetAddressRepository(@GetAddressApiService private val getAddressApiService: GetAddressApi) {
    suspend fun getAddressSuggestions(apikey: String, term: String) =
        getAddressApiService.getAddressSuggestions(term = term, apiKey = apikey)

    suspend fun getFullAddress(apikey: String, id: String) =
        getAddressApiService.getFullAddress(id = id, apiKey = apikey)
}