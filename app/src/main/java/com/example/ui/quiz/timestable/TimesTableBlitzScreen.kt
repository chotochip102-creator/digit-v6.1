package com.example.ui.quiz.timestable

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

val PrimaryIndigo = Color(0xFF54578c)
val SecondaryTeal = Color(0xFF1a5f7a)
val SurfaceOffWhite = Color(0xFFf8f9fa)
val ContainerWhite = Color(0xFFffffff)
val AccentLavender = Color(0xFFe0e0ff)
val NeutralMuted = Color(0xFF6c757d)
val ErrorRed = Color(0xFFba1a1a)
val SuccessTeal = SecondaryTeal

enum class AppScreen {
    MODE_SELECT,
    ACTIVE_GAME,
    RESULTS
}

enum class Difficulty(val title: String, val desc: String, val range: IntRange) {
    BEGINNER("Beginner", "Tables 1 to 10", 1..10),
    INTERMEDIATE("Intermediate", "Tables 11 to 15", 11..15),
    EXPERT("Expert", "Tables 16 to 20", 16..20)
}

data class Question(val a: Int, val b: Int, var userAnswer: Int? = null) {
    val correctAnswer = a * b
    val isAnswered get() = userAnswer != null
    val isCorrect get() = userAnswer == correctAnswer
}

