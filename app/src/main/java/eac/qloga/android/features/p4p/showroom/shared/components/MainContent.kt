package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eac.qloga.android.R

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onClickProviderSearch: () -> Unit,
    onClickRequest: () -> Unit,
    onClickBecomeProvider: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }
    var screenHeight: Dp = 0.dp
    val itemHeight = remember(screenHeight) {
        derivedStateOf {
            screenHeight / 4 - 8.dp
        }
    }

    with(LocalDensity.current) {
        val screenHeightInt = LocalConfiguration.current.screenHeightDp
        screenHeight = screenHeightInt.dp
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(top = 4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // request
        ItemCard(
            modifier = Modifier.height(itemHeight.value),
            iconId = R.drawable.ic_request_1,
            label = "Request"
        ) {
            onClickRequest()
        }
        Spacer(modifier = Modifier.height(22.dp))

        // Provider search
        ItemCard(
            modifier = Modifier.height(itemHeight.value),
            iconId = R.drawable.ic_no_providers_1,
            label = "Provider search"
        ) {
            onClickProviderSearch()
        }
        Spacer(modifier = Modifier.height(22.dp))

        // Become Provider
        ItemCard(
            modifier = Modifier.height(itemHeight.value),
            iconId = R.drawable.ic_become_to_prv,
            label = "Become to provider"
        ) {
            onClickBecomeProvider()
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


