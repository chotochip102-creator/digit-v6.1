package com.example.ui.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

private val LessonPrimary: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFBFC2FE) else Color(0xFF54578C)
private val LessonSecondary: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF4DB6AC) else Color(0xFF1A5F7A)
private val LessonSurface: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF191C1E) else Color(0xFFF8F9FA)
private val LessonCard: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF282A2D) else Color(0xFFFFFFFF)
private val LessonNeutral: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFC4C7C5) else Color(0xFF6C757D)
private val LessonHighlightBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3F4245) else Color(0xFFF0F1F2)
private val RedIcon: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFEF5350) else Color(0xFFD32F2F)
private val InfoChipBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3F4245) else Color(0xFFE9ECEF)
private val InfoChipText: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFE1E2E4) else Color(0xFF495057)
private val LessonTextPrimary: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFFE1E2E4) else Color(0xFF212529)
private val IconBg1: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF4A3438) else Color(0xFFFFEBEE)
private val IconBg2: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3F4245) else Color(0xFFF1F3F5)
private val QuizCardBg: Color @Composable get() = if (isSystemInDarkTheme()) Color(0xFF3C3F73) else Color(0xFF54578C)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonDetailScreen(onNavigateBack: () -> Unit, onNavigateToQuiz: () -> Unit, onNavigateToWhiteboard: () -> Unit) {
    Scaffold(
        containerColor = LessonSurface,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("আলোর প্রতিফলন", color = LessonPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = LessonPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = LessonPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = LessonSurface)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            item {
                Text(
                    text = "আলোর প্রতিফলন |",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = LessonPrimary,
                    lineHeight = 36.sp
                )
                Text(
                    text = "Reflection of Light",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = LessonPrimary,
                    lineHeight = 32.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = InfoChipBg,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("বিজ্ঞান", color = InfoChipText, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Surface(
                        color = InfoChipBg,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("অধ্যায় ৪", color = InfoChipText, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Teacher Note
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    color = LessonCard,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.AutoMirrored.Filled.Notes, contentDescription = null, tint = LessonPrimary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("শিক্ষকের নোট | Teacher's Notes", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = LessonPrimary)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "আজকের পাঠে আমরা দেখব কীভাবে আলো বিভিন্ন পৃষ্ঠ থেকে প্রতিফলিত হয়। মনে রেখো, আপতন কোণ সবসময় প্রতিফলন কোণের সমান হয় (∠i = ∠r)।",
                            color = LessonTextPrimary,
                            lineHeight = 24.sp,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(verticalAlignment = Alignment.Top) {
                            Text("■", color = LessonSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp, end = 8.dp))
                            Text("নিয়মিত প্রতিফলন (Regular Reflection) - মসৃণ পৃষ্ঠে ঘটে।", color = InfoChipText, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Top) {
                            Text("■", color = LessonSecondary, fontSize = 12.sp, modifier = Modifier.padding(top = 2.dp, end = 8.dp))
                            Text("ব্যাপ্ত প্রতিফলন (Diffuse Reflection) - অমসৃণ পৃষ্ঠে ঘটে।", color = InfoChipText, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Surface(
                            color = LessonHighlightBg,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                                Box(modifier = Modifier.width(4.dp).fillMaxHeight().background(LessonPrimary))
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("গুরুত্বপূর্ণ সূত্র:", fontWeight = FontWeight.Bold, color = LessonPrimary, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("আলোর প্রতিফলনের প্রথম সূত্র: আপতিত রশ্মি, প্রতিফলিত রশ্মি এবং আপতন বিন্দুতে অঙ্কিত অভিলম্ব একই সমতলে থাকে।", color = LessonPrimary, fontSize = 14.sp, lineHeight = 20.sp)
                                }
                            }
                        }
                    }
                }
            }
            
            // PDF Notes Card
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    color = LessonCard,
                    shadowElevation = 1.dp,
                    onClick = {}
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = IconBg1,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = RedIcon, modifier = Modifier.padding(12.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("পিডিএফ নোটস | PDF Notes", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = LessonTextPrimary, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("গুগল ড্রাইভ লিংক | Google Drive Link", fontSize = 10.sp, color = LessonNeutral, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("READ NOW", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = LessonPrimary, letterSpacing = 0.5.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = LessonPrimary, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            // Whiteboard Card
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    color = LessonCard,
                    shadowElevation = 1.dp,
                    onClick = onNavigateToWhiteboard
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = IconBg2,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, tint = LessonPrimary, modifier = Modifier.padding(12.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("হোয়াইটবোর্ড | Whiteboard", fontWeight = FontWeight.Medium, fontSize = 14.sp, color = LessonTextPrimary, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("রাফ করা অথবা নোট নেওয়ার জন্য | For drafting", fontSize = 10.sp, color = LessonNeutral, lineHeight = 14.sp, maxLines = 2, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("OPEN BOARD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = LessonPrimary, letterSpacing = 0.5.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(Icons.Outlined.OpenInNew, contentDescription = null, tint = LessonPrimary, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            // Lesson Quiz
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    color = QuizCardBg,
                    shadowElevation = 2.dp
                ) {
                    Box {
                        // Background decor
                        Box(modifier = Modifier.align(Alignment.Center).size(150.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.05f)))
                        
                        Column(modifier = Modifier.padding(32.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.SportsEsports, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("পাঠের কুইজ | Lesson Quiz", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("এই পাঠের উপর তোমার জ্ঞান যাচাই করো। | Test your knowledge on this lesson.", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp, lineHeight = 20.sp)
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = onNavigateToQuiz,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = QuizCardBg
                                ),
                                contentPadding = PaddingValues(vertical = 16.dp),
                            ) {
                                Icon(Icons.Outlined.PlayCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("কুইজ শুরু করো | Start Quiz", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Supplemental Video
            item {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 16.dp)) {
                        Icon(Icons.Outlined.PlayCircle, contentDescription = null, tint = RedIcon, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("সহায়ক ভিডিও | Supplemental Video", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = LessonPrimary)
                    }
                    
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(32.dp),
                        color = Color(0xFF495057),
                        shadowElevation = 2.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            // Placeholder for video thumbnail
                            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF6C757D)))
                            
                            Box(
                                modifier = Modifier.fillMaxSize().padding(24.dp),
                            ) {
                                // Red play icon overlay
                                Surface(
                                    shape = CircleShape,
                                    color = RedIcon,
                                    modifier = Modifier.size(64.dp).align(Alignment.Center)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.padding(16.dp))
                                }
                                
                                // Text overlay
                                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                                    Text("Introduction to Optics", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Duration: 08:45", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
