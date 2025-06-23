package com.parksupark.soomjae.features.post.presentation.utils

import androidx.compose.runtime.snapshotFlow
import com.mohamedrejeb.richeditor.model.RichTextState

internal fun RichTextState.collectAsHtmlFlow() = snapshotFlow { this.toHtml() }
