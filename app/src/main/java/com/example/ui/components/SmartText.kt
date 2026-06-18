package com.example.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.foundation.text.InlineTextContent
import com.example.ui.theme.AppFonts

/*
 * FONT RULE FOR THIS PROJECT: // FIXED
 * ----------------------------------------------- // FIXED
 * ALWAYS use SmartText() instead of Text() // FIXED
 * SmartText automatically applies: // FIXED
 *   - Poppins      → for English text // FIXED
 *   - Hind Siliguri → for Bengali text // FIXED
 * // FIXED
 * DO NOT use Text() directly anywhere in this project. // FIXED
 * DO NOT hardcode fontFamily in any Text() or TextStyle. // FIXED
 * DO NOT add fontFamily to themes.xml. // FIXED
 * All font logic lives in: AppFonts (Type.kt) // FIXED
 * ----------------------------------------------- // FIXED
 */ // FIXED

@Composable
fun SmartText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val fontFamily = AppFonts.forText(text) // FIXED
    val resolvedWeight = fontWeight ?: style.fontWeight ?: FontWeight.Normal // FIXED
    val finalWeight = if (fontFamily == AppFonts.poppins) { // FIXED
        when (resolvedWeight) { // FIXED
            FontWeight.Light -> FontWeight.Normal // FIXED
            FontWeight.Normal -> FontWeight.Medium // FIXED
            FontWeight.Medium -> FontWeight.SemiBold // FIXED
            FontWeight.SemiBold -> FontWeight.Bold // FIXED
            else -> resolvedWeight // FIXED
        } // FIXED
    } else { // FIXED
        fontWeight // FIXED
    } // FIXED
    
    Text(
        text = text,
        modifier = modifier,
        fontFamily = fontFamily, // FIXED
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = finalWeight, // FIXED
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style.copy(fontFamily = fontFamily) // FIXED
    )
}

@Composable
fun SmartText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val fontFamily = AppFonts.forText(text.text) // FIXED
    val resolvedWeight = fontWeight ?: style.fontWeight ?: FontWeight.Normal // FIXED
    val finalWeight = if (fontFamily == AppFonts.poppins) { // FIXED
        when (resolvedWeight) { // FIXED
            FontWeight.Light -> FontWeight.Normal // FIXED
            FontWeight.Normal -> FontWeight.Medium // FIXED
            FontWeight.Medium -> FontWeight.SemiBold // FIXED
            FontWeight.SemiBold -> FontWeight.Bold // FIXED
            else -> resolvedWeight // FIXED
        } // FIXED
    } else { // FIXED
        fontWeight // FIXED
    } // FIXED

    Text(
        text = text,
        modifier = modifier,
        fontFamily = fontFamily, // FIXED
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = finalWeight, // FIXED
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style.copy(fontFamily = fontFamily) // FIXED
    )
}
