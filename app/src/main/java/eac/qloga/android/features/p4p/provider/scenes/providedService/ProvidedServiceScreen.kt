package eac.qloga.android.features.p4p.provider.scenes.providedService

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.EditTextInputField
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesEvent
import eac.qloga.android.features.p4p.shared.scenes.account.ProfilesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvidedServiceScreen(
    navController: NavController,
    viewModel: ProfilesViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val imageHeight = 130.dp
    val title = "Complete home cleaning"
    val description  = "Internal and external drains and sewers" +
            " repairs including blockage removals, pipe replacements, etc."
    val notifySwitch = remember{ mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ProvidedService.titleName,
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
                .verticalScroll(scrollState)
                .padding(horizontal = containerHorizontalPadding)
        ) {
            Column {
                Spacer(modifier = Modifier.height(topPadding))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Box(modifier = Modifier.fillMaxWidth()){
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(imageHeight)
                            .clip(RoundedCornerShape(12.dp))
                        ,
                        painter = painterResource(id = R.drawable.washing_lady),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.BottomCenter
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, gray1, RoundedCornerShape(16.dp))
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /**TODO */ }
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
                                    text = "($)",
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
                                        value = viewModel.priceInputField.value.text,
                                        textStyle = MaterialTheme.typography.titleMedium.copy(
                                            textAlign = TextAlign.End
                                        ),
                                        keyboardType = KeyboardType.Number,
                                        onValueChange = { viewModel.onTriggerEvent(ProfilesEvent.EnterPrice(it))},
                                        onFocusChange = { viewModel.onTriggerEvent(ProfilesEvent.FocusPrice(it))}
                                    )
                                }
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
                            .clickable { /**TODO */ }
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
                                        value = viewModel.timeNormInputField.value.text,
                                        textStyle = MaterialTheme.typography.titleMedium.copy(
                                            textAlign = TextAlign.End
                                        ),
                                        keyboardType = KeyboardType.Number,
                                        onValueChange = { viewModel.onTriggerEvent(ProfilesEvent.EnterTimeNorm(it))},
                                        onFocusChange = { viewModel.onTriggerEvent(ProfilesEvent.FocusTimeNorm(it))}
                                    )
                                }
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
                                    text = "${viewModel.conditionsCount.value}",
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
                                checked = notifySwitch.value,
                                onCheckedChange = { notifySwitch.value = !notifySwitch.value },
                                colors = SwitchDefaults.colors(
                                    uncheckedBorderColor = gray1,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, gray1, RoundedCornerShape(16.dp))
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /**TODO */ }
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