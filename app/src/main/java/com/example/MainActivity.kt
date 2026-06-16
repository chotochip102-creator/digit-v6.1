package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.whiteboard.WhiteboardScreen
import com.example.ui.student.WeeklyChallengeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = RoleSelection,
                    enterTransition = { slideInHorizontally(animationSpec = tween(400, easing = FastOutSlowInEasing)) { it / 8 } + fadeIn(animationSpec = tween(400, easing = FastOutSlowInEasing)) },
                    exitTransition = { slideOutHorizontally(animationSpec = tween(400, easing = FastOutSlowInEasing)) { -it / 8 } + fadeOut(animationSpec = tween(400, easing = FastOutSlowInEasing)) },
                    popEnterTransition = { slideInHorizontally(animationSpec = tween(400, easing = FastOutSlowInEasing)) { -it / 8 } + fadeIn(animationSpec = tween(400, easing = FastOutSlowInEasing)) },
                    popExitTransition = { slideOutHorizontally(animationSpec = tween(400, easing = FastOutSlowInEasing)) { it / 8 } + fadeOut(animationSpec = tween(400, easing = FastOutSlowInEasing)) }
                ) {
                    composable<RoleSelection> {
                        com.example.ui.role.RoleSelectionScreen(
                            onNavigateToStudent = { navController.navigate(StudentDashboard) },
                            onNavigateToTeacher = { navController.navigate(TeacherDashboard) }
                        )
                    }
                    composable<StudentDashboard>(
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        com.example.ui.student.StudentDashboardScreen(
                            onNavigateToHome = {},
                            onNavigateToLesson = { navController.navigate(LessonDetail) },
                            onNavigateToLessonsTab = { navController.navigate(LessonsTab) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToQuiz = { navController.navigate(LessonQuiz) },
                            onNavigateToFlashcards = { navController.navigate(Flashcard) },
                            onNavigateToProgress = { navController.navigate(ProgressTracker) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToQuizzesTab = { navController.navigate(QuizzesTab) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToProfile = { navController.navigate(StudentProfile) { popUpTo(StudentDashboard) { inclusive = false } } }
                        )
                    }
                    composable<TeacherDashboard> {
                        com.example.ui.teacher.TeacherDashboardScreen(
                            onNavigateToWhiteboard = { navController.navigate(Whiteboard) },
                            onNavigateToLessonUpload = { navController.navigate(LessonUpload) },
                            onNavigateToQuizCreator = { navController.navigate(QuizCreator) },
                            onNavigateToAnnouncement = { navController.navigate(Announcement) },
                            onNavigateToProgress = { navController.navigate(TeacherProgress) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToLessons = { navController.navigate(TeacherLessons) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToQuizzes = { navController.navigate(TeacherQuizzes) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToAlerts = { navController.navigate(TeacherAlerts) },
                            onNavigateToProfile = { navController.navigate(TeacherProfile) }
                        )
                    }
                    composable<TeacherLessons> {
                        com.example.ui.teacher.TeacherLessonsScreen(
                            onNavigateToHome = { navController.navigate(TeacherDashboard) { popUpTo(TeacherDashboard) { inclusive = true } } },
                            onNavigateToQuizzes = { navController.navigate(TeacherQuizzes) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToProgress = { navController.navigate(TeacherProgress) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToAlerts = { navController.navigate(TeacherAlerts) },
                            onNavigateToProfile = { navController.navigate(TeacherProfile) }
                        )
                    }
                    composable<TeacherQuizzes> {
                        com.example.ui.teacher.TeacherQuizzesScreen(
                            onNavigateToHome = { navController.navigate(TeacherDashboard) { popUpTo(TeacherDashboard) { inclusive = true } } },
                            onNavigateToLessons = { navController.navigate(TeacherLessons) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToProgress = { navController.navigate(TeacherProgress) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToAlerts = { navController.navigate(TeacherAlerts) },
                            onNavigateToProfile = { navController.navigate(TeacherProfile) }
                        )
                    }
                    composable<TeacherProgress> {
                        com.example.ui.teacher.TeacherProgressScreen(
                            onNavigateToHome = { navController.navigate(TeacherDashboard) { popUpTo(TeacherDashboard) { inclusive = true } } },
                            onNavigateToLessons = { navController.navigate(TeacherLessons) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToQuizzes = { navController.navigate(TeacherQuizzes) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToAlerts = { navController.navigate(TeacherAlerts) },
                            onNavigateToProfile = { navController.navigate(TeacherProfile) }
                        )
                    }
                    composable<TeacherAlerts> {
                        com.example.ui.teacher.TeacherAlertsScreen(
                            onNavigateToHome = { navController.navigate(TeacherDashboard) { popUpTo(TeacherDashboard) { inclusive = true } } },
                            onNavigateToProgress = { navController.navigate(TeacherProgress) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToProfile = { navController.navigate(TeacherProfile) { popUpTo(TeacherDashboard) { inclusive = false } } }
                        )
                    }
                    composable<TeacherProfile> {
                        com.example.ui.teacher.TeacherProfileScreen(
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToHome = { navController.navigate(TeacherDashboard) { popUpTo(TeacherDashboard) { inclusive = true } } },
                            onNavigateToLessons = { navController.navigate(TeacherLessons) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToQuizzes = { navController.navigate(TeacherQuizzes) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToProgress = { navController.navigate(TeacherProgress) { popUpTo(TeacherDashboard) { inclusive = false } } },
                            onNavigateToAlerts = { navController.navigate(TeacherAlerts) { popUpTo(TeacherDashboard) { inclusive = false } } }
                        )
                    }
                    composable<Whiteboard> {
                        WhiteboardScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<LessonDetail> {
                        com.example.ui.student.LessonDetailScreen(
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToQuiz = { navController.navigate(LessonQuiz) },
                            onNavigateToWhiteboard = { navController.navigate(Whiteboard) }
                        )
                    }
                    composable<LessonQuiz> {
                       com.example.ui.student.LessonQuizScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<StudyReminder> {
                        com.example.ui.student.StudyReminderScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<Flashcard> {
                        com.example.ui.student.FlashcardScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<ProgressTracker>(
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        com.example.ui.student.ProgressTrackerScreen(
                            onNavigateHome = { navController.navigate(StudentDashboard) { popUpTo(StudentDashboard) { inclusive = true } } },
                            onNavigateLessons = { navController.navigate(LessonsTab) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateQuizzes = { navController.navigate(QuizzesTab) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToProfile = { navController.navigate(StudentProfile) { popUpTo(StudentDashboard) { inclusive = false } } }
                        )
                    }
                    composable<LessonsTab>(
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        com.example.ui.student.StudentLessonsScreen(
                            onNavigateToHome = { navController.navigate(StudentDashboard) { popUpTo(StudentDashboard) { inclusive = true } } },
                            onNavigateToProgress = { navController.navigate(ProgressTracker) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToQuizzesTab = { navController.navigate(QuizzesTab) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToLesson = { navController.navigate(LessonDetail) },
                            onNavigateToProfile = { navController.navigate(StudentProfile) { popUpTo(StudentDashboard) { inclusive = false } } }
                        )
                    }
                    composable<QuizzesTab>(
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        com.example.ui.student.StudentQuizzesScreen(
                            onNavigateToHome = { navController.navigate(StudentDashboard) { popUpTo(StudentDashboard) { inclusive = true } } },
                            onNavigateToLessonsTab = { navController.navigate(LessonsTab) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToProgress = { navController.navigate(ProgressTracker) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToQuiz = { navController.navigate(LessonQuiz) },
                            onNavigateToFillBlankQuiz = { navController.navigate(FillBlankGame) },
                            onNavigateToWeeklyChallenge = { navController.navigate(WeeklyChallenge) },
                            onNavigateToTimesTableBlitz = { navController.navigate(TimesTableBlitzGame) },
                            onNavigateToShoppingMath = { navController.navigate(ShoppingMathGame) },
                            onNavigateToMathSolver = { navController.navigate(MathSolverGame) },
                            onNavigateToProfile = { navController.navigate(StudentProfile) { popUpTo(StudentDashboard) { inclusive = false } } }
                        )
                    }
                    composable<StudentProfile>(
                        enterTransition = { fadeIn(animationSpec = tween(0)) },
                        exitTransition = { fadeOut(animationSpec = tween(0)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
                        popExitTransition = { fadeOut(animationSpec = tween(0)) }
                    ) {
                        com.example.ui.student.StudentProfileScreen(
                            onNavigateBack = { navController.popBackStack() },
                            onNavigateToHome = { navController.navigate(StudentDashboard) { popUpTo(StudentDashboard) { inclusive = true } } },
                            onNavigateToLessons = { navController.navigate(LessonsTab) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToProgress = { navController.navigate(ProgressTracker) { popUpTo(StudentDashboard) { inclusive = false } } },
                            onNavigateToQuizzes = { navController.navigate(QuizzesTab) { popUpTo(StudentDashboard) { inclusive = false } } }
                        )
                    }
                    composable<MathSolverGame> {
                        com.example.ui.quiz.mathsolver.MathSolverScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<ShoppingMathGame> {
                        com.example.ui.quiz.shoppingmath.ShoppingMathApp(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<TimesTableBlitzGame> {
                        com.example.ui.quiz.timestable.TimesTableBlitzApp(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<LessonUpload> {
                        com.example.ui.teacher.LessonUploadScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<QuizCreator> {
                         com.example.ui.teacher.QuizCreatorScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<Announcement> {
                         com.example.ui.teacher.AnnouncementScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<FillBlankGame> {
                        com.example.ui.quiz.fillblank.FillBlankScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    composable<WeeklyChallenge> {
                        com.example.ui.student.WeeklyChallengeScreen(
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
