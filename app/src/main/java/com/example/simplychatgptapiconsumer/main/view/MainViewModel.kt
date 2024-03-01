package com.example.simplychatgptapiconsumer.main.view

import androidx.lifecycle.viewModelScope
import com.example.simplychatgptapiconsumer.common.viewmodel.MVIViewModel
import com.example.simplychatgptapiconsumer.main.intent.MainViewIntent
import com.example.simplychatgptapiconsumer.main.intent.MainViewSideEffect
import com.example.simplychatgptapiconsumer.main.mvimodel.MainViewState
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.FAILURE
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.LOADING
import com.example.simplychatgptapiconsumer.main.mvimodel.ResponseType.SUCCESS
import com.example.simplychatgptapiconsumer.main.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : MVIViewModel<MainViewState, MainViewIntent, MainViewSideEffect>(initialState = MainViewState()) {

    fun updateTriviaSubject(newSubject: String) {
        _state.update {
            copy(triviaSubject = newSubject)
        }
    }

    private fun getResponse(query: String) {
        viewModelScope.launch {
            _state.value = MainViewState(type = LOADING)
            mainRepository.getQuestions(query)
                .onSuccess {
                    _state.update {
                        copy(
                            chatResponse = it.choices.first().message.content,
                            type = SUCCESS
                        )
                    }
                }
                .onFailure {
                    _state.update { copy(type = FAILURE) }
                }
        }
    }

    override fun handleIntent(intent: MainViewIntent) {
        when (intent) {
            MainViewIntent.AskChat -> getResponse(_state.value.triviaSubject)
        }
    }
}
