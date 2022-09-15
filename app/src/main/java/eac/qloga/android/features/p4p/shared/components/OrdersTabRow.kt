package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.features.p4p.shared.utils.OrdersTabTypes

@Composable
fun OrdersTabRow(
    modifier: Modifier = Modifier,
    ordersTabsItems: List<OrdersTabTypes>,
    selectedTabItem: OrdersTabTypes,
    onSelectTabItem: (OrdersTabTypes) -> Unit
) {
    val tabHeight = 54.dp

    TabRow(
        modifier = modifier
            .fillMaxWidth()
            .height(tabHeight)
        ,
        selectedTabIndex = OrdersTabTypes.listOfItems.indexOf(selectedTabItem)
    ) {
        ordersTabsItems.forEach { type ->
            OrdersTab(
                selected = type == selectedTabItem,
                text = type.label,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = gray30,
                onClick = {
                    onSelectTabItem(type)
                }
            )
        }
    }
}

@Composable
fun OrdersTab(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String,
    selectedContentColor: Color,
    unselectedContentColor: Color,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = if(selected) FontWeight.W600 else FontWeight.W500,
            color = if(selected) selectedContentColor else unselectedContentColor
        )
    }
}