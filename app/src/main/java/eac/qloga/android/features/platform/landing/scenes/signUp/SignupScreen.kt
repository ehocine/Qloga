package eac.qloga.android.features.platform.landing.scenes.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.InputFields
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.BUTTON_HEIGHT
import eac.qloga.android.core.shared.utils.PickerDialog
import eac.qloga.android.features.platform.landing.scenes.LandingScreens
import eac.qloga.bare.enums.Gender
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val name = viewModel.firstName.value
    val familyName = viewModel.familyName.value
    val emailAddress = viewModel.emailAddress.value
    val birthday = viewModel.birthday.value
    val gender =viewModel.gender.value
    val isAgreeChecked = remember{ mutableStateOf(false)}
    val buttonHeight = BUTTON_HEIGHT.dp
    val interactionSource = remember{ MutableInteractionSource() }
    val clickedApply = remember { mutableStateOf(false) }
    val showGenderOptions = remember{ mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val screenHeightDp = remember{ mutableStateOf(0)}

    with(LocalDensity.current){
        screenHeightDp.value = LocalConfiguration.current.screenHeightDp - 240
    }

    Scaffold { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Column{
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    painter = painterResource(id = R.drawable.curvy_wave_back_1),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(screenHeightDp.value.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    painter = painterResource(id = R.drawable.curvy_wave_back_2),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                Spacer(modifier = Modifier.padding(top = 64.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        modifier = Modifier.size(64.dp),
                        painter = painterResource(R.drawable.qloga_small_logo),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.padding(top = 48.dp))
                Buttons.FullRoundedButton(
                    showBorder = true,
                    borderColor = if(clickedApply.value && name.text.isEmpty()) {
                        Red10.copy(alpha = .2f)
                    }else gray30.copy(alpha = .2f),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    content = {
                        InputFields.TextInputField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                            ,
                            singleLine = true,
                            onValueChange = { viewModel.onTriggerEvent(SignupEvents.EnterFirstName(it)) },
                            value = name.text,
                            onFocusedChanged = { viewModel.onTriggerEvent(SignupEvents.FocusFirstName(it))},
                            hint = name.hint
                        )
                    },
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                Buttons.FullRoundedButton(
                    showBorder = true,
                    borderColor = if(clickedApply.value && familyName.text.isEmpty()) {
                        Red10.copy(alpha = .2f)
                    }else gray30.copy(alpha = .2f),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    content = {
                        InputFields.TextInputField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                            ,
                            singleLine = true,
                            onValueChange = { viewModel.onTriggerEvent(SignupEvents.EnterFamilyName(it))},
                            value = familyName.text,
                            onFocusedChanged = { viewModel.onTriggerEvent(SignupEvents.FocusFamilyName(it))},
                            hint = familyName.hint
                        )
                    },
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                Buttons.FullRoundedButton(
                    showBorder = true,
                    borderColor = if(clickedApply.value && emailAddress.text.isEmpty()) {
                        Red10.copy(alpha = .2f)
                    }else gray30.copy(alpha = .2f),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    content = {
                        InputFields.TextInputField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                            ,
                            singleLine = true,
                            onValueChange = { viewModel.onTriggerEvent(SignupEvents.EnterEmailAddress(it))},
                            value = emailAddress.text,
                            onFocusedChanged = { viewModel.onTriggerEvent(SignupEvents.FocusEmailAddress(it))},
                            hint = emailAddress.hint
                        )
                    },
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .clickable {
                                coroutineScope.launch {
                                    PickerDialog.showDatePickerDialog(context, onSetDate = {
                                        viewModel.onTriggerEvent(SignupEvents.EnterBirthday(it))
                                    })
                                }
                            }
                            .height(buttonHeight)
                            .border(
                                width = 1.4.dp,
                                color = if (clickedApply.value && birthday.isNullOrEmpty()) {
                                    Red10.copy(alpha = .2f)
                                } else gray30.copy(alpha = .2f),
                                shape = CircleShape
                            )
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        if(birthday.isNullOrEmpty()){
                            Text(
                                text = "Birthday",
                                color = gray30,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }else{
                            Text(
                                text = birthday,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .clip(CircleShape)
                            .clickable { showGenderOptions.value = true }
                            .height(buttonHeight)
                            .border(
                                width = 1.4.dp,
                                color = if (clickedApply.value && gender == null) {
                                    Red10.copy(alpha = .2f)
                                } else gray30.copy(alpha = .2f),
                                shape = CircleShape
                            )
                            .padding(horizontal = 16.dp)
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if(showGenderOptions.value){
                            Popup(
                                alignment = Alignment.BottomEnd,
                                properties = PopupProperties(),
                                offset = IntOffset(y = 0,x = 0),
                                onDismissRequest = { showGenderOptions.value = false }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .shadow(
                                            2.dp,
                                            shape = RoundedCornerShape(16.dp),
                                            spotColor = Color.Black
                                        )
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                                        Gender.values().forEach { gender ->
                                            Box(modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    coroutineScope.launch {
                                                        showGenderOptions.value = false
                                                        viewModel.onTriggerEvent(
                                                            SignupEvents.EnterGender(
                                                                gender
                                                            )
                                                        )
                                                    }
                                                }
                                                .padding(
                                                    start = 20.dp,
                                                    end = 20.dp,
                                                    top = 12.dp,
                                                    bottom = 12.dp
                                                )){
                                                Text(text = gender.toString(), style = MaterialTheme.typography.titleSmall)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(gender == null){
                            Text(
                                text = "Gender",
                                color = gray30,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Icon(
                                modifier = Modifier
                                    .size(18.dp)
                                    .rotate(90f),
                                imageVector = Icons.Rounded.ArrowForwardIos,
                                contentDescription = null,
                                tint = gray30.copy(alpha = .6f)
                            )
                        }else{
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = gender.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Checkbox(
                            checked = isAgreeChecked.value,
                            onCheckedChange = { isAgreeChecked.value = !isAgreeChecked.value },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = gray30.copy(alpha = .3f),
                                checkedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Row() {
                            Text(
                                text = "Agree with",
                                style = MaterialTheme.typography.titleMedium,
                                color = gray30
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    //navController.navigate(Screen.SignupTermsConditions.route)
                                }
                                ,
                                text = "terms and conditions",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Buttons.FullRoundedButton(
                        buttonText = "Apply"
                    ) {
                        val areFieldEmpty = viewModel.signUpApply()
                        if(areFieldEmpty && isAgreeChecked.value){
                            navController.navigate(LandingScreens.PostSignup.route)
                        }
                        clickedApply.value = true
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                //navController.navigate(Screen.DataPrivacy.route)
                            }
                        ,
                        text = "Data privacy",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}