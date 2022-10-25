package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.utils.Dimensions
import eac.qloga.android.core.shared.utils.Extensions.color
import eac.qloga.p4p.lookups.dto.ServiceCategory

@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    selectedNav: ServiceCategory? = null,
    scrollable: Boolean = true,
    lazyListState: LazyListState,
    navList: List<ServiceCategory>,
    onClickItem: (ServiceCategory) -> Unit
) {
    val screenHorizontalPadding = Dimensions.ScreenHorizontalPadding.dp
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background.copy(.94f))
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background.copy(.94f))
            ,
            userScrollEnabled = scrollable,
            state = lazyListState,
            contentPadding = PaddingValues(start = screenHorizontalPadding, end = 16.dp, bottom = 8.dp)
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
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}