package com.parksupark.soomjae.features.posts.meeting.presentation.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.common.coroutines.SoomjaeDispatcher
import com.parksupark.soomjae.features.posts.common.domain.repositories.CommentRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LikeRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.MeetingPostRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.ParticipationRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.meeting.presentation.detail.models.toMeetingPostDetailUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MeetingDetailViewModel(
    private val postId: Long,
    private val dispatcher: SoomjaeDispatcher,
    private val meetingPostRepository: MeetingPostRepository,
    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository,
    private val participationRepository: ParticipationRepository,
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<MeetingDetailState> =
        MutableStateFlow(MeetingDetailState.Loading)
    val stateFlow: StateFlow<MeetingDetailState> = _stateFlow.onStart {
        fetchMeetingPostDetails()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _stateFlow.value,
    )

    private fun fetchMeetingPostDetails() {
        viewModelScope.launch {
            meetingPostRepository.getMeetingPostDetail(postId).fold(
                ifLeft = { },
                ifRight = { postDetail ->
                    _stateFlow.update {
                        MeetingDetailState.Success(postDetail.toMeetingPostDetailUi())
                    }
                },
            )
        }
    }

    fun toggleLike() {
        val state = _stateFlow.value

        if (state !is MeetingDetailState.Success) return

        viewModelScope.launch {
            _stateFlow.update { state.copy(isLikeLoading = true) }

            if (state.postDetail.isLike) {
                likeRepository.unlike(postId)
            } else {
                likeRepository.like(postId)
            }

            _stateFlow.update { state.copy(isLikeLoading = false) }
        }
    }

    fun submitComment() {
        val state = _stateFlow.value

        if (state !is MeetingDetailState.Success) return

        viewModelScope.launch {
            _stateFlow.update { state ->
                if (state is MeetingDetailState.Success) {
                    state.copy(isCommentSubmitting = true)
                } else {
                    state
                }
            }

            commentRepository.addComment(
                postId = postId,
                content = state.inputCommentState.text.toString(),
            ).fold(
                ifLeft = {
                    // TODO: error handling
                },
                ifRight = { newComment ->
                    _stateFlow.update { state ->
                        if (state is MeetingDetailState.Success) {
                            state.copy(
                                postDetail = state.postDetail.copy(
                                    comments = (
                                        listOf(newComment.toUi()) +
                                            state.postDetail.comments
                                    ).toImmutableList(),
                                ),
                                inputCommentState = TextFieldState(),
                                isCommentSubmitting = false,
                            )
                        } else {
                            state
                        }
                    }
                },
            )
        }
    }

    fun toggleParticipation() {
        val state = _stateFlow.value

        if (state !is MeetingDetailState.Success) return

        if (state.postDetail.isUserJoined) {
            leaveMeeting()
        } else {
            joinMeeting()
        }
    }

    private fun joinMeeting() {
        val state = _stateFlow.value

        if (state !is MeetingDetailState.Success) return
        if (state.postDetail.isUserJoined) return
        if (state.isParticipationLoading) return

        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update { state ->
                if (state is MeetingDetailState.Success) {
                    state.copy(isParticipationLoading = true)
                } else {
                    state
                }
            }

            participationRepository.participate(postId).fold(
                ifLeft = {
                    _stateFlow.update { state ->
                        if (state is MeetingDetailState.Success) {
                            state.copy(isParticipationLoading = false)
                        } else {
                            state
                        }
                    }
                },
                ifRight = { updatedParticipation ->
                    _stateFlow.update { state ->
                        if (state is MeetingDetailState.Success) {
                            state.copy(
                                postDetail = state.postDetail.copy(
                                    isUserJoined = updatedParticipation.joined,
                                    currentParticipantCount = updatedParticipation.participantCount,
                                ),
                                isParticipationLoading = false,
                            )
                        } else {
                            state
                        }
                    }
                },
            )
        }
    }

    private fun leaveMeeting() {
        val state = _stateFlow.value

        if (state !is MeetingDetailState.Success) return
        if (!state.postDetail.isUserJoined) return
        if (state.isParticipationLoading) return

        viewModelScope.launch(dispatcher.io) {
            _stateFlow.update { state ->
                if (state is MeetingDetailState.Success) {
                    state.copy(isParticipationLoading = true)
                } else {
                    state
                }
            }

            participationRepository.deleteParticipation(postId).fold(
                ifLeft = {
                    // TODO: error handling
                    _stateFlow.update { state ->
                        if (state is MeetingDetailState.Success) {
                            state.copy(isParticipationLoading = false)
                        } else {
                            state
                        }
                    }
                },
                ifRight = { updatedParticipation ->
                    _stateFlow.update { state ->
                        if (state is MeetingDetailState.Success) {
                            state.copy(
                                postDetail = state.postDetail.copy(
                                    isUserJoined = updatedParticipation.joined,
                                    currentParticipantCount = updatedParticipation.participantCount,
                                ),
                                isParticipationLoading = false,
                            )
                        } else {
                            state
                        }
                    }
                },
            )
        }
    }
}
