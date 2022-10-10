package eac.qloga.android.features.p4p.shared.viewmodels

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.core.shared.viewmodels.SettingsViewModel
import eac.qloga.android.data.get_address.GetAddressRepository
import eac.qloga.android.data.qbe.FamiliesRepository
import eac.qloga.android.data.shared.models.FullAddress
import eac.qloga.android.data.shared.models.address_suggestions.Suggestion
import eac.qloga.android.features.p4p.showroom.scenes.addAddress.AddressEvent
import eac.qloga.bare.dto.person.Address
import eac.qloga.bare.enums.Parking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressRepository: GetAddressRepository,
    private val settingsViewModel: SettingsViewModel,
    private val familiesRepository: FamiliesRepository
) : ViewModel() {

    companion object {
        const val TAG = "${QTAG}-AddAddressViewModel"
        val selectedAddressSuggestion: MutableState<Suggestion> = mutableStateOf(Suggestion())
        val fullAddress: MutableState<FullAddress> =
            if (ApiViewModel.userProfile.value.contacts?.address != null) mutableStateOf(
                FullAddress(
                    postcode = ApiViewModel.userProfile.value.contacts.address.postcode ?: "",
                    latitude = ApiViewModel.userProfile.value.contacts.address.lat ?: 0.0,
                    longitude = ApiViewModel.userProfile.value.contacts.address.lng ?: 0.0,
                    townOrCity = ApiViewModel.userProfile.value.contacts.address.town ?: "",
                    country = "GB",
                    district = ApiViewModel.userProfile.value.contacts.address.line1 ?: "",
                    county = "",
                    formattedAddress = listOf(
                        ApiViewModel.userProfile.value.contacts.address.shortAddress ?: ""
                    ),
                    line1 = ApiViewModel.userProfile.value.contacts.address.line1 ?: "",
                    line2 = ApiViewModel.userProfile.value.contacts.address.line2 ?: "",
                    line3 = ApiViewModel.userProfile.value.contacts.address.line3 ?: "",
                    line4 = ApiViewModel.userProfile.value.contacts.address.line4 ?: "",
                    locality = "",
                    residential = false,
                    buildingName = "",
                    buildingNumber = "",
                    subBuildingName = "",
                    subBuildingNumber = "",
                    thoroughfare = "",
                )
            ) else mutableStateOf(FullAddress())
        var fullAddressLoadingState = MutableStateFlow(LoadingState.IDLE)
        val addressSaved: MutableState<Boolean> = mutableStateOf(false)
        val searchAddress: MutableState<Boolean> = mutableStateOf(false)
        val selectedAddress: MutableState<Address> =
            mutableStateOf(ApiViewModel.userProfile.value.contacts.address ?: Address())
        var hasAddressSaved :MutableState<Boolean> =  mutableStateOf(ApiViewModel.userProfile.value.contacts.address != null)
    }

    private val _addressInputFieldState =
        mutableStateOf(
            InputFieldState(
                text = selectedAddressSuggestion.value.address,
                hint = "Enter your address or postcode"
            )
        )
    val addressInputFieldState: State<InputFieldState> = _addressInputFieldState


    private val parking = if (ApiViewModel.userProfile.value.contacts?.address != null) {
        when (ApiViewModel.userProfile.value.contacts.address.parking) {
            Parking.PAID -> ParkingType.PaidType
            else -> ParkingType.FreeType
        }
    } else {
        ParkingType.FreeType
    }
    private val _parkingType = mutableStateOf(parking)
    val parkingType: State<ParkingType> = _parkingType

    private val _listOfAddress = mutableStateOf<List<String>>(emptyList())
    val listOfAddress: State<List<String>> = _listOfAddress

    private val _addressOrPostcode = mutableStateOf("")
    val addressOrPostcode: State<String> = _addressOrPostcode

    private val _addressState = mutableStateOf(AddressState())
    val addressState: State<AddressState> = _addressState

    private val _postCodeState =
        mutableStateOf(InputFieldState(text = fullAddress.value.postcode, hint = "Postcode"))
    val postCodeState: State<InputFieldState> = _postCodeState

    private val _line1State =
        mutableStateOf(InputFieldState(text = fullAddress.value.line1, hint = "Line 1"))
    val line1State: State<InputFieldState> = _line1State

    private val _line2State =
        mutableStateOf(InputFieldState(text = fullAddress.value.line2, hint = "Line 2"))
    val line2State: State<InputFieldState> = _line2State

    private val _cityState =
        mutableStateOf(
            InputFieldState(
                text = fullAddress.value.townOrCity,
                hint = "City"
            )
        )
    val cityState: State<InputFieldState> = _cityState

    private val _line3State = mutableStateOf(
        InputFieldState(
            text = fullAddress.value.line3,
            hint = "Line 3"
        )
    )
    val line3State: State<InputFieldState> = _line3State

    private val _selectedMapLatLng = mutableStateOf<LatLng?>(null)
    val selectedMapLatLng: State<LatLng?> = _selectedMapLatLng

    private val _selectedAddress = mutableStateOf("")
    val selectedAddress: State<String> = _selectedAddress

    val addressSuggestionsList: MutableState<List<Suggestion>> = mutableStateOf(listOf())

    var getAddressSuggestionsLoadingState = MutableStateFlow(LoadingState.IDLE)


    fun getAddressSuggestions(term: String) {
        viewModelScope.launch {
            try {
                getAddressSuggestionsLoadingState.emit(LoadingState.LOADING)
                val response = settingsViewModel.settings.value.get("GETADDRESS_KEY")?.let {
                    getAddressRepository.getAddressSuggestions(
                        term = term,
                        apikey = it
                    )
                }
                if (response != null) {
                    if (response.isSuccessful) {
                        addressSuggestionsList.value = response.body()!!.suggestions
                        getAddressSuggestionsLoadingState.emit(LoadingState.LOADED)
                    } else {
                        getAddressSuggestionsLoadingState.emit(LoadingState.ERROR)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getFullAddress(id: String) {
        viewModelScope.launch {
            try {
                viewModelScope.launch {
                    fullAddressLoadingState.emit(LoadingState.LOADING)
                    try {
                        val response = settingsViewModel.settings.value.get("GETADDRESS_KEY")?.let {
                            getAddressRepository.getFullAddress(
                                apikey = it,
                                id = id
                            )
                        }
                        if (response != null) {
                            if (response.isSuccessful) {
                                fullAddress.value = response.body()!!
                                fullAddressLoadingState.emit(LoadingState.LOADED)
                            } else {
                                fullAddressLoadingState.emit(LoadingState.ERROR)
                            }
                        }
                    } catch (e: Exception) {
                        fullAddressLoadingState.emit(LoadingState.ERROR)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("LongLogTag")
    fun onTriggerEvent(event: AddressEvent) {
        try {
            viewModelScope.launch {
                when (event) {
                    is AddressEvent.EnterPostcode -> {
                        _postCodeState.value = _postCodeState.value.copy(text = event.postCode)
                    }
                    is AddressEvent.AddressChosen -> {
                        _addressInputFieldState.value = addressInputFieldState.value.copy(
                            text = event.text
                        )
                    }
                    is AddressEvent.FocusPostcode -> {
                        _postCodeState.value =
                            _postCodeState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterCity -> {
                        _cityState.value = _cityState.value.copy(text = event.city)
                    }
                    is AddressEvent.FocusCity -> {
                        _cityState.value =
                            _cityState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterLine1 -> {
                        _line1State.value = _line1State.value.copy(text = event.line1)
                    }
                    is AddressEvent.FocusLine1 -> {
                        _line1State.value =
                            _line1State.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterLine2 -> {
                        _line2State.value = _line2State.value.copy(text = event.line2)
                    }
                    is AddressEvent.FocusLine2 -> {
                        _line2State.value =
                            _line2State.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterLine3 -> {
                        _line3State.value =
                            _line3State.value.copy(text = event.line3)
                    }
                    is AddressEvent.FocusLine3 -> {
                        _line3State.value =
                            _line3State.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterText -> {
                        _addressInputFieldState.value = addressInputFieldState.value.copy(
                            text = event.text
                        )
                    }

                    is AddressEvent.ClearInput -> {
                        clearInputState()
                    }

                    is AddressEvent.FocusInput -> {
                        _addressInputFieldState.value = addressInputFieldState.value.copy(
                            isFocused = event.focusState.isFocused
                        )
                    }

                    is AddressEvent.Search -> {
                        _addressOrPostcode.value = addressInputFieldState.value.text
                        onSearch()
                    }

                    is AddressEvent.ClickMap -> {
                        onClickMap(event.latLng)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    fun setAddress(address: String){
        _selectedAddress.value = address
    }

    var saveFamilyAddressLoadingState = MutableStateFlow(LoadingState.IDLE)
    val saveAddressResponse: MutableState<Address> = mutableStateOf(Address())

    @SuppressLint("LongLogTag")
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveNewAddress(newAddress: Address) {
        viewModelScope.launch {
            try {
                saveFamilyAddressLoadingState.emit(LoadingState.LOADING)
                val response = familiesRepository.addAddress(newAddress)
                if (response.isSuccessful) {
                    saveAddressResponse.value = response.body()!!
                    saveFamilyAddressLoadingState.emit(LoadingState.LOADED)
                } else {
                    saveFamilyAddressLoadingState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                saveFamilyAddressLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    var switchToAddressLoadingState = MutableStateFlow(LoadingState.IDLE)

    @RequiresApi(Build.VERSION_CODES.O)
    fun switchToAddress(aid: Long) {
        viewModelScope.launch {
            try {
                switchToAddressLoadingState.emit(LoadingState.LOADING)
                val response = familiesRepository.switchToAddress(aid)
                if (response.isSuccessful) {
                    switchToAddressLoadingState.emit(LoadingState.LOADED)
                } else {
                    switchToAddressLoadingState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                switchToAddressLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    var updateAddressLoadingState = MutableStateFlow(LoadingState.IDLE)

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAddress(address: Address) {

        viewModelScope.launch {
            try {
                updateAddressLoadingState.emit(LoadingState.LOADING)
                val response = familiesRepository.updateAddress(address)
                if (response.isSuccessful) {
                    saveAddressResponse.value = response.body()!!
                    updateAddressLoadingState.emit(LoadingState.LOADED)
                } else {
                    updateAddressLoadingState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updateAddressLoadingState.emit(LoadingState.ERROR)
            }
        }

    }

    private fun onClickMap(latLng: LatLng) {
        _selectedMapLatLng.value = latLng
    }

    private fun onSearch() {
        _addressInputFieldState.value = addressInputFieldState.value.copy(text = "")
    }

    fun onClickParkingType(parkingType: ParkingType) {
        _parkingType.value = parkingType
    }

    private fun clearInputState() {
        _addressInputFieldState.value = addressInputFieldState.value.copy(
            text = ""
        )
    }
}