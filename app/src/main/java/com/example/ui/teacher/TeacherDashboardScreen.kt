package com.example.ui.teacher

import androidx.compose.foundation.background
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DigitTabHeader
import com.example.ui.student.Primary
import com.example.ui.student.PrimaryContainer
import com.example.ui.student.PrimaryFixed
import com.example.ui.student.SecondaryContainer
import com.example.ui.student.TertiaryContainer
import com.example.ui.student.AchievementOrange
import com.example.ui.student.SurfaceCard
import com.example.ui.student.SurfaceContainerHighest
import com.example.ui.student.SurfaceContainerLow
import com.example.ui.student.SurfaceBg
import com.example.ui.student.OnSurface
import com.example.ui.student.OnSurfaceVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScreen(
    onNavigateToWhiteboard: () -> Unit,
    onNavigateToLessonUpload: () -> Unit,
    onNavigateToQuizCreator: () -> Unit,
    onNavigateToAnnouncement: () -> Unit,
    onNavigateToProgress: () -> Unit = {},
    onNavigateToLessons: () -> Unit = {},
    onNavigateToQuizzes: () -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var editingMaterial by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
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
                currentRoute = "Home",
                onNavigateToHome = { },
                onNavigateToLessons = onNavigateToLessons,
                onNavigateToProgress = onNavigateToProgress,
                onNavigateToQuizzes = onNavigateToQuizzes
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
                com.example.ui.student.DailyActivitySlideshow(onNavigateToLesson = onNavigateToWhiteboard)
            }

            // Manage Learning Materials
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("লেসন ম্যাটেরিয়াল", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
                        Text("Learning Materials", fontSize = 12.sp, color = OnSurfaceVariant)
                    }
                    TextButton(onClick = { }) { 
                        Text("সব দেখ", fontWeight = FontWeight.SemiBold, color = Primary) 
                    }
                }
            }
            item {
                androidx.compose.foundation.lazy.LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item { 
                        ManageMaterialCard(
                            title = "জানা অজানা", 
                            icon = Icons.Default.Lightbulb, 
                            iconCol = PrimaryContainer, 
                            iconTint = PrimaryFixed,
                            onEditClick = { editingMaterial = "Did You Know" }
                        ) 
                    }
                    item { 
                        ManageMaterialCard(
                            title = "কুইক কুইজ", 
                            icon = Icons.Outlined.Quiz, 
                            iconCol = TertiaryContainer, 
                            iconTint = if (isSystemInDarkTheme()) Color(0xFFF9E28B) else Color(0xFF4B3F00),
                            onEditClick = { editingMaterial = "Quick Quiz" }
                        ) 
                    }
                    item { 
                        ManageMaterialCard(
                            title = "শব্দার্থ", 
                            icon = Icons.Default.SortByAlpha, 
                            iconCol = SecondaryContainer, 
                            iconTint = Primary,
                            onEditClick = { editingMaterial = "Word Meaning" }
                        ) 
                    }
                    item { 
                        ManageMaterialCard(
                            title = "ফ্ল্যাশকার্ড", 
                            icon = Icons.Default.Style, 
                            iconCol = PrimaryContainer, 
                            iconTint = PrimaryFixed,
                            onEditClick = { editingMaterial = "Flashcards" }
                        ) 
                    }
                }
            }



            // Recent Student Activity
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("ছাত্র কার্যক্রম", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
                        Text("Student Activity", fontSize = 12.sp, color = OnSurfaceVariant)
                    }
                    TextButton(onClick = { }) { 
                        Text("সব দেখ", fontWeight = FontWeight.SemiBold, color = Primary) 
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    // Card 1
                    Surface(
                        shape = RoundedCornerShape(32.dp),
                        color = SurfaceCard,
                        shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = CircleShape, color = PrimaryContainer, modifier = Modifier.size(48.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("SR", color = PrimaryFixed, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) { append("Saimur Rahman\n") }
                                        withStyle(SpanStyle(color = OnSurfaceVariant)) { append("completed ") }
                                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = Primary)) { append("Math Quiz 3") }
                                    },
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("10 mins ago", fontSize = 11.sp, color = OnSurfaceVariant)
                            }
                            Text("95%", color = AchievementOrange, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                    
                    // Card 2
                    Surface(
                        shape = RoundedCornerShape(32.dp),
                        color = SurfaceCard,
                        shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = CircleShape, color = TertiaryContainer, modifier = Modifier.size(48.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("FA", color = if (isSystemInDarkTheme()) Color(0xFFF9E28B) else Color(0xFF4B3F00), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)) { append("Fariha Ahmed\n") }
                                        withStyle(SpanStyle(color = OnSurfaceVariant)) { append("submitted ") }
                                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = Primary)) { append("Science Essay") }
                                    },
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("1 hour ago", fontSize = 11.sp, color = OnSurfaceVariant)
                            }
                        }
                    }
                }
            }

            // Pending Approvals
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text("অপেক্ষমান কাজ", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
                        Text("Pending Actions", fontSize = 12.sp, color = OnSurfaceVariant)
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(32.dp),
                        color = SurfaceCard,
                        shadowElevation = 2.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Column {
                                    Text("Class 7 - Biology Project", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = OnSurface)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Needs review and grading", fontSize = 14.sp, color = OnSurfaceVariant)
                                }
                                Surface(
                                    shape = CircleShape,
                                    color = AchievementOrange.copy(alpha = 0.1f)
                                ) {
                                    Text("High Priority", color = AchievementOrange, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Row {
                                    Surface(shape = CircleShape, color = TertiaryContainer, border = androidx.compose.foundation.BorderStroke(2.dp, SurfaceCard), modifier = Modifier.size(36.dp)) {
                                        Box(contentAlignment = Alignment.Center) { Text("+3", color = if (isSystemInDarkTheme()) Color(0xFFF9E28B) else Color(0xFF4B3F00), fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                                    }
                                    Surface(shape = CircleShape, color = SecondaryContainer, border = androidx.compose.foundation.BorderStroke(2.dp, SurfaceCard), modifier = Modifier.size(36.dp).offset(x = (-8).dp)) {
                                        Box(contentAlignment = Alignment.Center) { Text("R", color = Primary, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                                    }
                                    Surface(shape = CircleShape, color = PrimaryContainer, border = androidx.compose.foundation.BorderStroke(2.dp, SurfaceCard), modifier = Modifier.size(36.dp).offset(x = (-16).dp)) {
                                        Box(contentAlignment = Alignment.Center) { Text("J", color = PrimaryFixed, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                                    }
                                }
                                Button(
                                    onClick = onNavigateToAnnouncement, // Assuming mapped to review action
                                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                    shape = CircleShape
                                ) {
                                    Text("রিভিউ করুন", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
    
    if (editingMaterial != null) {
        MaterialEditorPanel(
            materialTitleEn = editingMaterial!!,
            onDismiss = { editingMaterial = null }
        )
    }
}
}

@Composable
fun ManageMaterialCard(title: String, icon: ImageVector, iconCol: Color, iconTint: Color, onEditClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier.width(220.dp).height(200.dp),
        shape = RoundedCornerShape(32.dp),
        color = SurfaceCard,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Box {
            // Background decor
            Box(modifier = Modifier.align(Alignment.TopEnd).size(100.dp).clip(RoundedCornerShape(bottomStart = 80.dp)).background(iconCol.copy(alpha = 0.3f)))

            Column(modifier = Modifier.padding(24.dp)) {
                Surface(shape = CircleShape, color = iconCol, shadowElevation = 1.dp, modifier = Modifier.size(56.dp)) {
                    Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.padding(14.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(title, color = Primary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = CircleShape,
                        color = SurfaceContainerHighest,
                        modifier = Modifier.weight(1f).height(40.dp),
                        onClick = onEditClick
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Icon(Icons.Outlined.Edit, contentDescription = null, tint = OnSurfaceVariant, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("এডিট", color = OnSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFBA1A1A).copy(alpha = 0.1f),
                        modifier = Modifier.weight(1f).height(40.dp),
                        onClick = {}
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color(0xFFBA1A1A), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("মুছে ফেলুন", color = Color(0xFFBA1A1A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardStatCard(title: String, value: String, subtitle: String, icon: ImageVector, iconCol: Color, iconTint: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = SurfaceCard,
        shadowElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Box {
            // Background decor
            Box(modifier = Modifier.align(Alignment.TopEnd).size(128.dp).clip(RoundedCornerShape(bottomStart = 100.dp)).background(iconCol.copy(alpha = 0.2f)))

            Column(modifier = Modifier.padding(32.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Surface(shape = CircleShape, color = iconCol, shadowElevation = 0.dp, modifier = Modifier.size(56.dp)) {
                        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.padding(14.dp))
                    }
                    Surface(
                        color = SurfaceContainerLow,
                        shape = CircleShape
                    ) {
                        Text(
                            text = "View",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Primary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OnSurfaceVariant)
                Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Primary)
                Text(subtitle, fontSize = 13.sp, color = OnSurfaceVariant)
            }
        }
    }
}

