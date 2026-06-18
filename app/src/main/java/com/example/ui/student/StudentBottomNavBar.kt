package com.example.ui.student
import com.example.ui.components.SmartText

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentBottomNavBar(
    currentRoute: String,
    onNavigateToHome: () -> Unit,
    onNavigateToLessons: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToQuizzes: () -> Unit,
    onNavigateToProfile: () -> Unit = {}
) {
    val isDark = isSystemInDarkTheme()
    val pillBg = if (isDark) Color(0xFF282A2D).copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = RoundedCornerShape(percent = 50),
            color = pillBg,
            shadowElevation = if (isDark) 0.dp else 4.dp,
            border = if (isDark) androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f)) else null
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(
                    icon = if (currentRoute == "Home") Icons.Filled.Home else Icons.Outlined.Home,
                    label = "Home",
                    selected = currentRoute == "Home",
                    onClick = onNavigateToHome,
                    modifier = Modifier.weight(1f)
                )
                NavItem(
                    icon = if (currentRoute == "Lessons") Icons.Filled.LibraryBooks else Icons.Outlined.LibraryBooks,
                    label = "Lessons",
                    selected = currentRoute == "Lessons",
                    onClick = onNavigateToLessons,
                    modifier = Modifier.weight(1f)
                )
                NavItem(
                    icon = if (currentRoute == "Progress") Icons.Filled.BarChart else Icons.Outlined.BarChart,
                    label = "Progress",
                    selected = currentRoute == "Progress",
                    onClick = onNavigateToProgress,
                    modifier = Modifier.weight(1f)
                )
                NavItem(
                    icon = if (currentRoute == "Quizzes") Icons.Filled.Quiz else Icons.Outlined.Quiz,
                    label = "Quizzes",
                    selected = currentRoute == "Quizzes",
                    onClick = onNavigateToQuizzes,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val selectedBgColor = if (isDark) Color(0xFF3C3F73) else Color(0xFFDCE1FF)
    val selectedContentColor = if (isDark) Color(0xFFBFC2FE) else Color(0xFF54578C)
    // using colors from the design reference, deep gray for unselected text/icon
    val unselectedColor = if (isDark) Color(0xFFA0A0A0) else Color(0xFF4A4A4A)

    val bgColor by animateColorAsState(if (selected) selectedBgColor else Color.Transparent)
    val contentColor by animateColorAsState(if (selected) selectedContentColor else unselectedColor)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        SmartText( // FIXED
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = contentColor
        )
    }
}