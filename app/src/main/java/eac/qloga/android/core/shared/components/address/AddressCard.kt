package eac.qloga.android.core.shared.components.address

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.ParkingType

@Composable
fun AddressCard(
    parkingType: ParkingType,
    postcodeState: InputFieldState,
    streetState: InputFieldState,
    apartmentsState: InputFieldState,
    townState: InputFieldState,
    buildingState: InputFieldState,
    onChangePostcode: (String) -> Unit,
    onChangeStreet: (String) -> Unit,
    onChangeBuilding: (String) -> Unit,
    onChangeTown: (String) -> Unit,
    onChangeApartments: (String) -> Unit,
    onFocusPostcode: (FocusState) -> Unit,
    onFocusStreet: (FocusState) -> Unit,
    onFocusBuilding: (FocusState) -> Unit,
    onFocusTown: (FocusState) -> Unit,
    onFocusApartments: (FocusState) -> Unit,
    onClickParkingType: () -> Unit
) {

    ContainerBorderedCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // should be able to scrollable because adjustResize hides content
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp)
        ) {
            AddressCardItem(
                title = "Parking",
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                actions = { ParkingTypeButton(type = parkingType.label) { onClickParkingType() }
                }
            )

            AddressCardItem(
                title = "Postcode",
                value = postcodeState.text,
                hint = postcodeState.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangePostcode(it) },
                onFocusChange = { onFocusPostcode(it) }
            )

            AddressCardItem(
                title = "Town",
                value = townState.text,
                hint = townState.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangeTown(it) },
                onFocusChange = { onFocusTown(it) }
            )

            AddressCardItem(
                title = "Street",
                value = streetState.text,
                hint = streetState.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangeStreet(it) },
                onFocusChange = { onFocusStreet(it) }
            )

            AddressCardItem(
                title = "Building",
                value = buildingState.text,
                hint = buildingState.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangeBuilding(it) },
                onFocusChange = { onFocusBuilding(it) }
            )

            AddressCardItem(
                title = "Apartments",
                value = apartmentsState.text,
                hint = apartmentsState.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                showDivider = false,
                onValueChange = { onChangeApartments(it) },
                onFocusChange = { onFocusApartments(it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddressCard2(){
    AddressCard(
        parkingType = ParkingType.FreeType,
        postcodeState = InputFieldState(),
        streetState = InputFieldState(),
        apartmentsState = InputFieldState(),
        townState = InputFieldState(),
        buildingState = InputFieldState(),
        onChangePostcode = {},
        onChangeStreet = {},
        onChangeBuilding = {},
        onChangeTown = {},
        onChangeApartments = {},
        onFocusPostcode = {},
        onFocusStreet = {},
        onFocusBuilding = {},
        onFocusTown = {},
        onFocusApartments = {},
        onClickParkingType = {}
    )
}