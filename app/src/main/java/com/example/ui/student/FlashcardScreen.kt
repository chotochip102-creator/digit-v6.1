package com.example.ui.student

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(onNavigateBack: () -> Unit) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ফ্ল্যাশকার্ড", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("মহাকাশ (Subject: Science)", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 8 * density
                    },
                shape = RoundedCornerShape(24.dp),
                color = if (rotation > 90f) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primary,
                shadowElevation = 8.dp,
                onClick = { isFlipped = !isFlipped }
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(24.dp)) {
                    if (rotation <= 90f) {
                        // Front side
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("প্রশ্ন", fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("সৌরজগৎ কাকে বলে?", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary, textAlign = TextAlign.Center, lineHeight = 36.sp)
                        }
                    } else {
                        // Back side
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer { rotationY = 180f } // Un-flip text
                        ) {
                            Text("উত্তর", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("সূর্য এবং এর চারপাশে ঘূর্নায়মান গ্রহ, উপগ্রহ, গ্রহাণু এবং ধূমকেতু নিয়ে যে জগৎ তৈরি তাকে সৌরজগৎ বলে।", fontSize = 20.sp, color = MaterialTheme.colorScheme.onSecondaryContainer, textAlign = TextAlign.Center, lineHeight = 28.sp)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            ElevatedButton(
                onClick = { isFlipped = !isFlipped },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Flip Card (উল্টে দেখুন)")
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                TextButton(onClick = { /* Mark hard */ }, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                    Text("Hard")
                }
                TextButton(onClick = { /* Mark Good */ }, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)) {
                    Text("Good")
                }
                TextButton(onClick = { /* Mark Easy */ }, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)) {
                    Text("Easy")
                }
            }
        }
    }
}
