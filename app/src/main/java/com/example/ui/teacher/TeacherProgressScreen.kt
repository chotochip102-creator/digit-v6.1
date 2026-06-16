package com.example.ui.teacher

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.student.StudentBottomNavBar
import com.example.ui.theme.*
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme

data class StudentRecord(
    val id: Int,
    val infoNameEng: String,
    val infoNameBen: String,
    val grade: String,
    val imageInitial: String
)

val TeacherBgColor = Color(0xFFF8F9FA)
val DarkIndigo = Color(0xFF54578C)
val TealAccent = Color(0xFF1A5F7A)
val DeepIndigoBar = Color(0xFF54578C)
val OrangeAccent = Color(0xFFFB8500)

val studentsList = listOf(
    StudentRecord(1, "Aditya Roy", "আদিত্য রায়", "Grade 4", "A"),
    StudentRecord(2, "Rohan Das", "রোহন দাস", "Grade 5", "R"),
    StudentRecord(3, "Sara Rahman", "সারা রহমান", "Grade 4", "S"),
    StudentRecord(4, "Kabir Hasan", "কবির হাসান", "Grade 6", "K")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherProgressScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLessons: () -> Unit,
    onNavigateToQuizzes: () -> Unit,
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var selectedStudent by remember { mutableStateOf<StudentRecord?>(null) }
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) Color(0xFF1E1E1E) else TeacherBgColor
    val textColor = if (isDark) Color.White else DarkIndigo
    val subTextColor = if (isDark) Color.LightGray else DarkIndigo.copy(alpha = 0.8f)

    Scaffold(
        containerColor = bgColor,
        topBar = {
            if (selectedStudent == null) {
                TeacherHeader(
                    onNavigateToAlerts = onNavigateToAlerts,
                    onNavigateToProfile = onNavigateToProfile
                )
            } else {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { selectedStudent = null }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    title = {
                        Text("Student Progress", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = textColor)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = bgColor,
                        navigationIconContentColor = if (isDark) Color.White else Color.Black,
                        actionIconContentColor = if (isDark) Color.White else Color.Black
                    )
                )
            }
        },
        bottomBar = {
            if (selectedStudent == null) {
                TeacherBottomNavBar(
                    currentRoute = "Progress",
                    onNavigateToHome = onNavigateToHome,
                    onNavigateToLessons = onNavigateToLessons,
                    onNavigateToProgress = {},
                    onNavigateToQuizzes = onNavigateToQuizzes
                )
            }
        }
    ) { paddingValues ->
        if (selectedStudent == null) {
            StudentSelectionLayout(
                modifier = Modifier.padding(paddingValues),
                students = studentsList,
                onStudentSelect = { selectedStudent = it }
            )
        } else {
            StudentReportLayout(
                modifier = Modifier.padding(paddingValues),
                student = selectedStudent!!
            )
        }
    }
}

