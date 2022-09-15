package eac.qloga.android.features.p4p.provider.shared.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import eac.qloga.android.features.p4p.shared.components.AccountTypeSwitchInfoDialog
import eac.qloga.android.features.p4p.shared.utils.AccountType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderInfoDialog(
    showAgain: Boolean,
    onCheckShowAgain: () -> Unit,
    onClose: () -> Unit
) {
    AccountTypeSwitchInfoDialog(
        accountType = AccountType.PROVIDER,
        showAgain = showAgain,
        onCheckShowAgain = {onCheckShowAgain()}) {
        onClose()
    }
}