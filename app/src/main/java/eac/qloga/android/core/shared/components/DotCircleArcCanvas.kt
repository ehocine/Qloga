package eac.qloga.android.core.shared.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.QLOGATheme

@Composable
fun DotCircleArcCanvas(
    modifier: Modifier = Modifier,
    arcStrokeColor: Color = Color.Gray,
    circleColor: Color = Color.Green,
    strokeWidth: Float = 8f,
) {
    Column(
        modifier = modifier
    ) {

        Canvas(
            modifier = Modifier
                .size(200.dp)
                .padding(4.dp)
        ){

            val canvasWidth = size.width
            val canvasHeight = size.height
            val circleRadius = canvasWidth / 4
            val arcStrokeWidth = circleRadius/3

            drawCircle(
                color = circleColor,
                radius = circleRadius
            )


            drawArc(
                color = arcStrokeColor,
                size = Size(canvasWidth,canvasHeight),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = true,
                style = Stroke(width = arcStrokeWidth)
            )


        }




    }


}


@Preview(showBackground = true)
@Composable
fun PreviewDotCircleArcCanvas(){
    QLOGATheme(darkTheme = false, dynamicColor = false) {
        DotCircleArcCanvas()
    }
}