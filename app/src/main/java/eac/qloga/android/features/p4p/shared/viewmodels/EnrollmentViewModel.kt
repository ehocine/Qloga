package eac.qloga.android.features.p4p.shared.viewmodels

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.QlogaApplication
import eac.qloga.android.R
import eac.qloga.android.core.shared.interactors.GetCountry
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.p4p.customer.P4pCustomerRepository
import eac.qloga.android.data.p4p.lookups.StaticResourcesRepository
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.FamiliesRepository
import eac.qloga.android.data.qbe.PlatformRepository
import eac.qloga.android.data.shared.models.VrfPhone
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent.*
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.bare.dto.Contacts
import eac.qloga.bare.dto.lookups.Country
import eac.qloga.bare.dto.person.Address
import eac.qloga.bare.dto.person.Person
import eac.qloga.p4p.cst.dto.Customer
import eac.qloga.p4p.prv.dto.Provider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@SuppressLint("LongLogTag")
@HiltViewModel
class EnrollmentViewModel @Inject constructor(
    private val application: QlogaApplication,
    private val familiesRepository: FamiliesRepository,
    private val platformRepository: PlatformRepository,
    private val p4pCustomerRepository: P4pCustomerRepository,
    private val p4pProviderRepository: P4pProviderRepository,
    private val staticResourcesRepository: StaticResourcesRepository
) : ViewModel() {

    companion object {
        const val TAG = "${QTAG}-EnrollmentViewModel"
        val addressesList: MutableState<List<Address>> = mutableStateOf(listOf())
        val selectedAddress: MutableState<Address> =
            mutableStateOf(ApiViewModel.userProfile.value.contacts.address)
        val enrollmentType: MutableState<EnrollmentType?> = mutableStateOf(null)
        val currentEnrollmentType: MutableState<EnrollmentType?> = mutableStateOf(null)
        val addressSaved : MutableState<Boolean> = mutableStateOf(false)
    }

    var countries by mutableStateOf<List<Country>>(emptyList())
        private set

    init {
        getCountryCodes()
    }

    private val _numberFieldState = mutableStateOf(
        InputFieldState(
            text = if (ApiViewModel.userProfile.value.contacts.phoneNumber != null) ApiViewModel.userProfile.value.contacts.phoneNumber.toString() else "",
            hint = "Enter your number"
        )
    )
    val numberFieldState: State<InputFieldState> = _numberFieldState

    private val _codeState = mutableStateOf(InputFieldState())
    val codeState: State<InputFieldState> = _codeState

    private val _addressFieldState = mutableStateOf(
        InputFieldState(
            text = ApiViewModel.userProfile.value.contacts.address.shortAddress,
            hint = "Enter postcode or address"
        )
    )
    val addressFieldState: State<InputFieldState> = _addressFieldState

    var selectedCountryCode by mutableStateOf(Country("GB","GB","GB","44","United Kingdom"))
        private set

    private val _isCodeSent = mutableStateOf(false)
    val isCodeSent: MutableState<Boolean> = _isCodeSent

    private val _isCheckTermsConditions = mutableStateOf(false)
    val isCheckTermsConditions: State<Boolean> = _isCheckTermsConditions

    private val _listOfAddress = mutableStateOf<List<String>>(emptyList())
    val listOfAddress: State<List<String>> = _listOfAddress

    private val _selectedAddressIndex = mutableStateOf(0)
    val selectedAddressIndex = _selectedAddressIndex

    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    private val _userAddress = mutableStateOf<Address?>(null)
    val userAddress: State<Address?> = _userAddress

    var verifyPhoneLoadingState = MutableStateFlow(LoadingState.IDLE)

    fun onTriggerEvent(event: EnrollmentEvent) {
        try {
            viewModelScope.launch {
                when(event){
                    is EnterCode -> { enterCode(event.code)}
                    is EnterNumber -> { enterNumber(event.number) }
                    is EnterAddress -> { enterAddress(event.address) }
                    is FocusAddressInput -> { onFocusAddressInput(event.focusState) }
                    is FocusCodeInput -> { onFocusCodeInput(event.focusState) }
                    is FocusNumberInput -> { onFocusNumberInput(event.focusState) }
                    is SelectCountryCode -> { selectedCountryCode = event.countryCode }
                    is SendCode -> { sendCode() }
                    is SubmitCode -> { submitCode() }
                    is ToggleCheckTermsConditions -> { _isCheckTermsConditions.value = !isCheckTermsConditions.value}
                    is ClickMap -> { _userLocation.value = event.latLng }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    private fun submitCode(){

    }

    fun parseAddressFromLatLng(
        latLng: LatLng? = null,
        address: String? = null
    ) {
        try {
            val geocoder = Geocoder(application, Locale.getDefault())

            val addresses = if (latLng != null) {
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            } else {
                geocoder.getFromLocationName(address, 1)
            }

            if (addresses != null && addresses.size > 0) {
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
        } catch (e: Exception) {
            Log.e(TAG, "parseAddressFromLatLng: ${e.printStackTrace()}")
        }
    }

    fun setEnrollmentType(type: EnrollmentType) {
        enrollmentType.value = type
    }

    private fun enterNumber(number: String) {
        _numberFieldState.value = numberFieldState.value.copy(
            text = number
        )
    }

    private fun enterCode(code: String) {
        _codeState.value = codeState.value.copy(
            text = code
        )
    }

    private fun enterAddress(address: String) {
        _addressFieldState.value = addressFieldState.value.copy(
            text = address
        )
    }

    private fun onFocusNumberInput(focusState: FocusState) {
        _numberFieldState.value = numberFieldState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusCodeInput(focusState: FocusState) {
        _codeState.value = codeState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusAddressInput(focusState: FocusState) {
        _addressFieldState.value = addressFieldState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun sendCode() {
        val newContact = Contacts(
            ApiViewModel.userProfile.value.contacts.email,
            selectedCountryCode.iso2,
            _numberFieldState.value.text.toLong(),
            false,
            ApiViewModel.userProfile.value.contacts.address
        )
        val updatedUser = Person(
            ApiViewModel.userProfile.value.familyId,
            ApiViewModel.userProfile.value.fname,
            ApiViewModel.userProfile.value.mname,
            ApiViewModel.userProfile.value.madenname,
            ApiViewModel.userProfile.value.sname,
            ApiViewModel.userProfile.value.cognitoId,
            ApiViewModel.userProfile.value.gender,
            ApiViewModel.userProfile.value.dob,
            ApiViewModel.userProfile.value.created,
            ApiViewModel.userProfile.value.avatarVerified,
            ApiViewModel.userProfile.value.avatarId,
            ApiViewModel.userProfile.value.verifications,
            ApiViewModel.userProfile.value.settings,
            ApiViewModel.userProfile.value.langs,
            ApiViewModel.userProfile.value.oktaStatus,
            ApiViewModel.userProfile.value.oktaStatusUpdated,
            ApiViewModel.userProfile.value.payMethods,
            ApiViewModel.userProfile.value.cal,
            ApiViewModel.userProfile.value.marketing,
            newContact
        )
        viewModelScope.launch {
            try {
                val response = platformRepository.updateUser(updatedUser)
                if (response.isSuccessful) {
                    val verificationCodeRes = platformRepository.getPhoneVerificationCode()
                    if (verificationCodeRes.isSuccessful) {
                        _isCodeSent.value = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getAddresses(fid: Long) {
        viewModelScope.launch {
            try {
                val response = familiesRepository.getAddresses(fid)
                if (response.isSuccessful) {
                    addressesList.value = response.body()!!
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun onSelectAddressOption(index: Int) {
        _selectedAddressIndex.value = index
        _addressFieldState.value = _addressFieldState.value.copy(
            text = _listOfAddress.value[index]
        )
        parseAddressFromLatLng(address = _addressFieldState.value.text)
    }

    fun verifyPhone(verificationCode: Long) {
        viewModelScope.launch {
            try {
                verifyPhoneLoadingState.emit(LoadingState.LOADING)
                val response = platformRepository.verifyPhone(VrfPhone(verificationCode))
                if (response.isSuccessful) {
                    verifyPhoneLoadingState.emit(LoadingState.LOADED)
                } else {
                    verifyPhoneLoadingState.emit(LoadingState.ERROR)
                }
            } catch (e: Exception) {
                verifyPhoneLoadingState.emit(LoadingState.ERROR)
                e.printStackTrace()
            }
        }
    }

    private fun getCountryCodes() {
        viewModelScope.launch {
            countries = GetCountry(staticResourcesRepository).invoke()
            val iso2 = ApiViewModel.userProfile.value.contacts.phoneCountry
            iso2?.let {
                selectedCountryCode = countries.find { it.iso2 == iso2 }!!
            }
        }
    }

    var createCustomerState = MutableStateFlow(LoadingState.IDLE)
    var createProviderState = MutableStateFlow(LoadingState.IDLE)

    val customer: MutableState<Customer> = mutableStateOf(Customer())
    val provider: MutableState<Provider> = mutableStateOf(Provider())

    fun createCustomerOrProvider() {
        viewModelScope.launch {
            try {
                if (enrollmentType.value == EnrollmentType.PROVIDER) {
                    val newProvider = Provider(
                    )

                    createProviderState.emit(LoadingState.LOADING)
                    val providerResponse = p4pProviderRepository.create(newProvider)
                    if (providerResponse.isSuccessful) {
                        provider.value = providerResponse.body()!!
                        createProviderState.emit(LoadingState.LOADED)
                    } else {
                        createProviderState.emit(LoadingState.ERROR)
                    }

                } else {
                    createCustomerState.emit(LoadingState.LOADING)
                    val customerResponse = p4pCustomerRepository.create()
                    if (customerResponse.isSuccessful) {
                        customer.value = customerResponse.body()!!
                        createCustomerState.emit(LoadingState.LOADED)
                    } else {
                        createCustomerState.emit(LoadingState.ERROR)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                createCustomerState.emit(LoadingState.ERROR)
                createProviderState.emit(LoadingState.ERROR)
            }
        }
    }
}