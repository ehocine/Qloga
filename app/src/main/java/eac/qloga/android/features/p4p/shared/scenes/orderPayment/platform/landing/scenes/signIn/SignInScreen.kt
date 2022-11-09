package eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import eac.qloga.android.R
import eac.qloga.android.core.services.BrowserState
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.theme.green1
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.utils.PreTransition
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.core.shared.viewmodels.AuthenticationViewModel
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.shared.scenes.orderPayment.platform.landing.scenes.LandingScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(
    navController: NavController,
    authViewModel: AuthenticationViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val oktaState by authViewModel.oktaState.collectAsState(BrowserState.Loading)

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = oktaState is BrowserState.LoggedIn) {
        apiViewModel.getEnrolls()
    }

    val containerHorizontalPadding = 24.dp

    Scaffold { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.gray_vawe),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .rotate(180f)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.gray_vawe),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    text = stringResource(id = R.string.welcome_to_qloga),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.qloga_log_family),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
                Spacer(Modifier.height(32.dp))


                when (oktaState) {
                    is BrowserState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = green1)
                        }
                    }
                    is BrowserState.LoggedIn -> {
                        if (isLoading) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = green1)
                            }
                        }
                        LaunchedEffect(key1 = true) {
                            apiViewModel.preCallsLoad()
                        }
                        PreTransition(
                            apiViewModel = apiViewModel,
                            loadingState = {
                                isLoading = it
                            },
                            checkAddress = { hasAddress ->
                                if (!hasAddress) {
                                    navController.navigate(LandingScreens.NoAddress.route) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }
                            },
                            checkEnrollment = { isEnrolled ->
                                if (isEnrolled) {
                                    navController.navigate(
                                        P4pShowroomScreens.Enrolled.route
                                    ) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(
                                        P4pShowroomScreens.NotEnrolled.route
                                    ) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                }

                            })
                    }
                    else -> {
                        FullRoundedButton(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            content = {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier.align(Alignment.Center),
                                        text = "Sign Up",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                    Buttons.IOSArrowForwardButton(
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 16.dp),
                                        isClickable = true,
                                        color = MaterialTheme.colorScheme.background
                                    ) {
                                        navController.navigate(LandingScreens.Signup.route)
                                    }
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
                                    Text(
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
                                    Text(
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
                                    Text(
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
    }
}