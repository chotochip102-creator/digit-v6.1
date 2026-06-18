package com.example.ui.components

import androidx.compose.ui.text.font.FontFamily
import com.example.ui.theme.hindSiliguri
import com.example.ui.theme.poppins

fun isBengali(text: String): Boolean {
    return text.any { it.code in 0x0980..0x09FF } // FIXED
}

fun getFontFamily(text: String): FontFamily {
    return if (isBengali(text)) hindSiliguri else poppins // FIXED
}
