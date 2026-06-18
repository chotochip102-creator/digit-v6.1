package com.example.ui.teacher
import com.example.ui.components.SmartText

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.activity.compose.BackHandler
import com.example.ui.theme.isAppInDarkTheme

data class SheetColumn(val title: String, val weight: Float)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizEditorPanel(
    quizTitleEn: String,
    onDismiss: () -> Unit
) {
    var rows by remember { mutableStateOf(List(3) { List(getColsForQuiz(quizTitleEn).size) { "" } }) }
    val isDark = isAppInDarkTheme()
    val bgCol = if (isDark) Color(0xFF191C1D) else Color(0xFFF8F9FA)
    val onBgCol = if (isDark) Color.White else Color.Black
    val borderCol = if (isDark) Color(0xFF33353A) else Color(0xFFE1E3E4)
    val tableHeaderBg = if (isDark) Color(0xFF232528) else Color(0xFF1D1B20)
    val tableHeaderFg = Color.White
    val buttonBg = Color(0xFF54578C)
    
    val bnTitle = when (quizTitleEn) {
        "Lesson Quizzes" -> "পাঠ ভিত্তিক কুইজ"
        "Fill in the Blanks" -> "শূন্যস্থান পূরণ"
        "Math Solver" -> "গণিত সমাধানকারী"
        "Vocabulary Memory" -> "শব্দভাণ্ডার মেমরি"
        else -> quizTitleEn
    }

    val cols = getColsForQuiz(quizTitleEn)

    val primaryIndigo = Color(0xFF54578c)

    BackHandler(onBack = onDismiss)

    Scaffold(
        containerColor = bgCol,
        topBar = {
            TopAppBar(
                title = { 
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        SmartText( // FIXED
                            text = "সম্পাদনা: $bnTitle", 
                            fontWeight = FontWeight.Bold, 
                            color = primaryIndigo, 
                            modifier = Modifier.padding(end = 48.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = primaryIndigo)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                        // Action Buttons 2x2 Grid
                        Column(modifier = Modifier.fillMaxWidth(0.9f).padding(top = 16.dp)) {

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EditorActionButton(
                            icon = Icons.Default.ContentPaste,
                            text = "বাল্ক পেস্ট",
                            modifier = Modifier.weight(1f),
                            buttonBg = buttonBg
                        ) { /* TODO */ }
                        EditorActionButton(
                            icon = Icons.Default.Add,
                            text = "নতুন সারি",
                            modifier = Modifier.weight(1f),
                            buttonBg = buttonBg
                        ) { rows = rows + listOf(List(cols.size) { "" }) }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        EditorActionButton(
                            icon = Icons.Default.FileDownload,
                            text = "ইমপোর্ট",
                            modifier = Modifier.weight(1f),
                            buttonBg = buttonBg
                        ) { /* TODO */ }
                        EditorActionButton(
                            icon = Icons.Default.Save,
                            text = "সংরক্ষণ",
                            modifier = Modifier.weight(1f),
                            buttonBg = buttonBg
                        ) { onDismiss() }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Spreadsheet Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                        .padding(horizontal = 16.dp)
                ) {
                    val scrollState = rememberScrollState()
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isDark) Color(0xFF1E1E22) else Color.White,
                        border = BorderStroke(1.dp, borderCol),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(modifier = Modifier.horizontalScroll(scrollState)) {
                            // Header Row
                            Row(
                                modifier = Modifier
                                    .background(tableHeaderBg)
                                    .fillMaxWidth()
                            ) {
                                cols.forEach { col ->
                                    Box(
                                        modifier = Modifier
                                            .width(col.weight.dp)
                                            .padding(16.dp)
                                    ) {
                                        SmartText( // FIXED
                                            text = col.title,
                                            fontWeight = FontWeight.Bold,
                                            color = tableHeaderFg,
                                            fontSize = 15.sp
                                        )
                                    }
                                }
                                Box(modifier = Modifier.width(100.dp).padding(16.dp), contentAlignment = Alignment.Center) {
                                    SmartText("Actions", fontWeight = FontWeight.Bold, color = tableHeaderFg, fontSize = 15.sp) // FIXED
                                }
                            }

                            // Data Rows
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(rows.size) { r ->
                                    Row(
                                        modifier = Modifier.border(0.5.dp, borderCol),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        cols.forEachIndexed { c, col ->
                                            Box(
                                                modifier = Modifier
                                                    .width(col.weight.dp)
                                                    .border(0.5.dp, borderCol)
                                                    .heightIn(min = 60.dp)
                                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                                contentAlignment = Alignment.CenterStart
                                            ) {
                                                BasicTextField(
                                                    value = rows[r][c],
                                                    onValueChange = { newVal ->
                                                        val newRows = rows.toMutableList()
                                                        val newRow = newRows[r].toMutableList()
                                                        newRow[c] = newVal
                                                        newRows[r] = newRow
                                                        rows = newRows
                                                    },
                                                    textStyle = TextStyle(color = if (isDark) Color.LightGray else Color.DarkGray, fontSize = 15.sp),
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }
                                        }
                                        Box(
                                            modifier = Modifier
                                                .width(100.dp)
                                                .border(0.5.dp, borderCol)
                                                .heightIn(min = 60.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    val newRows = rows.toMutableList()
                                                    newRows.removeAt(r)
                                                    rows = newRows
                                                },
                                                modifier = Modifier.size(40.dp)
                                            ) {
                                                Icon(
                                                    Icons.Default.DeleteOutline,
                                                    contentDescription = "Delete Row",
                                                    tint = Color(0xFFD32F2F),
                                                    modifier = Modifier.size(24.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } // end QuizEditorPanel

@Composable
fun EditorActionButton(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    buttonBg: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonBg),
        shape = CircleShape
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        SmartText(text, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold) // FIXED
    }
}

fun getColsForQuiz(quizTitleEn: String): List<SheetColumn> {
    return when (quizTitleEn) {
        "Lesson Quizzes" -> listOf(
            SheetColumn("Question", 200f),
            SheetColumn("Option A", 120f),
            SheetColumn("Option B", 120f),
            SheetColumn("Option C", 120f),
            SheetColumn("Option D", 120f),
            SheetColumn("Answer", 100f)
        )
        "Fill in the Blanks" -> listOf(
            SheetColumn("Sentence (use [BLANK])", 300f),
            SheetColumn("Correct Word", 150f),
            SheetColumn("Distractors (comma separated)", 200f)
        )
        "Math Solver" -> listOf(
            SheetColumn("Problem", 250f),
            SheetColumn("Answer", 150f)
        )
        "Vocabulary Memory" -> listOf(
            SheetColumn("Word (En)", 200f),
            SheetColumn("Meaning (Bn)", 200f)
        )
        else -> listOf(
            SheetColumn("Field 1", 200f),
            SheetColumn("Field 2", 200f)
        )
    }
}