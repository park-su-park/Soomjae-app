package com.parksupark.soomjae.core.domain.usecases

import arrow.core.Either
import arrow.core.Option
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.core.domain.failures.Failure
import kotlinx.coroutines.withContext

abstract class UseCase<in P, out R> internal constructor(private val dispatcher: SoomjaeDispatcher) {

    abstract suspend fun run(params: P): R

    suspend operator fun invoke(params: P): R = withContext(dispatcher.io) {
        run(params)
    }
}

abstract class EitherUseCase<in P, out R>(dispatcher: SoomjaeDispatcher) :
    UseCase<P, Either<Failure, R>>(dispatcher)

abstract class OptionUseCase<in P, out R>(dispatcher: SoomjaeDispatcher) :
    UseCase<P, Option<R>>(dispatcher)

suspend operator fun <R> UseCase<Unit, R>.invoke(): R = invoke(Unit)
