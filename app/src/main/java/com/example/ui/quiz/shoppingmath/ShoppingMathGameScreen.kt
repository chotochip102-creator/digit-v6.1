package com.example.ui.quiz.shoppingmath

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.LocalDining
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.BakeryDining
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

val PrimaryIndigo = Color(0xFF54578c)
val SecondaryTeal = Color(0xFF1a5f7a)
val SuccessGreen = Color(0xFF2e7d32)
val SurfaceOffWhite = Color(0xFFf8f9fa)
val ContainerWhite = Color(0xFFffffff)
val AccentLavender = Color(0xFFe0e0ff)
val NeutralMuted = Color(0xFF6c757d)
val LightSuccess = Color(0xFFe8f5e9)

data class InventoryItem(
    val icon: ImageVector,
    val nameBn: String,
    val nameEn: String,
    val price: Int
)

data class Question(
    val prompt: String,
    val calculation: String,
    val answer: Int
)

@Composable
fun ShoppingMathBottomNav() {
    NavigationBar(
        containerColor = SurfaceOffWhite,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.MenuBook, null) },
            label = { Text("Learn") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = NeutralMuted,
                unselectedTextColor = NeutralMuted
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.ShoppingCart, null) },
            label = { Text("Shop") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = NeutralMuted,
                unselectedTextColor = NeutralMuted
            )
        )
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Outlined.SportsEsports, null) },
            label = { Text("Games") },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = AccentLavender,
                selectedIconColor = PrimaryIndigo,
                selectedTextColor = PrimaryIndigo
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.Person, null) },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = NeutralMuted,
                unselectedTextColor = NeutralMuted
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingMathApp(onNavigateBack: () -> Unit) {
    var score by remember { mutableIntStateOf(0) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var userInput by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    
    val totalQuestions = 5

    val inventory = listOf(
        InventoryItem(Icons.Outlined.LocalDining, "আপেল", "apple", 25), // Substituting icon for apple
        InventoryItem(Icons.Outlined.Egg, "ডিম", "eggs", 30), // eggs x6
        InventoryItem(Icons.Outlined.BakeryDining, "রুটি", "bread", 18), // Substituting for bread
        InventoryItem(Icons.Outlined.WaterDrop, "তেল", "oil", 95)
    )

    val questions = listOf(
        Question(
            "Rahela buys 1 apple, 6 eggs, and 1 loaf of bread from the market. How much does she pay in total?",
            "৳25 + ৳30 + ৳18",
            73
        ),
        Question(
            "Rahela buys 1 oil and 1 apple. How much does she pay?",
            "৳95 + ৳25",
            120
        ),
        Question(
            "Rahela buys 2 loafs of bread. How much does she pay?",
            "৳18 + ৳18",
            36
        ),
        Question(
            "Rahela buys 6 eggs and 1 oil. How much does she pay?",
            "৳30 + ৳95",
            125
        ),
        Question(
            "Rahela buys 1 apple and 2 loafs of bread. How much does she pay?",
            "৳25 + ৳18 + ৳18",
            61
        )
    )

    val currentQuestion = questions.getOrNull(currentQuestionIndex)
    
    val isCorrect = userInput.toIntOrNull() == currentQuestion?.answer

    Scaffold(
        containerColor = SurfaceOffWhite,
        topBar = {
            Column(modifier = Modifier.background(SurfaceOffWhite)) {
                TopAppBar(
                    title = {
                        Text(
                            "বাজারের\nঅংক",
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
                        IconButton(onClick = { }) {
                            Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = PrimaryIndigo)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceOffWhite)
                )
                
                // Progress Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        for (i in 0 until totalQuestions) {
                            Box(
                                modifier = Modifier
                                    .height(6.dp)
                                    .weight(1f)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(if (i <= currentQuestionIndex) SecondaryTeal.copy(alpha = 0.5f) else Color.LightGray.copy(alpha = 0.5f))
                            )
                        }
                    }
                    Text(
                        "Score: $score/$totalQuestions",
                        color = NeutralMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        bottomBar = {
            ShoppingMathBottomNav()
        }
    ) { paddingValues ->
        if (currentQuestion != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Market Inventory Card
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = SurfaceOffWhite,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            "রাহেলার বাজার (RAHELA'S\nMARKET)",
                            fontSize = 16.sp,
                            color = NeutralMuted,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            MarketItemCard(modifier = Modifier.weight(1f), item = inventory[0])
                            MarketItemCard(modifier = Modifier.weight(1f), item = inventory[1], isEgg = true)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            MarketItemCard(modifier = Modifier.weight(1f), item = inventory[2])
                            MarketItemCard(modifier = Modifier.weight(1f), item = inventory[3])
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Question Area
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = ContainerWhite,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            "Question ${currentQuestionIndex + 1} of $totalQuestions",
                            fontSize = 12.sp,
                            color = NeutralMuted,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            currentQuestion.prompt,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            lineHeight = 28.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Surface(
                            shape = RoundedCornerShape(percent = 50),
                            color = AccentLavender.copy(alpha = 0.3f),
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            Text(
                                "Total cost",
                                fontSize = 12.sp,
                                color = PrimaryIndigo,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Input
                            Surface(
                                shape = RoundedCornerShape(percent = 50),
                                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                                color = ContainerWhite,
                                modifier = Modifier.weight(1f).height(56.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                ) {
                                    Text("৳", fontSize = 20.sp, color = NeutralMuted)
                                    Spacer(modifier = Modifier.width(16.dp))
                                    BasicTextField(
                                        value = userInput,
                                        onValueChange = { if (it.length <= 4) userInput = it.filter { c -> c.isDigit() } },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true,
                                        enabled = !isChecked
                                    )
                                }
                            }
                            
                            // Check button
                            Button(
                                onClick = {
                                    if (userInput.isNotEmpty() && !isChecked) {
                                        isChecked = true
                                        if (isCorrect) score++
                                    }
                                },
                                shape = RoundedCornerShape(percent = 50),
                                modifier = Modifier.height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (userInput.isNotEmpty() && !isChecked) SecondaryTeal else Color.LightGray.copy(alpha = 0.2f),
                                    contentColor = if (userInput.isNotEmpty() && !isChecked) Color.White else Color.Black
                                ),
                                enabled = userInput.isNotEmpty() && !isChecked
                            ) {
                                Text("Check", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                        
                        // Feedback
                        if (isChecked) {
                            Spacer(modifier = Modifier.height(24.dp))
                            if (isCorrect) {
                                Surface(
                                    shape = RoundedCornerShape(percent = 50),
                                    color = LightSuccess,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = SuccessGreen)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Correct! ${currentQuestion.calculation} = ৳${currentQuestion.answer}",
                                            color = SuccessGreen,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(percent = 50),
                                    color = Color(0xFFFFEBEE),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "Incorrect. ${currentQuestion.calculation} = ৳${currentQuestion.answer}",
                                            color = Color(0xFFC62828),
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Show hint",
                            fontSize = 14.sp,
                            color = NeutralMuted,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Button(
                            onClick = {
                                if (currentQuestionIndex < totalQuestions - 1) {
                                    currentQuestionIndex++
                                    userInput = ""
                                    isChecked = false
                                } else {
                                    onNavigateBack() // Or navigate to results
                                }
                            },
                            shape = RoundedCornerShape(percent = 50),
                            modifier = Modifier.height(56.dp).width(160.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray.copy(alpha = 0.3f),
                                contentColor = Color.Black
                            ),
                            enabled = isChecked
                        ) {
                            Text(if (currentQuestionIndex < totalQuestions - 1) "Next question" else "Finish", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MarketItemCard(modifier: Modifier = Modifier, item: InventoryItem, isEgg: Boolean = false) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = ContainerWhite,
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.nameEn,
                tint = PrimaryIndigo,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    item.nameBn,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                if (isEgg) {
                    Text(
                        " ×৬",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Text(
                    " (${item.nameEn}${if(isEgg) " ×6" else ""})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "৳${item.price}",
                fontSize = 16.sp,
                color = NeutralMuted
            )
        }
    }
}
