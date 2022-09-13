package eac.qloga.android.core.shared.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import eac.qloga.android.core.shared.viewmodels.ApiViewModel


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
//                                                    actions.goToOrderLisrPrv.invoke()
                                                    checkEnrollment(true)

                                                } else {
//                                                    actions.goToIntro.invoke()
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
//                                        actions.goToNoAddress.invoke()
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