package eac.qloga.android.core.shared.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import eac.qloga.android.R

@OptIn(ExperimentalTextApi::class)
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalTextApi::class)
val fontName = GoogleFont("Roboto")
//val fontName = GoogleFont("Lobster Two")

@OptIn(ExperimentalTextApi::class)
val fontFamily = FontFamily(
    androidx.compose.ui.text.googlefonts.Font(googleFont = fontName, fontProvider = provider)
)

private val Arial = FontFamily(
    Font(R.font.arial)
)

private val Arial2 = FontFamily(
    Font(R.font.arial_normal, FontWeight.Normal),
    Font(R.font.arial_bd, FontWeight.Bold),
    Font(R.font.arial_bl, FontWeight.Bold),
    Font(R.font.arial_black, FontWeight.Black),
    Font(R.font.arail_narrow_bold, FontWeight.Bold),
    Font(R.font.arial_black_italic, FontWeight.Black),
    Font(R.font.arial_italic, FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.6.sp,
        ),

    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.6.sp,
        lineHeight = 24.0.sp
        ),

    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.6.sp,
        lineHeight = 20.0.sp
        ),

    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        letterSpacing = 0.6.sp,
        ),

    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight(570),
        fontSize = 16.sp,
        letterSpacing = 0.6.sp,
        lineHeight = 24.0.sp
    ),

    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        letterSpacing = 0.6.sp,
        lineHeight = 20.0.sp
        ),

    labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.6.sp,
        ),

    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        letterSpacing = 0.6.sp,
        ),

    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 0.6.sp,
        )
)