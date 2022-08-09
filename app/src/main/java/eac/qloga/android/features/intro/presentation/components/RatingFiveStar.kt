package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.ui.theme.gray30
import eac.qloga.android.ui.theme.orange1

@Composable
fun RatingFiveStar(
    label: String? = null,
    ratings: Int,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        label?.let {
            Text(
                modifier = Modifier.alpha(.75f).weight(1f),
                text = it,
                style = textStyle,
                color = gray30
            )
            Spacer(Modifier.width(8.dp))
        }

        Row(
            modifier = Modifier
        ) {
            for( i in 1..5){
                if(i <= ratings){
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_star_filled),
                        contentDescription = "",
                        tint = orange1
                    )
                }else{
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_start_empty),
                        contentDescription = ""
                    )
                }
                Spacer(Modifier.width(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRatingStar(){
    RatingFiveStar(label = "Sufficient enough chemicals ", ratings = 4)
}