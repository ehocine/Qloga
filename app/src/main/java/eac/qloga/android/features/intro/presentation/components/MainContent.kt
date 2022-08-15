package eac.qloga.android.features.intro.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.features.sign_in.convertTimeStampToDate
import eac.qloga.android.models.User

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onClickProviderSearch: () -> Unit,
    onClickRequest: () -> Unit,
    onClickBecomeProvider: () -> Unit,
    onClickEnrolled: () -> Unit,
    user: User,
    onLogOutClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }

    BoxWithConstraints(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(top = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // request
            ItemCard(
                modifier = Modifier.weight(1f),
                iconId = R.drawable.ic_request_1,
                label = "Request"
            ) {
                onClickRequest()
            }
            Spacer(modifier = Modifier.height(22.dp))

            // Provider search
            ItemCard(
                modifier = Modifier.weight(1f),
                iconId = R.drawable.ic_no_providers_1,
                label = "Provider search"
            ) {
                onClickProviderSearch()
            }
            Spacer(modifier = Modifier.height(22.dp))

            // Become Provider
            ItemCard(
                modifier = Modifier.weight(1f),
                iconId = R.drawable.ic_become_to_prv,
                label = "Become to provider"
            ) {
                onClickBecomeProvider()
            }

            //already enrolled
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .padding(top = 16.dp, bottom = 8.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onClickEnrolled() },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Already enrolled",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 17.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
                Text(
                    text = "Signed in as: ${user.userName}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = "Email: ${user.email}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = "Token expires at: ${
                        user.tokenExpiration?.let {
                            convertTimeStampToDate(
                                it.toLong()
                            )
                        }
                    }",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 13.sp
                    )
                )
                Button(onClick = {
                    onLogOutClicked()
                }) {
                    Text(text = "Log out")
                }
            }
        }
    }
}


