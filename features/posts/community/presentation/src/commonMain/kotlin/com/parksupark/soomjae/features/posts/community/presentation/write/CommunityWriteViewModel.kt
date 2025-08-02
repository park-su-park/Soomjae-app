package com.parksupark.soomjae.features.posts.community.presentation.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.posts.common.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.posts.common.domain.repositories.LocationRepository
import com.parksupark.soomjae.features.posts.common.presentation.models.toLocationUi
import com.parksupark.soomjae.features.posts.common.presentation.models.toUi
import com.parksupark.soomjae.features.posts.community.domain.repositories.CommunityRepository
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
    private val communityRepository: CommunityRepository,
    private val categoryRepository: CategoryRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<CommunityWriteState> =
        MutableStateFlow(CommunityWriteState())
    internal val uiStateFlow: StateFlow<CommunityWriteState> = _uiStateFlow.onStart {
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
        uiStateFlow.value.inputTitle.collectAsFlow().onEach { title ->
            val isTitleValid = title.toString().isNotBlank()
            _uiStateFlow.update {
                it.copy(isTitleValid = isTitleValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.value.inputContent.collectAsFlow().onEach { content ->
            val isContentValid = content.isNotBlank()
            _uiStateFlow.update {
                it.copy(isContentValid = isContentValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.distinctUntilChanged { old, new ->
            old.isTitleValid == new.isTitleValid &&
                old.isContentValid == new.isContentValid &&
                old.isSubmitting == new.isSubmitting &&
                old.selectedCategory == new.selectedCategory
        }.map {
            val canSubmit = it.isTitleValid && it.isContentValid && !it.isSubmitting && it.selectedCategory != null
            canSubmit
        }.onEach { canSubmit ->
            _uiStateFlow.update { it.copy(canSubmit = canSubmit) }
        }.launchIn(viewModelScope)
    }

    fun submitPost() {
        if (!uiStateFlow.value.canSubmit) return

        viewModelScope.launch {
            _uiStateFlow.update { it.copy(isSubmitting = true) }

            val title = uiStateFlow.value.inputTitle.text.trim().toString()
            val content = uiStateFlow.value.inputContent.text.trim().toString()
            val categoryId = uiStateFlow.value.selectedCategory?.id ?: return@launch

            communityRepository.createPost(
                title = title,
                content = content,
                categoryId = categoryId,
            ).fold(
                ifLeft = {
                    eventChannel.send(CommunityWriteEvent.CategoryError(it.asUiText()))
                },
                ifRight = {
                    eventChannel.send(CommunityWriteEvent.PostSubmitted(it.id))
                },
            )

            _uiStateFlow.update { it.copy(isSubmitting = false) }
        }
    }

    fun selectCategory(categoryId: Long) {
        val category = uiStateFlow.value.categories.find { it.id == categoryId } ?: return
        _uiStateFlow.update { it.copy(selectedCategory = category) }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().fold(
                ifLeft = {
                    Logger.e(TAG) { "Failed to load categories: $it" }
                    eventChannel.send(CommunityWriteEvent.LocationError(it.asUiText()))
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
                    eventChannel.send(CommunityWriteEvent.CategoryError(it.asUiText()))
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
