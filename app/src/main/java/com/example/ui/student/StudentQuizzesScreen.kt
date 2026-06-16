package com.example.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha

import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import com.example.ui.components.DigitTabHeader
import androidx.compose.material.icons.outlined.Notifications
import com.example.ui.quiz.QuizStateManager

internal val NavyBlue: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFBFC2FE) else Color(0xFF54578C)
private val DeepNavy: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3C3F73) else Color(0xFF3B3E66)
private val SoftYellow: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF6D5E13) else Color(0xFFFDE38C)
private val TealGreen: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF004D40) else Color(0xFF006D5B)
private val LightGray: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF33353A) else Color(0xFFEBEBEB)
private val LightLavender: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF282A36) else Color(0xFFDCE1FF)
private val CardBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF1E1E22) else Color.White
private val ScreenBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF121216) else Color(0xFFF8F9FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentQuizzesScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLessonsTab: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToFillBlankQuiz: () -> Unit,
    onNavigateToWeeklyChallenge: () -> Unit,
    onNavigateToTimesTableBlitz: () -> Unit,
    onNavigateToShoppingMath: () -> Unit,
    onNavigateToMathSolver: () -> Unit,
    onNavigateToProfile: () -> Unit = {}
) {
    Scaffold(
        containerColor = ScreenBg,
        topBar = {
            DigitTabHeader(
                actions = {
                    SharedStudentHeaderActions(onNavigateToProfile = onNavigateToProfile)
                }
            )
        },
        bottomBar = {
            StudentBottomNavBar(
                currentRoute = "Quizzes",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLessons = onNavigateToLessonsTab,
                onNavigateToProgress = onNavigateToProgress,
                onNavigateToQuizzes = {},
                onNavigateToProfile = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp, 
                end = 16.dp, 
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HeroGoalSection()
            }
            
            item {
                GameCard(
                    titleBn = "পাঠ ভিত্তিক কুইজ",
                    titleEn = "Lesson Quizzes",
                    icon = Icons.Outlined.MenuBook,
                    iconBgColor = LightLavender,
                    iconColor = NavyBlue,
                    level = "Level 4",
                    buttonText = "Continue",
                    buttonBg = LightGray,
                    buttonTextColor = NavyBlue,
                    onClick = onNavigateToQuiz
                )
            }
            
            item {
                GameCard(
                    titleBn = "শূন্যস্থান পূরণ",
                    titleEn = "Fill in the Blanks",
                    icon = Icons.Outlined.Edit,
                    iconBgColor = LightGray,
                    iconColor = NavyBlue,
                    level = null,
                    buttonText = "Start",
                    buttonBg = LightGray,
                    buttonTextColor = NavyBlue,
                    onClick = onNavigateToFillBlankQuiz
                )
            }
            
            item {
                GameCard(
                    titleBn = "অংক সমাধান",
                    titleEn = "Math Solver",
                    icon = Icons.Outlined.Calculate,
                    iconBgColor = SoftYellow,
                    iconColor = Color.Black,
                    level = null,
                    buttonText = "Start Now",
                    buttonBg = TealGreen,
                    buttonTextColor = Color.White,
                    onClick = onNavigateToQuiz
                )
            }
            
            item {
                GameCard(
                    titleBn = "শব্দার্থ মনে রাখা",
                    titleEn = "Vocabulary Memory",
                    icon = Icons.Outlined.Psychology,
                    iconBgColor = LightGray,
                    iconColor = NavyBlue,
                    level = null,
                    buttonText = "Play",
                    buttonBg = LightGray,
                    buttonTextColor = NavyBlue,
                    onClick = onNavigateToQuiz
                )
            }
            
            item {
                GameCard(
                    titleBn = "নামতা মুখস্ত করা",
                    titleEn = "Times Table Blitz Game Suite",
                    icon = Icons.Outlined.Calculate,
                    iconBgColor = LightLavender,
                    iconColor = NavyBlue,
                    level = null,
                    buttonText = "Play Game",
                    buttonBg = LightGray,
                    buttonTextColor = NavyBlue,
                    onClick = onNavigateToTimesTableBlitz
                )
            }
            
            item {
                GameCard(
                    titleBn = "বাজারের অংক",
                    titleEn = "Shopping Math Game",
                    icon = Icons.Outlined.ShoppingCart,
                    iconBgColor = LightLavender,
                    iconColor = NavyBlue,
                    level = null,
                    buttonText = "Play Game",
                    buttonBg = LightGray,
                    buttonTextColor = NavyBlue,
                    onClick = onNavigateToShoppingMath
                )
            }
            
            item {
                GameCard(
                    titleBn = "গণিত সমাধান",
                    titleEn = "Step-by-Step Math Solver",
                    icon = Icons.Outlined.Calculate,
                    iconBgColor = LightLavender,
                    iconColor = NavyBlue,
                    level = null,
                    buttonText = "Start Solving",
                    buttonBg = LightGray,
                    buttonTextColor = NavyBlue,
                    onClick = onNavigateToMathSolver
                )
            }
            
            item {
                WeeklyChallengeCard(onClick = onNavigateToWeeklyChallenge)
            }
        }
    }
}

