package eac.qloga.android.core.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.core.shared.utils.BUTTON_HEIGHT

private val iconButtonSize = 22.dp
private val defaultTitleBarBtnTextStyle = @Composable {
    MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.W600,
        fontSize = 17.sp
    )
}

object Buttons {
    @Composable
    fun FilterButton(onClick: () -> Unit, color: Color) {
        IconButton(onClick = { onClick() }, color = color, iconId = R.drawable.ic_filter)
    }

    @Composable
    fun MapButton(onClick: () -> Unit, color: Color) {
        IconButton(onClick = { onClick() }, color = color, iconId = R.drawable.ic_map_check)
    }

    @Composable
    fun ProviderUserButton(onClick: () -> Unit, color: Color = info_sky) {
        IconButton(onClick = { onClick() }, color = color, iconId = R.drawable.ic_ql_prv_user)
    }

    @Composable
    fun UserButton(onClick: () -> Unit, color: Color) {
        IconButton(onClick = { onClick() }, color = color, iconId = R.drawable.ic_ql_person)
    }

    @Composable
    fun InfoButton(onClick: () -> Unit, color: Color) {
        IconButton(onClick = { onClick() }, color = color, iconId = R.drawable.ic_info)
    }

    @Composable
    fun SortButton(onClick: () -> Unit, color: Color) {
        Box(modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
        ) {
            Icon(
                imageVector = Icons.Rounded.Sort,
                contentDescription = "Sort",
                tint = color
            )
        }
    }

    @Composable
    fun IOSArrowForwardButton(
        modifier: Modifier = Modifier,
        iconSize: Dp = 16.dp,
        isClickable: Boolean = true,
        color: Color = MaterialTheme.colorScheme.primary,
        onClick: () -> Unit = {}
    ) {
        Box(modifier = modifier
            .clip(CircleShape)
            .clickable(isClickable) { onClick() }
            .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = Icons.Rounded.ArrowForwardIos,
                contentDescription = null,
                tint = color
            )
        }
    }

    @Composable
    private fun IconButton(
        onClick: () -> Unit,
        color: Color,
        iconSize: Dp? = null,
        iconId: Int
    ) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize ?: iconButtonSize),
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = color
            )
        }
    }

    @Composable
    fun FullRoundedButton(
        modifier: Modifier = Modifier,
        buttonText: String = "",
        backgroundColor: Color = MaterialTheme.colorScheme.primary,
        textColor: Color = Color.White,
        showBorder: Boolean = false,
        borderColor: Color = Color.Gray,
        enabled: Boolean = true,
        content: ComposeFun? = null,
        onClick: () -> Unit
    ) {
        val buttonHeight = BUTTON_HEIGHT.dp

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .clip(CircleShape)
                .alpha(if (!enabled) 0.5f else 1f)
                .clickable(enabled = enabled) { onClick() }
                .border(
                    width = if (showBorder) 1.4.dp else 0.dp,
                    color = if (showBorder) borderColor else Color.Transparent,
                    shape = CircleShape
                )
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (content != null) {
                content()
            } else {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
        }
    }

    @Composable
    fun AddNewButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        textStyle: TextStyle = defaultTitleBarBtnTextStyle(),
        textColor: Color = orange1
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(4.dp),
        ) {
            Text(
                text = "Add new",
                color = textColor,
                style = textStyle,
                fontWeight = FontWeight.W600
            )
        }
    }

    @Composable
    fun ByStatusButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        textStyle: TextStyle = defaultTitleBarBtnTextStyle(),
        textColor: Color = MaterialTheme.colorScheme.primary
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(8.dp)
        ) {
            Text(
                text = "By Status",
                style = textStyle.copy(fontSize = 16.sp),
                color = textColor,
            )
        }
    }

    @Composable
    fun AddButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        textStyle: TextStyle = defaultTitleBarBtnTextStyle(),
        textColor: Color = orange1
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(8.dp),
        ) {
            Text(
                text = "Add",
                color = textColor,
                style = textStyle
            )
        }
    }

    @Composable
    fun SaveButton(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        textStyle: TextStyle = defaultTitleBarBtnTextStyle(),
        textColor : Color = orange1
    ){
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() }
                .padding(8.dp),
        ) {
            Text(
                text = "Save",
                color = textColor,
                style = textStyle
            )
        }
    }
}

@Composable
fun LocationMarkerIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                id = R.drawable.ic_location_point
            ),
            contentDescription = "",
        )
    }
}

@Composable
fun DoneButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textStyle: TextStyle = defaultTitleBarBtnTextStyle(),
    textColor: Color = orange1
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = "Done",
            style = textStyle,
            color = textColor,
        )
    }
}

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textStyle: TextStyle = defaultTitleBarBtnTextStyle(),
    isLoading: Boolean
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                if (!isLoading) { onClick() }
            }
            .padding(8.dp),
    ) {
        val textColor: Color = if (!isLoading) orange1 else gray1
        Text(
            text = "Done",
            color = textColor,
            style = textStyle
        )
    }
}