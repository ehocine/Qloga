package eac.qloga.android.features.p4p.shared.viewmodels

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.QlogaApplication
import eac.qloga.android.R
import eac.qloga.android.core.shared.utils.CountryCode
import eac.qloga.android.core.shared.utils.CountryCodes
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.QTAG
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.bare.dto.person.Address
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@SuppressLint("LongLogTag")
@HiltViewModel
class EnrollmentViewModel @Inject constructor(
    private val application: QlogaApplication
): ViewModel(){

    companion object {
        const val TAG = "${QTAG}-EnrollmentViewModel"
    }
    private val _enrollmentType = mutableStateOf<EnrollmentType?>(null)
    val enrollmentType: State<EnrollmentType?> = _enrollmentType

    private val _numberFieldState = mutableStateOf(InputFieldState(hint = "Enter you number"))
    val numberFieldState: State<InputFieldState> = _numberFieldState

    private val _codeState = mutableStateOf(InputFieldState())
    val codeState: State<InputFieldState> = _codeState

    private val _addressFieldState = mutableStateOf(InputFieldState(hint = "Enter postcode or address"))
    val addressFieldState: State<InputFieldState> = _addressFieldState

    private val _selectedCountryCode = mutableStateOf(CountryCode("Nepal","+977","NP"))
    val selectedCountryCode: State<CountryCode> = _selectedCountryCode

    private val _isCodeSent = mutableStateOf(false)
    val isCodeSent: State<Boolean> = _isCodeSent

    private val _isCheckTermsConditions = mutableStateOf(false)
    val isCheckTermsConditions: State<Boolean> = _isCheckTermsConditions

    private val _listOfAddress = mutableStateOf<List<String>>(emptyList())
    val listOfAddress: State<List<String>> = _listOfAddress

    private val _selectedAddressIndex = mutableStateOf(0)
    val selectedAddressIndex = _selectedAddressIndex

    private val _countryCodes = mutableStateOf<CountryCodes?>(null)
    val countryCodes: State<CountryCodes?> = _countryCodes

    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    private val _userAddress = mutableStateOf<Address?>(null)
    val userAddress: State<Address?> = _userAddress

    init {
        getCountryCodes()
        getAddresses()
    }

    fun onTriggerEvent(event: EnrollmentEvent){
        try {
            viewModelScope.launch {
                when(event){
                    is EnrollmentEvent.EnterCode -> { enterCode(event.code)}
                    is EnrollmentEvent.EnterNumber -> { enterNumber(event.number) }
                    is EnrollmentEvent.EnterAddress -> { enterAddress(event.address) }
                    is EnrollmentEvent.FocusAddressInput -> { onFocusAddressInput(event.focusState) }
                    is EnrollmentEvent.FocusCodeInput -> { onFocusCodeInput(event.focusState) }
                    is EnrollmentEvent.FocusNumberInput -> { onFocusNumberInput(event.focusState) }
                    is EnrollmentEvent.SelectCountryCode -> { _selectedCountryCode.value = event.countryCode }
                    is EnrollmentEvent.SendCode -> { sendCode() }
                    is EnrollmentEvent.SubmitCode -> { submitCode() }
                    is EnrollmentEvent.ToggleCheckTermsConditions -> { _isCheckTermsConditions.value = !isCheckTermsConditions.value}
                    is EnrollmentEvent.ClickMap -> { _userLocation.value = event.latLng }
                }
            }
        }catch (e: Exception){
            Log.d(TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    fun parseAddressFromLatLng(
        latLng: LatLng? = null,
        address: String? = null
    ){
        try {
            val geocoder = Geocoder(application, Locale.getDefault())

            val addresses  = if(latLng != null ) {
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            }else{
                geocoder.getFromLocationName(address, 1)
            }

            if(addresses != null && addresses.size > 0){
                _userAddress.value = Address().apply {
                    line1 = addresses[0].getAddressLine(0)
                    town = addresses[0].locality
                    country = addresses[0].countryName
                    postcode = addresses[0].postalCode
                    // knownName = addresses[0].featureName
                    // street = addresses[0].thoroughfare
                    // countryCode = addresses[0].countryCode
                }
            }
        }catch (e: Exception){
            Log.e(TAG, "parseAddressFromLatLng: ${e.printStackTrace()}")
        }
    }

    fun setEnrollmentType(type: EnrollmentType){
        _enrollmentType.value = type
    }

    private fun enterNumber(number: String){
        _numberFieldState.value = numberFieldState.value.copy(
            text = number
        )
    }

    private fun enterCode(code: String){
        _codeState.value = codeState.value.copy(
            text = code
        )
    }

    private fun enterAddress(address: String){
        _addressFieldState.value = addressFieldState.value.copy(
            text = address
        )
    }

    private fun onFocusNumberInput(focusState: FocusState){
        _numberFieldState.value = numberFieldState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusCodeInput(focusState: FocusState){
        _codeState.value = codeState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusAddressInput(focusState: FocusState){
        _addressFieldState.value = addressFieldState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun sendCode(){
        _isCodeSent.value = true
        _numberFieldState.value = numberFieldState.value.copy(
            text = "",
            isFocused = false
        )
    }

    private fun submitCode(){
        _codeState.value = codeState.value.copy(
            text = "",
            isFocused = false
        )
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
    }

    fun onSelectAddressOption(index: Int){
        _selectedAddressIndex.value = index
        _addressFieldState.value = _addressFieldState.value.copy(
            text = _listOfAddress.value[index]
        )
        parseAddressFromLatLng(address = _addressFieldState.value.text)
    }

    private fun getCountryCodes(){
        val countryCodesJson = application.resources.openRawResource(R.raw.country_codes).bufferedReader().use { it.readText() }
        val gson = Gson()
        val countryCodes = gson.fromJson(countryCodesJson, CountryCodes::class.java)
        _countryCodes.value = countryCodes
    }
}