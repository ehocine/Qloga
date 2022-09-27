package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.QLOGATheme
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    label: String = "",
    iconId : Int ,
    imageModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val cornerRadius = 24.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shadow(1.dp, shape = RoundedCornerShape(cornerRadius), spotColor = Color.Black)
            .border(.7.dp, color = gray1.copy(alpha = .3f), shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.background)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = imageModifier,
            painter = painterResource(id = iconId),
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(bottom = 8.dp),
            text = label,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTest(){
    QLOGATheme(darkTheme = false) {
        ItemCard(iconId = R.drawable.ic_undraw_learning_sketching_nd, label =  " request") {
        }
    }
}