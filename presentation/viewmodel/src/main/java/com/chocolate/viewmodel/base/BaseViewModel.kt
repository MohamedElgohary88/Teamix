package com.chocolate.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, UiEffect>(initialState: STATE) : ViewModel() {
    protected val _state: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val state = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<UiEffect>()
    val effect = _effect.asSharedFlow()

    fun <T> tryToExecute(
        call: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                call().also(onSuccess)
            } catch (throwable: Throwable) {
                onError(throwable)
            }
        }
    }

    protected fun <T> collectFlow(
        flow: Flow<T>,
        updateState: STATE.(T) -> STATE
    ) {
        viewModelScope.launch {
            flow.collect { value ->
                _state.update { currentState ->
                    currentState.updateState(value)
                }
            }
        }
    }

    protected fun sendUiEffect(effect: UiEffect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

}