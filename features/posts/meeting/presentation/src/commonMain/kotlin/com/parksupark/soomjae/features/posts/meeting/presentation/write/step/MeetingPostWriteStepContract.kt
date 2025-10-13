package com.parksupark.soomjae.features.posts.meeting.presentation.write.step

data class MeetingPostWriteStepState(
    val step: MeetingPostWriteStep = MeetingPostWriteStep.CONTENT,
)

enum class MeetingPostWriteStep {
    CONTENT,
    CREATE,
}
