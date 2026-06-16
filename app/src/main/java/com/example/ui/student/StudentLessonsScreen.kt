package com.example.ui.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Functions
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DigitTabHeader
import androidx.compose.material.icons.outlined.Notifications

private val LessonScreenBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF191C1E) else Color(0xFFF8F9FA)
private val LessonCardBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF282A2D) else Color(0xFFFFFFFF)
private val LessonPrimaryText: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFE1E2E4) else Color(0xFF1A1C1E)
private val LessonSecondaryText: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFC4C7C5) else Color(0xFF5E6368)
private val LessonBorder: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3F4245) else Color(0xFFEEF0F2)

private val LessonBlueText: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFC7CDFF) else Color(0xFF3C3F73)
private val LessonLavender: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF2E3150) else Color(0xFFE0E0FF)
private val LessonHeroBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF2E3150) else Color(0xFF3C3F73)
private val LessonTeal: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF14B8A6) else Color(0xFF00695C)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentLessonsScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToQuizzesTab: () -> Unit,
    onNavigateToLesson: () -> Unit,
    onNavigateToProfile: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            DigitTabHeader(
                actions = {
                    SharedStudentHeaderActions(onNavigateToProfile = onNavigateToProfile)
                }
            )
        },
        bottomBar = {
            StudentBottomNavBar(
                currentRoute = "Lessons",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLessons = {},
                onNavigateToProgress = onNavigateToProgress,
                onNavigateToQuizzes = onNavigateToQuizzesTab,
                onNavigateToProfile = onNavigateToProfile
            )
        },
        containerColor = LessonScreenBg
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 24.dp
            )
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "Lessons",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LessonBlueText
                    )
                    Text(
                        text = "পাঠসমূহ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LessonBlueText
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LessonsHeroSection()
                }
            }
            
            item {
                Column {
                    Text("Recently Viewed", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = LessonBlueText, modifier = Modifier.padding(horizontal = 16.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    androidx.compose.foundation.lazy.LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        item {
                            RecentlyViewedItem(titleBn = "বীজগণিত: সূত্রাবলি", titleEn = "Algebra Formulas", icon = Icons.Outlined.Description, progress = 0.6f)
                        }
                        item {
                            RecentlyViewedItem(titleBn = "পদার্থবিদ্যা: গতি", titleEn = "Physics: Motion", icon = Icons.Outlined.Science, progress = 0.2f)
                        }
                    }
                }
            }

            item {
                androidx.compose.foundation.lazy.LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { FilterChip(selected = true, onClick = {}, label = { Text("All Subjects", color = Color.White) }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = LessonBlueText, selectedLabelColor = Color.White), shape = CircleShape, border = null) }
                    item { FilterChip(selected = false, onClick = {}, label = { Text("Mathematics", color = LessonBlueText) }, shape = CircleShape, border = null, colors = FilterChipDefaults.filterChipColors(containerColor = Color(0xFFEEEEEE))) }
                    item { FilterChip(selected = false, onClick = {}, label = { Text("Science", color = LessonBlueText) }, shape = CircleShape, border = null, colors = FilterChipDefaults.filterChipColors(containerColor = Color(0xFFEEEEEE))) }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    LessonItem(titleBn = "বীজগণিত: সূত্রাবলি", titleEn = "Algebra: Formulas", iconBgColor = Color(0xFFC7D2FE), iconColor = Color(0xFF4F46E5), icon = Icons.Outlined.Description, onClick = onNavigateToLesson)
                    LessonItem(titleBn = "পাটিগণিত: ভগ্নাংশ", titleEn = "Arithmetic: Fractions", iconBgColor = Color(0xFFFDE68A), iconColor = Color(0xFFD97706), icon = Icons.Outlined.Calculate, onClick = onNavigateToLesson)
                    LessonItem(titleBn = "ত্রিকোণমিতি: বেসিক", titleEn = "Trigonometry: Basics", iconBgColor = Color(0xFFFECDD3), iconColor = Color(0xFFE11D48), icon = Icons.Outlined.Functions, onClick = onNavigateToLesson)
                }
            }
        }
    }
}

@Composable
fun LessonsHeroSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = Color(0xFF5A5E8D),
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = CircleShape
            ) {
                Text(
                    "CURRENT LESSON",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "জ্যামিতি: ত্রিভুজের\nবৈশিষ্ট্য",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Geometry: Properties of Triangles",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = LessonTeal, contentColor = Color.White),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text("Read Now", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun RecentlyViewedItem(titleBn: String, titleEn: String, icon: androidx.compose.ui.graphics.vector.ImageVector, progress: Float) {
    Surface(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(24.dp),
        color = LessonCardBg,
        shadowElevation = 0.dp,
        border = BorderStroke(1.dp, LessonBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp).heightIn(min = 140.dp)) {
            Box(
                modifier = Modifier.size(40.dp).background(LessonLavender, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = LessonBlueText, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(titleBn, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LessonPrimaryText, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(titleEn, fontSize = 12.sp, color = LessonSecondaryText, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.weight(1f))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                color = LessonBlueText,
                trackColor = LessonBorder
            )
        }
    }
}

@Composable
fun LessonItem(titleBn: String, titleEn: String, iconBgColor: Color, iconColor: Color, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(percent = 50),
        color = LessonCardBg,
        border = BorderStroke(1.dp, LessonBorder),
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(titleBn, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = LessonPrimaryText)
                Spacer(modifier = Modifier.height(2.dp))
                Text(titleEn, fontSize = 14.sp, color = LessonSecondaryText, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(LessonBorder),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = LessonSecondaryText, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
