package com.example.ui.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.ui.theme.isAppInDarkTheme as isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.student.ProfileMenuItem
import com.example.ui.student.PrimaryIndigo
import com.example.ui.student.SecondaryTeal
import com.example.ui.student.SurfaceGray
import com.example.ui.student.CardWhite
import com.example.ui.student.TextDark
import com.example.ui.student.TextMuted
import com.example.ui.student.DangerRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToLessons: () -> Unit,
    onNavigateToQuizzes: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToAlerts: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val bgColor = if (isDark) Color(0xFF121216) else SurfaceGray
    val cardColor = if (isDark) Color(0xFF1E1E22) else CardWhite
    val textPrimary = if (isDark) Color.White else TextDark
    val textSecondary = if (isDark) Color.LightGray else TextMuted

    Scaffold(
        containerColor = bgColor,
        topBar = {
            TopAppBar(
                title = { 
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("প্রোফাইল (শিক্ষক)", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.padding(end = 48.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryIndigo)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
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
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            // User Identity Card
            item {
                Surface(
                    shape = RoundedCornerShape(40.dp),
                    color = cardColor,
                    shadowElevation = 0.dp,
                    border = if (isDark) androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha=0.1f)) else null
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = PrimaryIndigo.copy(alpha = 0.1f),
                                modifier = Modifier.size(72.dp)
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryIndigo, modifier = Modifier.padding(16.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("মুস্তাফিজুর রহমান", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                                Text("সিনিয়র গণিত শিক্ষক", fontSize = 14.sp, color = textSecondary)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(percent = 50))
                                .background(if(isDark) Color(0xFF2B2D31) else Color(0xFFF0F2F5))
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("পরিচালিত ক্লাস:", fontSize = 12.sp, color = textSecondary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.Class, contentDescription = null, tint = PrimaryIndigo, modifier = Modifier.size(20.dp))
                                    Text("৪টি", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                                }
                            }
                            
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { /* See classes */ }) {
                                Text("সবগুলো দেখুন", fontSize = 12.sp, color = PrimaryIndigo, fontWeight = FontWeight.Medium)
                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PrimaryIndigo, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
            
            // Performance / Activity Overview Card
            item {
                Surface(
                    shape = RoundedCornerShape(40.dp),
                    color = cardColor,
                    shadowElevation = 0.dp,
                    border = if (isDark) androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha=0.1f)) else null
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = PrimaryIndigo,
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.UploadFile, contentDescription = null, tint = Color.White)
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(verticalArrangement = Arrangement.Center) {
                                    Text("আপলোডকৃত লেসন", fontSize = 14.sp, color = PrimaryIndigo, fontWeight = FontWeight.Bold)
                                    Text("৬৫টি লেসন", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                                }
                            }
                            Text("৬৫ / ৮০ রিভিউড", fontSize = 12.sp, color = PrimaryIndigo, fontWeight = FontWeight.SemiBold)
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(percent = 50))
                                .background(if(isDark) Color(0xFF2B2D31) else Color(0xFFE5E7EB))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.81f)
                                    .fillMaxHeight()
                                    .background(SecondaryTeal)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("৮১% সম্পন্ন", fontSize = 12.sp, color = textSecondary)
                            Text("পেন্ডিং কাজ: ১৫টি", fontSize = 12.sp, color = SecondaryTeal)
                        }
                    }
                }
            }
            
            // Settings & Navigation List
            item {
                Surface(
                    shape = RoundedCornerShape(40.dp),
                    color = cardColor,
                    shadowElevation = 0.dp,
                    border = if (isDark) androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha=0.1f)) else null
                ) {
                    Column(modifier = Modifier.padding(vertical = 12.dp)) {
                        ProfileMenuItem(
                            icon = Icons.Default.Edit,
                            text = "প্রোফাইল সম্পাদন",
                            iconTint = PrimaryIndigo,
                            textColor = textPrimary
                        )
                        HorizontalDivider(color = if (isDark) Color.White.copy(alpha=0.1f) else Color(0xFFF0F2F5))
                        ProfileMenuItem(
                            icon = Icons.Default.SettingsApplications,
                            text = "ক্লাস সেটিংস",
                            iconTint = PrimaryIndigo,
                            textColor = textPrimary
                        )
                        HorizontalDivider(color = if (isDark) Color.White.copy(alpha=0.1f) else Color(0xFFF0F2F5))
                        ProfileMenuItem(
                            icon = Icons.Default.HelpOutline,
                            text = "সাহায্য ও সাপোর্ট",
                            iconTint = PrimaryIndigo,
                            textColor = textPrimary
                        )
                        HorizontalDivider(color = if (isDark) Color.White.copy(alpha=0.1f) else Color(0xFFF0F2F5))
                        ProfileMenuItem(
                            icon = Icons.AutoMirrored.Filled.ExitToApp,
                            text = "লগ আউট",
                            iconTint = DangerRed,
                            textColor = DangerRed,
                            showChevron = false,
                            isBold = true
                        )
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
