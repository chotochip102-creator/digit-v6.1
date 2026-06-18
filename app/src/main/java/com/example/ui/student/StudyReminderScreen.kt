package com.example.ui.student
import com.example.ui.components.SmartText

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyReminderScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { SmartText("শিখন", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 24.sp) }, // FIXED
                navigationIcon = {
                    Surface(
                        modifier = Modifier.padding(start = 16.dp).size(40.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(8.dp))
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.Notifications, "Notifications", tint = MaterialTheme.colorScheme.primary) }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                NavigationBarItem(selected = false, onClick = onNavigateBack, icon = { Icon(Icons.AutoMirrored.Outlined.LibraryBooks, null) }, label = { SmartText("পাঠসমূহ\n(Lessons)") }) // FIXED
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Outlined.Style, null) }, label = { SmartText("ফ্ল্যাশকার্ড\n(Flashcards)") }) // FIXED
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 8.dp,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        modifier = Modifier.size(112.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(28.dp))
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    SmartText("পড়াশোনার সময় হয়েছে!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primaryContainer) // FIXED
                    SmartText("Time to Study!", fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) // FIXED
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            SmartText("আজকের আলোর প্রতিফলন", fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface) // FIXED
                            SmartText("পাঠটি রিভিশন দাও।", fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface) // FIXED
                            Spacer(modifier = Modifier.height(16.dp))
                            SmartText("Time to revise today's Reflection", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 16.sp) // FIXED
                            SmartText("of Light lesson.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 16.sp) // FIXED
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    ElevatedButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        SmartText("Start Now", fontWeight = FontWeight.Bold, fontSize = 18.sp) // FIXED
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    }
}