package com.example.ui.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.ui.components.DigitTabHeader
import androidx.compose.material.icons.outlined.Notifications

private val ProgressScreenBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF191C1E) else Color(0xFFF8F9FA)
private val ProgressCardBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF282A2D) else Color(0xFFFFFFFF)
private val ProgressPrimaryText: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFE1E2E4) else Color(0xFF1A1C1E)
private val ProgressSecondaryText: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFC4C7C5) else Color(0xFF5E6368)
private val ProgressBorder: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3F4245) else Color(0xFFEEF0F2)

private val ProgressTrackColor: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3F4245) else Color(0xFFEEF0F2)

private val Indigo = Color(0xFF54578C)
private val Teal = Color(0xFF1A5F7A)
private val Orange = Color(0xFFFB8500)
private val RedMagenta = Color(0xFFD9415D)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressTrackerScreen(
    onNavigateHome: () -> Unit,
    onNavigateLessons: () -> Unit,
    onNavigateQuizzes: () -> Unit,
    onNavigateBack: () -> Unit,
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
                currentRoute = "Progress",
                onNavigateToHome = onNavigateHome,
                onNavigateToLessons = onNavigateLessons,
                onNavigateToProgress = {},
                onNavigateToQuizzes = onNavigateQuizzes,
                onNavigateToProfile = onNavigateToProfile
            )
        },
        containerColor = ProgressScreenBg
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(), 
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 24.dp
            )
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                CoreMetricsGrid()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                SubjectProgressSectionNew()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                RecentBadgesSection()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                ActivityChartSection()
            }
        }
    }
}

@Composable
fun CoreMetricsGrid() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        color = if (isSystemInDarkTheme()) Color(0xFF282A2D).copy(alpha = 0.5f) else Color(0xFFF0F2F6),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = Indigo, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("My progress", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Indigo)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.height(IntrinsicSize.Max), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricCard(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    value = "24",
                    label = "Lessons done",
                    valueColor = Indigo
                )
                MetricCard(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    value = "87%",
                    label = "Quiz accuracy",
                    valueColor = Teal
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.height(IntrinsicSize.Max), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricCard(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    value = "1,190",
                    label = "Total points",
                    valueColor = Orange
                )
                MetricCard(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    value = "6h 40m",
                    label = "Study time",
                    valueColor = RedMagenta
                )
            }
        }
    }
}

@Composable
fun MetricCard(modifier: Modifier = Modifier, value: String, label: String, valueColor: Color) {
    val isDark = isSystemInDarkTheme()
    val adjustedValueColor = if (isDark) {
        when (valueColor) {
            Indigo -> Color(0xFFA9ADFF)
            Teal -> Color(0xFF5AB6D8)
            Orange -> Color(0xFFFFB74D)
            RedMagenta -> Color(0xFFFF899F)
            else -> valueColor
        }
    } else valueColor

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = ProgressCardBg,
        shadowElevation = if (isDark) 0.dp else 1.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value.replace(" ", "\n"), 
                fontSize = 36.sp, 
                lineHeight = 38.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Start,
                fontWeight = FontWeight.Bold, 
                color = adjustedValueColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontSize = 12.sp, color = ProgressSecondaryText, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
fun SubjectProgressSectionNew() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        color = ProgressCardBg,
        shadowElevation = if (isSystemInDarkTheme()) 0.dp else 1.dp
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Subject Progress", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = ProgressPrimaryText)
            Spacer(modifier = Modifier.height(24.dp))
            
            SubjectProgressItemNew(label = "Math", progress = 0.84f, percentage = "84%", trackColor = ProgressTrackColor, color = Teal)
            Spacer(modifier = Modifier.height(20.dp))
            SubjectProgressItemNew(label = "Science", progress = 0.77f, percentage = "77%", trackColor = ProgressTrackColor, color = Indigo)
            Spacer(modifier = Modifier.height(20.dp))
            SubjectProgressItemNew(label = "English", progress = 0.91f, percentage = "91%", trackColor = ProgressTrackColor, color = Orange)
        }
    }
}

