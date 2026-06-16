package com.example.ui.quiz.fillblank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BlankState(
    val input: String = "",
    val status: BlankStatus = BlankStatus.IDLE
)

enum class BlankStatus { IDLE, CORRECT, WRONG, REVEALED }

data class GameUiState(
    val lang: Lang = Lang.BN,
    val topic: Topic = Topic.PHYSICS,
    val questionIndex: Int = 0,
    val score: Int = 0,
    val streak: Int = 0,
    val wrongCount: Int = 0,
    val blankStates: List<BlankState> = emptyList(),
    val feedbackState: FeedbackState = FeedbackState.NONE,
    val hintUsed: Boolean = false,
    val hintsVisible: Boolean = false,
    val checked: Boolean = false,
    val phase: GamePhase = GamePhase.PLAYING
)

enum class FeedbackState { NONE, ALL_CORRECT, WRONG, REVEALED }
enum class GamePhase { PLAYING, SUMMARY }

class FillBlankViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        loadQuestion(0)
    }

    fun setLang(lang: Lang) {
        if (_uiState.value.lang != lang) {
            _uiState.value = _uiState.value.copy(lang = lang)
        }
    }

    fun setTopic(topic: Topic) {
        if (_uiState.value.topic != topic) {
            _uiState.value = GameUiState(topic = topic, lang = _uiState.value.lang)
            loadQuestion(0)
        }
    }

    fun updateInput(blankIndex: Int, value: String) {
        val states = _uiState.value.blankStates.toMutableList()
        states[blankIndex] = states[blankIndex].copy(input = value, status = BlankStatus.IDLE)
        _uiState.value = _uiState.value.copy(blankStates = states, checked = false, feedbackState = FeedbackState.NONE)
    }

    fun checkAnswers() {
        if (_uiState.value.checked) return
        
        val state = _uiState.value
        val questions = QuestionData.questions[state.topic] ?: return
        val currentQ = questions[state.questionIndex]
        
        val enContent = currentQ.en
        val bnContent = currentQ.bn
        
        var allCorrect = true
        val newBlankStates = state.blankStates.toMutableList()

        for (i in newBlankStates.indices) {
            val userInput = newBlankStates[i].input
            val normalizedInput = QuestionData.normalize(userInput)
            // User can type the answer in BN or EN, so we check both
            val enAns = QuestionData.normalize(enContent.blanks[i].answer)
            val bnAns = QuestionData.normalize(bnContent.blanks[i].answer)
            
            if (normalizedInput == enAns || normalizedInput == bnAns) {
                newBlankStates[i] = newBlankStates[i].copy(status = BlankStatus.CORRECT)
            } else {
                newBlankStates[i] = newBlankStates[i].copy(status = BlankStatus.WRONG)
                allCorrect = false
            }
        }

        if (allCorrect) {
            val points = if (state.hintUsed) 1 else 2
            _uiState.value = state.copy(
                blankStates = newBlankStates,
                score = state.score + points,
                streak = state.streak + 1,
                checked = true,
                feedbackState = FeedbackState.ALL_CORRECT
            )
            autoAdvance(1200) { nextQuestion() }
        } else {
            _uiState.value = state.copy(
                blankStates = newBlankStates,
                streak = 0,
                wrongCount = state.wrongCount + 1,
                checked = true,
                feedbackState = FeedbackState.WRONG
            )
            autoAdvance(1600) {
                // Clear wrong inputs and hide feedback
                val resetStates = _uiState.value.blankStates.map {
                    if (it.status == BlankStatus.WRONG) it.copy(input = "", status = BlankStatus.IDLE)
                    else it
                }
                _uiState.value = _uiState.value.copy(
                    blankStates = resetStates,
                    checked = false,
                    feedbackState = FeedbackState.NONE
                )
            }
        }
    }

    fun showHints() {
        _uiState.value = _uiState.value.copy(hintsVisible = true, hintUsed = true)
    }

    fun revealAnswers() {
        val state = _uiState.value
        val questions = QuestionData.questions[state.topic] ?: return
        val content = if (state.lang == Lang.BN) questions[state.questionIndex].bn else questions[state.questionIndex].en
        
        val revealedStates = content.blanks.map {
            BlankState(input = it.answer, status = BlankStatus.REVEALED)
        }
        
        _uiState.value = state.copy(
            blankStates = revealedStates,
            streak = 0,
            feedbackState = FeedbackState.REVEALED,
            checked = true
        )
        
        autoAdvance(1600) { nextQuestion() }
    }

    fun nextQuestion() {
        val state = _uiState.value
        val totalQ = QuestionData.questions[state.topic]?.size ?: 0
        if (state.questionIndex + 1 < totalQ) {
            loadQuestion(state.questionIndex + 1)
        } else {
            _uiState.value = state.copy(phase = GamePhase.SUMMARY, feedbackState = FeedbackState.NONE)
        }
    }

    fun restart() {
        val currentTopic = _uiState.value.topic
        val currentLang = _uiState.value.lang
        _uiState.value = GameUiState(topic = currentTopic, lang = currentLang)
        loadQuestion(0)
    }

    private fun loadQuestion(index: Int) {
        val questions = QuestionData.questions[_uiState.value.topic] ?: return
        if (index >= questions.size) return
        
        // Blank count is same for bn and en
        val blanksCount = questions[index].bn.blanks.size
        val initialBlanks = List(blanksCount) { BlankState() }
        
        _uiState.value = _uiState.value.copy(
            questionIndex = index,
            blankStates = initialBlanks,
            hintsVisible = false,
            hintUsed = false,
            checked = false,
            feedbackState = FeedbackState.NONE,
            phase = GamePhase.PLAYING
        )
    }

    private fun autoAdvance(delayMs: Long, action: () -> Unit) {
        viewModelScope.launch {
            delay(delayMs)
            // Ensure we are still showing the feedback we started with 
            // before auto advancing (in case user clicked something else)
            action()
        }
    }
}
