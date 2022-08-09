package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.features.intro.util.ServiceCategory
import eac.qloga.android.ui.theme.QLOGATheme
import kotlinx.coroutines.launch

@Composable
fun TopNavBar(
    modifier: Modifier= Modifier,
    selectedNav: ServiceCategory? = null,
    scrollable: Boolean = true,
    navList: List<ServiceCategory>,
    onClickItem: (ServiceCategory) -> Unit
){
    val scope = rememberCoroutineScope()
    val lazyScrollState = rememberLazyListState()

    LaunchedEffect(Unit){
        scope.launch {
            selectedNav?.let {
                lazyScrollState.animateScrollToItem(navList.indexOf(it))
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = scrollable,
            state = lazyScrollState,
            contentPadding = PaddingValues(start = 24.dp, end = 16.dp, bottom = 8.dp)
        ){
            items(navList, key = {it.label}){ navItems ->
                NavItem(
                    iconId = navItems.iconId,
                    label = navItems.label,
                    isSelected = selectedNav == navItems
                ) {
                    onClickItem(navItems)
                    scope.launch {
                        lazyScrollState.animateScrollToItem(navList.indexOf(navItems))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Preview
@Composable
fun PreviewTopNav(){
    QLOGATheme(darkTheme = false) {
        TopNavBar(navList = ServiceCategory.listOfItems){}
    }
}