@Composable
fun HeroGoalSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = DeepNavy
    ) {
        Box {
            // Background decor
            Box(modifier = Modifier.align(Alignment.TopEnd).size(128.dp).clip(RoundedCornerShape(bottomStart = 100.dp)).background(Color.White.copy(alpha = 0.05f)))
            Box(modifier = Modifier.align(Alignment.BottomStart).size(128.dp).clip(RoundedCornerShape(topEnd = 100.dp)).background(Color.White.copy(alpha = 0.05f)))

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(
                "আজকের কুইজ টার্গেট",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Complete 3 quizzes today to\nmaintain your streak!",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("DAILY PROGRESS", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Text("1/3 Quizzes", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            
            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.33f)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(SoftYellow)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Box(
                modifier = Modifier
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.05f))
                )
                Box(
                    modifier = Modifier.size(72.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = SoftYellow,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
        }
    }
}

@Composable
fun GameCard(
    titleBn: String,
    titleEn: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconColor: Color,
    level: String?,
    buttonText: String,
    buttonBg: Color,
    buttonTextColor: Color,
    onClick: () -> Unit
) {
    val quizState = QuizStateManager.getState(titleEn)
    if (quizState.isHidden) return

    val isLocked = quizState.isLocked

    Surface(
        modifier = Modifier.fillMaxWidth().alpha(if (isLocked) 0.6f else 1f),
        shape = RoundedCornerShape(32.dp),
        color = CardBg,
        shadowElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (com.example.ui.theme.isAppInDarkTheme()) Color(0xFF33353A) else Color(0xFFE1E3E4))
    ) {
        Box {
            // Background decor
            Box(modifier = Modifier
                .align(Alignment.TopEnd)
                .size(128.dp)
                .clip(RoundedCornerShape(bottomStart = 100.dp))
                .background(if (com.example.ui.theme.isAppInDarkTheme()) iconColor.copy(alpha = 0.1f) else iconBgColor.copy(alpha = 0.5f))
            )

            Column(
                modifier = Modifier.padding(24.dp)
            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(titleBn, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = NavyBlue)
                    Text(titleEn, fontSize = 14.sp, color = NavyBlue.copy(alpha = 0.7f))
                }
                if (level != null && !isLocked) {
                    Text(level, fontSize = 14.sp, color = NavyBlue, fontWeight = FontWeight.Medium)
                } else if (isLocked) {
                    Icon(Icons.Outlined.Lock, contentDescription = "Locked", tint = NavyBlue, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { if (!isLocked) onClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLocked) Color.Gray.copy(alpha = 0.3f) else buttonBg, 
                    contentColor = if (isLocked) NavyBlue.copy(alpha = 0.5f) else buttonTextColor
                )
            ) {
                Text(if (isLocked) "Locked" else buttonText, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        }
    }
}

@Composable
fun WeeklyChallengeCard(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(NavyBlue, DeepNavy)
                )
            )
        ) {
            // Background decor
            Box(modifier = Modifier.align(Alignment.TopEnd).size(128.dp).clip(RoundedCornerShape(bottomStart = 100.dp)).background(Color.White.copy(alpha = 0.1f)))

            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(SoftYellow),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.MilitaryTech, contentDescription = "Trophy", tint = Color.Black, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = CircleShape
                        ) {
                            Text(
                                "Weekly Challenge",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "সাপ্তাহিক চ্যালেঞ্জ",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = SoftYellow, contentColor = Color.Black)
                ) {
                    Text("Join Challenge", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
