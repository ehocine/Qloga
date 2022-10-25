package eac.qloga.android.features.p4p.shared.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eac.qloga.android.core.shared.interactors.GetCountry
import eac.qloga.android.core.shared.interactors.GetLanguage
import eac.qloga.android.core.shared.utils.*
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.data.p4p.lookups.StaticResourcesRepository
import eac.qloga.android.data.p4p.provider.P4pProviderRepository
import eac.qloga.android.data.qbe.OrgsRepository
import eac.qloga.android.data.qbe.PlatformRepository
import eac.qloga.android.data.shared.models.Language
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent
import eac.qloga.android.features.p4p.shared.utils.AccountSettingsEvent.*
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.bare.dto.Org
import eac.qloga.bare.dto.lookups.Country
import eac.qloga.bare.dto.person.Person
import eac.qloga.p4p.prv.dto.Provider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val platformRepository: PlatformRepository,
    private val p4pProviderRepository: P4pProviderRepository,
    private val orgsRepository: OrgsRepository,
    private val staticResourcesRepository: StaticResourcesRepository
): ViewModel(){

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    companion object{
        const val TAG = "$QTAG-AccStngsViewModel"
        var accountType by mutableStateOf<AccountType?>(null)
        var spokenLanguageState by mutableStateOf<List<Language?>>(emptyList())
        var spokenLanguageProvider by mutableStateOf<List<Language?>>(emptyList())
        val optionLanguages =  MutableStateFlow<List<Language?>>(emptyList())
        var registrationDetailsState by mutableStateOf(
            InputFieldState(
                hint = "Please enter Company House registration and date or any other registration info"
            )
        )
        var businessInsuranceState by mutableStateOf(InputFieldState())
        var businessDescriptionState by mutableStateOf(InputFieldState())
        var phoneNumberFieldState by mutableStateOf(InputFieldState(hint = "Number"))
        var emailInputFieldState by mutableStateOf(InputFieldState(hint = "Enter you email"))
        var orgEmailInputFieldState by mutableStateOf(InputFieldState(hint = "Enter you email"))
    }

    var birthday by mutableStateOf("")
        private set

    var fName by mutableStateOf(InputFieldState(hint = "First name", text = ""))
        private set

    var orgName by mutableStateOf(InputFieldState(hint=""))
        private set

    var lastName by mutableStateOf(InputFieldState(hint = "Last name", text = "Potter"))
        private set

    var middleName by mutableStateOf(InputFieldState(hint = "Middle name"))
        private set

    var maidenName by mutableStateOf(InputFieldState(hint = "Maiden name"))
        private set

    var cancellationPeriod by mutableStateOf(InputFieldState(hint = "Cancellation period (hours)"))
        private set

    var coverageZone by mutableStateOf(InputFieldState(hint = "Coverage zone (miles)"))
        private set

    var website by mutableStateOf(InputFieldState(hint = "Website"))
        private set

    var activeSwitch by mutableStateOf(true)
        private set

    var calloutChargeSwitch by mutableStateOf(false)
        private set

    var verifications by mutableStateOf("")
        private set

    var orgVerifications by mutableStateOf("")
        private set

    var codeState by mutableStateOf(InputFieldState(hint = "Enter 6-digits code"))
        private set

    var selectedCountryCode by mutableStateOf(Country())
        private set

    var isCodeSent by mutableStateOf(false)
        private set

    var selectedAddressIndex by mutableStateOf(0)
        private set

    var countries by mutableStateOf<List<Country>>(emptyList())
        private set

    var languages by mutableStateOf<List<Language>>(emptyList())
        private set

    var addressInputFieldState by mutableStateOf(InputFieldState())
        private set

    var initStatesFlag by mutableStateOf(false)
        private set

    /**** Address section states ****/

    var parkingType by mutableStateOf<ParkingType>(ParkingType.FreeType)
        private set

    var listOfAddress by mutableStateOf<List<String>>(emptyList())
        private set

    var addressOrPostcode by mutableStateOf("")
        private set

    var addressState by mutableStateOf(AddressState())
        private set

    var isBusinessOnly by mutableStateOf(false)
        private set

    var customerPostCodeState by mutableStateOf(InputFieldState(hint = "Postcode"))
        private set

    var customerTownState by mutableStateOf(InputFieldState(hint = "Town"))
        private set

    var customerStreetState by mutableStateOf(InputFieldState(hint = "Street"))
        private set

    var customerBuildingState by mutableStateOf(InputFieldState(hint = "Building"))
        private set

    var customerApartmentsState by mutableStateOf(InputFieldState(hint ="Apartments"))
        private set

    var providerPostCodeState by mutableStateOf(InputFieldState(hint = "Postcode"))
        private set

    var providerTownState by mutableStateOf(InputFieldState(hint ="Town"))
        private set

    var providerStreetState by mutableStateOf(InputFieldState(hint = "Street"))
        private set

    var providerBuildingState by mutableStateOf(InputFieldState(hint ="Building"))
        private set

    var providerApartmentsState by mutableStateOf(InputFieldState(hint ="Apartments"))
        private set
    /**** Address section states ****/

    private val _savingState = MutableStateFlow(LoadingState.IDLE)
    val savingState = _savingState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        preLoadCalls()
    }

    fun preLoadCalls(){
        getAddresses()
    }

    fun onTriggerEvent(event: AccountSettingsEvent){
        try {
            viewModelScope.launch {
                when(event){
                    is EnterCode -> { codeState = codeState.copy(text = event.code)}
                    is EnterEmail -> { emailInputFieldState = emailInputFieldState.copy(text = event.email)}
                    is EnterOrgsEmail -> { orgEmailInputFieldState = orgEmailInputFieldState.copy(text = event.email)}
                    is FocusOrgsEmail -> { orgEmailInputFieldState = orgEmailInputFieldState.copy(isFocused = event.focusState.isFocused)}
                    is EnterNumber -> { phoneNumberFieldState = phoneNumberFieldState.copy(text = event.number)}
                    is FocusCodeInput -> { codeState = codeState.copy(isFocused = event.focusState.isFocused)}
                    is FocusEmailInput -> { emailInputFieldState = emailInputFieldState.copy(isFocused = event.focusState.isFocused) }
                    is FocusNumberInput -> { phoneNumberFieldState = phoneNumberFieldState.copy(isFocused = event.focusState.isFocused)}
                    is SelectCountryCode -> { selectedCountryCode = event.countryCode }
                    is SelectSpokenLanguage -> { onSelectSpokenLang(event.language) }
                    is SelectSpokenLanguageProvider -> { onSelectSpokenLangPrv(event.language) }
                    is SelectBirthday -> { onSelectBirthday(event.date) }
                    is SendCode -> { sendCode() }
                    is SubmitCode -> { submitCode() }
                    is SubmitEmail -> { submitEmail() }
                    is SaveCustomerAccountSettings -> { updatePerson() }
                    is SaveProviderAccountSettings -> {savePrvSettings()}
                    is EnterAddress -> { enterAddress(event.text)}
                    is ClearAddressInput -> { clearInputState() }
                    is FocusAddressInput -> { focusAddressInput(event.focusState)}
                    is SearchAddress -> { onSearchAddress() }
                    is ToggleBusinessOnly -> { onToggleBusinessOnly() }
                    is EnterCancellationPeriod -> { onCancellationPeriod(event.value)}
                    is EnterCoverageZone -> { onCoverageZone(event.value)}
                    is EnterWebsite -> { onWebsite(event.value) }
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
                    is EnterOrgName -> { orgName = orgName.copy(text = event.name) }
                    is FocusOrgName -> { orgName = orgName.copy(isFocused = event.focusState.isFocused)}
                    is FocusCustomerStreet -> {
                        customerStreetState = customerStreetState.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterCustomerBuilding -> {
                        customerBuildingState = customerBuildingState.copy(text = event.building )
                    }
                    is FocusCustomerBuilding -> {
                        customerBuildingState = customerBuildingState.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterCustomerApartments -> {
                        customerApartmentsState = customerApartmentsState.copy(text = event.apartments)
                    }
                    is FocusCustomerApartments -> {
                        customerApartmentsState = customerApartmentsState.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderPostcode -> {
                        providerPostCodeState = providerPostCodeState.copy(text = event.postCode)
                    }
                    is FocusProviderPostcode -> {
                        providerPostCodeState = providerPostCodeState.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderTown -> {
                        providerTownState = providerTownState.copy(text = event.town)
                    }
                    is FocusProviderTown -> {
                        providerTownState = providerTownState.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderStreet -> {
                        providerStreetState = providerStreetState.copy(text = event.street)
                    }
                    is FocusProviderStreet -> {
                        providerStreetState = providerStreetState.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderBuilding -> {
                        providerBuildingState = providerBuildingState.copy(text = event.building)
                    }
                    is FocusProviderBuilding -> {
                        providerBuildingState = providerBuildingState.copy(isFocused = event.focusState.isFocused)
                    }
                    is EnterProviderApartments -> {
                        providerApartmentsState = providerApartmentsState.copy(text = event.apartments)
                    }
                    is FocusProviderApartments -> {
                        providerApartmentsState = providerApartmentsState.copy(isFocused = event.focusState.isFocused)
                    }
                }
            }
        }catch (e: Exception){
            Log.e("TAG", "onTriggerEvent: ${e.printStackTrace()}")
        }
    }

    private fun savePrvSettings(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            updateOrg()
            updateProvider()
        }
    }

    private fun updatePerson(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _savingState.emit(LoadingState.LOADING)

            val userProfile: Person = ApiViewModel.userProfile.value
            val dob = DateConverter.stringToLocalDate(birthday)
            val phoneNumber = phoneNumberFieldState.text

            //TODO address and phone update

            userProfile.contacts.email = emailInputFieldState.text
            userProfile.madenname = maidenName.text.ifEmpty { " " }
            userProfile.mname = middleName.text.ifEmpty { " " }
            userProfile.fname = fName.text
            userProfile.langs = spokenLanguageState.map { it?.code }.toTypedArray()
            userProfile.sname = lastName.text
            userProfile.dob = dob ?: userProfile.dob

            val response = platformRepository.updateUser(userProfile)
            if(response.isSuccessful){
                _eventFlow.emit(UiEvent.ShowToast("saved successfully!"))
                _eventFlow.emit(UiEvent.NavigateBack)
                _savingState.emit(LoadingState.LOADED)
            }else{
                _savingState.emit(LoadingState.LOADED)
                _eventFlow.emit(UiEvent.ShowToast("Something went wrong!"))
                Log.e(TAG, "updatePerson: failed to update ,${response.code()} ${response.errorBody()}")
            }
        }
    }

    private fun updateOrg(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val org = ApiViewModel.orgs[0]
            _savingState.emit(LoadingState.LOADING)

            //TODO update org.contacts.address
            //TODO update org.contacts.phoneNumber
            org.contacts.email = orgEmailInputFieldState.text
            org.descr = businessDescriptionState.text
            org.active = activeSwitch
            org.website = website.text
            org.name = orgName.text
            org.offTime = null
            org.workingHours = null
            org.settings = null
            org.regDetails = registrationDetailsState.text
            org.insurance = businessInsuranceState.text
            org.langs = spokenLanguageProvider.map { it?.code }.toTypedArray()

            val response = orgsRepository.update(org)
            if(response.isSuccessful){
                _eventFlow.emit(UiEvent.ShowToast("Saved!"))
                _eventFlow.emit(UiEvent.NavigateBack)
                _savingState.emit(LoadingState.LOADED)
            }else{
                Log.e(TAG, "updateOrg: code = ${response.code()}, error = ${response.errorBody()}")
                _eventFlow.emit(UiEvent.ShowToast("Failed!"))
                _savingState.emit(LoadingState.ERROR)
            }
        }
    }

    private fun updateProvider(){
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler){
            try {
                val provider = ApiViewModel.provider ?: Provider()
                provider.active = activeSwitch
                provider.calloutCharge = calloutChargeSwitch
                if(cancellationPeriod.text.isNotEmpty()) provider.cancelHrs = cancellationPeriod.text.toInt()
                if(coverageZone.text.isNotEmpty()) provider.coverageZone = coverageZone.text.toLong()

                p4pProviderRepository.update(provider)
            }catch (e: IOException){
                Log.e(TAG, "updateProvider: ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getOptionLanguages(spokenLanguages: List<Language?>) {
        val codes = listOf("en","ar","nl","fr") //TODO update options
        optionLanguages.emit(codes.map { getLangByCode(it) })
        val cloned = ArrayList(spokenLanguages)
        optionLanguages.value.forEach {
            if(it !in cloned) cloned.add(it)
        }
        optionLanguages.emit(cloned)
    }

    private suspend fun getLangByCode(code: String): Language?{
        if(languages.isEmpty()){
            languages = GetLanguage(staticResourcesRepository).invoke()
        }
        //val langs = GetLanguage(staticResourcesRepository).invoke()
        return languages.find { it.code?.lowercase() == code.lowercase() }
    }

    fun setInitialStates(){
        viewModelScope.launch {
            if(initStatesFlag) return@launch
            val org = if(ApiViewModel.orgs.isNotEmpty()) ApiViewModel.orgs[0] else Org()
            val provider = ApiViewModel.provider ?: Provider()
            val userProfile = ApiViewModel.userProfile.value

            orgVerifications = VerificationConverter.verificationToString(org.verifications)
            activeSwitch = org.active ?: false
            website.text = org.website?.trim() ?: ""
            orgName.text = org.name ?: ""
            calloutChargeSwitch = provider.calloutCharge ?: false
            cancellationPeriod.text = (provider.cancelHrs ?: "").toString()
            coverageZone.text = (provider.coverageZone ?: "").toString()
            fName.text = userProfile.fname?.trim() ?: ""
            addressInputFieldState.text = AddressConverter.addressToString(userProfile.contacts?.address)
            maidenName.text = userProfile.madenname?.trim() ?: ""
            middleName.text = userProfile.mname?.trim() ?: ""
            lastName.text = userProfile.sname?.trim() ?: ""
            verifications = VerificationConverter.verificationToString(userProfile.verifications)
            birthday = DateConverter.toTextDMY(userProfile.dob.toString())
            orgEmailInputFieldState.text = org.contacts?.email ?: ""
            emailInputFieldState.text = userProfile.contacts?.email ?: ""
            businessDescriptionState.text = org.descr?.trim() ?: ""
            businessInsuranceState.text = org.insurance?.trim() ?: ""
            registrationDetailsState.text = org.regDetails?.trim() ?: ""
            spokenLanguageState = userProfile.langs?.toList()?.map { getLangByCode(it) } ?: emptyList()
            spokenLanguageProvider = org.langs?.toList()?.map { getLangByCode(it) } ?: emptyList()
            // phoneNumberFieldState.text =  getDialCodeByCountryName(userProfile.contacts?.phoneCountry) +
            //            " " + userProfile.contacts?.phoneNumber?.toString()
            phoneNumberFieldState.text = userProfile.contacts?.phoneNumber?.toString() ?: ""
            getOptionLanguages(spokenLanguageState)
            getOptionLanguages(spokenLanguageProvider)
            initStatesFlag = true
        }
    }

    private fun getDialCodeByCountryName(iso2: String?): String{
        if(iso2 == null) return ""
        return countries.find { it.iso2?.lowercase() == iso2.lowercase() }?.dialcode ?: ""
    }

    fun onActiveSwitch(){
        activeSwitch = !activeSwitch
    }

    fun onCalloutChargeSwitch(){
        calloutChargeSwitch = !calloutChargeSwitch
    }

    private fun onCustomerStreet(street: String) {
        customerStreetState = customerStreetState.copy(
            text = street
        )
    }

    private fun onFocusCustomerTown(focusState: FocusState) {
        customerTownState = customerTownState.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerTown(town: String) {
        customerTownState = customerTownState.copy(
            text = town
        )
    }

    private fun onFocusCustomerPostcode(focusState: FocusState) {
        customerPostCodeState = customerPostCodeState.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerPostcode(postCode: String) {
        customerPostCodeState = customerPostCodeState.copy(
            text = postCode
        )
    }

    private fun onFocusCustomerLastName(focusState: FocusState) {
        lastName = lastName.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerLastName(name: String) {
        lastName = lastName.copy(
            text = name
        )
    }

    private fun onFocusCustomerFirstName(focusState: FocusState) {
        fName = fName.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCustomerFirstName(name: String) {
        fName = fName.copy(
            text = name
        )
    }

    private fun onNameSurname(value: String) {
        fName = fName.copy(
            text = value
        )
    }

    private fun onFocusNameSurname(focusState: FocusState){
        fName = fName.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onMaidenName(value: String) {
        maidenName = maidenName.copy(
            text = value
        )
    }

    private fun onFocusMaidenName(focusState: FocusState) {
        maidenName = maidenName.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onFocusMiddleName(focusState: FocusState) {
        middleName = middleName.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onMiddleName(value: String) {
        middleName = middleName.copy(
            text = value
        )
    }

    private fun focusDescription(focusState: FocusState) {
        businessDescriptionState = businessDescriptionState.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusBusinessInsurance(focusState: FocusState) {
        businessInsuranceState = businessInsuranceState.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusRegistrationDetails(focusState: FocusState) {
        registrationDetailsState = registrationDetailsState.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusWebsite(focusState: FocusState) {
        website = website.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun focusCoverageZone(focusState: FocusState) {
        coverageZone = coverageZone.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onDescription(value: String) {
        businessDescriptionState  = businessDescriptionState.copy(
            text = value
        )
    }

    private fun onBusinessInsurance(value: String) {
        businessInsuranceState = businessInsuranceState.copy(
            text = value
        )
    }

    private fun onRegistrationDetails(value: String) {
        registrationDetailsState = registrationDetailsState.copy(
            text = value
        )
    }

    private fun onCoverageZone(value: String) {
        coverageZone = coverageZone.copy(
            text = value
        )
    }

    private fun onWebsite(value: String) {
        website = website.copy(
            text = value
        )
    }

    private fun focusCancellationPeriod(focusState: FocusState) {
        cancellationPeriod = cancellationPeriod.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun onCancellationPeriod(value: String) {
        cancellationPeriod = cancellationPeriod.copy(
            text = value
        )
    }

    private fun onToggleBusinessOnly() {
        isBusinessOnly = !isBusinessOnly
    }

    private fun focusAddressInput(focusState: FocusState) {
        addressInputFieldState = addressInputFieldState.copy(
            isFocused = focusState.isFocused
        )
    }

    private fun sendCode(){
        isCodeSent = true
        phoneNumberFieldState = phoneNumberFieldState.copy(
            text = "",
            isFocused = false
        )
    }

    private fun submitCode(){
        codeState = codeState.copy(
            text = "",
            isFocused = false
        )
    }

    private fun submitEmail(){
        emailInputFieldState = emailInputFieldState.copy(
            text = "",
            isFocused = false
        )
    }

    fun submitOrgEmail(){
        orgEmailInputFieldState = orgEmailInputFieldState.copy(
            text = "",
            isFocused = false
        )
    }

    private fun onSelectSpokenLang(language: Language){
        val cloned = ArrayList(spokenLanguageState)
        if(language in spokenLanguageState){
            cloned.remove(language)
        }else{
            cloned.add(language)
        }
        spokenLanguageState = cloned
    }

    private fun onSelectSpokenLangPrv(language: Language){
        val cloned = ArrayList(spokenLanguageProvider)
        if(language in spokenLanguageProvider){
            cloned.remove(language)
        }else{
            cloned.add(language)
        }
        spokenLanguageProvider = cloned
    }

    private fun onSelectBirthday(date: String){
        birthday = date
    }

    private fun getAddresses(){
        /***
         *  Simulating the raw data as a address . Later fetch from server
         * **/
        listOfAddress = listOf(
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

    fun getCountries(){
        viewModelScope.launch {
            countries = GetCountry(staticResourcesRepository).invoke()
        }
    }

    fun setAccType(accType: AccountType){
        accountType = accType
    }

    private fun onSearchAddress() {
        addressOrPostcode = addressInputFieldState.text
        addressInputFieldState = addressInputFieldState.copy(text = "")
    }

    fun onClickParkingType(type: ParkingType){
        parkingType = type
    }

    private fun clearInputState(){
        addressInputFieldState = addressInputFieldState.copy(
            text = ""
        )
    }

    fun clearAddressState(){
        addressState = AddressState()
        clearInputState()
    }

    fun enterAddress(value: String){
        addressInputFieldState = addressInputFieldState.copy(
            text = value
        )
    }

    fun spokenLanguageString(spokenLanguage: List<Language?>): String {
        if(spokenLanguage.isEmpty()) return ""
        val first3 = if(spokenLanguage.size > 3) spokenLanguage.subList(0,3) else spokenLanguage
        return first3.joinToString(", ") { it?.descr ?: "" } +
                if (spokenLanguage.size > 3) " +${spokenLanguage.size - 3}" else ""
    }
}