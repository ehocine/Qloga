package eac.qloga.android.features.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eac.qloga.android.features.shared.util.Screen
import eac.qloga.android.ui.theme.orange1

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TestScreen(
    navController: NavController
) {

    val scrollState = rememberScrollState()

    Scaffold {
        Box(modifier = Modifier
            .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 32.dp)
            ) {
                val verticalSpace = 32.dp

                Screen.listOfScreen.forEach {
                    NavigationButton(onNav = { navController.navigate(it.route) }, label = it.route)
                    Spacer(modifier = Modifier.height(verticalSpace))
                }
            }
        }
    }
}

@Composable
fun NavigationButton(
    onNav: () -> Unit,
    label: String
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(44.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { onNav() }
        .background(orange1)
        ,
        contentAlignment = Alignment.Center
    ) {

        Text(text =  label, style = MaterialTheme.typography.titleLarge, color = Color.White)
    }
}