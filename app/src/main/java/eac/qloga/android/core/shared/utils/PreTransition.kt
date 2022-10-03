package eac.qloga.android.core.shared.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel


@Composable
fun PreTransition(
    apiViewModel: ApiViewModel,
    loadingState: (isLoading: Boolean) -> Unit,
    checkAddress: (hasAddress: Boolean) -> Unit,
    checkEnrollment: (isEnrolled: Boolean) -> Unit
) {

    val getEnrollsState by apiViewModel.getEnrollsLoadingState.collectAsState()
    val responseEnrollsModel by apiViewModel.responseEnrollsModel

    val getSettingsLoadingState by apiViewModel.settingsLoadingState.collectAsState()
    val getCategoriesLoadingState by apiViewModel.categoriesLoadingState.collectAsState()
    val getConditionsLoadingState by apiViewModel.conditionsLoadingState.collectAsState()

    val getUserProfileLoadingState by apiViewModel.userProfileLoadingState.collectAsState()
    val userProfile = ApiViewModel.userProfile

    when (getSettingsLoadingState) {
        LoadingState.LOADING -> {
            loadingState(true)
        }
        LoadingState.LOADED -> {
            when (getCategoriesLoadingState) {
                LoadingState.LOADING -> Unit
                LoadingState.LOADED -> {
                    when (getConditionsLoadingState) {
                        LoadingState.LOADING -> Unit
                        LoadingState.LOADED -> {
                            when (getUserProfileLoadingState) {
                                LoadingState.LOADING -> Unit
                                LoadingState.LOADED -> {
                                    if (userProfile.value.contacts.address != null) {
                                        checkAddress(true)
                                        Log.d("Tag", "User has an address")
                                        when (getEnrollsState) {
                                            LoadingState.LOADING -> Unit
                                            LoadingState.LOADED -> {
                                                loadingState(false)
                                                if (responseEnrollsModel.CUSTOMER != null || responseEnrollsModel.PROVIDER != null) {
                                                    when {
                                                        responseEnrollsModel.CUSTOMER != null && responseEnrollsModel.PROVIDER == null -> {
                                                            EnrollmentViewModel.currentEnrollmentType.value =
                                                                EnrollmentType.CUSTOMER
                                                        }
                                                        responseEnrollsModel.PROVIDER != null && responseEnrollsModel.CUSTOMER == null -> {
                                                            EnrollmentViewModel.currentEnrollmentType.value =
                                                                EnrollmentType.PROVIDER
                                                        }
                                                        else->{
                                                            EnrollmentViewModel.currentEnrollmentType.value =
                                                                EnrollmentType.BOTH
                                                        }
                                                    }
                                                    Log.d(
                                                        "Tag",
                                                        "Enro: ${EnrollmentViewModel.currentEnrollmentType.value}"
                                                    )
                                                    checkEnrollment(true)

                                                } else {
                                                    checkEnrollment(false)
                                                }
                                            }
                                            else -> Unit
                                        }
                                    } else {
                                        // We go to the screen where the user enters address
//                                        AddressViewModel.fullAddress.value = FullAddress()
                                        loadingState(false)
                                        checkAddress(false)
                                        Log.d("Tag", "User has no address")
                                    }
                                }
                                else -> Unit
                            }
                        }
                        else -> Unit
                    }
                }
                else -> Unit
            }
        }
        else -> Unit
    }

}