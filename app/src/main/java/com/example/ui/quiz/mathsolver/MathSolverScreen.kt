package com.example.ui.quiz.mathsolver

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryIndigo = Color(0xFF54578c)
val SecondaryTeal = Color(0xFF1a5f7a)
val SurfaceOffWhite = Color(0xFFf8f9fa)
val ContainerWhite = Color(0xFFffffff)
val AlertRedBg = Color(0xFFfff1f0)
val AlertRedText = Color(0xFFc62828)
val StepHighlightBg = Color(0xFFfff8f9)
val NeutralMuted = Color(0xFF6c757d)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathSolverScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = SurfaceOffWhite,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "গণিত\nসমাধান",
                        color = PrimaryIndigo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        lineHeight = 28.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryIndigo)
                    }
                },
                actions = {
                    TextButton(onClick = { }) {
                        Text("Help", color = PrimaryIndigo, fontSize = 16.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceOffWhite)
            )
        },
        bottomBar = {
            Surface(
                color = SurfaceOffWhite,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Button(
                    onClick = { /* Next Problem */ },
                    shape = RoundedCornerShape(percent = 50),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryTeal),
                    modifier = Modifier.fillMaxWidth().height(64.dp)
                ) {
                    Text("পরবর্তী সমস্যা", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Problem Statement
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = ContainerWhite,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            "সমস্যা",
                            fontSize = 14.sp,
                            color = NeutralMuted
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "x এর মান বের করো: 3x + 7 = 22",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }

            item {
                // Status & Alerts
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .border(1.dp, Color.LightGray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("0", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("সময়\nবাকি", fontSize = 14.sp, color = NeutralMuted, lineHeight = 18.sp)
                }
            }

            item {
                // Expired Banner
                Surface(
                    shape = RoundedCornerShape(percent = 50),
                    color = AlertRedBg,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "সময় শেষ! উত্তর দেখুন এবং আবার চেষ্টা করুন।",
                        color = AlertRedText,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
            }

            item {
                // Progress Indicator
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(PrimaryIndigo))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.LightGray))
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.LightGray))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("0/3", fontSize = 12.sp, color = NeutralMuted)
                }
            }

            item {
                // Step 1
                StepCard(
                    number = "1",
                    title = "ধাপ ১: ধ্রুবক ডান দিকে নিয়ে যাও",
                    instruction = "উভয় দিক থেকে 7 বিয়োগ করো",
                    calculation = "3x = 22 - 7"
                )
            }

            item {
                // Step 2
                StepCard(
                    number = "2",
                    title = "ধাপ ২: ডান দিক সরল করো",
                    instruction = "হিসাব করো",
                    calculation = "3x = 15"
                )
            }

            item {
                // Step 3
                StepCard(
                    number = "3",
                    title = "ধাপ ৩: x আলাদা করো",
                    instruction = "উভয় দিক 3 দিয়ে ভাগ করো",
                    calculation = "x = 5"
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StepCard(number: String, title: String, instruction: String, calculation: String) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        color = ContainerWhite,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(SurfaceOffWhite),
                    contentAlignment = Alignment.Center
                ) {
                    Text(number, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        instruction,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        color = NeutralMuted
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                shape = RoundedCornerShape(percent = 50),
                color = StepHighlightBg,
                modifier = Modifier.padding(start = 44.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Visibility, contentDescription = null, tint = AlertRedText, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        calculation,
                        color = AlertRedText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
