package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionText(
    text: String,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        ExpandableText(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDes() {
    DescriptionText(
        "As you know there are plenty of screen sizes out there, a long string that take".repeat(5),
    )
}