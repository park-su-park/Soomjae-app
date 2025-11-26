package com.parksupark.soomjae.core.presentation.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

inline fun <State, T> ViewModel.loadIfNeeded(
    stateFlow: MutableStateFlow<State>,
    crossinline isLoaded: (State) -> Boolean,
    crossinline setLoading: (State, Boolean) -> State,
    crossinline load: suspend () -> Either<DataFailure, T>,
    crossinline onSuccess: (State, T) -> State,
    crossinline onFailure: (State, DataFailure) -> State = { state, _ -> state },
) {
    val currentState = stateFlow.value
    if (isLoaded(currentState)) return

    viewModelScope.launch {
        stateFlow.update { state -> setLoading(state, true) }

        load().fold(
            ifLeft = { error ->
                stateFlow.update { state ->
                    onFailure(setLoading(state, false), error)
                }
            },
            ifRight = { data ->
                stateFlow.update { state ->
                    onSuccess(setLoading(state, false), data)
                }
            },
        )
    }
}
