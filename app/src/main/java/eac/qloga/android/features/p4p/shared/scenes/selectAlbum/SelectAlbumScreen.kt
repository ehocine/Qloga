package eac.qloga.android.features.p4p.shared.scenes.selectAlbum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.ListOutAlbums
import eac.qloga.android.features.p4p.showroom.shared.utils.MediaEvent
import eac.qloga.android.features.p4p.shared.viewmodels.MediaViewModel
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun SelectAlbumsScreen(
    navController: NavController,
    viewModel: MediaViewModel,
) {
    val selectedFolder = viewModel.selectedFolder.value
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.SelectAlbum.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                actions = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .clickable {
                                scope.launch {
                                    viewModel.onTriggerEvent(MediaEvent.Move)
                                    navController.navigate(P4pScreens.MediaView.route){
                                        popUpTo(P4pScreens.SelectAlbum.route){
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            .padding(4.dp),
                    ){
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Move",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.W500
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topPadding))
            ListOutAlbums(
                folders = viewModel.folders.value,
                selectedFolder = selectedFolder,
                onSelectFolder = { viewModel.onTriggerEvent(MediaEvent.SelectFolderToMove(it))},
                onCancel = { /*TODO*/ }) {
            }
        }
    }
}