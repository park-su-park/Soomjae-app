package com.parksupark.soomjae.features.posts.common.presentation.utils

import androidx.compose.runtime.snapshotFlow
import com.mohamedrejeb.richeditor.model.RichTextState

fun RichTextState.collectAsHtmlFlow() = snapshotFlow { this.toHtml() }
