package com.example.ui.student
import com.example.ui.components.SmartText

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import com.example.ui.components.DigitTabHeader
import androidx.compose.material.icons.outlined.Notifications

val Primary: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFFBFC2FE) else Color(0xFF3C3F73)
val PrimaryContainer: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3C3F73) else Color(0xFF54578C)
val PrimaryFixed: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFFE0E0FF) else Color(0xFFE0E0FF)
val SecondaryContainer: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF40436E) else Color(0xFFC9CBFF)
val TertiaryContainer: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF6D5E13) else Color(0xFFBFAB5A)
val AchievementOrange: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFFE59A6D) else Color(0xFFD38350)
val SurfaceCard: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF1E1E22) else Color(0xFFFFFFFF)
val SurfaceContainerHighest: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF33353A) else Color(0xFFE1E3E4)
val SurfaceContainer: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF2B2D31) else Color(0xFFEDEEEF)
val SurfaceContainerLow: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF282A2F) else Color(0xFFF3F4F5)
val SurfaceBg: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFF121216) else Color(0xFFF8F9FA)
val OnSurface: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFFE1E3E4) else Color(0xFF191C1D)
val OnSurfaceVariant: Color
    @Composable get() = if (isSystemInDarkTheme()) Color(0xFFC4C6D0) else Color(0xFF46464F)

