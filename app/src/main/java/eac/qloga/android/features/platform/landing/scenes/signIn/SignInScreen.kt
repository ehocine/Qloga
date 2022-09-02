package eac.qloga.android.features.platform.landing.scenes.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.core.shared.viewmodels.AuthenticationViewModel
import eac.qloga.android.features.platform.landing.scenes.LandingScreens


/*
@Composable
fun SignIn(
    actions: NavigationActions,
    authViewModel: AuthenticationViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)

    val getEnrollsState by apiViewModel.getEnrollsLoadingState.collectAsState()
    val responseEnrollsModel by apiViewModel.responseEnrollsModel

    LaunchedEffect(key1 = oktaState is BrowserState.LoggedIn) {
        apiViewModel.getEnrolls()
    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to QLOGA!",
                color = green1,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 25.sp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                painter = painterResource(id = R.drawable.family_together),
                contentDescription = ""
            )
            when (oktaState) {
                is BrowserState.Loading -> {
                    CircularProgressIndicator(color = green1)
                }
                is BrowserState.LoggedIn -> {
                    when (getEnrollsState) {
                        LoadingState.LOADING -> {
                            CircularProgressIndicator(color = green1)
                        }
                        LoadingState.LOADED -> {
                            if (responseEnrollsModel.CUSTOMER != null || responseEnrollsModel.PROVIDER != null) {
                                actions.goToOrderLisrPrv.invoke()

                            } else {
                                actions.goToIntro.invoke()
                            }
                        }
                        else -> Unit
                    }
                }
                else -> {
                    Buttons(
                        oktaOnclick = {
                            authViewModel.oktaLogin(context)
                        },
                        appleSignIn = {
                            authViewModel.appleLogin(context)
                        },
                        googleSignIn = {
                            authViewModel.googleLogin(context)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Buttons(
    oktaOnclick: () -> Unit,
    appleSignIn: () -> Unit,
    googleSignIn: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.padding(10.dp))
        Image(
            painter = painterResource(id = R.drawable.qloga_button),
            contentDescription = "Qloga button",
            modifier = Modifier.clickable {
                oktaOnclick()
            }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Image(
            painter = painterResource(id = R.drawable.appleid_button),
            contentDescription = "Apple button",
            modifier = Modifier.clickable {
                appleSignIn()
            }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Button(onClick = { googleSignIn() }) {
            Text(text = "Sign in with Google")
        }
    }

}



 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(
    navController: NavController,
    authViewModel: AuthenticationViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val containerHorizontalPadding = 24.dp
    val context = LocalContext.current

    Scaffold { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                ,
                painter = painterResource(id = R.drawable.curvy_wave_back_3),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                ,
                painter = painterResource(id = R.drawable.curvy_wave_back_2),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                androidx.compose.material3.Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                    ,
                    text = stringResource(id = R.string.welcome_to_qloga),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp , top = 16.dp ),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        painter = painterResource(id = R.drawable.qloga_log_family),
                        contentDescription =  null ,
                        contentScale = ContentScale.FillWidth
                    )
                }
                Spacer(Modifier.height(32.dp))
                FullRoundedButton(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    content = {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            androidx.compose.material3.Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "Sign Up",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.background
                            )
                            Buttons.IOSArrowForwardButton(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 16.dp)
                                ,
                                isClickable = false,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    },
                    onClick = {
                        navController.navigate(LandingScreens.Signup.route)
                    }
                )
                Spacer(Modifier.height(32.dp))

                FullRoundedButton(
                    showBorder = true,
                    borderColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(id = R.drawable.ic_ql_small_qloga_logo),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            androidx.compose.material3.Text(
                                text = "Sign in with QLOGA",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    onClick = {
                        authViewModel.oktaLogin(context)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                FullRoundedButton(
                    showBorder = true,
                    borderColor = info_sky,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(id = R.drawable.ic_ql_google),
                                contentDescription = null,
                                tint = info_sky
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            androidx.compose.material3.Text(
                                text = "Sign in with Google",
                                style = MaterialTheme.typography.titleMedium,
                                color = info_sky
                            )
                        }
                    },
                    onClick = {
                        authViewModel.googleLogin(context)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                FullRoundedButton(
                    showBorder = true,
                    borderColor = Color.Black,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(id = R.drawable.ic_ql_apple_logo),
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            androidx.compose.material3.Text(
                                text = "Sign in with Apple",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                        }
                    },
                    onClick = {
                        authViewModel.appleLogin(context)
                    }
                )
            }
        }
    }
}