@Composable
fun SubjectProgressItemNew(label: String, progress: Float, percentage: String, trackColor: Color, color: Color) {
    val isDark = isSystemInDarkTheme()
    val adjustedColor = if (isDark) {
        when (color) {
            Indigo -> Color(0xFFA9ADFF)
            Teal -> Color(0xFF5AB6D8)
            Orange -> Color(0xFFFFB74D)
            else -> color
        }
    } else color

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontWeight = FontWeight.Medium, fontSize = 16.sp, color = ProgressPrimaryText)
            Text(percentage, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = adjustedColor)
        }
        Spacer(modifier = Modifier.height(8.dp))
        CustomProgressBar(
            progress = progress,
            modifier = Modifier.fillMaxWidth().height(12.dp),
            color = adjustedColor,
            trackColor = trackColor
        )
    }
}

@Composable
fun RecentBadgesSection() {
    val isDark = isSystemInDarkTheme()
    var showBadgesDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        color = ProgressCardBg,
        shadowElevation = if (isDark) 0.dp else 1.dp
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Recent Badges", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = ProgressPrimaryText)
                Text(
                    "See all",
                    fontSize = 14.sp,
                    color = if (isDark) Color(0xFFA9ADFF) else Indigo,
                    modifier = Modifier.clickable { showBadgesDialog = true }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                BadgeItem(
                    icon = Icons.Default.Star,
                    label = "Rising Star",
                    circleColor = if (isDark) Color(0xFF332B13) else Color(0xFFFFF3CD),
                    iconColor = Color(0xFFFFC107)
                )
                BadgeItem(
                    icon = Icons.Default.LocalFireDepartment,
                    label = "7 Day Streak",
                    circleColor = if (isDark) Color(0xFF3A1C22) else Color(0xFFFFE4E6),
                    iconColor = Color(0xFFF43F5E)
                )
                BadgeItem(
                    icon = Icons.Default.School,
                    label = "Quiz Master",
                    circleColor = if (isDark) Color(0xFF1E2138) else Color(0xFFE0E7FF),
                    iconColor = if (isDark) Color(0xFFA9ADFF) else Indigo
                )
            }
        }
    }

    if (showBadgesDialog) {
        BadgesDialog(onDismiss = { showBadgesDialog = false })
    }
}

@Composable
fun BadgeItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, circleColor: Color, iconColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            color = circleColor,
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(40.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(label, fontSize = 12.sp, color = ProgressSecondaryText)
    }
}

@Composable
fun ActivityChartSection() {
    val isDark = isSystemInDarkTheme()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        color = ProgressCardBg,
        shadowElevation = if (isDark) 0.dp else 1.dp
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Activity", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = ProgressPrimaryText)
            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                ActivityBar("M", 0.4f, false)
                ActivityBar("T", 0.6f, false)
                ActivityBar("W", 0.5f, false)
                ActivityBar("T", 0.8f, false)
                ActivityBar("F", 0.3f, false)
                ActivityBar("S", 1.0f, true)
                ActivityBar("S", 0.7f, false)
            }
        }
    }
}

@Composable
fun ActivityBar(day: String, ratio: Float, isActive: Boolean) {
    val maxBarHeight = 120.dp
    val barHeight = maxBarHeight * ratio
    val isDark = isSystemInDarkTheme()
    val activeColor = if (isDark) Color(0xFFA9ADFF) else Indigo
    val color = if (isActive) activeColor else ProgressTrackColor
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier
                .width(12.dp)
                .height(barHeight),
            shape = RoundedCornerShape(50),
            color = color
        ) {}
        Spacer(modifier = Modifier.height(16.dp))
        Text(day, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = ProgressSecondaryText)
    }
}
