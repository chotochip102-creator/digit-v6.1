package com.example.ui.student

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.AppSettings
import com.example.ui.theme.ThemeMode
import com.example.ui.theme.AppLanguage

val PrimaryIndigo = Color(0xFF54578C)
val SecondaryTeal = Color(0xFF1A5F7A)
val SurfaceGray = Color(0xFFF7F9FB)
val CardWhite = Color(0xFFFFFFFF)
val TextDark = Color(0xFF1D1B20)
val TextMuted = Color(0xFF49454F)
val DangerRed = Color(0xFFB3261E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToLessons: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToQuizzes: () -> Unit
) {
    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showBadgesDialog by remember { mutableStateOf(false) }

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
                        Text("প্রোফাইল", fontWeight = FontWeight.Bold, color = PrimaryIndigo, modifier = Modifier.padding(end = 48.dp))
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
                                Text("তানভীর আহমেদ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                                Text("শ্রেণী: ৬", fontSize = 14.sp, color = textSecondary)
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
                                Text("অর্জিত ব্যাজ:", fontSize = 12.sp, color = textSecondary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Icon(Icons.Default.Stars, contentDescription = null, tint = Color(0xFFD4AF37), modifier = Modifier.size(20.dp))
                                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color(0xFFC0C0C0), modifier = Modifier.size(20.dp))
                                }
                            }
                            
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { showBadgesDialog = true }) {
                                Text("সবগুলো দেখুন", fontSize = 12.sp, color = PrimaryIndigo, fontWeight = FontWeight.Medium)
                                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = PrimaryIndigo, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
            
            // Level & XP Progress Card
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
                                        Text("৭", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(verticalArrangement = Arrangement.Center) {
                                    Text("বর্তমান লেভেল", fontSize = 14.sp, color = PrimaryIndigo, fontWeight = FontWeight.Bold)
                                    Text("লেভেল ৭", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = textPrimary)
                                }
                            }
                            Text("১,১৯০ / ১,৫০০ XP", fontSize = 12.sp, color = PrimaryIndigo, fontWeight = FontWeight.SemiBold)
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
                                    .fillMaxWidth(0.79f)
                                    .fillMaxHeight()
                                    .background(SecondaryTeal)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("৭৯% সম্পন্ন", fontSize = 12.sp, color = textSecondary)
                            Text("পরবর্তী লেভেল: ৩১০ XP প্রয়োজন", fontSize = 12.sp, color = SecondaryTeal)
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
                            icon = Icons.Default.Notifications,
                            text = "নোটিফিকেশন সেটিংসে",
                            iconTint = PrimaryIndigo,
                            textColor = textPrimary
                        )
                        HorizontalDivider(color = if (isDark) Color.White.copy(alpha=0.1f) else Color(0xFFF0F2F5))
                        ProfileMenuItem(
                            icon = Icons.Default.Palette,
                            text = if (AppSettings.appLanguage == AppLanguage.BENGALI) "অ্যাপের থিম" else "App Theme",
                            iconTint = PrimaryIndigo,
                            textColor = textPrimary,
                            onClick = { showThemeDialog = true }
                        )
                        HorizontalDivider(color = if (isDark) Color.White.copy(alpha=0.1f) else Color(0xFFF0F2F5))
                        ProfileMenuItem(
                            icon = Icons.Default.Language,
                            text = if (AppSettings.appLanguage == AppLanguage.BENGALI) "ভাষা (Language)" else "Language (ভাষা)",
                            iconTint = PrimaryIndigo,
                            textColor = textPrimary,
                            onClick = { showLanguageDialog = true }
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

    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text(if (AppSettings.appLanguage == AppLanguage.BENGALI) "থিম নির্বাচন করুন" else "Select Theme") },
            text = {
                Column {
                    Row(modifier = Modifier.fillMaxWidth().clickable { AppSettings.themeMode = ThemeMode.LIGHT; showThemeDialog = false }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = AppSettings.themeMode == ThemeMode.LIGHT, onClick = { AppSettings.themeMode = ThemeMode.LIGHT; showThemeDialog = false })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (AppSettings.appLanguage == AppLanguage.BENGALI) "লাইট মোড" else "Light Mode", color = textPrimary)
                    }
                    Row(modifier = Modifier.fillMaxWidth().clickable { AppSettings.themeMode = ThemeMode.DARK; showThemeDialog = false }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = AppSettings.themeMode == ThemeMode.DARK, onClick = { AppSettings.themeMode = ThemeMode.DARK; showThemeDialog = false })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (AppSettings.appLanguage == AppLanguage.BENGALI) "ডার্ক মোড" else "Dark Mode", color = textPrimary)
                    }
                    Row(modifier = Modifier.fillMaxWidth().clickable { AppSettings.themeMode = ThemeMode.SYSTEM; showThemeDialog = false }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = AppSettings.themeMode == ThemeMode.SYSTEM, onClick = { AppSettings.themeMode = ThemeMode.SYSTEM; showThemeDialog = false })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (AppSettings.appLanguage == AppLanguage.BENGALI) "সিস্টেম ডিফল্ট" else "System Default", color = textPrimary)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text(if (AppSettings.appLanguage == AppLanguage.BENGALI) "বাতিল" else "Cancel")
                }
            }
        )
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text(if (AppSettings.appLanguage == AppLanguage.BENGALI) "ভাষা নির্বাচন করুন" else "Select Language") },
            text = {
                Column {
                    Row(modifier = Modifier.fillMaxWidth().clickable { AppSettings.appLanguage = AppLanguage.ENGLISH; showLanguageDialog = false }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = AppSettings.appLanguage == AppLanguage.ENGLISH, onClick = { AppSettings.appLanguage = AppLanguage.ENGLISH; showLanguageDialog = false })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("English", color = textPrimary)
                    }
                    Row(modifier = Modifier.fillMaxWidth().clickable { AppSettings.appLanguage = AppLanguage.BENGALI; showLanguageDialog = false }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = AppSettings.appLanguage == AppLanguage.BENGALI, onClick = { AppSettings.appLanguage = AppLanguage.BENGALI; showLanguageDialog = false })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("বাংলা", color = textPrimary)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text(if (AppSettings.appLanguage == AppLanguage.BENGALI) "বাতিল" else "Cancel")
                }
            }
        )
    }

    if (showBadgesDialog) {
        BadgesDialog(onDismiss = { showBadgesDialog = false })
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    text: String,
    iconTint: Color,
    textColor: Color,
    showChevron: Boolean = true,
    isBold: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                color = textColor,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
            )
        }
        if (showChevron) {
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted, modifier = Modifier.size(24.dp))
        }
    }
}
