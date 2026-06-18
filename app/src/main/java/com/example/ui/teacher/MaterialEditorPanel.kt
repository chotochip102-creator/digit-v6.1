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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.BackHandler
import com.example.ui.theme.isAppInDarkTheme

@Composable
fun MaterialEditorPanel(
    materialTitleEn: String,
    onDismiss: () -> Unit
) {
    var rows by remember { mutableStateOf(List(3) { List(getColsForMaterial(materialTitleEn).size) { "" } }) }
    val isDark = isAppInDarkTheme()
    val bgCol = if (isDark) Color(0xFF191C1D) else Color(0xFFF8F9FA)
    val onBgCol = if (isDark) Color.White else Color.Black
    val borderCol = if (isDark) Color(0xFF33353A) else Color(0xFFE1E3E4)
    val tableHeaderBg = if (isDark) Color(0xFF232528) else Color(0xFF1D1B20)
    val tableHeaderFg = Color.White
    val buttonBg = Color(0xFF54578C)
    
    val bnTitle = when (materialTitleEn) {
        "Did You Know" -> "জানা অজানা"
        "Quick Quiz" -> "কুইক কুইজ"
        "Word Meaning" -> "শব্দার্থ"
        "Flashcards" -> "ফ্ল্যাশকার্ড"
        else -> materialTitleEn
    }

    val cols = getColsForMaterial(materialTitleEn)

    val primaryIndigo = Color(0xFF54578c)

    BackHandler(onBack = onDismiss)

    @OptIn(ExperimentalMaterial3Api::class)
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
}

fun getColsForMaterial(title: String): List<SheetColumn> {
    return when (title) {
        "Did You Know" -> listOf(
            SheetColumn("Fact / Info", 400f)
        )
        "Quick Quiz" -> listOf(
            SheetColumn("Question", 250f),
            SheetColumn("Option 1", 120f),
            SheetColumn("Option 2", 120f),
            SheetColumn("Option 3", 120f),
            SheetColumn("Option 4", 120f),
            SheetColumn("Correct Answer", 150f)
        )
        "Word Meaning" -> listOf(
            SheetColumn("Word", 150f),
            SheetColumn("Meaning", 150f),
            SheetColumn("Type (e.g. Verb)", 120f),
            SheetColumn("Sentence", 300f)
        )
        "Flashcards" -> listOf(
            SheetColumn("Topic Bn", 150f),
            SheetColumn("Topic En", 150f),
            SheetColumn("Definition Bn", 300f),
            SheetColumn("Definition En", 300f)
        )
        else -> listOf(
            SheetColumn("Field 1", 200f),
            SheetColumn("Field 2", 200f)
        )
    }
}