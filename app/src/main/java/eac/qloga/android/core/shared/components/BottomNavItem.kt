package eac.qloga.android.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.lightBlack

@Composable
fun BottomNavItem(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean = false,
    count: Int = 44,
    icon: Int,
    selectContentColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedTextColor: Color = lightBlack,
    unSelectedIconColor: Color = gray30,
    onClick: () -> Unit
) {
    val textColor = remember(isSelected) { derivedStateOf { if(isSelected) selectContentColor else unSelectedTextColor}}
    val iconColor = remember(isSelected) { derivedStateOf { if(isSelected) selectContentColor else unSelectedIconColor}}

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            Box(modifier = Modifier.padding(4.dp)){
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = iconColor.value
                )
            }
            
            //red notification show box
            if(count > 0){
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .sizeIn(minWidth = 16.dp, minHeight = 16.dp)
                        .clip(CircleShape)
                        .background(Red10),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 2.dp),
                        text = "$count",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 12.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.W600
            ),
            color = textColor.value,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavItem() {
    Box(Modifier.size(100.dp)) {
        BottomNavItem(label = "Orders", icon = R.drawable.ic_ql_orders_take, onClick = {})
    }
}