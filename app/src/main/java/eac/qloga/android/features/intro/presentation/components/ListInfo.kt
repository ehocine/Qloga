package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.ui.theme.gray30

@Composable
fun ListInfo(
    modifier: Modifier = Modifier,
    label: String,
    measureValue: String? = null,
    price: String? = null
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircleDot()

            Text(
                modifier = Modifier.weight(1f).padding(start = 12.dp),
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W400,
                color = gray30
            )

            price?.let {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "\$$it",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        measureValue?.let {
            Text(
                modifier = Modifier.align(Alignment.End),
                text = it,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.W400,
                color = gray30
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoITem(){
    ListInfo(label = "Window Cleaning", measureValue = "window/hour", price = "$234")
}