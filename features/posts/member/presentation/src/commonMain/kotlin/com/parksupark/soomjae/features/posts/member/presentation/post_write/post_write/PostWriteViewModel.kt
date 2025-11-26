package com.parksupark.soomjae.features.posts.member.presentation.post_write.post_write

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import com.parksupark.soomjae.features.posts.member.presentation.post_write.MemberPostWriteScreenType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "PostWriteViewModel"

class PostWriteViewModel : ViewModel() {
    private val _stateFlow: MutableStateFlow<PostWriteState> = MutableStateFlow(PostWriteState())
    val stateFlow: StateFlow<PostWriteState> = _stateFlow.asStateFlow()

    fun moveToNextScreen() {
        val currentScreen = _stateFlow.value.currentScreen
        val currentIndex = MemberPostWriteScreenType.entries.indexOf(currentScreen)

        if (currentIndex == -1) {
            Logger.e(TAG) { "Unknown screen: $currentScreen" }
            return
        }

        val nextIndex = currentIndex + 1
        if (nextIndex < MemberPostWriteScreenType.entries.size) {
            val nextScreen = MemberPostWriteScreenType.entries[nextIndex]
            _stateFlow.update { it.copy(currentScreen = nextScreen) }
        } else {
            Logger.i(TAG) { "Already at the last screen: $currentScreen" }
        }
    }

    fun moveToPrevScreen() {
        val currentScreen = _stateFlow.value.currentScreen
        val currentIndex = MemberPostWriteScreenType.entries.indexOf(currentScreen)

        if (currentIndex == -1) {
            Logger.e(TAG) { "Unknown screen: $currentScreen" }
            return
        }

        val prevIndex = currentIndex - 1
        if (prevIndex >= 0) {
            val prevScreen = MemberPostWriteScreenType.entries[prevIndex]
            _stateFlow.update { it.copy(currentScreen = prevScreen) }
        } else {
            Logger.i(TAG) { "Already at the first screen: $currentScreen" }
        }
    }
}
