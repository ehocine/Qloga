package eac.qloga.android.features.platform.landing.scenes.postSignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.features.platform.landing.scenes.LandingScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostSignupScreen(
    navController: NavController
) {
    val containerHorizontalPadding = 24.dp
    val textFirst = "Your registration request has been successfully sent."
    val textSecond = "To enjoy full QLoGa experience please follow instruction in the received email."
    val textThird = "By completing signing in you will be able to invite other members" +
            " of your family to join you. In case if you wish to join existing family " +
            "you need to obtain the invite from the family main subscriber."

    Scaffold { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                ,
                painter = painterResource(id = R.drawable.curvy_wave_back_1),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
//            Spacer(modifier = Modifier.weight(1f))
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
                Spacer(modifier = Modifier.padding(top = 64.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        modifier = Modifier,
                        painter = painterResource(R.drawable.post_signup_avatar),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                    ,
                    text = textFirst,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
                    color = info_sky,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                    ,
                    text = textSecond,
                    style = MaterialTheme.typography.titleMedium,
                    color = gray30,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                    ,
                    text = textThird,
                    style = MaterialTheme.typography.labelLarge,
                    color = gray30,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Buttons.FullRoundedButton(
                    buttonText = "Let's start!"
                ) {
                    navController.navigate(LandingScreens.SignIn.route)
                }
            }
        }
    }
}