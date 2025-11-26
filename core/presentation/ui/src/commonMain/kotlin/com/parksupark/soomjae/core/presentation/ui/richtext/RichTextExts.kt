package com.parksupark.soomjae.core.presentation.ui.richtext

import com.mohamedrejeb.richeditor.model.RichTextState

fun RichTextState.insertImageAtCursor(imageUrl: String) {
    val imgTag = "<img src=\"$imageUrl\"/>"
    insertHtmlAfterSelection(imgTag)
}
