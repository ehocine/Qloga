package eac.qloga.android.features.p4p.shared.scenes.orderNotes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.shared.components.OrdersTab
import eac.qloga.android.features.p4p.shared.scenes.P4pScreens
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrderNoteType
import eac.qloga.android.features.p4p.shared.viewmodels.OrderViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun OrderNotesScreen(
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel(),
    accountType: String? = null
) {
    val providerNote = "Internal and external drains and " +
            "sewers repairs including blockage removals," +
            " pipe replacements, etc."
    val customerNote = "Customer Internal and external drains and " +
            "sewers repairs including blockage removals," +
            " pipe replacements, etc."
    val yourNote = "Your Internal and external drains and " +
            "sewers repairs including blockage removals," +
            " pipe replacements, etc."
    val tabHeight = 54.dp
    val noteText = remember{ mutableStateOf("") }
    val  notesTab = remember{ mutableStateOf(listOf(OrderNoteType.PROVIDER, OrderNoteType.YOUR))}

    val pagerState = rememberPagerState()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        viewModel.orderNoteType(OrderNoteType.PROVIDER)
        if(accountType == AccountType.CUSTOMER.label){
            notesTab.value = listOf(OrderNoteType.PROVIDER, OrderNoteType.YOUR)
            noteText.value = customerNote
        }else if(accountType == AccountType.PROVIDER.label){
            notesTab.value = listOf(OrderNoteType.CUSTOMER, OrderNoteType.YOUR)
            noteText.value = providerNote
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pScreens.OrderNotes.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val topPadding = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(topPadding))

            TabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(tabHeight)
                ,
                selectedTabIndex = pagerState.currentPage
            ) {
                notesTab.value.forEachIndexed { index, orderNoteType ->
                    OrdersTab(
                        selected = index == pagerState.currentPage,
                        text = orderNoteType.label,
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = gray30,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(
                modifier = Modifier.weight(1f),
                count = notesTab.value.size,
                state = pagerState
            ) { page ->
                when(page){
                    0 -> {
                        NoteContent(contentText = noteText.value)
                    }

                    1 -> {
                        NoteContent(contentText = yourNote)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteContent(
    modifier: Modifier = Modifier,
    contentText: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = contentText,
            style = MaterialTheme.typography.titleMedium,
            color = gray30
        )
    }
}