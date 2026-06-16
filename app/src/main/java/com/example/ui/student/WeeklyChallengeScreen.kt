package com.example.ui.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val surface = Color(0xFFf8f9fa)
private val surfaceContainerLowest = Color(0xFFffffff)
private val surfaceContainerLow = Color(0xFFf3f4f5)
private val surfaceContainer = Color(0xFFeeeeef)
private val surfaceContainerHigh = Color(0xFFe8e9e9)
private val surfaceContainerHighest = Color(0xFFe2e3e4)
private val onSurface = Color(0xFF1a1c1e)
private val onSurfaceVariant = Color(0xFF43474e)
private val outline = Color(0xFF73777f)
private val outlineVariant = Color(0xFFc3c7cf)
private val primary = Color(0xFF54578c)
private val onPrimary = Color(0xFFffffff)
private val primaryContainer = Color(0xFFe0e0ff)
private val onPrimaryContainer = Color(0xFF10144d)
private val secondary = Color(0xFF1a5f7a)
private val onSecondary = Color(0xFFffffff)
private val error = Color(0xFFba1a1a)
private val onError = Color(0xFFffffff)

val HindSiliguriTypography = Typography(
    headlineLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    titleMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelSmall = androidx.compose.ui.text.TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeeklyChallengeScreen(
    onNavigateBack: () -> Unit
) {
    var selectedOption by remember { mutableStateOf("Ampere") }
    var timeLeft by remember { mutableIntStateOf(60) }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            kotlinx.coroutines.delay(1000L)
            timeLeft -= 1
        }
    }

    Scaffold(
        containerColor = surface,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Bolt,
                            contentDescription = null,
                            tint = primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Weekly Challenge",
                            style = HindSiliguriTypography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                            color = primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = onSurfaceVariant)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = surface)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "$timeLeft",
                        style = HindSiliguriTypography.headlineLarge.copy(fontSize = 48.sp, lineHeight = 48.sp),
                        color = error
                    )
                    Text(
                        text = "SECONDS REMAINING",
                        style = HindSiliguriTypography.labelSmall.copy(letterSpacing = 1.sp),
                        color = onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = "Timer",
                    tint = outline,
                    modifier = Modifier.size(24.dp).padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(CircleShape)
                    .background(surfaceContainerHighest)
            ) {
                val progress = if (timeLeft > 0) timeLeft / 60f else 0f
                if (progress > 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(secondary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Question Card
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = surfaceContainerLowest,
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "What is the SI unit of electric current?",
                        style = HindSiliguriTypography.bodyMedium.copy(fontSize = 16.sp, color = onSurface)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "বিদ্যুৎ প্রবাহের SI একক কী?",
                        style = HindSiliguriTypography.bodyMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = primary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            val options = listOf("Volt", "Ampere", "Ohm", "Watt")

            options.forEach { option ->
                val isSelected = selectedOption == option
                val borderColor = if (isSelected) primary else surfaceContainerHighest
                val bgColor = if (isSelected) surfaceContainerLowest else surfaceContainerLowest

                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = bgColor,
                    border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable { selectedOption = option },
                    shadowElevation = if (isSelected) 4.dp else 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 20.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            style = HindSiliguriTypography.titleMedium.copy(
                                color = if (isSelected) primary else onSurface,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        )

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = onPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, outlineVariant, CircleShape)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Removing unused bottom nav bar
