package eac.qloga.android.features.p4p.showroom.shared.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
            if (ApiViewModel.userProfile.value.contacts.address != null) mutableStateOf(
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
    }

    private val _addressInputFieldState =
        mutableStateOf(
            InputFieldState(
                text = selectedAddressSuggestion.value.address,
                hint = "Enter your address or postcode"
            )
        )
    val addressInputFieldState: State<InputFieldState> = _addressInputFieldState


    private val parking = if (ApiViewModel.userProfile.value.contacts.address != null) {
        when (ApiViewModel.userProfile.value.contacts.address.parking) {
            Parking.PAID -> ParkingType.PaidType
            else -> ParkingType.FreeType
        }
    }else{
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

    private val _townState =
        mutableStateOf(InputFieldState(text = fullAddress.value.townOrCity, hint = "Town"))
    val townState: State<InputFieldState> = _townState

    private val _streetState =
        mutableStateOf(InputFieldState(text = fullAddress.value.district, hint = "Street"))
    val streetState: State<InputFieldState> = _streetState

    private val _buildingState =
        mutableStateOf(
            InputFieldState(
                text = fullAddress.value.buildingNumber,
                hint = "Building"
            )
        )
    val buildingState: State<InputFieldState> = _buildingState

    private val _apartmentsState = mutableStateOf(
        InputFieldState(
            text = fullAddress.value.subBuildingNumber,
            hint = "Apartments"
        )
    )
    val apartmentsState: State<InputFieldState> = _apartmentsState

    private val _selectedMapLatLng = mutableStateOf<LatLng?>(null)
    val selectedMapLatLng: State<LatLng?> = _selectedMapLatLng

    private val _selectedAddress = mutableStateOf("")
    val selectedAddress: State<String> = _selectedAddress

    init {
        getAddresses()
        setAddressState()
    }

    val addressSuggestionsList: MutableState<List<Suggestion>> = mutableStateOf(listOf())

    var getAddressSuggestionsLoadingState = MutableStateFlow(LoadingState.IDLE)

    var fullAddressLoadingState = MutableStateFlow(LoadingState.IDLE)

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
                    is AddressEvent.EnterTown -> {
                        _townState.value = _townState.value.copy(text = event.town)
                    }
                    is AddressEvent.FocusTown -> {
                        _townState.value =
                            _townState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterStreet -> {
                        _streetState.value = _streetState.value.copy(text = event.street)
                    }
                    is AddressEvent.FocusStreet -> {
                        _streetState.value =
                            _streetState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterBuilding -> {
                        _buildingState.value = _buildingState.value.copy(text = event.building)
                    }
                    is AddressEvent.FocusBuilding -> {
                        _buildingState.value =
                            _buildingState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterApartments -> {
                        _apartmentsState.value =
                            _apartmentsState.value.copy(text = event.apartments)
                    }
                    is AddressEvent.FocusApartments -> {
                        _apartmentsState.value =
                            _apartmentsState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterText -> {
                        _addressInputFieldState.value = addressInputFieldState.value.copy(
                            text = event.text
                        )
                        getAddresses()
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

    var saveFamilyAddressLoadingState = MutableStateFlow(LoadingState.IDLE)
    val saveAddressResponse: MutableState<Address> = mutableStateOf(Address())

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


        val addrList = ArrayList<String>(_listOfAddress.value)
        Log.d(TAG, "onSaveNewAddress: ${_addressInputFieldState.value.text}")
        addrList.add(_addressInputFieldState.value.text)
        Log.d(TAG, "onSaveNewAddress: $addrList")
        _listOfAddress.value = addrList
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

    private fun onClickMap(latLng: LatLng) {
        _selectedMapLatLng.value = latLng
        //parse address using geo coding here
        setAddressState()
    }

    private fun onSearch() {
        _addressInputFieldState.value = addressInputFieldState.value.copy(text = "")
    }

    fun onClickParkingType(parkingType: ParkingType) {
        _parkingType.value = parkingType
    }

    fun setAddress(address: String) {
        _selectedAddress.value = address
    }

    private fun getAddresses() {
        _listOfAddress.value = listOf(
            "09 Princes Street, Edinburgh, GB",
            "3 Lupus St, Pimlico, London, GB",
            "Barnett House 53, Fountain Street, Manchester, GB",
            "30 Cloth Market, Merchant House, Newcastle upon Tyne, GB",
            "05 West George Street, Glasgow, GB",
            "54 George Street, Edinburgh, GB",
            "St Martin-in-the-Fields Trafalgar Square, London, GB",
            "14 St Mary's Pl, Newcastle upon Tyne, GB"
        )
        _selectedAddress.value = _listOfAddress.value[0]
    }

    fun setAddressState() {
        _addressState.value = addressState.value.copy(
            postCode = fullAddress.value.postcode,
            town = fullAddress.value.townOrCity,
            street = fullAddress.value.district,
            building = fullAddress.value.buildingNumber,
            apartments = fullAddress.value.subBuildingNumber
        )
    }

    private fun clearInputState() {
        _addressInputFieldState.value = addressInputFieldState.value.copy(
            text = ""
        )
    }

    fun clearAddressState() {
        _addressState.value = AddressState()
        clearInputState()
    }
}