package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.theme.QLOGATheme

@Composable
fun VerificationsStatusButton(
    modifier : Modifier = Modifier,
    iconId: Int ,
    label: String ,
) {
    ContainerBorderedCard(
        cornerRadius = 12.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 14.dp, end = 16.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier.padding(end = 12.dp),
                painter = painterResource(id = iconId),
                contentDescription = ""
            )

            Text(
                modifier = Modifier.weight(1f),
                text = label,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerificationStatus(){
    QLOGATheme {
        Box(modifier = Modifier.padding(16.dp)) {
            VerificationsStatusButton(
                iconId = R.drawable.ic_verification,
                label = "Providers verifications"
            )
        }
    }
}