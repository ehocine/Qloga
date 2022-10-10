package eac.qloga.android.features.p4p.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eac.qloga.android.core.shared.components.Buttons
import eac.qloga.android.core.shared.theme.Red10
import eac.qloga.android.core.shared.theme.gray20
import eac.qloga.android.core.shared.theme.orange1
import eac.qloga.android.features.p4p.shared.utils.AccountType
import eac.qloga.android.features.p4p.shared.utils.OrderCategory

@Composable
fun OrderActions(
    modifier: Modifier = Modifier,
    orderCategory: OrderCategory,
    accountType: AccountType,
    confirmedArrival: Boolean,
    onAcceptedCancel: () -> Unit = {},
    onAcceptedReschedule: () -> Unit = {},
    onArrivedReschedule: () -> Unit = {},
    onProviderNotArrived: () -> Unit = {},
    onFundsCancel: () -> Unit = {},
    onClickReserveFunds: () -> Unit = {},
    onArrivedCancel : () -> Unit = {},
    onComplete: () -> Unit = {},
    onCompletedCancel: () -> Unit = {},
    onApprove: () -> Unit = {},
    onUnsatisfied: () -> Unit = {},
    onProviderNearCancel: () -> Unit = {},
    onConfirmArrival: () -> Unit = {},
    onMarkArrived: () -> Unit = {},
    onDispute: () -> Unit = {}
) {
    Box(
        modifier = modifier.animateContentSize()
    ) {
        when(orderCategory){
            OrderCategory.Accept -> {
                AcceptedButtons(
                    accountType = accountType,
                    onCancel = { onAcceptedCancel() },
                    onReschedule = {  onAcceptedReschedule()},
                    onProviderNotArrived = { onProviderNotArrived() },
                    onMarkArrived = { onMarkArrived() }
                )
            }
            OrderCategory.FundsReservation -> {
                FundsReservationButtons(
                    onClickCancel = { onFundsCancel() },
                    onClickReserveFunds = {  onClickReserveFunds() }
                )
            }
            OrderCategory.Arrived -> {
                ArrivedButtons(
                    accountType = accountType,
                    onCancel = { onArrivedCancel() },
                    onReschedule = { onArrivedReschedule() },
                    onComplete = { onComplete() }
                )
            }
            OrderCategory.Completed -> {
                if(accountType == AccountType.CUSTOMER){
                    CompletedButtons(
                        onCancel = { onCompletedCancel() },
                        onApprove = { onApprove() },
                        onUnsatisfied = { onUnsatisfied()}
                    )
                }
            }
            OrderCategory.ProviderIsNear -> {
                ProviderNearButtons(
                    accountType = accountType,
                    confirmedArrival = confirmedArrival,
                    onConfirmArrival = { onConfirmArrival() },
                    onCancel = { onProviderNearCancel() }
                )
            }
            OrderCategory.PaymentProcessing -> {
                if(accountType == AccountType.PROVIDER){
                    PaymentProcessingButtons {
                        onDispute()
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
private fun FundsReservationButtons(
    onClickCancel: () -> Unit,
    onClickReserveFunds: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Buttons.FullRoundedButton(
            modifier = Modifier.weight(.4f),
            buttonText = "Cancel",
            backgroundColor = gray20,
            onClick = { onClickCancel() }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Buttons.FullRoundedButton(
            modifier = Modifier.weight(1f),
            buttonText = "Reserve funds",
            onClick = { onClickReserveFunds()}
        )
    }
}

@Composable
private fun AcceptedButtons(
    modifier: Modifier = Modifier,
    accountType: AccountType,
    onCancel: () -> Unit,
    onReschedule: () -> Unit,
    onProviderNotArrived: () -> Unit,
    onMarkArrived: () -> Unit
){
    Column(modifier = modifier) {
        if(accountType == AccountType.CUSTOMER){
            Buttons.FullRoundedButton(
                buttonText = "Reschedule",
                backgroundColor = orange1,
                onClick = { onReschedule() }
            )
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Buttons.FullRoundedButton(
                    modifier = Modifier.weight(.4f),
                    buttonText = "Cancel",
                    backgroundColor = gray20,
                    onClick = { onCancel() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Buttons.FullRoundedButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Provider not arrived",
                    backgroundColor = Red10,
                    onClick = { onProviderNotArrived() }
                )
            }
        }else{
            Row(modifier = Modifier.fillMaxWidth()) {
                Buttons.FullRoundedButton(
                    modifier = Modifier.weight(.4f),
                    buttonText = "Cancel",
                    backgroundColor = gray20,
                    onClick = { onCancel() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Buttons.FullRoundedButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Mark Arrived",
                    onClick = { onMarkArrived() }
                )
            }
        }
    }
}

@Composable
private fun ProviderNearButtons(
    accountType: AccountType,
    confirmedArrival: Boolean,
    onConfirmArrival: () -> Unit,
    onCancel: () -> Unit
) {
    if(accountType == AccountType.CUSTOMER){
        if(!confirmedArrival){
            Buttons.FullRoundedButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "Confirm Arrival",
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = { onConfirmArrival() }
            )
        }
        AnimatedVisibility(visible = confirmedArrival) {
            Buttons.FullRoundedButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "Cancel",
                backgroundColor = gray20,
                onClick = { onCancel() }
            )
        }
    }else{
        Buttons.FullRoundedButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = "Cancel",
            backgroundColor = gray20,
            onClick = { onCancel() }
        )
    }
}

@Composable
private fun ArrivedButtons(
    accountType: AccountType,
    onCancel: () -> Unit,
    onReschedule: () -> Unit,
    onComplete: () -> Unit
) {
    if(accountType == AccountType.CUSTOMER){
        Buttons.FullRoundedButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = "Cancel",
            backgroundColor = gray20,
            onClick = { onCancel() }
        )
    }else{
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Buttons.FullRoundedButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Cancel",
                    backgroundColor = gray20,
                    onClick = { onCancel() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Buttons.FullRoundedButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Reschedule",
                    backgroundColor = orange1,
                    onClick = { onReschedule() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Buttons.FullRoundedButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "Complete",
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = { onComplete() }
            )
        }
    }
}

@Composable
private fun CompletedButtons(
    onCancel: () -> Unit,
    onApprove: () -> Unit,
    onUnsatisfied: () -> Unit
){
    Column {
        Buttons.FullRoundedButton(
            buttonText = "Approve",
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = { onApprove() }
        )
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Buttons.FullRoundedButton(
                modifier = Modifier.weight(.4f),
                buttonText = "Cancel",
                backgroundColor = gray20,
                onClick = { onCancel() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Buttons.FullRoundedButton(
                modifier = Modifier.weight(1f),
                buttonText = "Unsatisfied",
                backgroundColor = Red10,
                onClick = { onUnsatisfied() }
            )
        }
    }
}

@Composable
private fun PaymentProcessingButtons(
    onDispute: () -> Unit,
){
    Column {
        Buttons.FullRoundedButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = "Dispute",
            backgroundColor = orange1,
            onClick = { onDispute() }
        )
    }
}
