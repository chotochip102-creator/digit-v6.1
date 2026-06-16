package com.example.ui.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DigitTabHeader
import com.example.ui.student.Primary
import com.example.ui.student.PrimaryContainer
import com.example.ui.student.SecondaryContainer
import com.example.ui.student.SurfaceCard
import com.example.ui.student.SurfaceContainerHighest
import com.example.ui.student.SurfaceBg
import com.example.ui.student.OnSurface
import com.example.ui.student.OnSurfaceVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherAlertsScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        containerColor = SurfaceBg,
        topBar = {
            DigitTabHeader()
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
            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text("Alerts", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Primary, lineHeight = 40.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Stay updated with your classes.", fontSize = 14.sp, color = OnSurfaceVariant)
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    AlertCard(
                        title = "Low Attendance Detected",
                        message = "Class 10-A attendance fell below 80% this week. Consider a follow up.",
                        time = "10 mins ago",
                        icon = Icons.Default.Warning,
                        iconTint = Color(0xFFD32F2F)
                    )

                    AlertCard(
                        title = "New Message from Principal",
                        message = "Please review the upcoming exam schedule sent via email.",
                        time = "1 hr ago",
                        icon = Icons.Default.Inbox,
                        iconTint = Primary
                    )

                    AlertCard(
                        title = "Pending Homework Review",
                        message = "15 submissions for 'Algebra Revision' are awaiting review.",
                        time = "3 hrs ago",
                        icon = Icons.Default.AssignmentLate,
                        iconTint = Color(0xFFF57C00)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun AlertCard(title: String, message: String, time: String, icon: ImageVector, iconTint: Color) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = SurfaceCard,
        shadowElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceContainerHighest)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Top) {
            Surface(shape = CircleShape, color = iconTint.copy(alpha = 0.1f), modifier = Modifier.size(52.dp)) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.padding(14.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = OnSurface)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(message, fontSize = 13.sp, color = OnSurfaceVariant, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(time, fontSize = 11.sp, color = Primary, fontWeight = FontWeight.Medium)
            }
        }
    }
}
