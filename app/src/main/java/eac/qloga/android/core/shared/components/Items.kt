package eac.qloga.android.core.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Buttons.IOSArrowForwardButton
import eac.qloga.android.core.shared.components.DividerLines.LightDividerLine
import eac.qloga.android.core.shared.theme.gray30

typealias ComposeFun  = @Composable (() -> Unit)

object Items {

    @Composable
    fun OpenRequestListItem(
        modifier: Modifier = Modifier,
        label: String,
        showBottomLine: Boolean = true,
        extraTexts : ComposeFun = {},
    ) {
        ItemOne(
            modifier = modifier,
            label = label,
            contentPadding = PaddingValues(0.dp),
            showBottomLine = showBottomLine,
            extraTexts = extraTexts,
        )
    }

    @Composable
    fun RequestItem(
        modifier: Modifier = Modifier,
        label: String,
        value: String = "",
        isIconClickable: Boolean = true,
        showForwardArrow: Boolean = false,
        showBottomLine: Boolean = true,
        isItemClickable: Boolean,
        leadingIcon: ComposeFun? = null,
        extraTexts : ComposeFun = {},
        editableValue: ComposeFun? = null,
        onClickArrowForward: () -> Unit = {},
        onClickItem: () -> Unit = {}
    ) {
        ItemOne(
            modifier = modifier
                .clickable(isItemClickable) { onClickItem() }
            ,
            label = label,
            value = value,
            isIconClickable = isIconClickable,
            showForwardArrow = showForwardArrow,
            showBottomLine = showBottomLine ,
            leadingIcon = leadingIcon,
            bottomLineRightOffset = if(leadingIcon != null ) 64.dp else 0.dp,
            extraTexts = extraTexts,
            editableValue = editableValue,
            onClickArrowForward =onClickArrowForward,
        )
    }

    @Composable
    fun RequestDetailsItem(
        modifier: Modifier = Modifier,
        label: String,
        value: String = "",
        isIconClickable: Boolean = true,
        isItemClickable: Boolean,
        showForwardArrow: Boolean = false,
        showBottomLine: Boolean = true,
        leadingIcon: ComposeFun? = null,
        extraTexts : ComposeFun = {},
        onClickArrowForward: () -> Unit = {},
        onClickItem: () -> Unit = {}
    ) {
        ItemOne(
            modifier = modifier
                .clickable(enabled = isItemClickable) { onClickItem() }
                .padding(start = 16.dp)
            ,
            label = label,
            value = value,
            isIconClickable = isIconClickable,
            showForwardArrow = showForwardArrow,
            showBottomLine = showBottomLine ,
            leadingIcon = leadingIcon,
            bottomLineRightOffset = if(leadingIcon != null ) 64.dp else 0.dp,
            extraTexts = extraTexts,
            onClickArrowForward =onClickArrowForward,
        )
    }

    @Composable
    fun ServicesListItem(
        modifier: Modifier = Modifier,
        label: String,
        value: String = "",
        isItemClickable: Boolean = true,
        isIconClickable: Boolean = true,
        onClickArrowForward: () -> Unit = {},
        onClickItem: () -> Unit = {}
    ) {
        ItemOne(
            modifier = modifier
                .clickable(isItemClickable) { onClickItem() }
                .padding(start = 16.dp)
            ,
            label = label,
            value = value,
            isIconClickable = isIconClickable,
            showForwardArrow = true,
            showBottomLine = true,
            onClickArrowForward = onClickArrowForward,
        )
    }

    @Composable
    fun PaymentsListItem(
        modifier: Modifier = Modifier,
        label: String,
        value: String = "",
    ) {
        ItemOne(
            modifier = modifier.padding(start = 16.dp),
            label = label,
            value = value,
            bottomLineRightOffset = 64.dp,
            showBottomLine = true,
            isIconClickable = false,
            showForwardArrow = false,
        )
    }

    @Composable
    private fun ItemOne(
        modifier: Modifier = Modifier,
        label: String,
        value: String = "",
        isIconClickable: Boolean = false,
        showForwardArrow: Boolean = false,
        showBottomLine: Boolean = false,
        leadingIcon: ComposeFun? = null,
        contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
        bottomLineRightOffset: Dp = 0.dp,
        extraTexts : ComposeFun? = null,
        editableValue: @Composable (() -> Unit)? = null,
        onClickArrowForward: () -> Unit = {},
    ) {
        Column(modifier = modifier.fillMaxWidth()){
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leadingIcon?.let {
                    Spacer(modifier = Modifier.width(16.dp))
                    it()
                }
                Column(
                    Modifier
                        .weight(1f)
                        .padding(if(!showForwardArrow) contentPadding else PaddingValues(start = 16.dp, end = 8.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = if (showForwardArrow) 8.dp else 12.dp,
                                bottom = if (showForwardArrow) 8.dp else 12.dp
                            )
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .wrapContentWidth()
                            ,
                            text = label,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            extraTexts?.let { 
                                it()
                            }
                            if(value.isNotEmpty()){
                                Box(
                                    modifier = Modifier
                                ) {
                                    if(editableValue != null){
                                        Box(modifier = Modifier.align(Alignment.CenterEnd)){
                                            editableValue()
                                        }
                                    }else{
                                        Text(
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                            ,
                                            text = value,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = gray30,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }
                            }
                        }
                        if(showForwardArrow){
                            IOSArrowForwardButton(onClick = { onClickArrowForward() }, isClickable = isIconClickable)
                        }
                    }
                }
            }
            if(showBottomLine){
                LightDividerLine(Modifier.padding(start = bottomLineRightOffset))
            }
        }
    }
}