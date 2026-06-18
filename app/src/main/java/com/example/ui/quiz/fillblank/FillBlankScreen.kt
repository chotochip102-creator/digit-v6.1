package com.example.ui.quiz.fillblank
import com.example.ui.components.SmartText

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.max

// Exact Color Palette to Match Image
val ScreenBgColor = Color(0xFFF8F9FA)
val CorrectColor = Color(0xFF639922)
val ErrorColor = Color(0xFFE24B4A)
val WarningColor = Color(0xFFBA7517)
val CardOutlineColor = Color(0xFFE5E7EB)

val TitlePurple = Color(0xFF2E3165)
val ActionGreen = Color(0xFF006D5B)
val LightChipBg = Color(0xFFEFEFEF)
val CardPillColor = Color(0xFF5B5B82)
val OrangeIcon = Color(0xFFE87B35)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillBlankScreen(
    onNavigateBack: () -> Unit,
    viewModel: FillBlankViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val currentBgColor = if (isDark) Color(0xFF121212) else ScreenBgColor

    Scaffold(
        containerColor = currentBgColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(currentBgColor)
        ) {
            if (uiState.phase == GamePhase.PLAYING) {
                GameContent(uiState = uiState, viewModel = viewModel, onNavigateBack = onNavigateBack)
            } else {
                SummaryScreen(uiState = uiState, onReplay = { viewModel.restart() })
            }
        }
    }
}

@Composable
fun GameContent(
    uiState: GameUiState,
    viewModel: FillBlankViewModel,
    onNavigateBack: () -> Unit
) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val currentTitleColor = if (isDark) Color(0xFFD8E1FF) else TitlePurple
    val currentChipBg = if (isDark) Color(0xFF2A2A2A) else LightChipBg
    val currentActionBg = if (isDark) Color(0xFF4DB6AC) else ActionGreen
    val currentHintText = if (isDark) Color.White else Color(0xFF1F2937)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // TOP BAR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(36.dp).align(Alignment.CenterStart)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = currentTitleColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            SmartText( // FIXED
                text = if (uiState.lang == Lang.BN) "শূন্যস্থান পূরণ" else "Fill in the Blanks",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = currentTitleColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // STATS CHIPS ROW
        val totalQ = QuestionData.questions[uiState.topic]?.size ?: 1
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatChip(
                icon = Icons.Outlined.StarOutline,
                iconColor = OrangeIcon,
                text = if (uiState.lang == Lang.BN) "স্কোর: ${toBengaliNumber(uiState.score * 5)}" else "Score: ${uiState.score * 5}"
            )
            StatChip(
                icon = Icons.Outlined.HelpOutline,
                iconColor = currentTitleColor,
                text = if (uiState.lang == Lang.BN) "প্রশ্ন: ${toBengaliNumber(uiState.questionIndex + 1)}/${toBengaliNumber(totalQ)}" else "Q: ${uiState.questionIndex + 1}/$totalQ"
            )
            StatChip(
                icon = Icons.Outlined.LocalFireDepartment,
                iconColor = OrangeIcon,
                text = if (uiState.lang == Lang.BN) "ধারা: ${toBengaliNumber(uiState.streak)}" else "Streak: ${uiState.streak}"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PROGRESS BAR
        val progress by androidx.compose.animation.core.animateFloatAsState(
            targetValue = (uiState.questionIndex + 1) / totalQ.toFloat(),
            label = "progress"
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                trackColor = currentChipBg,
                color = currentActionBg
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // QUESTION CARD
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            QuestionCard(uiState = uiState, viewModel = viewModel)
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        // ACTION BUTTONS MATCHING IMAGE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionPill(
                text = if (uiState.lang == Lang.BN) "ইঙ্গিত" else "Hint",
                icon = Icons.Outlined.Lightbulb,
                bgColor = currentChipBg,
                contentColor = currentHintText,
                onClick = { viewModel.showHints() },
                enabled = !uiState.checked && !uiState.hintsVisible,
                modifier = Modifier.weight(1f)
            )
            ActionPill(
                text = if (uiState.lang == Lang.BN) "যাচাই করো" else "Check",
                icon = Icons.Outlined.CheckCircle,
                bgColor = currentActionBg,
                contentColor = if (isDark) Color(0xFF121212) else Color.White,
                onClick = { viewModel.checkAnswers() },
                enabled = !uiState.checked,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        TextButton(
            onClick = { viewModel.revealAnswers() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = !uiState.checked
        ) {
            SmartText( // FIXED
                text = if (uiState.lang == Lang.BN) "উত্তর দেখো" else "See Answer",
                color = currentTitleColor,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // FEEDBACK NOTIFICATION
        AnimatedVisibility(
            visible = uiState.feedbackState != FeedbackState.NONE,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically() + fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            val (bgColor, iconColor, icon, msgBn, msgEn) = when (uiState.feedbackState) {
                FeedbackState.ALL_CORRECT -> listOf(CorrectColor.copy(alpha = 0.1f), CorrectColor, Icons.Default.CheckCircle, "সব সঠিক! +${if(uiState.hintUsed) 1 else 2} পয়েন্ট", "All correct! +${if(uiState.hintUsed) 1 else 2} points")
                FeedbackState.WRONG -> listOf(ErrorColor.copy(alpha = 0.1f), ErrorColor, Icons.Default.ErrorOutline, "ভুল হয়েছে। আবার চেষ্টা করো।", "Incorrect. Try again.")
                FeedbackState.REVEALED -> listOf(WarningColor.copy(alpha = 0.1f), WarningColor, Icons.Default.Visibility, "উত্তর দেখানো হয়েছে।", "Answers revealed.")
                else -> listOf(Color.Transparent, Color.Transparent, Icons.Default.CheckCircle, "", "")
            }
            Surface(
                color = bgColor as Color,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val textColor = if (com.example.ui.theme.isAppInDarkTheme() && iconColor == ErrorColor) Color.White else iconColor as Color
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon as androidx.compose.ui.graphics.vector.ImageVector, contentDescription = null, tint = iconColor as Color)
                    Spacer(modifier = Modifier.width(12.dp))
                    SmartText(if (uiState.lang == Lang.BN) msgBn as String else msgEn as String, fontWeight = FontWeight.Bold, color = textColor) // FIXED
                }
            }
        }
    }
}

@Composable
fun StatChip(icon: androidx.compose.ui.graphics.vector.ImageVector, iconColor: Color, text: String) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val bg = if (isDark) Color(0xFF2A2A2A) else LightChipBg
    val textColor = if (isDark) Color(0xFFE5E7EB) else Color(0xFF4B5563)
    
    Row(
        modifier = Modifier
            .background(bg, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        SmartText(text, fontSize = 13.sp, color = textColor, fontWeight = FontWeight.Medium) // FIXED
    }
}

@Composable
fun ActionPill(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    bgColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val disabledBgColor = if (isDark) Color(0xFF2A2A2A) else LightChipBg
    val disabledTextColor = if (isDark) Color(0xFF6B7280) else Color(0xFF9CA3AF)

    Surface(
        onClick = if (enabled) onClick else ({}),
        shape = RoundedCornerShape(28.dp),
        color = if (enabled) bgColor else disabledBgColor,
        modifier = modifier.height(56.dp),
        enabled = enabled
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) contentColor else disabledTextColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            SmartText( // FIXED
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (enabled) contentColor else disabledTextColor
            )
        }
    }
}

