package com.parksupark.soomjae.features.profile.presentation.profile_edit.model.mapper

import com.parksupark.soomjae.features.profile.domain.model.NicknameValidationResult

internal val NicknameValidationResult.message: String?
    get() = when (this) {
        is NicknameValidationResult.Invalid -> when (reason) {
            NicknameValidationResult.Invalid.Reason.AlreadyTaken -> "이미 사용 중인 닉네임입니다."
            NicknameValidationResult.Invalid.Reason.InvalidFormat -> "닉네임 형식이 올바르지 않습니다."
            NicknameValidationResult.Invalid.Reason.Unknown -> "알 수 없는 오류가 발생했습니다."
        }

        else -> null
    }
