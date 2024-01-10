package com.example.simplychatgptapiconsumer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Error
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Loading
import com.example.simplychatgptapiconsumer.common.model.ChatAnswerState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _questionsState = MutableStateFlow<ChatAnswerState>(Loading)
    val questionsState = _questionsState.asStateFlow()

    init {
        getResponse()
    }

    private fun getResponse() {
        viewModelScope.launch {
            mainRepository.getQuestions("Say hello human")
                .onSuccess {
                    _questionsState.value = Success(it.choices.first().message)
                }
                .onFailure {
                    _questionsState.value = Error
                }
        }
    }
}
