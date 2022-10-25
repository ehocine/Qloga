package eac.qloga.android.features.p4p.shared.utils

import androidx.compose.ui.focus.FocusState
import eac.qloga.android.data.shared.models.Language
import eac.qloga.bare.dto.lookups.Country

sealed class AccountSettingsEvent {
    data class SelectCountryCode(val countryCode: Country): AccountSettingsEvent()
    data class SelectBirthday(val date: String): AccountSettingsEvent()
    data class SelectSpokenLanguage(val language: Language): AccountSettingsEvent()
    data class SelectSpokenLanguageProvider(val language: Language): AccountSettingsEvent()
    data class EnterNumber(val number: String): AccountSettingsEvent()
    data class EnterCode(val code: String): AccountSettingsEvent()
    data class EnterEmail(val email: String): AccountSettingsEvent()
    data class EnterOrgsEmail(val email: String): AccountSettingsEvent()
    data class FocusOrgsEmail(val focusState: FocusState): AccountSettingsEvent()
    data class EnterNameSurname(val value: String): AccountSettingsEvent()
    data class EnterMiddleName(val value: String): AccountSettingsEvent()
    data class EnterMaidenName(val value: String): AccountSettingsEvent()
    data class EnterCancellationPeriod(val value: String): AccountSettingsEvent()
    data class EnterCoverageZone(val value: String): AccountSettingsEvent()
    data class EnterWebsite(val value: String): AccountSettingsEvent()
    data class EnterRegistrationDetails(val value: String): AccountSettingsEvent()
    data class EnterBusinessInsurance(val value: String): AccountSettingsEvent()
    data class EnterDescription(val value: String): AccountSettingsEvent()
    data class FocusMiddleName(val focusState: FocusState): AccountSettingsEvent()
    data class FocusMaidenName(val focusState: FocusState): AccountSettingsEvent()
    data class FocusNameSurname(val focusState: FocusState): AccountSettingsEvent()
    data class FocusCancellationPeriod(val focusState: FocusState): AccountSettingsEvent()
    data class FocusCoverageZone(val focusState: FocusState): AccountSettingsEvent()
    data class FocusWebsite(val focusState: FocusState): AccountSettingsEvent()
    data class FocusRegistrationDetails(val focusState: FocusState): AccountSettingsEvent()
    data class FocusBusinessInsurance(val focusState: FocusState): AccountSettingsEvent()
    data class FocusDescription(val focusState: FocusState): AccountSettingsEvent()
    data class FocusNumberInput(val focusState: FocusState): AccountSettingsEvent()
    data class FocusCodeInput(val focusState: FocusState): AccountSettingsEvent()
    data class FocusEmailInput(val focusState: FocusState): AccountSettingsEvent()
    object SendCode: AccountSettingsEvent()
    object SubmitCode: AccountSettingsEvent()
    object SubmitEmail: AccountSettingsEvent()
    object SaveCustomerAccountSettings: AccountSettingsEvent()
    object SaveProviderAccountSettings: AccountSettingsEvent()
    data class EnterAddress(val text: String): AccountSettingsEvent()
    object SearchAddress: AccountSettingsEvent()
    object ClearAddressInput: AccountSettingsEvent()
    object ToggleBusinessOnly: AccountSettingsEvent()
    data class FocusAddressInput(val focusState: FocusState): AccountSettingsEvent()
    data class EnterCustomerFirstName(val name: String): AccountSettingsEvent()
    data class FocusCustomerFirstName(val focusState: FocusState): AccountSettingsEvent()
    data class EnterCustomerLastName(val name: String): AccountSettingsEvent()
    data class FocusCustomerLastName(val focusState: FocusState): AccountSettingsEvent()
    data class EnterCustomerPostcode(val postCode: String): AccountSettingsEvent()
    data class FocusCustomerPostcode(val focusState: FocusState): AccountSettingsEvent()
    data class EnterCustomerTown(val town: String): AccountSettingsEvent()
    data class FocusCustomerTown(val focusState: FocusState): AccountSettingsEvent()
    data class EnterCustomerStreet(val street: String): AccountSettingsEvent()
    data class FocusCustomerStreet(val focusState: FocusState): AccountSettingsEvent()
    data class EnterCustomerBuilding(val building: String): AccountSettingsEvent()
    data class FocusCustomerBuilding(val focusState: FocusState): AccountSettingsEvent()
    data class EnterCustomerApartments(val apartments: String): AccountSettingsEvent()
    data class FocusCustomerApartments(val focusState: FocusState): AccountSettingsEvent()
    data class EnterProviderPostcode(val postCode: String): AccountSettingsEvent()
    data class FocusProviderPostcode(val focusState: FocusState): AccountSettingsEvent()
    data class EnterProviderTown(val town: String): AccountSettingsEvent()
    data class FocusProviderTown(val focusState: FocusState): AccountSettingsEvent()
    data class EnterProviderStreet(val street: String): AccountSettingsEvent()
    data class FocusProviderStreet(val focusState: FocusState): AccountSettingsEvent()
    data class EnterProviderBuilding(val building: String): AccountSettingsEvent()
    data class FocusProviderBuilding(val focusState: FocusState): AccountSettingsEvent()
    data class EnterProviderApartments(val apartments: String): AccountSettingsEvent()
    data class FocusProviderApartments(val focusState: FocusState): AccountSettingsEvent()
    data class EnterOrgName(val name: String): AccountSettingsEvent()
    data class FocusOrgName(val focusState: FocusState): AccountSettingsEvent()
}