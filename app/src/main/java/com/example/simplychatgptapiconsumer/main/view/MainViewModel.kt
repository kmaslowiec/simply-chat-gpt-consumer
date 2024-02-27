package com.example.simplychatgptapiconsumer.main.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplychatgptapiconsumer.main.intent.MainViewIntent
import com.example.simplychatgptapiconsumer.main.intent.MainViewSideEffect
import com.example.simplychatgptapiconsumer.main.mvimodel.MainViewState
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.FAILURE
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.LOADING
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.SUCCESS
import com.example.simplychatgptapiconsumer.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState())
    val state = _state.asStateFlow()
    private val _sideEffects = MutableSharedFlow<MainViewSideEffect>()
    val sideEffects = _sideEffects.asSharedFlow()
    private val _intent = MutableSharedFlow<MainViewIntent>()

    init {
        collectIntents()
    }

    fun emitIntent(intent: MainViewIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    fun emitSideEffect(sideEffect: MainViewSideEffect) {
        viewModelScope.launch {
            _sideEffects.emit(sideEffect)
        }
    }

    fun updateTriviaSubject(newSubject: String) {
        updateState { copy(triviaSubject = newSubject) }
    }

    private fun updateState(update: MainViewState.() -> MainViewState) {
        _state.value = _state.value.update()
    }

    private fun getResponse(query: String) {
        viewModelScope.launch {
            _state.value = MainViewState(type = LOADING)
            mainRepository.getQuestions(query)
                .onSuccess {
                    updateState {
                        copy(
                            chatResponse = it.choices.first().message.content,
                            type = SUCCESS
                        )
                    }
                }
                .onFailure {
                    updateState { copy(type = FAILURE) }
                }
        }
    }

    private fun collectIntents() {
        viewModelScope.launch {
            _intent.collect(::handleIntent)
        }
    }

    private fun handleIntent(intent: MainViewIntent) {
        when (intent) {
            MainViewIntent.AskChat -> getResponse(_state.value.chatResponse)
        }
    }
}
