package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.utils.Extensions.color
import eac.qloga.android.data.shared.models.categories.CategoriesResponseItem
import kotlinx.coroutines.launch

@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    selectedNav: CategoriesResponseItem? = null,
    scrollable: Boolean = true,
    navList: List<CategoriesResponseItem>,
    onClickItem: (CategoriesResponseItem) -> Unit
) {
    val scope = rememberCoroutineScope()
    val lazyScrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
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
        ) {
            items(navList, key = { it.name }) { navItems ->
                NavItem(
                    iconUrl = navItems.avatarUrl,
                    label = navItems.name,
                    isSelected = selectedNav == navItems,
                    strokeColor = navItems.catGroupColour.color,
                    BGColor = navItems.catGroupBgColour.color
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
