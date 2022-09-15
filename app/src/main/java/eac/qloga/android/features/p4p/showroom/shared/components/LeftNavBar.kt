package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.business.util.Extensions.color
import eac.qloga.p4p.lookups.dto.ServiceCategory

@Composable
fun LeftNavBar(
    modifier: Modifier = Modifier,
    selectedNav: ServiceCategory? = null,
    navList: List<ServiceCategory>,
    enableClick: Boolean = true,
    topSpace: Dp = 8.dp,
    onClickItem: (ServiceCategory) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .width(IntrinsicSize.Min)
    ) {
        Spacer(modifier = Modifier.height(topSpace))

        navList.forEach { navItems ->
            Spacer(modifier = Modifier.height(16.dp))
            NavItem(
                modifier = Modifier
                    .fillMaxWidth(),
                iconUrl = navItems.avatarUrl,
                label = navItems.name,
                isSelected = selectedNav == navItems,
                strokeColor = navItems.catGroupColour.color,
                BGColor = navItems.catGroupBgColour.color
            ) {
                onClickItem(navItems)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}