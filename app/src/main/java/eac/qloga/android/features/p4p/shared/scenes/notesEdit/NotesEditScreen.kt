package eac.qloga.android.features.p4p.shared.scenes.notesEdit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.DoneButton
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.features.p4p.shared.components.NotesInputField
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesEditScreen(
    navController: NavController,
    viewModel: NotesEditViewModel = hiltViewModel()
) {
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.NotesEdit.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
                onBackPress = { navController.navigateUp() },
                actions = {
                    DoneButton(
                        onClick = { navController.popBackStack() }
                    )
                }
            )
        }
    ) { paddingValues ->

        val titleBarHeight = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = horizontalContentPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(titleBarHeight))
            // Top screen Space
            Spacer(modifier = Modifier.height(containerTopPadding))

            NotesInputField(
                onValueChange = { viewModel.onEnterNotes(it) },
                onSubmit = {
                    scope.launch {
                        navController.popBackStack()
                    }
                },
                value = viewModel.notes.value.text,
                isFocused = viewModel.notes.value.isFocused,
                onFocusedChanged = { viewModel.onFocusNotes(it) }
            )
        }
    }
}