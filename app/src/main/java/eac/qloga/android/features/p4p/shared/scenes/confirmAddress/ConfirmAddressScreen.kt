package eac.qloga.android.features.p4p.shared.scenes.confirmAddress

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.*
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.EnrollmentEvent
import eac.qloga.android.features.p4p.shared.utils.EnrollmentType
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ConfirmAddressScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel()
) {
    val infoMsg = "Your address is required to proceed with the order."
    val infoMsgInput = "You can start by typing postcode or first line of your address"
    val containerHorizontalPadding = 24.dp
    val enrollmentType = viewModel.enrollmentType.value
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
        sheetState = modalBottomSheetState,
        sheetContent = {
            ExistAddressOptions(
                addressList = viewModel.listOfAddress.value,
                selectedAddress = viewModel.selectedAddressIndex.value,
                onClick = {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                        viewModel.onSelectAddressOption(it)
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TitleBar(
                    label = P4pScreens.ConfirmAddress.titleName,
                    iconColor = MaterialTheme.colorScheme.primary,
                ) {
                    navController.navigateUp()
                }
            }
        ) { paddingValues ->

            val topPadding = paddingValues.calculateTopPadding()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { focusManager.clearFocus() }
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(topPadding + 4.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(20.dp)
                                .align(Alignment.CenterStart)
                                .padding(end = 20.dp)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            DottedLine(
                                arcStrokeColor = gray1,
                                vertical = false
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        ){
                            DotCircleArcCanvas(
                                arcStrokeColor = gray1,
                                circleColor = MaterialTheme.colorScheme.primary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(.5f)
                                .height(20.dp)
                                .align(Alignment.CenterEnd)
                                .padding(start = 20.dp)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            DottedLine(
                                arcStrokeColor = gray1,
                                vertical = false
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Box(modifier = Modifier.padding(horizontal = containerHorizontalPadding)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, gray1, RoundedCornerShape(16.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = infoMsg,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SearchInputField(
                        modifier = Modifier.padding(horizontal = containerHorizontalPadding),
                        hint= viewModel.addressFieldState.value.hint,
                        isFocused = viewModel.addressFieldState.value.isFocused,
                        onValueChange = {
                            coroutineScope.launch {
                                viewModel.onTriggerEvent(EnrollmentEvent.EnterAddress(it)) }
                            },
                        onSubmit = {
                            coroutineScope.launch {
                                viewModel.parseAddressFromLatLng(address = viewModel.addressFieldState.value.text)
                            }
                        },
                        value = viewModel.addressFieldState.value.text,
                        onFocusedChanged ={ viewModel.onTriggerEvent(
                            EnrollmentEvent.FocusAddressInput(it)
                        )}
                    )

                    if(enrollmentType == EnrollmentType.PROVIDER){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                                .padding(horizontal = containerHorizontalPadding, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Select from exist address",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Rounded.ArrowForwardIos,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    navController.navigate(P4pScreens.SelectLocationMap.route)
                                }
                            }
                            .padding(horizontal = containerHorizontalPadding, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "My location on map",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Rounded.ArrowForwardIos,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if(viewModel.userAddress.value == null){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = containerHorizontalPadding)
                            ,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = "",
                                tint = info_sky
                            )
                            Text(
                                modifier = Modifier
                                    .alpha(.5f)
                                    .padding(horizontal = containerHorizontalPadding),
                                text = infoMsgInput,
                                style = MaterialTheme.typography.bodySmall,
                                color = info_sky
                            )
                        }
                    }else{
                        Box(modifier = Modifier.padding(horizontal = containerHorizontalPadding)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(2.dp, gray1, RoundedCornerShape(16.dp))
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Line 1",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )

                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = viewModel.userAddress.value?.line1?: "",
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Line 2",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )

                                    /* TODO: anyway this screen should be changed
                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text =  viewModel.userAddress.value?.countryCode ?: "",
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                     */
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Line 3",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )

                                    /* TODO: anyway this screen should be changed
                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = viewModel.userAddress.value?.state ?: "Optional",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if(viewModel.userAddress.value?.state == null){
                                            gray30
                                        } else MaterialTheme.colorScheme.onBackground,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    */
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "City",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )

                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text = viewModel.userAddress.value?.town ?: "No city",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if(viewModel.userAddress.value?.town == null){
                                            gray30
                                        }else MaterialTheme.colorScheme.onBackground,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Postcode",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )

                                    Text(
                                        modifier = Modifier.padding(start = 16.dp),
                                        text =  viewModel.userAddress.value?.postcode ?: "",
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }

                //next button
                FullRoundedButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = containerHorizontalPadding, vertical = 16.dp),
                    buttonText = "Next",
                    textColor = MaterialTheme.colorScheme.background,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                ) {
                    coroutineScope.launch {
                        navController.navigate(P4pScreens.IdVerification.route)
                    }
                }
            }
        }
    }
}