@Composable
fun SharedStudentHeaderActions(onNavigateToProfile: () -> Unit) {
    Box(contentAlignment = Alignment.TopEnd) {
        IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(40.dp)) {
            Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Primary, modifier = Modifier.size(24.dp))
        }
        Box(
            modifier = Modifier
                .padding(top = 8.dp, end = 8.dp)
                .size(8.dp)
                .background(Color.Red, CircleShape)
        )
    }
    Spacer(modifier = Modifier.width(8.dp))
    Surface(
        modifier = Modifier.size(40.dp).clickable(onClick = onNavigateToProfile),
        shape = CircleShape,
        color = PrimaryContainer
    ) {
        Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(8.dp), tint = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToLesson: () -> Unit,
    onNavigateToLessonsTab: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    onNavigateToFlashcards: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToQuizzesTab: () -> Unit,
    onNavigateToProfile: () -> Unit = {}
) {
    Scaffold(
        containerColor = SurfaceBg,
        topBar = {
            DigitTabHeader(
                actions = {
                    SharedStudentHeaderActions(onNavigateToProfile)
                }
            )
        },
        bottomBar = {
            StudentBottomNavBar(
                currentRoute = "Home",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLessons = onNavigateToLessonsTab,
                onNavigateToProgress = onNavigateToProgress,
                onNavigateToQuizzes = onNavigateToQuizzesTab,
                onNavigateToProfile = onNavigateToProfile
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            )
        ) {
            
            // Activity Slideshow
            item {
                DailyActivitySlideshow(onNavigateToLesson)
            }
            
            // Daily Goal
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(32.dp),
                    color = SurfaceCard,
                    shadowElevation = 0.dp,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.GpsFixed, contentDescription = null, tint = PrimaryContainer, modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                SmartText("Daily goals", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = OnSurface) // FIXED
                            }
                            SmartText("2 of 4 done", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = OnSurfaceVariant) // FIXED
                        }

                        DailyGoalItem(title = "Complete 1 lesson", xp = 10, isCompleted = true)
                        DailyGoalItem(title = "Take a quiz", xp = 15, isCompleted = true)
                        DailyGoalItem(title = "Review previous lesson", xp = 5, isCompleted = false)
                        DailyGoalItem(title = "Read PDF lesson", xp = 20, isCompleted = false)
                        
                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = SurfaceContainerHighest)
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SmartText("Today's XP earned", color = OnSurface, fontSize = 16.sp, fontWeight = FontWeight.Medium) // FIXED
                            SmartText("25 / 50 XP", color = Color(0xFFFB8500), fontSize = 18.sp, fontWeight = FontWeight.Bold) // FIXED
                        }
                    }
                }
            }

            // Recommended for you
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Grade, contentDescription = null, tint = PrimaryContainer, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            SmartText("Recommended for you", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = OnSurface) // FIXED
                        }
                        SmartText("See all", fontWeight = FontWeight.Medium, fontSize = 16.sp, color = PrimaryContainer, modifier = Modifier.clickable { /* see all */ }) // FIXED
                    }
                    
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(32.dp),
                        color = SurfaceCard,
                        shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
                    ) {
                        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            RecommendedItem(
                                icon = Icons.Default.Functions,
                                title = "Algebra — Equations",
                                subtitle = "Math • 15 min",
                                badgeText = "Weak area",
                                iconBgColor = Color(0xFFEFF4FF),
                                contentColor = Color(0xFF54578C),
                                badgeBgColor = Color(0xFFE8F0FE)
                            )
                            RecommendedItem(
                                icon = Icons.Default.Science,
                                title = "Cell structure quiz",
                                subtitle = "Science • 5 questions",
                                badgeText = "New",
                                iconBgColor = Color(0xFFE6F4EA),
                                contentColor = Color(0xFF1A5F7A),
                                badgeBgColor = Color(0xFFE6F4EA)
                            )
                            RecommendedItem(
                                icon = Icons.Default.Public,
                                title = "Geography — Rivers",
                                subtitle = "Review • Due today",
                                badgeText = "Revision",
                                iconBgColor = Color(0xFFFFF7ED),
                                contentColor = Color(0xFFFB8500),
                                badgeBgColor = Color(0xFFFFF7ED)
                            )
                        }
                    }
                }
            }

            // My Subjects
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.MenuBook, 
                        contentDescription = "My subjects", 
                        tint = Primary, 
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    SmartText("My subjects", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Primary) // FIXED
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        SubjectCard(
                            subjectEn = "Mathematics",
                            subjectBn = "গণিত",
                            progress = 0.72f,
                            icon = Icons.Default.Calculate,
                            iconBgColor = Color(0xFFF1EEFE),
                            iconTintColor = Color(0xFF5C52C9),
                            progressColor = Color(0xFF5C52C9),
                            modifier = Modifier.weight(1f)
                        )
                        SubjectCard(
                            subjectEn = "Science",
                            subjectBn = "বিজ্ঞান",
                            progress = 0.45f,
                            icon = Icons.Default.Science,
                            iconBgColor = Color(0xFFE8F6F1),
                            iconTintColor = Color(0xFF2B8F72),
                            progressColor = Color(0xFF2B8F72),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        SubjectCard(
                            subjectEn = "English",
                            subjectBn = "ইংরেজি",
                            progress = 0.60f,
                            icon = Icons.Default.Language,
                            iconBgColor = Color(0xFFFBF2E6),
                            iconTintColor = Color(0xFFC7782A),
                            progressColor = Color(0xFFC7782A),
                            modifier = Modifier.weight(1f)
                        )
                        SubjectCard(
                            subjectEn = "Bangla",
                            subjectBn = "বাংলা",
                            progress = 0.88f,
                            icon = Icons.Default.Translate,
                            iconBgColor = Color(0xFFFCECF2),
                            iconTintColor = Color(0xFFD44B7E),
                            progressColor = Color(0xFFD44B7E),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun SubjectCard(
    subjectEn: String,
    subjectBn: String,
    progress: Float,
    icon: ImageVector,
    iconBgColor: Color,
    iconTintColor: Color,
    progressColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = SurfaceCard,
        shadowElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = iconBgColor,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(icon, contentDescription = subjectEn, tint = iconTintColor, modifier = Modifier.padding(12.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                SmartText(subjectEn, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = OnSurface) // FIXED
                SmartText(subjectBn, fontSize = 12.sp, color = OnSurfaceVariant) // FIXED
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CustomProgressBar(
                    progress = progress,
                    modifier = Modifier.weight(1f).height(12.dp),
                    color = progressColor,
                    trackColor = SurfaceContainerHighest
                )
                Surface(
                    color = SurfaceContainer,
                    shape = RoundedCornerShape(percent = 50)
                ) {
                    SmartText( // FIXED
                        "${(progress * 100).toInt()}%",
                        color = progressColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DailyGoalItem(
    title: String,
    xp: Int,
    isCompleted: Boolean
) {
    val iconColor = if (isCompleted) PrimaryContainer else OnSurfaceVariant
    val icon = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.RadioButtonUnchecked
    val textStyle = if (isCompleted) androidx.compose.ui.text.TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough) else androidx.compose.ui.text.TextStyle.Default
    val textColor = if (isCompleted) OnSurfaceVariant.copy(alpha = 0.6f) else OnSurface
    
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(16.dp))
            SmartText(title, style = textStyle, color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Medium) // FIXED
        }
        SmartText("+$xp XP", color = Color(0xFFFB8500), fontWeight = FontWeight.Bold, fontSize = 16.sp) // FIXED
    }
}

@Composable
fun RecommendedItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    badgeText: String,
    iconBgColor: Color,
    contentColor: Color,
    badgeBgColor: Color
) {
    val isDark = com.example.ui.theme.isAppInDarkTheme()
    val adjustedIconBg = if (isDark) contentColor.copy(alpha = 0.2f) else iconBgColor
    val adjustedBadgeBg = if (isDark) contentColor.copy(alpha = 0.2f) else badgeBgColor
    val adjustedContentColor = if (isDark) {
        when (contentColor) {
            Color(0xFF54578C) -> Color(0xFFA9ADFF)
            Color(0xFF1A5F7A) -> Color(0xFF5AB6D8)
            Color(0xFFFB8500) -> Color(0xFFFFB74D)
            else -> contentColor
        }
    } else {
        contentColor
    }

    Row(
        modifier = Modifier.fillMaxWidth().clickable { },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = adjustedIconBg,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(icon, contentDescription = null, tint = adjustedContentColor, modifier = Modifier.padding(12.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                SmartText(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = OnSurface) // FIXED
                SmartText(subtitle, fontSize = 14.sp, color = OnSurfaceVariant) // FIXED
            }
        }
        
        Surface(
            shape = RoundedCornerShape(percent = 50),
            color = adjustedBadgeBg
        ) {
            SmartText( // FIXED
                text = badgeText.uppercase(),
                color = adjustedContentColor,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun CustomProgressBar(
    progress: Float,
    color: Color,
    trackColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(trackColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = progress.coerceIn(0f, 1f))
                .fillMaxHeight()
                .background(color)
        )
    }
}