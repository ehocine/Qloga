package eac.qloga.android.core.shared.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DottedLine(
    modifier: Modifier = Modifier,
    arcStrokeColor: Color = Color.Gray,
    strokeWidth : Float = 6f,
    vertical: Boolean = true
){
    val dottedPathEffect = PathEffect.dashPathEffect(floatArrayOf(6f,20f),4f)

    Canvas(modifier = modifier.fillMaxSize()){

        val canvasWidth = size.width
        val canvasHeight = size.height

        val vertStartOffset = Offset(canvasWidth/2, 0f)
        val vertEndOffset = Offset(canvasWidth/2, canvasHeight)
        val horiStartOffset = Offset(0f,canvasHeight/2)
        val horiEndOffset = Offset(canvasWidth, canvasHeight/2)
        val startOffset = if(vertical) vertStartOffset else horiStartOffset
        val endOffset = if(vertical) vertEndOffset else horiEndOffset

        drawLine(
            cap = StrokeCap.Round,
            strokeWidth = strokeWidth,
            color = arcStrokeColor,
            start = startOffset,
            end = endOffset,
            pathEffect = dottedPathEffect
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDottedLine(){
    DottedLine()
}