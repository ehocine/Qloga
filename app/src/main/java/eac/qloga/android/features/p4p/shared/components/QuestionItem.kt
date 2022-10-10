package eac.qloga.android.features.p4p.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.DividerLines.DividerLine
import eac.qloga.android.core.shared.theme.gray30

@Composable
fun QuestionItem(
    modifier: Modifier = Modifier,
    question: String,
    answer: String,
    expandable: Boolean = true,
) {
    val iconSize = 18.dp
    val expanded = remember { mutableStateOf(false) }
    val animateArrowIconAngle  = animateFloatAsState(targetValue = if(expanded.value) -90f else 90f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(expandable) {
                expanded.value = !expanded.value
            }
            .padding(start = 16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp, end = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = question,
                        style = MaterialTheme.typography.titleMedium
                    )

                    if(expandable){
                        Icon(
                            modifier = Modifier
                                .size(iconSize)
                                .rotate(animateArrowIconAngle.value),
                            imageVector = Icons.Rounded.ArrowForwardIos,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                AnimatedVisibility(
                    visible = expanded.value,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = answer,
                        style = MaterialTheme.typography.bodySmall,
                        color = gray30
                    )
                }
            }
            DividerLine( Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 64.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewFaqQuestionItem() {
    QuestionItem( question = "Provider F.A.Q.", answer = "")
}