package com.parksupark.soomjae.core.common.utils

import arrow.core.Either

fun <A, B> Either<A, B>.mapToEmpty(): Either<A, Unit> = map { }
