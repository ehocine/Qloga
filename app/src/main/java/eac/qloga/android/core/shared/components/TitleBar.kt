package eac.qloga.android.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.business.util.Extensions.advancedShadow
import eac.qloga.android.core.shared.components.Buttons.SortButton
import eac.qloga.android.data.shared.models.MediaSize

@Composable
fun TitleBar(
    label : String ,
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Black,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    showBottomBorder: Boolean = false,
    showBackPressButton: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    leadingActions: @Composable (() -> Unit) = {},
    actions: @Composable (() -> Unit ) = {},
    onBackPress: () -> Unit = {}
) {
    val titleBarHeight = 60.dp
    val brushBackground = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background.copy(.96f),
            MaterialTheme.colorScheme.background.copy(.96f),
            MaterialTheme.colorScheme.background.copy(.96f),
            MaterialTheme.colorScheme.background.copy(.96f),
            MaterialTheme.colorScheme.background.copy(.96f),
            Color.Transparent,
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = titleBarHeight)
            .advancedShadow(
                color = if (showBottomBorder) Color.Gray else Color.Transparent,
                .8f,
                0.dp,
                if (showBottomBorder) 1.dp else 0.dp
            )
            .background(backgroundColor.copy(.95f))
            .padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        if(showBackPressButton){
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = { onBackPress()}
            ) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    imageVector = Icons.Rounded.ArrowBackIosNew,
                    contentDescription = "Back arrow ",
                    tint = iconColor
                )
            }
        }

        //leading actions
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = 13.dp, start = 16.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingActions()
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = label,
            style = titleTextStyle.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W600
            ),
            color = MaterialTheme.colorScheme.onBackground.copy(.7f),
            textAlign = TextAlign.Center,
        )

        //actions
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 13.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitle(){
    TitleBar(
        "Provider Search",
        showBackPressButton = false,
        actions = { SortButton(onClick = { /*TODO*/ }, color = Color.Red) }
    )
}