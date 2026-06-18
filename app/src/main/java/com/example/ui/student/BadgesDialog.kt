package com.example.ui.student
import com.example.ui.components.SmartText

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.isAppInDarkTheme

import androidx.compose.ui.window.DialogProperties

data class BadgeData(
    val title: String,
    val icon: ImageVector,
    val iconColor: Color,
    val bgColor: Color,
    val darkBgColor: Color
)

val allBadgesList = listOf(
    BadgeData("সেরা ছাত্র", Icons.Default.Stars, Color(0xFFD97706), Color(0xFFFEF3C7), Color(0xFF452E07)),
    BadgeData("৭ দিন ধারা", Icons.Default.WorkspacePremium, Color(0xFF6B7280), Color(0xFFF3F4F6), Color(0xFF1F2937)),
    BadgeData("কুইজ মাস্টার", Icons.Default.MilitaryTech, Color(0xFFEA580C), Color(0xFFFFEDD5), Color(0xFF431407)),
    BadgeData("দ্রুত উত্তরদাতা", Icons.Default.Bolt, Color(0xFF4F46E5), Color(0xFFE0E7FF), Color(0xFF1E1B4B)),
    BadgeData("শীর্ষ স্থান", Icons.Default.EmojiEvents, Color(0xFF656214), Color(0xFFFEF08A), Color(0xFF422006)),
    BadgeData("পারফেক্ট স্কোর", Icons.Default.AutoAwesome, Color(0xFF3730A3), Color(0xFFE2E8F0), Color(0xFF0F172A))
)

@Composable
fun BadgesDialog(onDismiss: () -> Unit) {
    val isDark = isAppInDarkTheme()
    val dialogBgColor = if (isDark) Color(0xFF1E1E22) else Color.White
    val textPrimary = if (isDark) Color.White else Color(0xFF1D1B20)
    val textSecondary = if (isDark) Color.LightGray else Color(0xFF49454F)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = dialogBgColor,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmartText( // FIXED
                        text = "All Badges",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isDark) Color(0xFFA9ADFF) else Color(0xFF3C3F73)
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = textSecondary)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = if (isDark) Color.White.copy(alpha=0.1f) else Color(0xFFF0F2F5))
                Spacer(modifier = Modifier.height(24.dp))
                
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(allBadgesList) { badge ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                shape = CircleShape,
                                color = if (isDark) badge.darkBgColor else badge.bgColor,
                                modifier = Modifier.size(64.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        badge.icon,
                                        contentDescription = null,
                                        tint = badge.iconColor,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            SmartText( // FIXED
                                badge.title,
                                fontSize = 12.sp,
                                color = textPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}