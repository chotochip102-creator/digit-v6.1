package com.example.ui.student
import com.example.ui.components.SmartText

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

import com.example.ui.SlideshowDataStore

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DailyActivitySlideshow(onNavigateToLesson: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 4 })

    LaunchedEffect(Unit) {
        while (true) {
            var waited = 0
            while (waited < 5000) {
                delay(100)
                // Don't count waiting time if user is interacting/dragging
                if (!pagerState.isScrollInProgress) {
                    waited += 100
                }
            }

            val nextPage = (pagerState.currentPage + 1) % 4
            
            try {
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                )
            } catch (e: kotlinx.coroutines.CancellationException) {
                if (!isActive) throw e
            }
            
            // Safely update contents of pages that are now completely off-screen
            val current = pagerState.currentPage
            
            if (current == 2) { // Page 0 (Quiz) is far off-screen
                if (SlideshowDataStore.quickQuizNeedsUpdate.value) {
                    val quizzesSize = SlideshowDataStore.quickQuizRows.value.count { it.any { cell -> cell.isNotBlank() } }
                    if (quizzesSize > 0) {
                        SlideshowDataStore.currentQuizIndex.intValue = (SlideshowDataStore.currentQuizIndex.intValue + 1) % quizzesSize
                    }
                    SlideshowDataStore.quickQuizNeedsUpdate.value = false
                    SlideshowDataStore.quickQuizAnswered.value = null
                    SlideshowDataStore.quickQuizSelected.value = null
                }
            }
            
            if (current == 3) { // Page 1 (Did You Know) is off-screen
                SlideshowDataStore.slideshowCycleCount.intValue++
                if (SlideshowDataStore.slideshowCycleCount.intValue % 3 == 0) {
                    val factsSize = SlideshowDataStore.didYouKnowRows.value.count { it.any { cell -> cell.isNotBlank() } }
                    if (factsSize > 0) SlideshowDataStore.currentFactIndex.intValue = (SlideshowDataStore.currentFactIndex.intValue + 1) % factsSize
                }
            }
            
            if (current == 1) { // Page 3 (Word Meaning) is off-screen
                if (SlideshowDataStore.slideshowCycleCount.intValue % 5 == 0) {
                    val wordsSize = SlideshowDataStore.wordMeaningRows.value.count { it.any { cell -> cell.isNotBlank() } }
                    if (wordsSize > 0) SlideshowDataStore.currentWordIndex.intValue = (SlideshowDataStore.currentWordIndex.intValue + 1) % wordsSize
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp
        ) { page ->
            Box(
                modifier = Modifier.graphicsLayer {
                    val pageOffset = (
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    ).coerceIn(-1f, 1f)
                    val scale = 1f - (0.05f * kotlin.math.abs(pageOffset))
                    scaleX = scale
                    scaleY = scale
                    alpha = 1f - (0.2f * kotlin.math.abs(pageOffset))
                }
            ) {
                when (page) {
                    0 -> DidYouKnowCard()
                    1 -> DailyChallengeCard()
                    2 -> WordOfTheDayCard()
                    3 -> ReviewPreviousLessonCard(onNavigateToLesson)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(4) { iteration ->
                val isSelected = pagerState.currentPage == iteration
                val targetColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                val targetWidth = if (isSelected) 24.dp else 8.dp
                
                val color by androidx.compose.animation.animateColorAsState(
                    targetValue = targetColor,
                    animationSpec = tween(400, easing = FastOutSlowInEasing),
                    label = "color"
                )
                val width by androidx.compose.animation.core.animateDpAsState(
                    targetValue = targetWidth,
                    animationSpec = tween(400, easing = FastOutSlowInEasing),
                    label = "width"
                )
                
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(width = width, height = 8.dp)
                )
            }
        }
    }
}

@Composable
fun DidYouKnowCard() {
    val didYouKnowRows by SlideshowDataStore.didYouKnowRows
    val facts = didYouKnowRows.filter { it.any { cell -> cell.isNotBlank() } }
    val activeRow = facts.getOrNull(SlideshowDataStore.currentFactIndex.intValue % maxOf(1, facts.size)) ?: facts.firstOrNull()
    val fact = activeRow?.getOrNull(0)?.takeIf { it.isNotBlank() } ?: "শুক্র গ্রহের এক দিন পৃথিবীর এক বছরের চেয়ে বড়!"

    SlideshowCardBase(
        gradientBrush = Brush.linearGradient(listOf(Color(0xFFFF8008), Color(0xFFFFC837)))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                SmartText( // FIXED
                    "DID YOU KNOW",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Lightbulb, 
                    contentDescription = null, 
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        SmartText( // FIXED
            fact,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 34.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SlideshowCardBase(
    gradientBrush: Brush = Brush.linearGradient(colors = listOf(Primary, PrimaryContainer)),
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 0.dp,
        color = Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = content
            )
        }
    }
}

@Composable
fun FlashcardOfTheDay() {
    var flipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "flip"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            },
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 0.dp,
        color = Color.Transparent,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF667EEA), Color(0xFF764BA2))
                    )
                )
        ) {
            if (rotation <= 90f) {
                // Front
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp).fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                            SmartText("FLASHCARD OF THE DAY", fontSize = 10.sp, letterSpacing = 2.sp, color = Color.White.copy(alpha = 0.9f)) // FIXED
                        }
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    SmartText("মহাকাশ", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center) // FIXED
                    SmartText("(Mohakash)", fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.9f), textAlign = TextAlign.Center) // FIXED
                    Spacer(modifier = Modifier.height(12.dp))
                    SmartText("Space / Universe", fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f)) // FIXED
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Button(
                        onClick = { flipped = !flipped },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x19FFFFFF), // white/10
                            contentColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                        shape = RoundedCornerShape(percent = 50),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Icon(Icons.Default.Autorenew, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        SmartText("উল্টে দেখুন", fontWeight = FontWeight.SemiBold, fontSize = 14.sp) // FIXED
                    }
                }
            } else {
                // Back
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f }, // Un-flip the content
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SmartText("Definition", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f)) // FIXED
                    Spacer(modifier = Modifier.height(16.dp))
                    SmartText("তাত্ত্বিকভাবে পৃথিবীর বায়ুমণ্ডলের বাইরের অনন্ত স্থান;", fontSize = 18.sp, textAlign = TextAlign.Center, lineHeight = 24.sp, color = Color.White) // FIXED
                    Spacer(modifier = Modifier.height(8.dp))
                    SmartText("The boundless three-dimensional extent in which objects and events have relative position and direction.", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f), textAlign = TextAlign.Center) // FIXED
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { flipped = !flipped },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x19FFFFFF),
                            contentColor = Color.White
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF)),
                        shape = RoundedCornerShape(percent = 50),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Icon(Icons.Default.Autorenew, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        SmartText("উল্টে দেখুন") // FIXED
                    }
                }
            }
        }
    }
}

