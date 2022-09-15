package eac.qloga.android.features.p4p.shared.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.lightGrayBackground
import kotlin.math.roundToInt

@Composable
fun TwoSwitchTabRow(
    modifier: Modifier = Modifier,
    selectedTapIndex: Int = 1,
    selectedColor: Color,
    height: Dp = 36.dp,
    containerColor: Color = lightGrayBackground,
    tabsContent: List<String>  = emptyList(),
    onSelect: (Int) -> Unit
) {
    val roundedCornerRadius = 8.dp
    val tabWidth = remember { mutableStateOf(0) }
    val tabOffset = derivedStateOf { if (selectedTapIndex == 0) 0 else tabWidth.value }
    val animateTabOffset by animateFloatAsState(
        targetValue = tabOffset.value.toFloat(),
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(containerColor)
    ) {
        Box(
            modifier = Modifier
                .height(height)
                .fillMaxWidth(.5f)
                .onGloballyPositioned { layoutCoordinates ->
                    tabWidth.value = layoutCoordinates.size.width
                }
                .offset { IntOffset(x = animateTabOffset.roundToInt(), y = 0) }
                .align(Alignment.CenterStart)
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(roundedCornerRadius))
                .background(selectedColor),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(roundedCornerRadius))
                .background(Color.Transparent)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabsContent.forEachIndexed { index, label ->
                val isSelected = index == selectedTapIndex
                Box(
                    modifier = Modifier
                        .height(height)
                        .weight(.5f)
                        .clip(RoundedCornerShape(roundedCornerRadius))
                        .clickable { onSelect(index) }
                        ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccountType(){
    TwoSwitchTabRow(onSelect = {}, selectedColor = MaterialTheme.colorScheme.primary, )
}