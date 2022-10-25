package eac.qloga.android.features.p4p.showroom.scenes.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.core.shared.utils.SCREEN_HORIZONTAL_PADDING
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.shared.components.ServicesListCard
import eac.qloga.android.features.p4p.showroom.scenes.P4pShowroomScreens
import eac.qloga.android.features.p4p.showroom.shared.components.DescriptionText
import eac.qloga.android.features.p4p.showroom.shared.components.TopNavBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    parentRoute: String? = null,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {

    val selectedCategory by CategoriesViewModel.selectedNav

    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val horizontalContentPadding = SCREEN_HORIZONTAL_PADDING.dp
    val detailText = selectedCategory?.descr?.trimMargin()?.repeat(2)

    val topNavLazyListState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val categoriesList = ApiViewModel.categories.value.sortedBy {
        it.sortOrder
    }.sortedBy {
        it.catGroupOrder
    }

    var catChanged by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        categoriesList.indexOf(selectedCategory).apply {
            if(this >= 0) topNavLazyListState.animateScrollToItem(categoriesList.indexOf(selectedCategory))
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pShowroomScreens.Categories.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->

        val titleBarHeight = paddingValues.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(titleBarHeight))
            // Top screen Space
            Spacer(modifier = Modifier.height(containerTopPadding))

            TopNavBar(
                onClickItem = {
                    catChanged = !catChanged
                    viewModel.onTriggerEvent(CategoriesEvent.NavItemClick(it))
                    scope.launch {
                        delay(200)
                        catChanged = !catChanged
                    }
                },
                lazyListState = topNavLazyListState,
                selectedNav = CategoriesViewModel.selectedNav.value,
                navList = categoriesList
            )

            LaunchedEffect(key1 = catChanged) {

            }
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = horizontalContentPadding,
                        top = 24.dp,
                        end = horizontalContentPadding
                    ),
                    verticalArrangement = Arrangement.Center
                ) {
                    //expandable description text
                    if (detailText != null) {
                        DescriptionText(text = detailText)
                    }
                }
                //list card
                selectedCategory?.let {
                    ServicesListCard(
                        navController = navController,
                        listOfServices = it.services,
                        catChanged = catChanged
                    )
                }
            }
        }
    }
}