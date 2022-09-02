package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.features.p4p.showroom.shared.utils.ServiceCategory
import eac.qloga.android.core.shared.theme.QLOGATheme

@Composable
fun LeftNavBar(
    modifier: Modifier = Modifier,
    selectedNav: ServiceCategory? = null,
    enableClick: Boolean = true,
    topSpace: Dp = 8.dp,
    onClickItem: (ServiceCategory) -> Unit
){
    val scrollState = rememberScrollState()
    val navList = ServiceCategory.listOfItems

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .width(IntrinsicSize.Min)
    ) {
        Spacer(modifier = Modifier.height(topSpace))

        navList.forEach{ navItems ->
            Spacer(modifier = Modifier.height(16.dp))
            NavItem(
                modifier = Modifier.fillMaxWidth(),
                iconId = navItems.iconId,
                label = navItems.label,
                isSelected = selectedNav == navItems,
            ) {
                onClickItem(navItems)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun PreviewLeftNav(){
    QLOGATheme(darkTheme = false) {
        LeftNavBar(){}
    }
}