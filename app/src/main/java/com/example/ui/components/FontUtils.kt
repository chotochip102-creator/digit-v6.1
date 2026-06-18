package com.example.ui.components

fun isBengali(text: String): Boolean {
    if (text.isBlank()) return false // FIXED
    val bengaliCount = text.count { it.code in 0x0980..0x09FF } // FIXED
    val totalLetters = text.count { it.isLetter() } // FIXED
    return totalLetters > 0 && bengaliCount > totalLetters / 2 // FIXED
}
