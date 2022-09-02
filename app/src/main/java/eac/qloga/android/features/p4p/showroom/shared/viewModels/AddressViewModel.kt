package eac.qloga.android.features.p4p.showroom.shared.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.utils.AddressState
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.ParkingType
import eac.qloga.android.features.p4p.showroom.scenes.addAddress.AddressEvent
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(): ViewModel(){

    companion object {
        const val TAG = "AddAddressViewModel"
    }

    private val _addressInputFieldState = mutableStateOf(InputFieldState(hint = "Enter your address or postcode"))
    val addressInputFieldState: State<InputFieldState> = _addressInputFieldState

    private val _parkingType = mutableStateOf<ParkingType>(ParkingType.FreeType)
    val parkingType: State<ParkingType> = _parkingType

    private val _listOfAddress = mutableStateOf<List<String>>(emptyList())
    val listOfAddress: State<List<String>> = _listOfAddress

    private val _addressOrPostcode = mutableStateOf("")
    val addressOrPostcode: State<String> = _addressOrPostcode

    private val _addressState = mutableStateOf(AddressState())
    val addressState: State<AddressState> = _addressState

    private val _postCodeState = mutableStateOf(InputFieldState(hint = "Postcode"))
    val postCodeState: State<InputFieldState> = _postCodeState

    private val _townState = mutableStateOf(InputFieldState(hint = "Town"))
    val townState: State<InputFieldState> = _townState

    private val _streetState = mutableStateOf(InputFieldState(hint = "Street"))
    val streetState: State<InputFieldState> = _streetState

    private val _buildingState = mutableStateOf(InputFieldState(hint = "Building"))
    val buildingState: State<InputFieldState> = _buildingState

    private val _apartmentsState = mutableStateOf(InputFieldState(hint = "Apartments"))
    val apartmentsState: State<InputFieldState> = _apartmentsState

    private val _selectedMapLatLng = mutableStateOf<LatLng?>(null)
    val selectedMapLatLng: State<LatLng?> = _selectedMapLatLng

    private val _selectedAddress = mutableStateOf("")
    val selectedAddress: State<String> = _selectedAddress

    init {
        getAddresses()
        setAddressState()
    }

    fun onTriggerEvent(event: AddressEvent){
        try {
            viewModelScope.launch {
                when(event){
                    is AddressEvent.EnterPostcode -> {
                        _postCodeState.value = _postCodeState.value.copy(text = event.postCode)
                    }
                    is AddressEvent.FocusPostcode -> {
                        _postCodeState.value = _postCodeState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterTown -> {
                        _townState.value = _townState.value.copy(text = event.town)
                    }
                    is AddressEvent.FocusTown -> {
                        _townState.value = _townState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterStreet -> {
                        _streetState.value = _streetState.value.copy(text = event.street)
                    }
                    is AddressEvent.FocusStreet -> {
                        _streetState.value = _streetState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterBuilding -> {
                        _buildingState.value = _buildingState.value.copy(text = event.building)
                    }
                    is AddressEvent.FocusBuilding -> {
                        _buildingState.value = _buildingState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is AddressEvent.EnterApartments -> {
                        _apartmentsState.value = _apartmentsState.value.copy(text = event.apartments)
                    }
                    is AddressEvent.FocusApartments -> {
                        _apartmentsState.value = _apartmentsState.value.copy(isFocused = event.focusState.isFocused)
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

                    is AddressEvent.ClickMap -> { onClickMap(event.latLng)}
                }
            }
        }catch (e: Exception){
            Log.e(TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    fun onSaveNewAddress(){
        val addrList = ArrayList<String>(_listOfAddress.value)
        Log.d(TAG, "onSaveNewAddress: ${_addressInputFieldState.value.text}")
        addrList.add(_addressInputFieldState.value.text)
        Log.d(TAG, "onSaveNewAddress: $addrList")
        _listOfAddress.value = addrList
    }

    private fun onClickMap(latLng: LatLng){
        _selectedMapLatLng.value = latLng
        //parse address using geo coding here
        setAddressState()
    }

    private fun onSearch() {
        _addressInputFieldState.value = addressInputFieldState.value.copy(text = "")
    }

    fun onClickParkingType(parkingType: ParkingType){
        _parkingType.value = parkingType
    }

    fun setAddress(address: String){
        _selectedAddress.value = address
    }

    private fun getAddresses(){
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

    fun setAddressState(){
        _addressState.value = addressState.value.copy(
            postCode = "EH2 2ER",
            town = "Edinburgh",
            street = "Princess Street",
            building = "9",
            apartments = "12"
        )
    }

    private fun clearInputState(){
        _addressInputFieldState.value = addressInputFieldState.value.copy(
            text = ""
        )
    }

    fun clearAddressState(){
        _addressState.value = AddressState()
        clearInputState()
    }
}