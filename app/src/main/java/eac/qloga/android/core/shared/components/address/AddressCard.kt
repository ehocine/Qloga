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
import eac.qloga.android.core.shared.components.Cards
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.ParkingType

@Composable
fun AddressCard(
    parkingType: ParkingType,
    line1State: InputFieldState,
    line2State: InputFieldState,
    line3State: InputFieldState,
    postcodeState: InputFieldState,
    cityState: InputFieldState,
    onChangeLine1: (String) -> Unit,
    onChangeLine3: (String) -> Unit,
    onChangeCity: (String) -> Unit,
    onChangeLine2: (String) -> Unit,
    onChangePostcode: (String) -> Unit,
    onFocusLine1: (FocusState) -> Unit,
    onFocusLine3: (FocusState) -> Unit,
    onFocusCity: (FocusState) -> Unit,
    onFocusLine2: (FocusState) -> Unit,
    onFocusPostcode: (FocusState) -> Unit,
    onClickParkingType: () -> Unit
) {
    Cards.ContainerBorderedCard {
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
                actions = {
                    ParkingTypeButton(type = parkingType.label) { onClickParkingType() }
                }
            )

            AddressCardItem(
                title = "Line 1",
                value = line1State.text,
                hint = line1State.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangeLine1(it) },
                onFocusChange = { onFocusLine1(it) }
            )

            AddressCardItem(
                title = "Line 2",
                value = line2State.text,
                hint = line2State.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangeLine2(it) },
                onFocusChange = { onFocusLine2(it) }
            )

            AddressCardItem(
                title = "Line 3",
                value = line3State.text,
                hint = line3State.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangeLine3(it) },
                onFocusChange = { onFocusLine3(it) }
            )

            AddressCardItem(
                title = "City",
                value = cityState.text,
                hint = cityState.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                onValueChange = { onChangeCity(it) },
                onFocusChange = { onFocusCity(it) }
            )

            AddressCardItem(
                title = "Postcode",
                value = postcodeState.text,
                hint = postcodeState.hint,
                textStyle = MaterialTheme.typography.bodyMedium,
                editable = true,
                showDivider = false,
                onValueChange = { onChangePostcode(it) },
                onFocusChange = { onFocusPostcode(it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddressCard2(){
    AddressCard(
        parkingType = ParkingType.FreeType,
        line1State = InputFieldState(),
        line2State = InputFieldState(),
        line3State = InputFieldState(),
        postcodeState = InputFieldState(),
        cityState = InputFieldState(),
        onChangeLine1 = {},
        onChangeLine3 = {},
        onChangeCity = {},
        onChangeLine2 = {},
        onChangePostcode = {},
        onFocusLine1 = {},
        onFocusLine3 = {},
        onFocusCity = {},
        onFocusLine2 = {},
        onFocusPostcode = {},
        onClickParkingType = {}
    )
}