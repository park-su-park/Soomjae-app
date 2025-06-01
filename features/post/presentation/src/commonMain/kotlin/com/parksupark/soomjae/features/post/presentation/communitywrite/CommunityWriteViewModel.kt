package com.parksupark.soomjae.features.post.presentation.communitywrite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parksupark.soomjae.core.presentation.ui.errors.asUiText
import com.parksupark.soomjae.core.presentation.ui.utils.collectAsFlow
import com.parksupark.soomjae.features.post.domain.repositories.CategoryRepository
import com.parksupark.soomjae.features.post.domain.repositories.CommunityRepository
import com.parksupark.soomjae.features.post.presentation.models.toUi
import com.parksupark.soomjae.features.post.presentation.utils.collectAsHtmlFlow
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

internal class CommunityWriteViewModel(
    private val communityRepository: CommunityRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _uiStateFlow: MutableStateFlow<CommunityWriteState> =
        MutableStateFlow(CommunityWriteState())
    val uiStateFlow: StateFlow<CommunityWriteState> = _uiStateFlow.onStart {
        loadCategories()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _uiStateFlow.value,
    )

    private val _eventChannel = Channel<CommunityWriteEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        uiStateFlow.value.inputTitle.collectAsFlow().onEach { title ->
            val isTitleValid = title.toString().isNotBlank()
            _uiStateFlow.update {
                it.copy(isTitleValid = isTitleValid)
            }
        }.launchIn(viewModelScope)

        uiStateFlow.value.inputContent.collectAsHtmlFlow().onEach { content ->
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

            val title = uiStateFlow.value.inputTitle.text.toString().trim()
            val content = uiStateFlow.value.inputContent.toHtml().trim()
            val categoryId = uiStateFlow.value.selectedCategory?.id ?: return@launch

            communityRepository.createPost(
                title = title,
                content = content,
                categoryId = categoryId,
            ).fold(
                ifLeft = {
                    _eventChannel.send(CommunityWriteEvent.Error(it.asUiText()))
                },
                ifRight = {
                    _eventChannel.send(CommunityWriteEvent.PostSubmitted(it.id))
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
                    _eventChannel.send(CommunityWriteEvent.Error(it.asUiText()))
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
}
