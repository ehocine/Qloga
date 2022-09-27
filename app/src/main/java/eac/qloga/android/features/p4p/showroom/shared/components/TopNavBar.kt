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
import eac.qloga.android.business.util.Extensions.color
import eac.qloga.p4p.lookups.dto.ServiceCategory
import kotlinx.coroutines.launch

@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    selectedNav: ServiceCategory? = null,
    scrollable: Boolean = true,
    navList: List<ServiceCategory>,
    onClickItem: (ServiceCategory) -> Unit
) {
    val scope = rememberCoroutineScope()
    val lazyScrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        scope.launch {
            selectedNav?.let {
                val index =
                    when (navList.indexOf(it)) {
                        0 -> {
                            navList.indexOf(it)
                        }
                        1 -> {
                            navList.indexOf(it) - 1
                        }
                        else -> {
                            navList.indexOf(it) - 2
                        }
                    }
                lazyScrollState.animateScrollToItem(index)
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
//                    scope.launch {
//                        val index =
//                            when (navList.indexOf(navItems)) {
//                                0 -> {
//                                    navList.indexOf(navItems)
//                                }
//                                1 -> {
//                                    navList.indexOf(navItems) - 1
//                                }
//                                else -> {
//                                    navList.indexOf(navItems) - 2
//                                }
//                            }
//                        lazyScrollState.animateScrollToItem(index)
//                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}
