package eac.qloga.android.features.p4p.shared.scenes.contactDetails

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.NavigationActions
import eac.qloga.android.R
import eac.qloga.android.core.shared.components.TitleBar
import eac.qloga.android.core.shared.utils.AddressConverter
import eac.qloga.android.core.shared.utils.CONTAINER_TOP_PADDING
import eac.qloga.android.features.p4p.shared.scenes.P4pSharedScreens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsScreen(
    navigationActions: NavigationActions
) {
    val containerTopPadding = CONTAINER_TOP_PADDING.dp
    val contacts = ContactDetailsViewModel.contacts.value
    val contactAddress = AddressConverter.addressToString(contacts?.address)
    val email = contacts?.email
    val phoneNumber =  contacts?.phoneNumber

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TitleBar(
                label = P4pSharedScreens.ContactDetails.titleName,
                iconColor = MaterialTheme.colorScheme.primary,
            ) {
                navigationActions.upPress()
            }
        }
    ) { paddingValues ->
        val titleBarHeight = paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(titleBarHeight))
                Spacer(modifier = Modifier.height(containerTopPadding))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    phoneNumber?.let {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "$it",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { /*TODO*/ }
                                .padding(8.dp)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ql_phone),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if(!email.isNullOrEmpty()){
                        Text(
                            modifier = Modifier.weight(1f),
                            text = email,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { /*TODO*/ }
                                .padding(8.dp)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ql_mail),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        text = contactAddress,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                scope.launch {
//                                    navController.navigate(Screen.MapView.route)
                                }
                            }
                            .padding(8.dp)
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_point),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContactScreen() {
    ContactDetailsScreen(NavigationActions(NavController(Application())))
}