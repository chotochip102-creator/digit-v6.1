package com.example.ui.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.isAppInDarkTheme

@Composable
fun WeeklyChallengeSection() {
    val isDark = isAppInDarkTheme()
    val headerTextColor = if (isDark) Color.LightGray else Color(0xFF54578C)
    // Actually the prompt says: "dark gray/muted text" for main header, but the image shows dark blue/indigo `#54578c` text which is brand color. Let's use 54578c for light mode.
    val orangeColor = Color(0xFFFB8500)
    val indigoCardColor = if (isDark) Color(0xFF3B3D6B) else Color(0xFF54578C)
    val cardContentBg = if (isDark) Color(0xFF1E1E22) else Color.White
    
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        // Outside Header Row
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = headerTextColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Weekly challenge",
                    color = headerTextColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Schedule,
                    contentDescription = null,
                    tint = orangeColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "2d left",
                    color = orangeColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))

        // Main Card
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = indigoCardColor,
            shadowElevation = 2.dp
        ) {
            Column {
                // Main Title Block (inside the dark card)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Science sprint — win 50 XP",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "3 of 5 done",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // White inner content area
                Surface(
                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 32.dp, bottomEnd = 32.dp),
                    color = cardContentBg,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        
                        // Tasks
                        ChallengeTaskItem(index = "1", title = "Read: Cell structure", subtitle = "15 min read", status = TaskStatus.DONE)
                        Spacer(modifier = Modifier.height(16.dp))
                        ChallengeTaskItem(index = "2", title = "Quiz: Photosynthesis", subtitle = "5 questions", status = TaskStatus.DONE)
                        Spacer(modifier = Modifier.height(16.dp))
                        ChallengeTaskItem(index = "3", title = "Read: Food chains", subtitle = "10 min read", status = TaskStatus.DONE)
                        Spacer(modifier = Modifier.height(16.dp))
                        ChallengeTaskItem(index = "4", title = "Quiz: Ecosystems", subtitle = "8 questions", status = TaskStatus.UP_NEXT)
                        Spacer(modifier = Modifier.height(16.dp))
                        ChallengeTaskItem(index = "5", title = "Read: Human body", subtitle = "20 min read", status = TaskStatus.LOCKED)
                        
                        Spacer(modifier = Modifier.height(24.dp))

                        // Button
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A5F7A)),
                            shape = CircleShape,
                            modifier = Modifier.fillMaxWidth().height(56.dp)
                        ) {
                            Text(
                                "Continue challenge",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class TaskStatus { DONE, UP_NEXT, LOCKED }

@Composable
fun ChallengeTaskItem(index: String, title: String, subtitle: String, status: TaskStatus) {
    val isDark = isAppInDarkTheme()
    val doneGreen = Color(0xFF1E8E62) // Actually prompt says: Success Green: #e6f4ea (bg) / #1a5f7a (text)
    val doneBgColor = Color(0xFFE6F4EA)
    val doneTextColor = Color(0xFF1A5F7A)
    
    val upNextColor = Color(0xFF54578C)
    val upNextBgColor = Color(0xFFE8EAF6)
    val upNextTextColor = Color(0xFF54578C)
    
    val lockedColor = Color(0xFF9E9E9E)
    val lockedBgColor = if (isDark) Color(0xFF333333) else Color(0xFFEEEEEE)
    val lockedTextColor = Color(0xFF757575)

    val titleColor = when (status) {
        TaskStatus.DONE -> if (isDark) Color.White else Color(0xFF1D1B20)
        TaskStatus.UP_NEXT -> if (isDark) Color.White else Color(0xFF1D1B20)
        TaskStatus.LOCKED -> lockedColor
    }
    val subtitleColor = if (isDark) Color.LightGray else Color(0xFF757575)
    
    val indexColor = when (status) {
        TaskStatus.DONE -> doneGreen
        TaskStatus.UP_NEXT -> upNextColor
        TaskStatus.LOCKED -> lockedColor
    }

    val badgeBg = when (status) {
        TaskStatus.DONE -> if (isDark) doneGreen.copy(alpha = 0.2f) else doneBgColor
        TaskStatus.UP_NEXT -> if (isDark) upNextColor.copy(alpha = 0.2f) else upNextBgColor
        TaskStatus.LOCKED -> lockedBgColor
    }
    
    val badgeText = when (status) {
        TaskStatus.DONE -> if (isDark) Color(0xFF66FFAA) else doneTextColor
        TaskStatus.UP_NEXT -> if (isDark) Color(0xFFA9ADFF) else upNextTextColor
        TaskStatus.LOCKED -> if (isDark) Color.LightGray else lockedTextColor
    }
    
    val badgeString = when (status) {
        TaskStatus.DONE -> "Done"
        TaskStatus.UP_NEXT -> "Up next"
        TaskStatus.LOCKED -> "Locked"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Text(
                text = index,
                color = indexColor,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                modifier = Modifier.width(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = titleColor
                )
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = subtitleColor
                )
            }
        }
        
        Surface(
            color = badgeBg,
            shape = CircleShape
        ) {
            Text(
                text = badgeString,
                color = badgeText,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}