fun toBengaliNumber(number: Int): String {
    val bengaliDigits = arrayOf("০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯")
    return number.toString().map { char ->
        if (char.isDigit()) bengaliDigits[char.toString().toInt()] else char
    }.joinToString("")
}

sealed class PassageSegment
data class WordSegment(val text: String) : PassageSegment()
data class BlankSegment(val blankIndex: Int) : PassageSegment()

fun parsePassage(text: String): List<PassageSegment> {
    val segments = mutableListOf<PassageSegment>()
    val regex = "\\[([^]]+)]".toRegex()
    var lastIndex = 0
    var blankCounter = 0

    regex.findAll(text).forEach { matchResult ->
        if (matchResult.range.first > lastIndex) {
            val textPart = text.substring(lastIndex, matchResult.range.first)
            val words = textPart.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }
            segments.addAll(words.map { WordSegment(it) })
        }
        segments.add(BlankSegment(blankCounter))
        blankCounter++
        lastIndex = matchResult.range.last + 1
    }

    if (lastIndex < text.length) {
        val textPart = text.substring(lastIndex)
        val words = textPart.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }
        segments.addAll(words.map { WordSegment(it) })
    }
    return segments
}

@Composable
fun PillTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    status: BlankStatus,
    enabled: Boolean
) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val bg = if (isDark) Color(0xFF2A2A2A) else LightChipBg
    val textCol = if (isDark) Color.White else Color.Black
    val pCol = if (isDark) Color(0xFFE5E7EB) else Color(0xFF1F2937)
    val iconCol = if (isDark) Color(0xFFAAAEB6) else Color(0xFF4B5563)
    val defaultBorder = if (isDark) Color(0xFF4B5563) else Color(0xFFD1D5DB)

    val bColor = when (status) {
        BlankStatus.CORRECT -> CorrectColor
        BlankStatus.WRONG -> ErrorColor
        BlankStatus.REVEALED -> WarningColor
        else -> defaultBorder
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(100.dp)
            .height(44.dp)
            .border(1.dp, bColor, RoundedCornerShape(22.dp))
            .background(bg, RoundedCornerShape(22.dp))
            .padding(horizontal = 12.dp)
    ) {
        if (value.isEmpty()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null,
                    tint = iconCol,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                SmartText( // FIXED
                    placeholder,
                    color = pCol,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        androidx.compose.foundation.text.BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = textCol
            ),
            singleLine = true,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionCard(uiState: GameUiState, viewModel: FillBlankViewModel) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val cardBg = if (isDark) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDark) Color.White else Color(0xFF1F2937)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(32.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
             Box(
                 modifier = Modifier
                     .background(CardPillColor, RoundedCornerShape(12.dp))
                     .padding(horizontal = 12.dp, vertical = 6.dp)
             ) {
                 SmartText( // FIXED
                     if (uiState.lang == Lang.BN) "QUESTION ${toBengaliNumber(uiState.questionIndex + 1)}" else "QUESTION ${uiState.questionIndex + 1}",
                     color = Color.White,
                     fontWeight = FontWeight.SemiBold,
                     fontSize = 12.sp
                 )
             }
             Spacer(modifier = Modifier.height(24.dp))

             val questions = QuestionData.questions[uiState.topic]
             if (questions != null && uiState.questionIndex < questions.size) {
                 val content = if (uiState.lang == Lang.BN) questions[uiState.questionIndex].bn else questions[uiState.questionIndex].en
                 val segments = parsePassage(content.text)

                 FlowRow(
                     horizontalArrangement = Arrangement.spacedBy(6.dp),
                     verticalArrangement = Arrangement.spacedBy(14.dp),
                     modifier = Modifier.fillMaxWidth()
                 ) {
                     segments.forEach { segment ->
                         when (segment) {
                             is WordSegment -> {
                                 Box(
                                     modifier = Modifier.height(44.dp),
                                     contentAlignment = Alignment.Center
                                 ) {
                                     SmartText( // FIXED
                                         text = segment.text,
                                         fontSize = 22.sp,
                                         fontWeight = FontWeight.Bold,
                                         color = textColor,
                                         modifier = Modifier.align(Alignment.Center)
                                     )
                                 }
                             }
                             is BlankSegment -> {
                                 val bState = uiState.blankStates.getOrNull(segment.blankIndex) ?: BlankState()
                                 
                                 Box(
                                     modifier = Modifier.height(44.dp),
                                     contentAlignment = Alignment.Center
                                 ) {
                                     PillTextField(
                                         value = bState.input,
                                         onValueChange = { viewModel.updateInput(segment.blankIndex, it) },
                                         placeholder = if (uiState.lang == Lang.BN) "লেখো" else "Type",
                                         status = bState.status,
                                         enabled = bState.status == BlankStatus.IDLE && !uiState.checked
                                     )
                                 }
                             }
                         }
                     }
                 }
             }
        }
    }
}



