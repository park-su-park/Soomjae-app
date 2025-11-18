package com.parksupark.soomjae.features.profile.domain.model

sealed class NicknameValidationResult {

    data object Idle : NicknameValidationResult()

    data object Validating : NicknameValidationResult()

    data object Valid : NicknameValidationResult()

    data class Invalid(val reason: Reason) : NicknameValidationResult() {

        enum class Reason { AlreadyTaken, InvalidFormat, Unknown }
    }
}

val NicknameValidationResult.isValid: Boolean
    get() = this is NicknameValidationResult.Valid || this is NicknameValidationResult.Idle
