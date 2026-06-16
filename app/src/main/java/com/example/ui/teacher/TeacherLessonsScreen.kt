package com.example.ui.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.example.ui.components.DigitTabHeader
import com.example.ui.student.SurfaceBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherLessonsScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToQuizzes: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    Scaffold(
        containerColor = SurfaceBg,
        topBar = {
            TeacherHeader(
                onNavigateToAlerts = onNavigateToAlerts,
                onNavigateToProfile = onNavigateToProfile
            )
        },
        bottomBar = {
            TeacherBottomNavBar(
                currentRoute = "Lessons",
                onNavigateToHome = onNavigateToHome,
                onNavigateToLessons = {},
                onNavigateToProgress = onNavigateToProgress,
                onNavigateToQuizzes = onNavigateToQuizzes
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Teacher Lessons")
        }
    }
}
