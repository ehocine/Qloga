package eac.qloga.android.features.p4p.customer.shared.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import eac.qloga.android.features.p4p.shared.components.AccountTypeSwitchInfoDialog
import eac.qloga.android.features.p4p.shared.utils.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerInfoDialog(
    showAgain: Boolean,
    onCheckShowAgain: () -> Unit,
    onClose: () -> Unit
) {
    AccountTypeSwitchInfoDialog(
        accountType = AccountType.CUSTOMER,
        showAgain = showAgain,
        onCheckShowAgain = {onCheckShowAgain()}
    ) {
        onClose()
    }
}