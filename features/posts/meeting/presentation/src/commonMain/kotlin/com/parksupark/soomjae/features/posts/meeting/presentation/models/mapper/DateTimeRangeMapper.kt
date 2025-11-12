package com.parksupark.soomjae.features.posts.meeting.presentation.models.mapper

import com.parksupark.soomjae.features.posts.common.domain.usecase.ValidatePeriodUseCase
import com.parksupark.soomjae.features.posts.meeting.presentation.models.DateTimeRangeUi

internal fun DateTimeRangeUi.toValidatePeriodParam(
    changed: ValidatePeriodUseCase.ChangedField,
): ValidatePeriodUseCase.Input = ValidatePeriodUseCase.Input(
    startDate = this.startDate,
    startTime = this.startTime,
    endDate = this.endDate,
    endTime = this.endTime,
    changed = changed,
)

internal fun ValidatePeriodUseCase.Result.toDateTimeRangeUi(isAllDay: Boolean): DateTimeRangeUi =
    DateTimeRangeUi(
        startDate = this.startDate,
        startTime = this.startTime,
        endDate = this.endDate,
        endTime = this.endTime,
        isAllDay = isAllDay,
    )
