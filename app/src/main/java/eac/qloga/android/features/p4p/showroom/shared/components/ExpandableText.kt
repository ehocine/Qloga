package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        fontSize = 15.sp
    ),
    expandButtonTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
    minimizedMaxLines: Int = 4,
    expandableText: String = " Learn more",
    textColor: Color = gray30
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val invisibleTextLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }
    var expandBtn by remember { mutableStateOf(true) }

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(invisibleTextLayoutResultState.value) {
        val lineCount = invisibleTextLayoutResultState.value?.lineCount
        lineCount?.let {
            expandBtn = it > minimizedMaxLines
        }
    }

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {

        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.trim().substring(startIndex = 0, endIndex = lastCharIndex - 3) + "..."
        }
    }

    Column {
        Box(modifier.animateContentSize()) {
            // invisible text to count total line of text
            Box(modifier = Modifier
                .alpha(0f)
                .height(16.dp)) {
                Text(
                    text = text.trim(),
                    modifier = Modifier.alpha(0f),
                    style = textStyle,
                    onTextLayout = { invisibleTextLayoutResultState.value = it },
                    maxLines = minimizedMaxLines + 1
                )
            }

            Text(
                text = cutText ?: text.trim(),
                maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResultState.value = it },
                style = textStyle,
                color = textColor
            )

            val density = LocalDensity.current

            if (!expanded && expandBtn) {
                Text(
                    expandableText,
                    onTextLayout = { seeMoreSizeState.value = it.size },
                    modifier = Modifier
                        .then(
                            if (seeMoreOffset != null)
                                Modifier.offset(
                                    x = with(density) { seeMoreOffset.x.toDp() },
                                    y = with(density) { seeMoreOffset.y.toDp() },
                                )
                            else
                                Modifier
                        )
                        .clickable {
                            expanded = true
                            cutText = null
                        }
                        .alpha(if (seeMoreOffset != null) 1f else 0f),
                    style = textStyle,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        if (expanded && expandBtn) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { expanded = false },
                text = "Show less",
                style = textStyle,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpandableText() {
    ExpandableText(text = "Yy ")
}