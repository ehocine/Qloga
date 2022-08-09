package eac.qloga.android.features.shared.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.ui.theme.QLOGATheme

@Composable
fun TextTestScreen() {
    val scrollable = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(scrollable)
    ) {
        Text(text = "Head")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(text = "Display")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.displayLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.displayMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.displaySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(text = "title")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(text = "Body")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(text = "Label")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "A quick brown fox jumps over the lazy dog",
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextTest() {
    QLOGATheme(dynamicColor = false, darkTheme = false) {
        TextTestScreen()
    }
}