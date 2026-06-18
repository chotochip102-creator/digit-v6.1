package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.R
import com.example.ui.components.isBengali

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val poppinsGoogleFont = GoogleFont("Poppins")
val hindGoogleFont = GoogleFont("Hind Siliguri")

object AppFonts {
    val poppins = FontFamily(
        Font(googleFont = poppinsGoogleFont, fontProvider = provider, weight = FontWeight.Light),
        Font(googleFont = poppinsGoogleFont, fontProvider = provider, weight = FontWeight.Normal),
        Font(googleFont = poppinsGoogleFont, fontProvider = provider, weight = FontWeight.Medium),
        Font(googleFont = poppinsGoogleFont, fontProvider = provider, weight = FontWeight.SemiBold),
        Font(googleFont = poppinsGoogleFont, fontProvider = provider, weight = FontWeight.Bold),
        Font(googleFont = poppinsGoogleFont, fontProvider = provider, weight = FontWeight.ExtraBold),
        Font(googleFont = poppinsGoogleFont, fontProvider = provider, weight = FontWeight.Black)
    ) // FIXED
    val hindSiliguri = FontFamily(
        Font(googleFont = hindGoogleFont, fontProvider = provider, weight = FontWeight.Light),
        Font(googleFont = hindGoogleFont, fontProvider = provider, weight = FontWeight.Normal),
        Font(googleFont = hindGoogleFont, fontProvider = provider, weight = FontWeight.Medium),
        Font(googleFont = hindGoogleFont, fontProvider = provider, weight = FontWeight.SemiBold),
        Font(googleFont = hindGoogleFont, fontProvider = provider, weight = FontWeight.Bold),
        Font(googleFont = hindGoogleFont, fontProvider = provider, weight = FontWeight.ExtraBold),
        Font(googleFont = hindGoogleFont, fontProvider = provider, weight = FontWeight.Black)
    ) // FIXED
    
    fun forText(text: String): FontFamily { // FIXED
        return if (isBengali(text)) hindSiliguri else poppins // FIXED
    } // FIXED
}

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Normal, fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-0.25).sp), // FIXED
    displayMedium = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Normal, fontSize = 45.sp, lineHeight = 52.sp, letterSpacing = 0.sp), // FIXED
    displaySmall = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Normal, fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = 0.sp), // FIXED
    headlineLarge = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Normal, fontSize = 32.sp, lineHeight = 40.sp, letterSpacing = 0.sp), // FIXED
    headlineMedium = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Normal, fontSize = 28.sp, lineHeight = 36.sp, letterSpacing = 0.sp), // FIXED
    headlineSmall = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Normal, fontSize = 24.sp, lineHeight = 32.sp, letterSpacing = 0.sp), // FIXED
    titleLarge = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Medium, fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = 0.sp), // FIXED
    titleMedium = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp), // FIXED
    titleSmall = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp), // FIXED
    bodyLarge = TextStyle(fontFamily = AppFonts.hindSiliguri, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp), // FIXED
    bodyMedium = TextStyle(fontFamily = AppFonts.hindSiliguri, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp), // FIXED
    bodySmall = TextStyle(fontFamily = AppFonts.hindSiliguri, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.4.sp), // FIXED
    labelLarge = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp), // FIXED
    labelMedium = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp), // FIXED
    labelSmall = TextStyle(fontFamily = AppFonts.poppins, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp) // FIXED
)
