package com.example.simplychatgptapiconsumer.common.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplychatgptapiconsumer.common.mvicomponents.MviIntent
import com.example.simplychatgptapiconsumer.common.mvicomponents.MviSideEffect
import com.example.simplychatgptapiconsumer.common.mvicomponents.MviState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class MVIViewModel<S : MviState, I : MviIntent, SE : MviSideEffect>(initialState: S) : ViewModel() {

    protected val _state = mutableStateOf(initialState)
    val state: State<S> = _state
    protected val _sideEffects = MutableSharedFlow<SE>()
    val sideEffects = _sideEffects.asSharedFlow()
    protected val _intent = MutableSharedFlow<I>()

    init {
        collectIntents()
    }

    protected abstract fun handleIntent(intent: I)

    protected fun MutableState<S>.update(copyBlock: S.() -> S) {
        _state.value = value.copyBlock()
    }

    fun emitIntent(intent: I) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    fun emitSideEffect(sideEffect: SE) {
        viewModelScope.launch {
            _sideEffects.emit(sideEffect)
        }
    }

    private fun collectIntents() {
        viewModelScope.launch {
            _intent.collect(::handleIntent)
        }
    }
}