@Composable
fun DailyChallengeCard() {
    var answered by SlideshowDataStore.quickQuizAnswered
    var selectedOption by SlideshowDataStore.quickQuizSelected
    
    val currentRows by SlideshowDataStore.quickQuizRows
    val quizzes = currentRows.filter { it.any { cell -> cell.isNotBlank() } }
    
    val activeRow = quizzes.getOrNull(SlideshowDataStore.currentQuizIndex.intValue % maxOf(1, quizzes.size)) ?: quizzes.firstOrNull()
    
    val questionText = activeRow?.getOrNull(0)?.takeIf { it.isNotBlank() } ?: "বিপরীত শব্দ লিখুন: আকাশ"
    
    val options = listOf(
        activeRow?.getOrNull(1)?.takeIf { it.isNotBlank() } ?: "পাতাল",
        activeRow?.getOrNull(2)?.takeIf { it.isNotBlank() } ?: "নদী",
        activeRow?.getOrNull(3)?.takeIf { it.isNotBlank() } ?: "মাটি",
        activeRow?.getOrNull(4)?.takeIf { it.isNotBlank() } ?: "বাতাস"
    )
    val answerText = activeRow?.getOrNull(5)?.takeIf { it.isNotBlank() } ?: "পাতাল"
    val correctOption = options.indexOf(answerText).takeIf { it >= 0 } ?: 0

    SlideshowCardBase(
        gradientBrush = Brush.linearGradient(listOf(Color(0xFF00B4DB), Color(0xFF0083B0)))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                SmartText( // FIXED
                    "QUICK QUIZ",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Lightbulb, 
                    contentDescription = null, 
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        SmartText(questionText, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.White) // FIXED
        Spacer(modifier = Modifier.weight(1f))
        
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ChallengeOption(options[0], Modifier.weight(1f), answered, selectedOption == 0, 0 == correctOption) { if(answered == null) { selectedOption = 0; answered = (0 == correctOption); SlideshowDataStore.quickQuizNeedsUpdate.value = true } }
                ChallengeOption(options[1], Modifier.weight(1f), answered, selectedOption == 1, 1 == correctOption) { if(answered == null) { selectedOption = 1; answered = (1 == correctOption); SlideshowDataStore.quickQuizNeedsUpdate.value = true } }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ChallengeOption(options[2], Modifier.weight(1f), answered, selectedOption == 2, 2 == correctOption) { if(answered == null) { selectedOption = 2; answered = (2 == correctOption); SlideshowDataStore.quickQuizNeedsUpdate.value = true } }
                ChallengeOption(options[3], Modifier.weight(1f), answered, selectedOption == 3, 3 == correctOption) { if(answered == null) { selectedOption = 3; answered = (3 == correctOption); SlideshowDataStore.quickQuizNeedsUpdate.value = true } }
            }
        }
        
        androidx.compose.animation.AnimatedVisibility(
            visible = answered != null,
            enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically(),
            exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.shrinkVertically()
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                SmartText(if (answered == true) "সঠিক উত্তর! (Correct!)" else "ভুল উত্তর! (Incorrect!)", color = if (answered == true) Color(0xFFC8E6C9) else Color(0xFFFFCDD2), fontWeight = FontWeight.Bold, fontSize = 14.sp) // FIXED
            }
        }
    }
}

