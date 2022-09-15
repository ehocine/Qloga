package eac.qloga.android.features.p4p.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eac.qloga.android.R
import eac.qloga.android.core.shared.theme.gray1
import eac.qloga.android.core.shared.theme.gray30
import eac.qloga.android.core.shared.theme.info_sky
import eac.qloga.android.features.p4p.shared.utils.AccountType

@ExperimentalMaterial3Api
@Composable
fun AccountTypeSwitchInfoDialog(
    accountType: AccountType,
    showAgain: Boolean,
    onCheckShowAgain: () -> Unit,
    onClose: () -> Unit
) {
    val imageSize = 130.dp
    val inlineId = "inlineContent"
    val labelText = buildAnnotatedString {
        append("To switch to ")
        appendInlineContent(inlineId, "[Account Type]")
        append(" mode click onto profile icon")
    }

    val inlineContent = mapOf(
        Pair(inlineId,
            InlineTextContent(
                Placeholder(
                    width = 85.sp,
                    height = 24.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (accountType == AccountType.CUSTOMER) "Provider" else "Customer",
                    style = MaterialTheme.typography.titleLarge,
                    color = if(accountType == AccountType.CUSTOMER) info_sky else MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .shadow(
                8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.background
            )
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier= Modifier
            ) {
                Image(
                    modifier = Modifier
                        .size(imageSize)
                        .padding(top = 8.dp)
                    ,
                    painter = painterResource(
                        id = if(accountType == AccountType.CUSTOMER) R.drawable.ql_cst_avtr_acc else R.drawable.pvr_profile_ava
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopCenter
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Start),
                text = buildAnnotatedString {
                    append("You entered QLOGA as  ")
                    append(
                        AnnotatedString(if(accountType == AccountType.CUSTOMER) "Customer" else "Provider",
                            spanStyle = SpanStyle(
                                fontSize = 16.sp,
                                color = if(accountType == AccountType.CUSTOMER) MaterialTheme.colorScheme.primary else info_sky
                            )
                        )
                    )
                },
                style = MaterialTheme.typography.titleMedium,
                color = gray30
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    text = labelText,
                    inlineContent = inlineContent,
                    style = MaterialTheme.typography.bodySmall,
                    color = gray30
                )
                Icon(
                    painter = painterResource(
                        id = if(accountType == AccountType.CUSTOMER) R.drawable.ic_ql_person else R.drawable.ic_ql_provider_people
                    ),
                    contentDescription = "",
                    tint = if(accountType == AccountType.CUSTOMER) MaterialTheme.colorScheme.primary else info_sky
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    modifier = Modifier.padding(0.dp),
                    checked = showAgain, //if show is true not check else check
                    onCheckedChange = { onCheckShowAgain() },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = gray1
                    )
                )
                Text(
                    text = "Do not show me again",
                    style = MaterialTheme.typography.bodySmall,
                    color = gray30
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onClose() }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 48.dp, vertical = 8.dp)
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Close", color  = Color.White, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}