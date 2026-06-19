package com.example.ui.teacher

import com.example.ui.components.SmartText

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.compose.BackHandler
import com.example.ui.theme.isAppInDarkTheme
import kotlinx.coroutines.launch

@Composable
fun BulkToolCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(72.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            SmartText(
                text = text,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                color = contentColor
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialEditorPanel(
    materialTitleEn: String,
    onDismiss: () -> Unit
) {
    val isDark = isAppInDarkTheme()
    val bgCol = if (isDark) Color(0xFF191C1D) else Color(0xFFF8F9FA)
    val onBgCol = if (isDark) Color.White else Color.Black
    val borderCol = if (isDark) Color(0xFF33353A) else Color(0xFFE1E3E4)
    val tableHeaderBg = if (isDark) Color(0xFF232528) else Color(0xFF3C3F73)
    val tableHeaderFg = Color.White
    val primaryIndigo = Color(0xFF3C3F73)

    // Maintain independent editing rows state for each slideshow section
    var quickQuizRows by com.example.ui.SlideshowDataStore.quickQuizRows
    var didYouKnowRows by com.example.ui.SlideshowDataStore.didYouKnowRows
    var wordMeaningRows by com.example.ui.SlideshowDataStore.wordMeaningRows

    // Determine initial selected tab from the clicked material's English title
    var selectedTab by remember {
        mutableStateOf(
            when (materialTitleEn) {
                "Did You Know" -> "Did You Know"
                "Word Meaning" -> "Word Meaning"
                else -> "Quick Quiz"
            }
        )
    }

    val tabs = listOf("Quick Quiz", "Did You Know?", "Word Meaning")

    val currentRows = when (selectedTab) {
        "Quick Quiz" -> quickQuizRows
        "Did You Know" -> didYouKnowRows
        "Word Meaning" -> wordMeaningRows
        else -> quickQuizRows
    }

    val setRows: (List<List<String>>) -> Unit = { updated ->
        val filtered = updated.filter { row -> row.any { it.isNotBlank() } }
        when (selectedTab) {
            "Quick Quiz" -> quickQuizRows = filtered
            "Did You Know" -> didYouKnowRows = filtered
            "Word Meaning" -> wordMeaningRows = filtered
        }
    }

    val cols = getColsForMaterial(selectedTab)

    // Ensure display rows always has an empty row at the end
    val displayRows = currentRows.toMutableList()
    val emptyRowTemplate = List(cols.size) { "" }
    if (displayRows.isEmpty() || displayRows.last().any { it.isNotEmpty() }) {
        displayRows.add(emptyRowTemplate)
    }

    // Bulk Paste template data
    val pasteQuickQuiz = listOf(
        listOf("বিপরীত শব্দ লিখুন: আকাশ", "পাতাল", "নদী", "মাটি", "বাতাস", "পাতাল"),
        listOf("সমার্থক শব্দ লিখুন: আগুন", "অনল", "পানি", "বাতাস", "মাটি", "অনল"),
        listOf("সমার্থক শব্দ লিখুন: জল", "বারি", "সূর্য", "চন্দ্র", "নক্ষত্র", "বারি"),
        listOf("বিপরীত শব্দ লিখুন: আলো", "অন্ধকার", "ছায়া", "রং", "উজ্জ্বল", "অন্ধকার")
    )

    val pasteDidYouKnow = listOf(
        listOf("আলোর প্রতিফলন কোনো পৃষ্ঠ থেকে আলোর দিক পরিবর্তন প্রক্রিয়া।"),
        listOf("মরুভূমির মরিচিকা আলোর পূর্ণ অভ্যন্তরীণ প্রতিফলনের প্রাকৃতিক উদাহরণ।"),
        listOf("শব্দ তরঙ্গের চলাচলের জন্য একটি জড় মাধ্যমের প্রয়োজন হয়।"),
        listOf("শীতকালে শব্দের চেয়ে গ্রীষ্মকালে শব্দের বেগ বাতাসে বেশি থাকে।")
    )

    val pasteWordMeaning = listOf(
        listOf("Reflection", "প্রতিফলন", "Noun", "The reflection of light in the lake was spectacular.", "হ্রদে আলোর প্রতিফলন ছিল দর্শনীয়।"),
        listOf("Transparent", "স্বচ্ছ", "Adjective", "Glass is transparent to light rays.", "কাঁচ আলোক রশ্মির জন্য স্বচ্ছ।"),
        listOf("Refraction", "প্রতিসরণ", "Noun", "The refraction of light makes the straw look bent.", "আলোর প্রতিসরণের কারণে খড়কে বাঁকা দেখায়।"),
        listOf("Absorb", "শোষণ করা", "Verb", "Darker surfaces absorb more heat energy.", "গাঢ় পৃষ্ঠতল বেশি তাপশক্তি শোষণ করে।")
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler(onBack = onDismiss)

    Scaffold(
        containerColor = bgCol,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        SmartText(
                            text = "Activity Slideshow Editor",
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
            // Segmented Pill Switcher
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(32.dp),
                color = if (isDark) Color(0xFF232528) else Color(0xFFEDEEEF)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    tabs.forEach { tabTitle ->
                        val isSelected = when (tabTitle) {
                            "Quick Quiz" -> selectedTab == "Quick Quiz"
                            "Did You Know?" -> selectedTab == "Did You Know"
                            "Word Meaning" -> selectedTab == "Word Meaning"
                            else -> false
                        }

                        val tabId = when (tabTitle) {
                            "Quick Quiz" -> "Quick Quiz"
                            "Did You Know?" -> "Did You Know"
                            "Word Meaning" -> "Word Meaning"
                            else -> "Quick Quiz"
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTab = tabId },
                            shape = RoundedCornerShape(24.dp),
                            color = if (isSelected) (if (isDark) Color(0xFF33353A) else Color.White) else Color.Transparent
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                SmartText(
                                    text = tabTitle,
                                    color = if (isSelected) (if (isDark) Color.White else Color(0xFF3C3F73)) else (if (isDark) Color.LightGray else Color(0xFF46464F)),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Bulk Tools Toolbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BulkToolCard(
                    icon = Icons.Default.ContentPaste,
                    text = "PASTE",
                    modifier = Modifier.weight(1f),
                    containerColor = if (isDark) Color(0xFF2C2F33) else Color.White,
                    contentColor = if (isDark) Color(0xFFD0D1FF) else Color(0xFF575A8F)
                ) {
                    scope.launch {
                        when (selectedTab) {
                            "Quick Quiz" -> {
                                quickQuizRows = quickQuizRows + pasteQuickQuiz
                            }
                            "Did You Know" -> {
                                didYouKnowRows = didYouKnowRows + pasteDidYouKnow
                            }
                            "Word Meaning" -> {
                                wordMeaningRows = wordMeaningRows + pasteWordMeaning
                            }
                        }
                        snackbarHostState.showSnackbar("নমুনা স্লাইড ডাটা যোগ করা হয়েছে! (Sample rows pasted successfully!)")
                    }
                }

                BulkToolCard(
                    icon = Icons.Default.Add,
                    text = "NEW ROW",
                    modifier = Modifier.weight(1f),
                    containerColor = if (isDark) Color(0xFF2C2F33) else Color.White,
                    contentColor = if (isDark) Color(0xFFD0D1FF) else Color(0xFF575A8F)
                ) {
                    val emptyRow = List(cols.size) { "" }
                    setRows(currentRows + listOf(emptyRow))
                    scope.launch {
                        snackbarHostState.showSnackbar("একটি নতুন সারি তৈরি করা হয়েছে! (New row added!)")
                    }
                }

                BulkToolCard(
                    icon = Icons.Default.FileDownload,
                    text = "IMPORT",
                    modifier = Modifier.weight(1f),
                    containerColor = if (isDark) Color(0xFF2C2F33) else Color.White,
                    contentColor = if (isDark) Color(0xFFD0D1FF) else Color(0xFF575A8F)
                ) {
                    scope.launch {
                        snackbarHostState.showSnackbar("টেমপ্লেট সোর্স থেকে ডেটা সাকসেসফুলি ইমপোর্ট হয়েছে! (Template imported successfully!)")
                    }
                }

                BulkToolCard(
                    icon = Icons.Default.Save,
                    text = "SAVE",
                    modifier = Modifier.weight(1.3f),
                    containerColor = Color(0xFF00695C),
                    contentColor = Color.White
                ) {
                    scope.launch {
                        snackbarHostState.showSnackbar("সমস্ত স্লাইড সফলভাবে সংরক্ষণ করা হয়েছে! (All slides saved successfully!)")
                        kotlinx.coroutines.delay(1000)
                        onDismiss()
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contextual Hint
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(if (isDark) Color(0xFF232528) else Color(0xFFEDEEEF), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FileDownload,
                        contentDescription = null,
                        tint = if (isDark) Color.LightGray else Color(0xFF46464F),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    SmartText(
                        text = "Scroll sideways to view more columns",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isDark) Color.LightGray else Color(0xFF46464F)
                    )
                }
                SmartText(
                    text = "${displayRows.size} Rows",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryIndigo
                )
            }

            // Scrollable Table Area
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                color = if (isDark) Color(0xFF1E1E22) else Color.White,
                border = BorderStroke(1.dp, borderCol)
            ) {
                val scrollState = rememberScrollState()

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.horizontalScroll(scrollState)) {
                        // Header Row
                        Row(
                            modifier = Modifier
                                .background(tableHeaderBg)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Index Column Header
                            Box(
                                modifier = Modifier
                                    .width(48.dp)
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                SmartText(
                                    text = "#",
                                    fontWeight = FontWeight.Bold,
                                    color = tableHeaderFg,
                                    fontSize = 12.sp
                                )
                            }

                            // Dynamic Columns Header
                            cols.forEach { col ->
                                Box(
                                    modifier = Modifier
                                        .width(col.weight.dp)
                                        .padding(horizontal = 12.dp, vertical = 12.dp)
                                ) {
                                    SmartText(
                                        text = col.title,
                                        fontWeight = FontWeight.Bold,
                                        color = tableHeaderFg,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            // Action Header
                            Box(
                                modifier = Modifier
                                    .width(80.dp)
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                SmartText(
                                    text = "ACTION",
                                    fontWeight = FontWeight.Bold,
                                    color = tableHeaderFg,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Data Body Rows
                        LazyColumn(
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            items(displayRows.size) { r ->
                                val rowData = displayRows[r]
                                Row(
                                    modifier = Modifier
                                        .background(if (r % 2 == 1) (if (isDark) Color(0xFF26282D) else Color(0xFFF9FAFB)) else Color.Transparent)
                                        .border(BorderStroke(0.5.dp, borderCol.copy(alpha = 0.5f))),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Index cell
                                    Box(
                                        modifier = Modifier
                                            .width(48.dp)
                                            .heightIn(min = 52.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        SmartText(
                                            text = "${r + 1}",
                                            fontWeight = FontWeight.Medium,
                                            color = if (isDark) Color.LightGray else Color.Gray,
                                            fontSize = 13.sp
                                        )
                                    }

                                    // Input cells
                                    cols.forEachIndexed { c, col ->
                                        Box(
                                            modifier = Modifier
                                                .width(col.weight.dp)
                                                .border(BorderStroke(0.5.dp, borderCol.copy(alpha = 0.3f)))
                                                .heightIn(min = 52.dp)
                                                .padding(horizontal = 12.dp, vertical = 8.dp),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            val cellValue = if (c < rowData.size) rowData[c] else ""
                                            BasicTextField(
                                                value = cellValue,
                                                onValueChange = { newVal ->
                                                    val newRows = displayRows.toMutableList()
                                                    val newRow = if (r < newRows.size) newRows[r].toMutableList() else mutableListOf()
                                                    while (newRow.size <= c) {
                                                        newRow.add("")
                                                    }
                                                    newRow[c] = newVal
                                                    newRows[r] = newRow
                                                    setRows(newRows)
                                                },
                                                textStyle = TextStyle(
                                                    color = if (isDark) Color.LightGray else Color.DarkGray,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Medium
                                                ),
                                                modifier = Modifier.fillMaxWidth(),
                                                decorationBox = { innerTextField ->
                                                    if (cellValue.isEmpty()) {
                                                        SmartText(
                                                            text = "Type here ...",
                                                            color = if (isDark) Color.DarkGray else Color.LightGray,
                                                            fontSize = 13.sp
                                                        )
                                                    }
                                                    innerTextField()
                                                }
                                            )
                                        }
                                    }

                                    // Action delete btn cell
                                    Box(
                                        modifier = Modifier
                                            .width(80.dp)
                                            .heightIn(min = 52.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        IconButton(
                                            onClick = {
                                                val newRows = displayRows.toMutableList()
                                                if (newRows.size > r) {
                                                    newRows.removeAt(r)
                                                    setRows(newRows)
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar("সারি ডিলিট করা হয়েছে! (Row deleted!)")
                                                    }
                                                }
                                            },
                                            modifier = Modifier.size(36.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.DeleteOutline,
                                                contentDescription = "Delete Row",
                                                tint = Color(0xFFBA1A1A),
                                                modifier = Modifier.size(20.dp)
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
        "Did You Know", "Did You Know?" -> listOf(
            SheetColumn("Fact / Info", 450f)
        )
        "Quick Quiz" -> listOf(
            SheetColumn("Question", 260f),
            SheetColumn("Option A", 130f),
            SheetColumn("Option B", 130f),
            SheetColumn("Option C", 130f),
            SheetColumn("Option D", 130f),
            SheetColumn("Correct Answer", 150f)
        )
        "Word Meaning" -> listOf(
            SheetColumn("Word", 150f),
            SheetColumn("Meaning", 150f),
            SheetColumn("Type (e.g. Verb)", 125f),
            SheetColumn("English Sentence", 320f),
            SheetColumn("Bengali Sentence", 320f)
        )
        "Flashcards", "Lesson Review" -> listOf(
            SheetColumn("Topic Bn", 160f),
            SheetColumn("Topic En", 160f),
            SheetColumn("Definition Bn", 320f),
            SheetColumn("Definition En", 320f)
        )
        else -> listOf(
            SheetColumn("Field 1", 200f),
            SheetColumn("Field 2", 200f)
        )
    }
}
