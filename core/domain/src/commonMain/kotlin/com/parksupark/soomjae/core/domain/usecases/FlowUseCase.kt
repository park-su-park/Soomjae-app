package com.parksupark.soomjae.core.domain.usecases

import arrow.core.Either
import arrow.core.Option
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.failures.Failure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn

@OptIn(ExperimentalCoroutinesApi::class)
abstract class FlowUseCase<in P, out R> internal constructor(
    private val dispatcher: SoomjaeDispatcher,
) {
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    protected open val isShared: Boolean = true

    val flow: Flow<R> = paramState
        .distinctUntilChanged()
        .flatMapLatest { params ->
            createFlow(params)
                .flowOn(dispatcher.io)
                .distinctUntilChanged()
        }.maybeShare()

    protected abstract fun createFlow(params: P): Flow<R>

    suspend operator fun invoke(params: P) {
        paramState.emit(params)
    }

    private fun <T> Flow<T>.maybeShare() = when (isShared) {
        false -> this
        true -> shareIn(
            scope = dispatcher,
            started = SharingStarted.WhileSubscribed(SUBSCRIPTION_DURATION),
            replay = 1,
        )
    }
}

suspend operator fun <R> FlowUseCase<Unit, R>.invoke() {
    invoke(Unit)
}

abstract class FlowEitherUseCase<in P, out R>(dispatcher: SoomjaeDispatcher) :
    FlowUseCase<P, Either<Failure, R>>(dispatcher)

abstract class FlowOptionUseCase<in P, out R>(dispatcher: SoomjaeDispatcher) :
    FlowUseCase<P, Option<R>>(dispatcher)

private const val SUBSCRIPTION_DURATION = 500L
