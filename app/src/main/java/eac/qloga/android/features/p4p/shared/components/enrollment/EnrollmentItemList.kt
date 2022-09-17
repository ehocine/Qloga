package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.DotCircleArcCanvas
import eac.qloga.android.core.shared.components.DottedLine
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun EnrollmentItemList(
    modifier: Modifier = Modifier,
    label: String,
    onClickInfo: () -> Unit
) {
    val containerHorizontalPadding = 24.dp
    val dottedLineHeight = 70.dp
    val dotCircleSize = 40.dp


    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = containerHorizontalPadding)
            ,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                Modifier.size(dotCircleSize)
            ) {
                DotCircleArcCanvas(arcStrokeColor = gray1, circleColor = gray1)
            }

            Box(modifier = Modifier
                .weight(1f)
                .padding(start = 18.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onClickInfo() }
                ,
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = "",
                tint = gray1
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .width(dotCircleSize)
                    .height(dottedLineHeight)
            ) {
                DottedLine(arcStrokeColor = gray1, vertical = true)
            }

            Divider(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 48.dp)
                    .alpha(.1f)
                    .height(1.dp)
                    .background(gray1)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEnrollmentItem(){
    EnrollmentItemList(label = "Verify phone", onClickInfo = {})
}