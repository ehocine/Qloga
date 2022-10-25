package eac.qloga.android.features.p4p.provider.scenes.providedService

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import eac.qloga.android.core.shared.components.*
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.utils.UiEvent
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.provider.shared.utils.ProviderServicesEvents.*
import eac.qloga.android.features.p4p.provider.shared.viewModels.ProviderServicesViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidedServiceScreen(
    navController: NavController,
    viewModel: ProviderServicesViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val imageHeight = 130.dp
    val price = viewModel.priceInputField
    val timeNorm = viewModel.timeNormInputField
    val notify = viewModel.notify
    val title = ProviderServicesViewModel.providedQService?.name ?: ""
    val description  = ProviderServicesViewModel.providedQService?.descr ?: ""
    val conditionsCount = ProviderServicesViewModel.providerConditions.size
    val providerServicesLoadingState by apiViewModel.providerServicesLoadingState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val savingState by viewModel.savingState.collectAsState()

    LaunchedEffect(key1 = providerServicesLoadingState){
        if(providerServicesLoadingState == LoadingState.LOADED){
            viewModel.preLoadCalls()
        }
    }

    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.msg, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(Unit){
        viewModel.preLoadCalls()
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ProvidedService.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    Buttons.SaveButton(
                        onClick = {
                            apiViewModel.getProviderServices()
                            viewModel.onSaveProviderService()
                        },
                        isLoading = savingState == LoadingState.LOADING,
                        textColor = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = containerHorizontalPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Box(modifier = Modifier.fillMaxWidth()){
                    val painter = rememberAsyncImagePainter(
                        model = ProviderServicesViewModel.providedQService?.avatarUrl
                    )

                    if(painter.state is AsyncImagePainter.State.Loading){
                        PulsePlaceholder(
                            modifier = Modifier
                                .height(imageHeight)
                                .fillMaxWidth(),
                            roundedCornerShape = RoundedCornerShape(12.dp)
                        )
                    }

                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(imageHeight)
                            .clip(RoundedCornerShape(12.dp))
                        ,
                        painter = painter,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = title, style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.alpha(.75f),
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color= gray30
                )

                Spacer(modifier = Modifier.height(16.dp))

                Cards.ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Row {
                                    Text(text = "Price", style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        modifier = Modifier.padding(start = 4.dp),
                                        text = "(£)",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )
                                }

                                Row {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                    ) {
                                        EditTextInputField(
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 8.dp)
                                            ,
                                            value = price.text,
                                            hint = price.hint,
                                            hintAlign = TextAlign.End,
                                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                                textAlign = TextAlign.End
                                            ),
                                            keyboardType = KeyboardType.Number,
                                            onValueChange = { viewModel.onTriggerEvent(EnterPrice(it))},
                                            onFocusChange = { viewModel.onTriggerEvent(FocusPrice(it))}
                                        )
                                    }
                                }
                            }
                            Divider(
                                Modifier
                                    .height(1.dp)
                                    .alpha(.2f)
                                    .padding(start = 38.dp)
                                    .background(gray1)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Row {
                                    Text(text = "Time norm", style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        modifier = Modifier.padding(start = 4.dp),
                                        text = "(min)",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )
                                }

                                Row {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                    ) {
                                        EditTextInputField(
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 8.dp)
                                            ,
                                            value = timeNorm.text,
                                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                                textAlign = TextAlign.End
                                            ),
                                            keyboardType = KeyboardType.Number,
                                            onValueChange = { viewModel.onTriggerEvent(EnterTimeNorm(it))},
                                            onFocusChange = { viewModel.onTriggerEvent(FocusTimeNorm(it))}
                                        )
                                    }
                                }
                            }
                            Divider(
                                Modifier
                                    .height(1.dp)
                                    .alpha(.2f)
                                    .padding(start = 38.dp)
                                    .background(gray1)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        navController.navigate(P4pProviderScreens.ProvidedServiceConditions.route)
                                    }
                                }
                        ){
                            Row(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Row {
                                    Text(text = "Conditions", style = MaterialTheme.typography.titleMedium)
                                }

                                Row {
                                    Text(
                                        modifier = Modifier.padding(end = 8.dp),
                                        text = "$conditionsCount",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = gray30
                                    )
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = Icons.Rounded.ArrowForwardIos,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Divider(
                                Modifier
                                    .height(1.dp)
                                    .alpha(.2f)
                                    .padding(start = 38.dp)
                                    .background(gray1)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Row(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(text = "Notify", style = MaterialTheme.typography.titleMedium)
                                Switch(
                                    modifier = Modifier.height(24.dp),
                                    checked = notify,
                                    onCheckedChange = { viewModel.onTriggerEvent(ToggleNotify) },
                                    colors = SwitchDefaults.colors(
                                        uncheckedBorderColor = gray1,
                                        uncheckedTrackColor = MaterialTheme.colorScheme.background
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Cards.ContainerBorderedCard {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(P4pShowroomScreens.ServiceContract.route)
                                }
                        ){
                            Row(
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                ,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = "Full service contract",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Rounded.ArrowForwardIos,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}