package eac.qloga.android.features.p4p.shared.scenes.passport

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Buttons.FullRoundedButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.viewmodels.EnrollmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassportScreen(
    navController: NavController,
    viewModel: EnrollmentViewModel = hiltViewModel()
) {

    val imageId = R.drawable.passport_sample
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val containerHorizontalPadding = 24.dp

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.Passport.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->

        val topPadding = paddingValues.calculateTopPadding()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = containerHorizontalPadding)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(topPadding))

                Spacer(modifier = Modifier.height(containerTopPadding))

                //image & description
                Column(modifier = Modifier
                    .fillMaxHeight(.7f)
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {

                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(id = imageId),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )

                    Text(
                        modifier = Modifier
                            .alpha(.75f)
                            .padding(top = 16.dp)
                        ,
                        text = "Make sure your passport details are clear to read with now blur or glare ",
                        style = MaterialTheme.typography.bodySmall,
                        color = grayTextColor
                    )
                }
            }

            Column(
                modifier= Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                //next button
                FullRoundedButton(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    buttonText = "My password is readable",
                    textColor = MaterialTheme.colorScheme.background,
                    backgroundColor = MaterialTheme.colorScheme.primary,
                ) {
                    //TODO
                }

                //skip button
                FullRoundedButton(
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                    buttonText = "Take a new picture",
                    textColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = Color.Transparent,
                    borderColor = MaterialTheme.colorScheme.primary,
                    showBorder = true
                ) {
                    //TODO
                }
            }
        }
    }
}