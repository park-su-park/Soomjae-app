package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.mapTextFieldState
import com.parksupark.soomjae.features.posts.common.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LocationRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toLocationUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.community.domain.repository.CommunityPostRepository
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityDestination
import com.parksupark.soomjae.features.posts.community.presentation.write.CommunityWriteState.WriteMode
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunityWriteViewModel(
    savedStateHandle: SavedStateHandle,
    private val communityRepository: CommunityPostRepository,
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    val postId: Long? = savedStateHandle.toRoute<CommunityDestination.CommunityWrite>().postID

    private val _uiStateFlow: MutableStateFlow<CommunityWriteState> = MutableStateFlow(
        CommunityWriteState(
            mode = if (postId != null) WriteMode.Edit(postId, null) else WriteMode.Create,
        ),
    )
    val uiStateFlow: StateFlow<CommunityWriteState> = _uiStateFlow.onStart {
        loadContentIfNeeded()
        loadCategories()
        loadLocations()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _uiStateFlow.value,
    )

    private val eventChannel = Channel<CommunityWriteEvent>()
    internal val events = eventChannel.receiveAsFlow()

    init {
        uiStateFlow.mapTextFieldState { it.inputTitle }.onEach { title ->
            val isTitleValid = title.toString().isNotBlank()
            _uiStateFlow.update {
                it.copy(isTitleValid = isTitleValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.mapTextFieldState { it.inputContent }.onEach { content ->
            val isContentValid = content.isNotBlank()
            _uiStateFlow.update {
                it.copy(isContentValid = isContentValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.distinctUntilChanged { old, new ->
            old.isTitleValid == new.isTitleValid && old.isContentValid == new.isContentValid &&
                old.isSubmitting == new.isSubmitting
        }.map {
            val canSubmit = it.isTitleValid && it.isContentValid && !it.isSubmitting
            canSubmit
        }.onEach { canSubmit ->
            _uiStateFlow.update { it.copy(canSubmit = canSubmit) }
        }.launchIn(viewModelScope)
    }

    @OptIn(ExperimentalTime::class)
    fun submitPost() {
        val state = uiStateFlow.value
        if (!state.canSubmit) return

        val title = state.inputTitle.text.trim().toString()
        val content = state.inputContent.text.trim().toString()
        val categoryId = state.selectedCategory?.id
        val locationCode = state.selectedLocation?.code

        when (val mode = state.mode) {
            WriteMode.Create -> viewModelScope.launch {
                _uiStateFlow.update { it.copy(isSubmitting = true) }

                communityRepository.createPost(
                    title = title,
                    content = content,
                    categoryId = categoryId,
                    locationCode = locationCode,
                ).fold(
                    ifLeft = {
                        eventChannel.send(CommunityWriteEvent.PostError(it.asUiText()))
                    },
                    ifRight = {
                        eventChannel.send(CommunityWriteEvent.PostSubmitted(it.id))
                    },
                )

                _uiStateFlow.update { it.copy(isSubmitting = false) }
            }

            is WriteMode.Edit -> viewModelScope.launch {
                _uiStateFlow.update { it.copy(isSubmitting = true) }

                mode.originalPost?.post?.copy(
                    title = title,
                    content = content,
                    categoryName = state.selectedCategory?.name,
                    locationName = state.selectedLocation?.name,
                )?.let { editedPost ->
                    communityRepository.editPost(
                        editedPost = editedPost,
                    ).fold(
                        ifLeft = {
                            eventChannel.send(CommunityWriteEvent.PostError(it.asUiText()))
                        },
                        ifRight = {
                            eventChannel.send(CommunityWriteEvent.PostSubmitted(it.id))
                        },
                    )
                }

                _uiStateFlow.update { it.copy(isSubmitting = false) }
            }
        }
    }

    fun selectCategory(categoryId: Long) {
        val category = uiStateFlow.value.categories.find { it.id == categoryId } ?: return
        _uiStateFlow.update { it.copy(selectedCategory = category) }
    }

    fun selectLocation(locationCode: Long) {
        val location = uiStateFlow.value.locations.find { it.code == locationCode } ?: return
        _uiStateFlow.update { it.copy(selectedLocation = location) }
    }

    private fun loadContentIfNeeded() {
        val state = _uiStateFlow.value
        if (state.mode !is WriteMode.Edit) return

        viewModelScope.launch {
            communityRepository.getPostDetails(state.mode.postId).fold(
                ifLeft = {
                    Logger.e(TAG) { "Failed to load post content: $it" }
                    eventChannel.send(CommunityWriteEvent.PostError(it.asUiText()))
                },
                ifRight = { postDetail ->
                    _uiStateFlow.update { writeState ->
                        writeState.copy(
                            mode = WriteMode.Edit(
                                postId = postDetail.post.id,
                                originalPost = postDetail,
                            ),
                            inputTitle = TextFieldState(postDetail.post.title),
                            isTitleValid = postDetail.post.title.isNotBlank(),
                            inputContent = TextFieldState(postDetail.post.content),
                            isContentValid = postDetail.post.content.isNotBlank(),
                            selectedCategory = writeState.categories.find { categoryUi ->
                                categoryUi.name == postDetail.post.categoryName
                            },
                            selectedLocation = writeState.locations.find { locationUi ->
                                locationUi.name == postDetail.post.locationName
                            },
                        )
                    }
                },
            )
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().fold(
                ifLeft = {
                    Logger.e(TAG) { "Failed to load categories: $it" }
                    eventChannel.send(CommunityWriteEvent.CategoryError(it.asUiText()))
                },
                ifRight = { categories ->
                    val categoryUiList = categories.map { it.value.toUi() }.toPersistentList()
                    _uiStateFlow.update {
                        it.copy(
                            categories = categoryUiList,
                            isCategoryLoading = false,
                        )
                    }
                },
            )
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            locationRepository.getAllLocations().fold(
                ifLeft = {
                    Logger.e(TAG) { "Failed to load locations: $it" }
                    eventChannel.send(CommunityWriteEvent.LocationError(it.asUiText()))
                },
                ifRight = { locations ->
                    val locationUis = locations.map { it.toLocationUi() }.toPersistentList()
                    _uiStateFlow.update { it.copy(locations = locationUis) }
                },
            )
        }
    }
}

private const val TAG = "CommunityWriteViewModel"
