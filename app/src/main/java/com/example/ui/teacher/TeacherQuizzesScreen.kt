package com.example.ui.teacher

import androidx.compose.foundation.background
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.PopupProperties
import com.example.ui.student.NavyBlue
import com.example.ui.theme.*
import com.example.ui.quiz.QuizStateManager

import com.example.ui.components.DigitTabHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherQuizzesScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLessons: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val isDark = isSystemInDarkTheme()
    val bgCol = if (isDark) Color(0xFF191C1D) else Color(0xFFF8F9FA)
    
    var editingQuiz by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = bgCol,
            topBar = {
                TeacherHeader(
                    onNavigateToAlerts = onNavigateToAlerts,
                    onNavigateToProfile = onNavigateToProfile
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { /* TODO Navigate to create quiz */ },
                    containerColor = Primary,
                    contentColor = OnPrimary,
                    icon = { Icon(Icons.Default.Add, contentDescription = "Create Quiz") },
                    text = { Text("নতুন কুইজ", fontWeight = FontWeight.Bold) }
                )
            },
            bottomBar = {
                TeacherBottomNavBar(
                    currentRoute = "Quizzes",
                    onNavigateToHome = onNavigateToHome,
                    onNavigateToLessons = onNavigateToLessons,
                    onNavigateToProgress = onNavigateToProgress,
                    onNavigateToQuizzes = {}
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = paddingValues.calculateTopPadding() + 24.dp,
                    bottom = paddingValues.calculateBottomPadding() + 80.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column {
                        Text("কুইজ ম্যানেজমেন্ট", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Primary)
                        Text("Manage Quizzes", fontSize = 14.sp, color = OnSurfaceVariant)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                item {
                    EditableQuizCard(
                        titleBn = "পাঠ ভিত্তিক কুইজ",
                        titleEn = "Lesson Quizzes",
                        icon = Icons.Outlined.MenuBook,
                        iconCol = PrimaryContainer,
                        iconTint = Primary,
                        onEditClick = { editingQuiz = "Lesson Quizzes" }
                    )
                }
                
                item {
                    EditableQuizCard(
                        titleBn = "শূন্যস্থান পূরণ",
                        titleEn = "Fill in the Blanks",
                        icon = Icons.Outlined.Edit,
                        iconCol = SecondaryContainer,
                        iconTint = Primary,
                        onEditClick = { editingQuiz = "Fill in the Blanks" }
                    )
                }
                
                item {
                    EditableQuizCard(
                        titleBn = "অংক সমাধান",
                        titleEn = "Math Solver",
                        icon = Icons.Outlined.Calculate,
                        iconCol = TertiaryContainer,
                        iconTint = if (isDark) Color(0xFFF9E28B) else Color(0xFF4B3F00),
                        onEditClick = { editingQuiz = "Math Solver" }
                    )
                }
                
                item {
                    EditableQuizCard(
                        titleBn = "শব্দার্থ মনে রাখা",
                        titleEn = "Vocabulary Memory",
                        icon = Icons.Outlined.Psychology,
                        iconCol = PrimaryContainer,
                        iconTint = Primary,
                        onEditClick = { editingQuiz = "Vocabulary Memory" }
                    )
                }
                
                item {
                    EditableQuizCard(
                        titleBn = "নামতা মুখস্ত করা",
                        titleEn = "Times Table Blitz",
                        icon = Icons.Outlined.Timer,
                        iconCol = SecondaryContainer,
                        iconTint = Primary,
                        onEditClick = null
                    )
                }
            }
        } // end Scaffold

        if (editingQuiz != null) {
            QuizEditorPanel(
                quizTitleEn = editingQuiz!!,
                onDismiss = { editingQuiz = null }
            )
        }
    } // end Box
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableQuizCard(
    titleBn: String,
    titleEn: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconCol: Color,
    iconTint: Color,
    onEditClick: (() -> Unit)? = null
) {
    val isDark = isSystemInDarkTheme()
    val quizState = QuizStateManager.getState(titleEn)
    val isLocked = quizState.isLocked
    val isHidden = quizState.isHidden
    var showMenu by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    val cardBg = if (isDark) Color(0xFF232528) else Color(0xFFFFFFFF)
    val cardBorder = if (isDark) Color(0xFF33353A) else Color(0xFFE1E3E4)
    val alphaModifier = if (isHidden) 0.5f else 1f
    
    Surface(
        modifier = Modifier.fillMaxWidth().alpha(alphaModifier),
        shape = RoundedCornerShape(32.dp),
        color = cardBg,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
    ) {
        Box {
            // Background decor
            Box(modifier = Modifier
                .align(Alignment.TopEnd)
                .size(100.dp)
                .clip(RoundedCornerShape(bottomStart = 80.dp))
                .background(iconCol.copy(alpha = 0.3f))
            )

            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(iconCol),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(titleBn, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color.White else Color(0xFF191C1D))
                        Text(titleEn, fontSize = 14.sp, color = if (isDark) Color.LightGray else Color(0xFF46464F))
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (onEditClick != null) {
                        Surface(
                            shape = CircleShape,
                            color = SurfaceContainer,
                            modifier = Modifier.weight(1f).height(48.dp),
                            onClick = onEditClick
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Icon(Icons.Outlined.Edit, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("এডিট (Edit)", color = OnSurfaceVariant, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    
                    // Quick Action Toggles
                    Surface(
                        shape = CircleShape,
                        color = if (isLocked) Error.copy(alpha=0.1f) else SurfaceContainer,
                        modifier = Modifier.height(48.dp).width(48.dp),
                        onClick = { QuizStateManager.toggleLock(titleEn) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                if (isLocked) Icons.Outlined.Lock else Icons.Outlined.LockOpen, 
                                contentDescription = "Toggle Lock", 
                                tint = if (isLocked) Error else OnSurfaceVariant, 
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = if (isHidden) SecondaryContainer else SurfaceContainer,
                        modifier = Modifier.height(48.dp).width(48.dp),
                        onClick = { QuizStateManager.toggleHide(titleEn) }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                if (isHidden) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility, 
                                contentDescription = "Toggle Visibility", 
                                tint = if (isHidden) OnSecondaryContainer else OnSurfaceVariant, 
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
