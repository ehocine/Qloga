package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.theme.gray1

@Composable
fun ClosedOrderFeedbackContent(
    modifier: Modifier = Modifier,
) {
    val publicContent = "Internal and external drains and sewers repairs " +
            "including blockage removals, pipe replacements, etc."
    val privateContent = "Internal and external drains and sewers " +
            "repairs including blockage removals, pipe replacements, etc."

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Public", style = MaterialTheme.typography.titleMedium,)
        Text(text = publicContent, style = MaterialTheme.typography.titleMedium,color = gray1)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Private", style = MaterialTheme.typography.titleMedium,)
        Text(text = privateContent, style = MaterialTheme.typography.titleMedium,color = gray1)
    }
}