@Composable
fun ChallengeOption(text: String, modifier: Modifier, answered: Boolean?, isSelected: Boolean, isCorrect: Boolean, onClick: () -> Unit) {
    val targetBgColor = when {
        answered != null && isCorrect -> Color(0xFF4CAF50) // Correct
        answered != null && isSelected && !isCorrect -> Color(0xFFFF5252) // Wrong selected
        else -> Color.White.copy(alpha = 0.15f)
    }
    
    val bgColor by androidx.compose.animation.animateColorAsState(
        targetValue = targetBgColor,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "bgColor"
    )
    
    Surface(
        modifier = modifier.height(48.dp),
        onClick = onClick,
        shape = RoundedCornerShape(percent = 50),
        color = bgColor,
        contentColor = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            SmartText(text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold) // FIXED
        }
    }
}

@Composable
fun WordOfTheDayCard() {
    val currentRows by SlideshowDataStore.wordMeaningRows
    val words = currentRows.filter { it.any { cell -> cell.isNotBlank() } }
    val activeRow = words.getOrNull(SlideshowDataStore.currentWordIndex.intValue % maxOf(1, words.size)) ?: words.firstOrNull()

    val wordEn = activeRow?.getOrNull(0)?.takeIf { it.isNotBlank() } ?: "Learn"
    val wordBn = activeRow?.getOrNull(1)?.takeIf { it.isNotBlank() } ?: "শেখা"
    val type = activeRow?.getOrNull(2)?.takeIf { it.isNotBlank() } ?: "Verb"
    val sentenceEn = activeRow?.getOrNull(3)?.takeIf { it.isNotBlank() } ?: "I want to learn something new every day."
    val sentenceBn = activeRow?.getOrNull(4)?.takeIf { it.isNotBlank() } ?: "আমি প্রতিদিন নতুন কিছু শিখতে চাই।"

    SlideshowCardBase(
        gradientBrush = Brush.linearGradient(listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0)))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                SmartText( // FIXED
                    "WORD FLASH",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Style, 
                    contentDescription = null, 
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        SmartText(wordEn, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White) // FIXED
        SmartText("$wordBn • $type", fontSize = 16.sp, color = Color.White.copy(alpha = 0.9f)) // FIXED
        Spacer(modifier = Modifier.weight(1f))
        Surface(
            color = Color.White.copy(alpha = 0.1f),
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                SmartText(sentenceEn, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White) // FIXED
                Spacer(modifier = Modifier.height(6.dp))
                SmartText(sentenceBn, fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f)) // FIXED
            }
        }
    }
}

@Composable
fun ReviewPreviousLessonCard(onNavigateToLesson: () -> Unit) {
    SlideshowCardBase(
        gradientBrush = Brush.linearGradient(listOf(Color(0xFF585AA5), Color(0xFF8F63E5)))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                SmartText(
                    "REVIEW LESSON",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    Icons.Default.Autorenew, 
                    contentDescription = null, 
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(0.8f))
        
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            SmartText("বাংলা ব্যাকরণ", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(4.dp))
            SmartText("বিরাম চিহ্ন বা জ্যোতিচিহ্ন", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        
        Spacer(modifier = Modifier.weight(1.2f))
        
        Button(
            onClick = onNavigateToLesson,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF2D2B5A)),
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SmartText("পড়া চালিয়ে যান", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}