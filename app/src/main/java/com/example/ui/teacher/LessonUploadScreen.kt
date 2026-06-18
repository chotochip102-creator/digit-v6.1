package com.example.ui.teacher
import com.example.ui.components.SmartText

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonUploadScreen(onNavigateBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var classLevel by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { SmartText("নতুন পাঠ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 20.sp) }, // FIXED
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(24.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { SmartText("Title (Bengali)") }, modifier = Modifier.fillMaxWidth()) // FIXED
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { SmartText("Subject (e.g. Math)") }, modifier = Modifier.fillMaxWidth()) // FIXED
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = classLevel, onValueChange = { classLevel = it }, label = { SmartText("Class Level") }, modifier = Modifier.fillMaxWidth()) // FIXED
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = link, onValueChange = { link = it }, label = { SmartText("Google Drive Link") }, modifier = Modifier.fillMaxWidth()) // FIXED
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onNavigateBack() }, 
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    enabled = title.isNotBlank() && subject.isNotBlank()
                ) {
                    SmartText("Save Lesson") // FIXED
                }
            }
        }
    }
}