fun generateQuestions(difficulty: Difficulty, count: Int = 15): List<Question> {
    return (1..count).map {
        Question(
            a = difficulty.range.random(),
            b = (1..10).random()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimesTableBlitzApp(onNavigateBack: () -> Unit) {
    var currentScreen by remember { mutableStateOf(AppScreen.MODE_SELECT) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.BEGINNER) }
    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
    
    Scaffold(
        containerColor = SurfaceOffWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Math Tables",
                        color = PrimaryIndigo,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentScreen == AppScreen.MODE_SELECT) onNavigateBack()
                        else currentScreen = AppScreen.MODE_SELECT
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryIndigo)
                    }
                },
                actions = {
                    TextButton(onClick = { }) {
                        Text("HELP", color = PrimaryIndigo, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceOffWhite)
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Crossfade(targetState = currentScreen, animationSpec = tween(300)) { screen ->
                when (screen) {
                    AppScreen.MODE_SELECT -> {
                        ModeSelectScreen(
                            selectedDifficulty = selectedDifficulty,
                            onDifficultySelected = { selectedDifficulty = it },
                            onStart = {
                                questions = generateQuestions(selectedDifficulty)
                                currentScreen = AppScreen.ACTIVE_GAME
                            }
                        )
                    }
                    AppScreen.ACTIVE_GAME -> {
                        ActiveGameScreen(
                            questions = questions,
                            onComplete = { currentScreen = AppScreen.RESULTS }
                        )
                    }
                    AppScreen.RESULTS -> {
                        ResultsScreen(
                            questions = questions,
                            onPlayAgain = {
                                questions = generateQuestions(selectedDifficulty)
                                currentScreen = AppScreen.ACTIVE_GAME
                            },
                            onChangeMode = {
                                currentScreen = AppScreen.MODE_SELECT
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ModeSelectScreen(
    selectedDifficulty: Difficulty,
    onDifficultySelected: (Difficulty) -> Unit,
    onStart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hero Header
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = ContainerWhite,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "Times Table Blitz",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryIndigo,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Master your multiplication skills\nwith timed challenges",
                    fontSize = 16.sp,
                    color = NeutralMuted,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Difficulty Grid
        DifficultyCard(Difficulty.BEGINNER, Icons.Outlined.School, selectedDifficulty == Difficulty.BEGINNER) {
            onDifficultySelected(Difficulty.BEGINNER)
        }
        Spacer(modifier = Modifier.height(12.dp))
        DifficultyCard(Difficulty.INTERMEDIATE, Icons.Outlined.Timer, selectedDifficulty == Difficulty.INTERMEDIATE) {
            onDifficultySelected(Difficulty.INTERMEDIATE)
        }
        Spacer(modifier = Modifier.height(12.dp))
        DifficultyCard(Difficulty.EXPERT, Icons.Outlined.Bolt, selectedDifficulty == Difficulty.EXPERT) {
            onDifficultySelected(Difficulty.EXPERT)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Primary Action
        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth().height(64.dp),
            shape = RoundedCornerShape(percent = 50),
            colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal)
        ) {
            Text("Start Challenge", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DifficultyCard(difficulty: Difficulty, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor = if (isSelected) AccentLavender else ContainerWhite
    val borderColor = if (isSelected) PrimaryIndigo.copy(alpha = 0.5f) else Color.Transparent

    Surface(
        shape = RoundedCornerShape(32.dp),
        color = bgColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp)
        ) {
            Box(
                modifier = Modifier.size(56.dp).clip(CircleShape).background(if (isSelected) ContainerWhite else SurfaceOffWhite),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = PrimaryIndigo)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(difficulty.title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = PrimaryIndigo)
                Spacer(modifier = Modifier.height(4.dp))
                Text(difficulty.desc, fontSize = 14.sp, color = NeutralMuted)
            }
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .border(2.dp, if (isSelected) AccentLavender.copy(alpha = 0.5f) else NeutralMuted.copy(alpha = 0.3f), CircleShape)
                    .background(Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Empty circle as per UI
            }
        }
    }
}

@Composable
fun ActiveGameScreen(
    questions: List<Question>,
    onComplete: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    val currentQuestion = questions.getOrNull(currentIndex)
    var inputText by remember { mutableStateOf("") }
    
    // Calculate Score & Streak
    var score by remember { mutableStateOf(0) }
    var streak by remember { mutableStateOf(0) }

    if (currentQuestion == null) {
        LaunchedEffect(Unit) { onComplete() }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Score Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("SCORE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NeutralMuted, letterSpacing = 1.sp)
                Text("%,d".format(score), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("STREAK", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NeutralMuted, letterSpacing = 1.sp)
                Text("$streak", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFe88b4a))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Progress Trackers
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            questions.forEachIndexed { index, question ->
                val dotColor = when {
                    index == currentIndex -> PrimaryIndigo
                    question.isAnswered && question.isCorrect -> SecondaryTeal
                    question.isAnswered && !question.isCorrect -> ErrorRed
                    else -> NeutralMuted.copy(alpha = 0.2f)
                }
                Box(
                    modifier = Modifier.padding(2.dp).size(10.dp).clip(CircleShape).background(dotColor)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape).background(NeutralMuted.copy(alpha = 0.2f))
        ) {
            val progress = (currentIndex + 1).toFloat() / questions.size.toFloat()
            Box(modifier = Modifier.fillMaxWidth(progress).fillMaxHeight().clip(CircleShape).background(SecondaryTeal))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Question Card
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = ContainerWhite,
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.padding(vertical = 40.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "TIMES TABLE BLITZ", 
                    fontSize = 12.sp, 
                    fontWeight = FontWeight.Bold, 
                    color = NeutralMuted,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Question ${currentIndex + 1} of ${questions.size}", fontSize = 18.sp, color = PrimaryIndigo)
                
                Spacer(modifier = Modifier.height(48.dp))
                
                Text(
                    text = "${currentQuestion.a} × ${currentQuestion.b} = ?",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryIndigo,
                    letterSpacing = (-2).sp
                )
                
                Spacer(modifier = Modifier.height(48.dp))
                
                // Input Area
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = { if (it.length <= 4) inputText = it.filter { char -> char.isDigit() } },
                        modifier = Modifier.weight(1f).height(72.dp),
                        shape = RoundedCornerShape(percent = 50),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = SurfaceOffWhite,
                            unfocusedContainerColor = SurfaceOffWhite,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text("?", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                        leadingIcon = { Text("=", fontSize = 24.sp, color = NeutralMuted, modifier = Modifier.padding(start = 24.dp)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo, textAlign = TextAlign.Center),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            val answer = inputText.toIntOrNull()
                            if (answer != null) {
                                currentQuestion.userAnswer = answer
                                if (currentQuestion.isCorrect) {
                                    score += 100 + (streak * 20)
                                    streak += 1
                                } else {
                                    streak = 0
                                }
                                inputText = ""
                                currentIndex += 1
                            }
                        },
                        modifier = Modifier.height(72.dp).width(100.dp),
                        shape = RoundedCornerShape(percent = 40),
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal)
                    ) {
                        Text("OK", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ResultsScreen(
    questions: List<Question>,
    onPlayAgain: () -> Unit,
    onChangeMode: () -> Unit
) {
    val correctCount = questions.count { it.isCorrect }
    val totalQuestions = questions.size
    val accuracy = if (totalQuestions > 0) (correctCount * 100) / totalQuestions else 0
    val score = questions.filter { it.isCorrect }.sumOf { 10 } // Simplified score for review
    
    // Find longest streak
    var maxStreak = 0
    var currentStreak = 0
    questions.forEach {
        if (it.isCorrect) {
            currentStreak++
            if (currentStreak > maxStreak) maxStreak = currentStreak
        } else {
            currentStreak = 0
        }
    }
    
    val missedQuestions = questions.filter { !it.isCorrect }

    val scoreText = if (score > 0) "%,d".format(score) else "0"

    Column(modifier = Modifier.fillMaxSize()) {
        // Top section with SurfaceOffWhite background
        Surface(
            color = SurfaceOffWhite,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Main Result Card
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = ContainerWhite,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = scoreText,
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryIndigo,
                            style = androidx.compose.ui.text.TextStyle(letterSpacing = (-2).sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Points Scored", fontSize = 20.sp, color = NeutralMuted, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
        
        // Bottom section with Black background
        Surface(
            color = Color.Black,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StatBox(modifier = Modifier.weight(1f), title = "$correctCount/$totalQuestions", subtitle = "CORRECT")
                        StatBox(modifier = Modifier.weight(1f), title = "$accuracy%", subtitle = "ACCURACY")
                        StatBox(modifier = Modifier.weight(1f), title = "${maxStreak}x", subtitle = "STREAK")
                    }
                }

                item {
                    if (missedQuestions.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(32.dp),
                            color = SurfaceOffWhite,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text("Review these:", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.padding(bottom = 16.dp))
                                
                                missedQuestions.forEach { mq ->
                                    Surface(
                                        shape = CircleShape,
                                        color = ContainerWhite,
                                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text("${mq.a} × ${mq.b} = ", fontSize = 18.sp, color = PrimaryIndigo, fontWeight = FontWeight.Medium)
                                            Text("${mq.correctAnswer}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = ErrorRed)
                                            Text(" (you: ${mq.userAnswer ?: "skip"})", fontSize = 14.sp, color = NeutralMuted, modifier = Modifier.padding(start = 8.dp))
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // Showing a state when no miss
                        Surface(
                            shape = RoundedCornerShape(32.dp),
                            color = SurfaceOffWhite,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = SecondaryTeal, modifier = Modifier.size(48.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Perfect Score!", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo)
                                Text("You didn't miss any questions.", fontSize = 16.sp, color = NeutralMuted, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }

                item {
                    Button(
                        onClick = onPlayAgain,
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(percent = 50),
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal)
                    ) {
                        Icon(Icons.Outlined.Refresh, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Play Again", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = onChangeMode,
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(percent = 50),
                        border = BorderStroke(1.dp, PrimaryIndigo)
                    ) {
                        Icon(Icons.Outlined.Tune, contentDescription = null, tint = PrimaryIndigo)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Change Mode", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo)
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

@Composable
fun StatBox(modifier: Modifier = Modifier, title: String, subtitle: String) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = ContainerWhite,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryIndigo)
            Spacer(modifier = Modifier.height(4.dp))
            Text(subtitle, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeutralMuted, letterSpacing = 1.sp)
        }
    }
}