@Composable
fun SummaryScreen(uiState: GameUiState, onReplay: () -> Unit) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val totalQ = QuestionData.questions[uiState.topic]?.size ?: 0
        SmartText( // FIXED
            if (uiState.lang == Lang.BN) "রাউন্ড শেষ!" else "Round complete!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDark) Color(0xFF75AADB) else Color(0xFF378ADD)
        )
        Spacer(modifier = Modifier.height(32.dp))
        
        // Stats Grid
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(if (uiState.lang == Lang.BN) "স্কোর" else "Score", "${uiState.score}", modifier = Modifier.weight(1f))
            StatCard(if (uiState.lang == Lang.BN) "স্ট্রিক" else "Streak", "${uiState.streak}", modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
            StatCard(if (uiState.lang == Lang.BN) "ভুল" else "Wrong", "${uiState.wrongCount}", modifier = Modifier.weight(1f))
            StatCard(if (uiState.lang == Lang.BN) "মোট প্রশ্ন" else "Total Q", "$totalQ", modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = onReplay,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            SmartText(if (uiState.lang == Lang.BN) "আবার খেলো" else "Play again", fontSize = 18.sp) // FIXED
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val bg = if (isDark) Color(0xFF1E1E1E) else Color.White
    val borderCol = if (isDark) Color(0xFF2A2A2A) else Color(0xFFE5E7EB)
    
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = bg,
        border = BorderStroke(1.dp, borderCol)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SmartText(value, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color(0xFF75AADB) else Color(0xFF3B82F6)) // FIXED
            Spacer(modifier = Modifier.height(8.dp))
            SmartText(label, fontSize = 14.sp, color = if (isDark) Color(0xFF9CA3AF) else Color(0xFF6B7280)) // FIXED
        }
    }
}