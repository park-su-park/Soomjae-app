package com.parksupark.soomjae.core.common.utils

import arrow.core.Either

fun <A, B> Either<A, B>.mapToEmpty(): Either<A, Unit> = map { }

inline fun <A, B, C> Either<A, B>.fold(
    ifLeft: (left: A) -> C,
    ifRight: (right: B) -> C,
    finally: () -> Unit,
): C {
    val result = fold(
        ifLeft = ifLeft,
        ifRight = ifRight,
    )
    finally()

    return result
}
