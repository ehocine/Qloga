package eac.qloga.android.features.p4p.shared.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.QlogaApplication
import eac.qloga.android.R
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent.*
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.SpokenLanguage
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val application: QlogaApplication
): ViewModel(){

    private val _accountType = mutableStateOf(AccountType.PROVIDER)
    val accountType: State<AccountType> = _accountType

    private val _numberFieldState = mutableStateOf(InputFieldState(hint = "Number"))
    val numberFieldState: State<InputFieldState> = _numberFieldState

    private val _codeState = mutableStateOf(InputFieldState(hint = "Enter 6-digits code"))
    val codeState: State<InputFieldState> = _codeState

    private val _emailInputFieldState = mutableStateOf(InputFieldState(hint = "Enter you email"))
    val emailInputFieldState: State<InputFieldState> = _emailInputFieldState

    private val _selectedCountryCode = mutableStateOf(CountryCode("Nepal","+977","NP"))
    val selectedCountryCode: State<CountryCode> = _selectedCountryCode

    private val _isCodeSent = mutableStateOf(false)
    val isCodeSent: State<Boolean> = _isCodeSent

    private val _selectedAddressIndex = mutableStateOf(0)
    val selectedAddressIndex = _selectedAddressIndex

    private val _countryCodes = mutableStateOf<CountryCodes?>(null)
    val countryCodes: State<CountryCodes?> = _countryCodes

    private val _spokenLanguageState= mutableStateOf<List<SpokenLanguage>>(emptyList())
    val spokenLanguageState: State<List<SpokenLanguage>> = _spokenLanguageState

    private val _birthday = mutableStateOf("")
    val birthday: State<String> = _birthday

    private val _nameSurname = mutableStateOf(
        InputFieldState(hint = "Name and surname", text = "Duncan McCleod Cleaning")
    )
    val nameSurname: State<InputFieldState> = _nameSurname

    private val _customerFirstName = mutableStateOf(
        InputFieldState(hint = "First name", text = "Duncan McCleod Cleaning")
    )
    val customerFirstName: State<InputFieldState> = _customerFirstName

    private val _customerLastName = mutableStateOf(InputFieldState(hint = "Last name", text = "Potter"))
    val customerLastName: State<InputFieldState> = _customerLastName

    private val _middleName = mutableStateOf(InputFieldState(hint = "Middle name"))
    val middleName: State<InputFieldState> = _middleName

    private val _maidenName = mutableStateOf(InputFieldState(hint = "Maiden name"))
    val maidenName: State<InputFieldState> = _maidenName

    private val _cancellationPeriod = mutableStateOf(InputFieldState(hint = "Cancellation period (hours)"))
    val cancellationPeriod: State<InputFieldState> = _cancellationPeriod

    private val _coverageZone = mutableStateOf(InputFieldState(hint = "Coverage zone (miles)"))
    val coverageZone: State<InputFieldState> = _coverageZone

    private val _website = mutableStateOf(InputFieldState(hint = "Website"))
    val website: State<InputFieldState> = _website

    private val _registrationDetailsState = mutableStateOf(
        InputFieldState(
            hint = "Please enter Company House registration and date or any other registration info"
        )
    )
    val registrationDetailsState: State<InputFieldState> = _registrationDetailsState

    private val _businessInsuranceState = mutableStateOf(
        InputFieldState(hint = "Name of your insurer, policy number, policy expiry date")
    )
    val businessInsuranceState: State<InputFieldState> = _businessInsuranceState

    private val _businessDescriptionState = mutableStateOf(
        InputFieldState(hint = "This text can differentiate your business when shown to the customers")
    )
    val businessDescriptionState: State<InputFieldState> = _businessDescriptionState

    private val _phoneSwitch = mutableStateOf(false)
    val phoneSwitch: State<Boolean> = _phoneSwitch

    private val _activeSwitch = mutableStateOf(true)
    val activeSwitch: State<Boolean> = _activeSwitch

    private val _timeOffSwitch = mutableStateOf(false)
    val timeOffSwitch: State<Boolean> = _timeOffSwitch

    private val _hideAll = mutableStateOf(false)
    val hideAll: State<Boolean> = _hideAll

    private val _calloutChargeSwitch = mutableStateOf(false)
    val calloutChargeSwitch: State<Boolean> = _calloutChargeSwitch


    /**** Address section states ****/
    private val _addressInputFieldState = mutableStateOf(
        InputFieldState(hint = "Enter your address or postcode"))
    val addressInputFieldState: State<InputFieldState> = _addressInputFieldState

    private val _parkingType = mutableStateOf<ParkingType>(ParkingType.FreeType)
    val parkingType: State<ParkingType> = _parkingType

    private val _listOfAddress = mutableStateOf<List<String>>(emptyList())
    val listOfAddress: State<List<String>> = _listOfAddress

    private val _addressOrPostcode = mutableStateOf("")
    val addressOrPostcode: State<String> = _addressOrPostcode

    private val _addressState = mutableStateOf(AddressState())
    val addressState: State<AddressState> = _addressState

    private val _isBusinessOnly = mutableStateOf(false)
    val isBusinessOnly: State<Boolean> = _isBusinessOnly

    private val _customerPostCodeState = mutableStateOf(InputFieldState(hint = "Postcode"))
    val customerPostCodeState: State<InputFieldState> = _customerPostCodeState

    private val _customerTownState = mutableStateOf(InputFieldState(hint = "Town"))
    val customerTownState: State<InputFieldState> = _customerTownState

    private val _customerStreetState = mutableStateOf(InputFieldState(hint = "Street"))
    val customerStreetState: State<InputFieldState> = _customerStreetState

    private val _customerBuildingState = mutableStateOf(InputFieldState(hint = "Building"))
    val customerBuildingState: State<InputFieldState> = _customerBuildingState

    private val _customerApartmentsState = mutableStateOf(InputFieldState(hint ="Apartments"))
    val customerApartmentsState: State<InputFieldState> = _customerApartmentsState

    private val _providerPostCodeState = mutableStateOf(InputFieldState(hint = "Postcode"))
    val providerPostCodeState: State<InputFieldState> = _providerPostCodeState

    private val _providerTownState = mutableStateOf(InputFieldState(hint ="Town"))
    val providerTownState: State<InputFieldState> = _providerTownState

    private val _providerStreetState = mutableStateOf(InputFieldState(hint = "Street"))
    val providerStreetState: State<InputFieldState> = _providerStreetState

    private val _providerBuildingState = mutableStateOf(InputFieldState(hint ="Building"))
    val providerBuildingState: State<InputFieldState> = _providerBuildingState

    private val _providerApartmentsState = mutableStateOf(InputFieldState(hint ="Apartments"))
    val providerApartmentsState: State<InputFieldState> = _providerApartmentsState
    /**** Address section states ****/

    init {
        getCountryCodes()
        getAddresses()
        getSpokenLanguages()
    }

    fun onTriggerEvent(event: AccountSettingsEvent){
        try {
            viewModelScope.launch {
                when(event){
                    is EnterCode -> { enterCode(event.code)}
                    is EnterEmail -> { enterEmail(event.email)}
                    is EnterNumber -> { enterNumber(event.number)}
                    is FocusCodeInput -> { onFocusCodeInput(event.focusState)}
                    is FocusEmailInput -> { onFocusEmailInput(event.focusState)}
                    is FocusNumberInput -> { onFocusNumberInput(event.focusState)}
                    is SelectCountryCode -> { _selectedCountryCode.value = event.countryCode }
                    is SelectSpokenLanguage -> { onSelectSpokenLang(event.language) }
                    is SelectBirthday -> { onSelectBirthday(event.date) }
                    is SendCode -> { sendCode() }
                    is SubmitCode -> { submitCode() }
                    is SubmitEmail -> { submitEmail() }
                    is SaveCustomerAccountSettings -> {}
                    is SaveProviderAccountSettings -> {}
                    is EnterAddress -> { enterAddress(event.text)}
                    is ClearAddressInput -> { clearInputState() }
                    is FocusAddressInput -> { focusAddressInput(event.focusState)}
                    is SearchAddress -> { onSearchAddress() }
                    is ToggleBusinessOnly -> { onToggleBusinessOnly() }
                    is EnterCancellationPeriod -> { onCancellationPeriod(event.value)}
                    is EnterCoverageZone -> { onCoverageZone(event.value)}
                    is EnterWebsite -> { onWebsite(event.value)}
                    is EnterNameSurname -> { onNameSurname(event.value) }
                    is FocusNameSurname -> { onFocusNameSurname(event.focusState) }
                    is EnterRegistrationDetails -> { onRegistrationDetails(event.value)}
                    is EnterBusinessInsurance -> { onBusinessInsurance(event.value)}
                    is EnterDescription -> { onDescription(event.value)}
                    is FocusCancellationPeriod -> { focusCancellationPeriod(event.focusState)}
                    is FocusCoverageZone -> { focusCoverageZone(event.focusState)}
                    is FocusWebsite -> { focusWebsite(event.focusState)}
                    is FocusRegistrationDetails -> { focusRegistrationDetails(event.focusState)}
                    is FocusBusinessInsurance -> { focusBusinessInsurance(event.focusState)}
                    is FocusDescription -> { focusDescription(event.focusState)}
                    is EnterMiddleName -> { onMiddleName(event.value)}
                    is FocusMiddleName -> { onFocusMiddleName(event.focusState)}
                    is FocusMaidenName-> { onFocusMaidenName(event.focusState)}
                    is EnterMaidenName-> { onMaidenName(event.value)}
                    is EnterCustomerFirstName -> { onCustomerFirstName(event.name)}
                    is FocusCustomerFirstName -> { onFocusCustomerFirstName(event.focusState) }
                    is EnterCustomerLastName -> { onCustomerLastName(event.name) }
                    is FocusCustomerLastName -> { onFocusCustomerLastName(event.focusState) }
                    is EnterCustomerPostcode -> { onCustomerPostcode(event.postCode)}
                    is FocusCustomerPostcode -> { onFocusCustomerPostcode(event.focusState) }
                    is EnterCustomerTown -> { onCustomerTown(event.town) }
                    is FocusCustomerTown -> { onFocusCustomerTown(event.focusState)}
                    is EnterCustomerStreet -> { onCustomerStreet(event.street)}
                    is FocusCustomerStreet -> {
                        _customerStreetState.value = _customerStreetState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterCustomerBuilding -> {
                        _customerBuildingState.value = _customerBuildingState.value.copy(text = event.building )
                    }
                    is FocusCustomerBuilding -> {
                        _customerBuildingState.value = _customerBuildingState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterCustomerApartments -> {
                        _customerApartmentsState.value = _customerApartmentsState.value.copy(text = event.apartments)
                    }
                    is FocusCustomerApartments -> {
                        _customerApartmentsState.value = _customerApartmentsState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderPostcode -> {
                        _providerPostCodeState.value = _providerPostCodeState.value.copy(text = event.postCode)
                    }
                    is FocusProviderPostcode -> {
                        _providerPostCodeState.value = _providerPostCodeState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderTown -> {
                        _providerTownState.value = _providerTownState.value.copy(text = event.town)
                    }
                    is FocusProviderTown -> {
                        _providerTownState.value = _providerTownState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderStreet -> {
                        _providerStreetState.value = _providerStreetState.value.copy(text = event.street)
                    }
                    is FocusProviderStreet -> {
                        _providerStreetState.value = _providerStreetState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderBuilding -> {
                        _providerBuildingState.value = _providerBuildingState.value.copy(text = event.building)
                    }
                    is FocusProviderBuilding -> {
                        _providerBuildingState.value = _providerBuildingState.value.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderApartments -> {
                        _providerApartmentsState.value = _providerApartmentsState.value.copy(text = event.apartments)
                    }
                    is FocusProviderApartments -> {
                        _providerApartmentsState.value = _providerApartmentsState.value.copy(isFocused = event.focusState.isFocused)
                    }
                }
            }
        }catch (e: Exception){
            Log.e("TAG", "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    fun onPhoneSwitch(){
        _phoneSwitch.value = !_phoneSwitch.value
    }

    fun onTimeOffSwitch(){
        _timeOffSwitch.value = !_timeOffSwitch.value
    }

    fun onActiveSwitch(){
        _activeSwitch.value = !_activeSwitch.value
    }

    fun onCalloutChargeSwitch(){
        _calloutChargeSwitch.value = !_calloutChargeSwitch.value
    }

    fun onHideAll(){
        _hideAll.value = !_hideAll.value
        if(_hideAll.value){
            _phoneSwitch.value = false
            _timeOffSwitch.value = false
        }else{
            _phoneSwitch.value = true
            _timeOffSwitch.value = true
        }
    }

    private fun onCustomerStreet(street: String) {
        _customerStreetState.value = _customerStreetState.value.copy(
            text = street
        )
    }

    private fun onFocusCustomerTown(focusState: FocusState) {
        _customerTownState.value = _customerTownState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerTown(town: String) {
        _customerTownState.value = _customerTownState.value.copy(
            text = town
        )
    }

    private fun onFocusCustomerPostcode(focusState: FocusState) {
        _customerPostCodeState.value = _customerPostCodeState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerPostcode(postCode: String) {
        _customerPostCodeState.value = _customerPostCodeState.value.copy(
            text = postCode
        )
    }

    private fun onFocusCustomerLastName(focusState: FocusState) {
        _customerLastName.value = _customerLastName.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerLastName(name: String) {
        _customerLastName.value = _customerLastName.value.copy(
            text = name
        )
    }

    private fun onFocusCustomerFirstName(focusState: FocusState) {
        _customerFirstName.value = _customerFirstName.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerFirstName(name: String) {
        _customerFirstName.value = _customerFirstName.value.copy(
            text = name
        )
    }

    private fun onNameSurname(value: String) {
        _nameSurname.value = _nameSurname.value.copy(
            text = value
        )
    }

    private fun onFocusNameSurname(focusState: FocusState){
        _nameSurname.value = _nameSurname.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onMaidenName(value: String) {
        _maidenName.value = maidenName.value.copy(
            text = value
        )
    }

    private fun onFocusMaidenName(focusState: FocusState) {
        _maidenName.value = maidenName.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusMiddleName(focusState: FocusState) {
        _middleName.value = middleName.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onMiddleName(value: String) {
        _middleName.value = middleName.value.copy(
            text = value
        )
    }

    private fun focusDescription(focusState: FocusState) {
        _businessDescriptionState.value = businessDescriptionState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusBusinessInsurance(focusState: FocusState) {
        _businessInsuranceState.value = businessInsuranceState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusRegistrationDetails(focusState: FocusState) {
        _registrationDetailsState.value = registrationDetailsState.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusWebsite(focusState: FocusState) {
        _website.value = website.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusCoverageZone(focusState: FocusState) {
        _coverageZone.value = coverageZone.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onDescription(value: String) {
        _businessDescriptionState.value  = _businessDescriptionState.value.copy(
            text = value
        )
    }

    private fun onBusinessInsurance(value: String) {
        _businessInsuranceState.value = businessInsuranceState.value.copy(
            text = value
        )
    }

    private fun onRegistrationDetails(value: String) {
        _registrationDetailsState.value = registrationDetailsState.value.copy(
            text = value
        )
    }

    private fun onCoverageZone(value: String) {
        _coverageZone.value = coverageZone.value.copy(
            text = value
        )
    }

    private fun onWebsite(value: String) {
        _website.value = website.value.copy(
            text = value
        )
    }

    private fun focusCancellationPeriod(focusState: FocusState) {
        _cancellationPeriod.value = cancellationPeriod.value.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCancellationPeriod(value: String) {
        _cancellationPeriod.value = cancellationPeriod.value.copy(
            text = value
        )
    }

    private fun onToggleBusinessOnly() {
        _isBusinessOnly.value = !isBusinessOnly.value
    }

    private fun focusAddressInput(focusState: FocusState) {
        _addressInputFieldState.value = addressInputFieldState.value.copy(
            isFocused = focusState.isFocused
        )
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

    private fun enterEmail(email: String){
        _emailInputFieldState.value = emailInputFieldState.value.copy(
            text = email
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

    private fun onFocusEmailInput(focusState: FocusState){
        _emailInputFieldState.value = emailInputFieldState.value.copy(
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

    private fun submitEmail(){
        _emailInputFieldState.value = emailInputFieldState.value.copy(
            text = "",
            isFocused = false
        )
    }

    private fun onSelectSpokenLang(language: SpokenLanguage){
        _spokenLanguageState.value = spokenLanguageState.value.map {
            if(it.id == language.id){
                SpokenLanguage(id = it.id, title = it.title , isSelected  = !it.isSelected)
            }else {
                it
            }
        }
    }

    private fun onSelectBirthday(date: String){
        _birthday.value = date
    }

    private fun getAddresses(){
        /***
         *  Simulating the raw data as a address . Later fetch from server
         * **/
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

    private fun getCountryCodes(){
        val countryCodesJson = application.resources.openRawResource(R.raw.country_codes).bufferedReader().use { it.readText() }
        val gson = Gson()
        val countryCodes = gson.fromJson(countryCodesJson, CountryCodes::class.java)
        _countryCodes.value = countryCodes
    }

    fun setAccountType(accountType: AccountType){
        _accountType.value = accountType
    }

    private fun getSpokenLanguages(){
//        _spokenLanguageState.value = listOf(
//            SpokenLanguage(id = "1", "English",true),
//            SpokenLanguage(id = "2", "French",false),
//            SpokenLanguage(id = "3", "Germany",false),
//            SpokenLanguage(id = "4", "Detch",false),
//            SpokenLanguage(id = "5", "Spain",false),
//            SpokenLanguage(id = "6", "Hebrow",false),
//        )
    }

    private fun onSearchAddress() {
        _addressOrPostcode.value = addressInputFieldState.value.text
        _addressInputFieldState.value = addressInputFieldState.value.copy(text = "")
    }

    fun onClickParkingType(parkingType: ParkingType){
        _parkingType.value = parkingType
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

    fun enterAddress(value: String){
        _addressInputFieldState.value = addressInputFieldState.value.copy(
            text = value
        )
        //getAddresses()
    }

    fun onSelectAddress(index: Int){
        _selectedAddressIndex.value = index
    }

    fun spokenLanguageString(spokenLanguage: List<SpokenLanguage>): String {
        val first3 = if(spokenLanguage.size > 3) spokenLanguage.subList(0,3) else spokenLanguage
        return first3.joinToString(", ") { it.title } + if (spokenLanguage.size > 3) " +${spokenLanguage.size - 3}" else ""
    }
}