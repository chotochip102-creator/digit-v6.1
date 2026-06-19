package com.example.ui

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

object SlideshowDataStore {
    val quickQuizRows = mutableStateOf(
        listOf(
            listOf("বিপরীত শব্দ লিখুন: আকাশ", "পাতাল", "নদী", "মাটি", "বাতাস", "পাতাল"),
            listOf("সমার্থক শব্দ লিখুন: আগুন", "অনল", "পানি", "বাতাস", "মাটি", "অনল"),
            listOf("সমার্থক শব্দ লিখুন: জল", "বারি", "সূর্য", "চন্দ্র", "নক্ষত্র", "বারি")
        )
    )

    val didYouKnowRows = mutableStateOf(
        listOf(
            listOf("আলোর প্রতিফলন কোনো পৃষ্ঠ থেকে আলোর দিক পরিবর্তন প্রক্রিয়া।"),
            listOf("মরুভূমির মরিচিকা আলোর পূর্ণ অভ্যন্তরীণ প্রতিফলনের প্রাকৃতিক উদাহরণ।"),
            listOf("শব্দ তরঙ্গের চলাচলের জন্য একটি জড় মাধ্যমের প্রয়োজন হয়।")
        )
    )

    val wordMeaningRows = mutableStateOf(
        listOf(
            listOf("Reflection", "প্রতিফলন", "Noun", "The reflection of light is beautiful.", "আলোর প্রতিফলন সুন্দর।"),
            listOf("Lens", "লেন্স", "Noun", "A camera pad uses a lens.", "একটি ক্যামেরা প্যাড লেন্স ব্যবহার করে।"),
            listOf("Transparent", "স্বচ্ছ", "Adjective", "Glass is transparent to light.", "কাচ আলোর জন্য স্বচ্ছ।")
        )
    )
    
    val slideshowCycleCount = mutableIntStateOf(0)
    val currentFactIndex = mutableIntStateOf(0)
    val currentWordIndex = mutableIntStateOf(0)
    val currentQuizIndex = mutableIntStateOf(0)
    
    val quickQuizNeedsUpdate = mutableStateOf(false)
    val quickQuizAnswered = mutableStateOf<Boolean?>(null)
    val quickQuizSelected = mutableStateOf<Int?>(null)
}
