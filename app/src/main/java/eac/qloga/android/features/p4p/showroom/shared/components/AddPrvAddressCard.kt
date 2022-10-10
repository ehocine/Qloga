package eac.qloga.android.features.p4p.showroom.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.core.shared.components.Cards.ContainerBorderedCard
import eac.qloga.android.core.shared.components.address.AddressCardItem
import eac.qloga.android.core.shared.components.address.ParkingTypeButton
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.utils.InputFieldState
import eac.qloga.android.core.shared.utils.ParkingType

@Composable
fun AddPrvAddressCard(
    parkingType: ParkingType,
    countryCode: String,
    checkBusinessOnly: Boolean = false,
    postcodeState: InputFieldState,
    streetState: InputFieldState,
    apartmentsState: InputFieldState,
    townState: InputFieldState,
    buildingState: InputFieldState,
    onClickParkingType: () -> Unit,
    onSelectCountryCode: () -> Unit,
    onChangeBusinessOnly: () -> Unit,
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
) {
    ContainerBorderedCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(start = 16.dp)
        ) {
            AddressCardItem(
                title = "Business only",
                verticalPadding = 4.dp,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                actions = { Switch(checked = checkBusinessOnly, onCheckedChange = { onChangeBusinessOnly() }) }
            )

            AddressCardItem(
                title = "Parking",
                textStyle = MaterialTheme.typography.bodyMedium,
                actions = { ParkingTypeButton(type = parkingType.label) { onClickParkingType() } }
            )

            AddressCardItem(
                title = "Country",
                textStyle = MaterialTheme.typography.bodyMedium,
                actions = { CountryTypeButton(type = countryCode) { onSelectCountryCode() } }
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

@Composable
private fun CountryTypeButton(
    type: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = type,
            style = MaterialTheme.typography.bodySmall,
            color = gray30
        )

        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Rounded.ArrowForwardIos,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProviderAddressCard(){
    AddPrvAddressCard(
        countryCode = "",
        onClickParkingType = {},
        onSelectCountryCode = {},
        onChangeBusinessOnly = {},
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
        onFocusApartments = {}
    )
}