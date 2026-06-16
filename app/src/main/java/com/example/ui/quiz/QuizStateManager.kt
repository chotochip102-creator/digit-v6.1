package com.example.ui.quiz

import androidx.compose.runtime.mutableStateMapOf

object QuizStateManager {
    // Map of quiz id (or English title) to a QuizState
    val quizStates = mutableStateMapOf<String, QuizState>()
    
    fun getState(quizId: String): QuizState {
        return quizStates.getOrPut(quizId) { QuizState() }
    }
    
    fun toggleLock(quizId: String) {
        val curr = getState(quizId)
        quizStates[quizId] = curr.copy(isLocked = !curr.isLocked)
    }

    fun toggleHide(quizId: String) {
        val curr = getState(quizId)
        quizStates[quizId] = curr.copy(isHidden = !curr.isHidden)
    }
}

data class QuizState(
    val isLocked: Boolean = false,
    val isHidden: Boolean = false
)
