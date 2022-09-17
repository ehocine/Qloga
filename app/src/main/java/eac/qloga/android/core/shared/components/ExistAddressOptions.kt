package eac.qloga.android.core.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.grayTextColor
import eac.qloga.android.core.shared.theme.green1

@Composable
fun ExistAddressOptions(
    modifier: Modifier = Modifier,
    addressList: List<String>,
    selectedAddress: Int= 0,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(.15f)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(gray1)
            ,
        )

        Spacer(modifier = Modifier.height(8.dp))

        addressList.forEachIndexed { index, value ->

            val isSelected = index == selectedAddress

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(index)
                    }
                    .padding(
                        // padding is not given to parent component because
                        // we needed ripple effect on click in full width and height
                        top = if (index == 0) 16.dp else 8.dp,
                        bottom = if (index == addressList.size - 1) 16.dp else 8.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                Text(
                    modifier = Modifier.alpha(.75f),
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    color = if(isSelected) green1 else grayTextColor
                )
            }
        }
    }
}