package eac.qloga.android.features.p4p.provider.scenes.servicesConditions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import eac.qloga.android.core.shared.components.Chips.SelectedServiceChip
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.LoadingState
import eac.qloga.android.core.shared.viewmodels.ApiViewModel
import eac.qloga.android.features.p4p.provider.scenes.P4pProviderScreens
import eac.qloga.android.features.p4p.shared.components.ServicesListCard
import eac.qloga.android.features.p4p.showroom.shared.components.DescriptionText
import eac.qloga.android.features.p4p.showroom.shared.components.TopNavBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesConditionsScreen(
    navController: NavController,
    viewModel: ServicesConditionsViewModel = hiltViewModel(),
    apiViewModel: ApiViewModel = hiltViewModel()
) {
    val containerTopPadding = Dimensions.ScreenTopPadding.dp
    val horizontalContentPadding = Dimensions.ScreenHorizontalPadding.dp

    val topNavScrollState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var catChanged by remember{ mutableStateOf(false ) }
    val selectedCategory = ServicesConditionsViewModel.selectNavItem
    val detailText = selectedCategory?.descr?.trimMargin()?.repeat(2)
    val categoriesList = ApiViewModel.categories.value.sortedBy {
        it.sortOrder
    }.sortedBy {
        it.catGroupOrder
    }

    LaunchedEffect(key1 = ApiViewModel.providerServices.value ){
        viewModel.groupServiceCount(ApiViewModel.providerServices.value)
    }

    LaunchedEffect(key1 = Unit){
        apiViewModel.getProviderServices()
        if(categoriesList.isNotEmpty() && selectedCategory == null) viewModel.onChangeCategory(categoriesList[0])
        categoriesList.indexOf(selectedCategory).apply {
            if(this >= 0) topNavScrollState.animateScrollToItem(categoriesList.indexOf(selectedCategory))
        }
    }

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pProviderScreens.ServicesConditions.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Column(
            modifier = Modifier.fillMaxSize()
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //space for title bar
            Spacer(modifier = Modifier.height(titleBarHeight))
            // Top screen Space
            Spacer(modifier = Modifier.height(containerTopPadding))

            TopNavBar(
                onClickItem = {
                    catChanged = !catChanged
                    coroutineScope.launch {
                        delay(200)
                        catChanged = !catChanged
                    }
                    viewModel.onChangeCategory(it)
                },
                lazyListState = topNavScrollState,
                selectedNav = selectedCategory,
                scrollable = true,
                navList = categoriesList
            )

            Column {
                Column(
                    modifier = Modifier.padding(
                        start = horizontalContentPadding,
                        top = 16.dp,
                        end = horizontalContentPadding
                    ),
                    verticalArrangement = Arrangement.Center
                ) {
                    DescriptionText(text = detailText ?: "")
                }

                Spacer(Modifier.height(16.dp))

                if(viewModel.serviceCount.isNotEmpty()){
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background.copy(.92f))
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalContentPadding)
                        ){
                            Text(
                                text = "Selected",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            FlowRow(modifier = Modifier.fillMaxWidth()) {
                                viewModel.serviceCount.forEach { (name, count) ->
                                    SelectedServiceChip(
                                        label = name,
                                        count = count,
                                        onClick = {
                                            coroutineScope.launch {
                                                val serviceCategory = categoriesList.find { it.name == name }
                                                val index = categoriesList.indexOf(serviceCategory)
                                                topNavScrollState.animateScrollToItem(index)
                                                ApiViewModel.categories.value.find { it.name == name }
                                                    ?.let { viewModel.onChangeCategory(it) }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    selectedCategory?.let {
                        ServicesListCard(
                            navController = navController,
                            listOfServices = it.services,
                            expandableItem = false,
                            providerServices = ApiViewModel.providerServices.value,
                            catChanged = catChanged
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}