@Composable
fun StudentSelectionLayout(modifier: Modifier = Modifier, students: List<StudentRecord>, onStudentSelect: (StudentRecord) -> Unit) {
    val isDark = isSystemInDarkTheme()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Select Student",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = if (isDark) Color.White else DarkIndigo
        )
        Text(
            text = "শিক্ষার্থী নির্বাচন করুন",
            fontSize = 22.sp,
            color = if (isDark) Color.LightGray else DarkIndigo.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(students.size) { index ->
                val student = students[index]
                StudentItemCard(
                    student = student,
                    onClick = { onStudentSelect(student) }
                )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun StudentItemCard(student: StudentRecord, onClick: () -> Unit) {
    val isDark = isSystemInDarkTheme()
    val cardBg = if (isDark) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDark) Color.White else Color.Black
    val subTextColor = if (isDark) Color.LightGray else Color.Gray

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(32.dp),
        color = cardBg,
        shadowElevation = if (isDark) 0.dp else 2.dp,
        border = BorderStroke(1.dp, if (isDark) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha=0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile icon mimic
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFDAB9)), // Peachish background
                contentAlignment = Alignment.Center
            ) {
                 Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.infoNameBen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = textColor
                )
                Text(
                    text = student.grade,
                    fontSize = 16.sp,
                    color = subTextColor
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Forward",
                    tint = if (isDark) Color.White else DarkIndigo
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentReportLayout(modifier: Modifier = Modifier, student: StudentRecord) {
    var feedbackText by remember { mutableStateOf("") }
    val isDark = isSystemInDarkTheme()
    val cardBg = if (isDark) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDark) Color.White else Color.Black
    val inputBg = if (isDark) Color(0xFF1E1E1E) else TeacherBgColor
    val outlineBtnBorder = if (isDark) Color.White.copy(alpha=0.5f) else Color.Gray.copy(alpha=0.5f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Profile Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = DarkIndigo
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "রাহুল দাশ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${student.grade} • Science Track",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = OrangeAccent, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "LEVEL 12 EXPLORER   2,450 XP",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        item {
            // Metrics Grid
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.BarChart,
                    metric = "88",
                    suffix = "%",
                    label = "AVG SCORE",
                    tint = DarkIndigo
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.CheckCircleOutline,
                    metric = "42",
                    suffix = "",
                    label = "QUIZZES DONE",
                    tint = TealAccent
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.MilitaryTech,
                    metric = "12k",
                    suffix = "",
                    label = "TOTAL POINTS",
                    tint = OrangeAccent
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.AccessTime,
                    metric = "15",
                    suffix = "h",
                    label = "STUDY TIME",
                    tint = Color(0xFFD32F2F)
                )
            }
        }

        item {
            // Subject Mastery
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = cardBg,
                shadowElevation = if (isDark) 0.dp else 1.dp
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PieChartOutline, contentDescription = null, tint = DarkIndigo)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Subject Mastery", fontSize = 18.sp, color = textColor)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    SubjectProgressRow("Mathematics", 0.92f, "92%", TealAccent)
                    Spacer(modifier = Modifier.height(16.dp))
                    SubjectProgressRow("Reading", 0.85f, "85%", TealAccent)
                    Spacer(modifier = Modifier.height(16.dp))
                    SubjectProgressRow("Science", 0.78f, "78%", DeepIndigoBar)
                    Spacer(modifier = Modifier.height(16.dp))
                    SubjectProgressRow("History", 0.65f, "65%", OrangeAccent)
                }
            }
        }

        item {
            // Teacher's Feedback
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                color = cardBg,
                shadowElevation = if (isDark) 0.dp else 1.dp
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.EditNote, contentDescription = null, tint = DarkIndigo)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Teacher's Feedback", fontSize = 18.sp, color = textColor)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = feedbackText,
                        onValueChange = { feedbackText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = { Text("Write encouraging feedback for the student here...", color = Color.Gray) },
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = inputBg,
                            focusedContainerColor = inputBg,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = TealAccent,
                            unfocusedTextColor = textColor,
                            focusedTextColor = textColor
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = TealAccent),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("SAVE FEEDBACK")
                    }
                }
            }
        }

        item {
            // Actions
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor),
                border = BorderStroke(1.dp, outlineBtnBorder)
            ) {
                Icon(Icons.Outlined.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("DOWNLOAD PDF")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor),
                border = BorderStroke(1.dp, outlineBtnBorder)
            ) {
                Icon(Icons.Outlined.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("SEND TO PARENT")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun MetricCard(modifier: Modifier = Modifier, icon: ImageVector, metric: String, suffix: String, label: String, tint: Color) {
    val isDark = isSystemInDarkTheme()
    val cardBg = if (isDark) Color(0xFF2C2C2C) else Color.White
    val labelColor = if (isDark) Color.LightGray else Color.Black.copy(alpha=0.7f)

    Surface(
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(32.dp),
        color = cardBg,
        shadowElevation = if (isDark) 0.dp else 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = tint)
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = labelColor)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(metric, fontSize = 42.sp, fontWeight = FontWeight.Bold, color = tint, lineHeight = 42.sp)
                if (suffix.isNotEmpty()) {
                    Text(suffix, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = tint, modifier = Modifier.padding(bottom=4.dp))
                }
            }
        }
    }
}

@Composable
fun SubjectProgressRow(subject: String, progress: Float, label: String, barColor: Color) {
    val isDark = isSystemInDarkTheme()
    val textColor = if (isDark) Color.White else Color.Black
    val bgTrack = if (isDark) Color(0xFF1E1E1E) else TeacherBgColor

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(subject, fontSize = 14.sp, color = textColor, modifier = Modifier.weight(1.5f), fontWeight = FontWeight.Medium)
        
        Box(
            modifier = Modifier
                .weight(2f)
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(bgTrack)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .clip(RoundedCornerShape(50))
                    .background(barColor)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Surface(
            color = barColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = barColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
}
