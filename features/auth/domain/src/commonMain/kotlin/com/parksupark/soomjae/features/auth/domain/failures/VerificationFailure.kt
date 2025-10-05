package com.parksupark.soomjae.features.auth.domain.failures

import com.parksupark.soomjae.core.domain.failures.Failure
import com.parksupark.soomjae.core.domain.failures.DataFailure as CoreDataFailure

sealed interface VerificationFailure : Failure {
    data object InvalidCode : VerificationFailure

    data class DataFailure(val error: CoreDataFailure) : VerificationFailure
}
