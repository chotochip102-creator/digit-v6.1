package com.example.ui.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DigitTabHeader
import com.example.ui.student.Primary
import com.example.ui.student.PrimaryContainer
import com.example.ui.student.SurfaceContainerLow
import com.example.ui.student.AchievementOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherHeader(
    onNavigateToAlerts: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    DigitTabHeader(
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.People, contentDescription = null, tint = AchievementOrange, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("25", fontWeight = FontWeight.Bold, color = AchievementOrange, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = onNavigateToAlerts, modifier = Modifier.size(40.dp)) {
                    Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Primary, modifier = Modifier.size(24.dp))
                }
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp)
                        .size(8.dp)
                        .background(Color.Red, CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = PrimaryContainer,
                onClick = onNavigateToProfile
            ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(8.dp), tint = Color.White)
            }
        }
    )
}
