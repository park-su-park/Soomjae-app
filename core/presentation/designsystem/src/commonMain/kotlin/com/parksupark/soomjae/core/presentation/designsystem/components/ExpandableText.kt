@file:Suppress("ktlint:standard:max-line-length")

package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.resources.Res
import com.parksupark.soomjae.core.presentation.designsystem.resources.expandable_text_show_less
import com.parksupark.soomjae.core.presentation.designsystem.resources.expandable_text_show_more
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import kotlin.math.max
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Stable
class ExpandableTextState internal constructor(initialExpanded: Boolean) {
    var expanded by mutableStateOf(initialExpanded)
        internal set

    fun toggle() {
        expanded = !expanded
    }
}

@Composable
fun rememberExpandableTextState(initialExpanded: Boolean = false): ExpandableTextState {
    val saveable = rememberSaveable { mutableStateOf(initialExpanded) }
    val state = remember { ExpandableTextState(saveable.value) }
    LaunchedEffect(state.expanded) { saveable.value = state.expanded }
    return state
}

@OptIn(ExperimentalTextApi::class)
private class ExpandCutCalculator(
    private val measurer: TextMeasurer,
    private val style: TextStyle,
) {
    fun findBestCutIndex(
        text: String,
        maxWidthPx: Int,
        collapsedLines: Int,
        expandText: String,
        includeEllipsis: Boolean,
        expandStyle: SpanStyle,
    ): Int {
        if (maxWidthPx <= 0) return 0
        var low = 0
        var high = text.length
        var best = 0
        while (low <= high) {
            val mid = (low + high) / 2
            val candidate = buildCollapsedAnnotated(
                text = text,
                idx = mid,
                expandText = expandText,
                includeEllipsis = includeEllipsis,
                expandStyle = expandStyle,
            )
            val layout = measurer.measure(
                text = candidate,
                style = style,
                maxLines = collapsedLines,
                softWrap = true,
                overflow = TextOverflow.Clip,
                constraints = Constraints(maxWidth = maxWidthPx),
            )
            if (layout.hasVisualOverflow) {
                high = mid - 1
            } else {
                best = mid
                low = mid + 1
            }
        }
        return best
    }

    private fun buildCollapsedAnnotated(
        text: String,
        idx: Int,
        expandText: String,
        includeEllipsis: Boolean,
        expandStyle: SpanStyle,
    ): AnnotatedString = buildAnnotatedString {
        append(text.substring(0, idx).trimEnd())
        if (includeEllipsis) append("…")
        withStyle(expandStyle) { append(expandText) }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    state: ExpandableTextState = rememberExpandableTextState(),
    collapsedLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    expandText: String = stringResource(Res.string.expandable_text_show_more),
    collapseText: String = stringResource(Res.string.expandable_text_show_less),
    expandTextStyle: SpanStyle = SoomjaeTheme.typography.captionS.copy(
        color = SoomjaeTheme.colorScheme.text3,
    ).toSpanStyle(),
    includeEllipsis: Boolean = true,
) {
    require(collapsedLines >= 1)

    val measurer = rememberTextMeasurer()
    val calculator = remember { ExpandCutCalculator(measurer, style) }

    var canExpand by remember(text) { mutableStateOf(false) }
    var cutIndex by remember(text, collapsedLines) { mutableStateOf<Int?>(null) }
    var measuredWidthPx by remember { mutableStateOf(0) }

    LaunchedEffect(state.expanded, text, collapsedLines) {
        if (!state.expanded) cutIndex = null
    }

    val content = remember(text, state.expanded, canExpand, cutIndex) {
        buildAnnotatedString {
            if (state.expanded || !canExpand) {
                append(text)
                if (canExpand && state.expanded) {
                    append(" ")
                    withStyle(expandTextStyle) { append(collapseText) }
                }
            } else {
                val idx = cutIndex
                if (idx == null) {
                    append(text)
                } else {
                    append(text.substring(0, idx).trimEnd())
                    if (includeEllipsis) append("…")
                    withStyle(expandTextStyle) { append(expandText) }
                }
            }
        }
    }

    Text(
        text = content,
        modifier = modifier
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            )
            .semantics(mergeDescendants = true) {
                onClick(
                    label = if (state.expanded) "Collapse" else "Expand",
                    action = {
                        state.toggle()
                        true
                    },
                )
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { state.toggle() },
            ),
        style = style,
        maxLines = if (state.expanded) Int.MAX_VALUE else collapsedLines,
        overflow = TextOverflow.Clip,
        onTextLayout = { result ->
            measuredWidthPx = result.size.width

            if (!state.expanded && cutIndex == null) {
                if (result.hasVisualOverflow) {
                    canExpand = true

                    val endOfLine = result.getLineEnd(collapsedLines - 1, visibleEnd = true)
                    val firstNewline = text.indexOf('\n')
                    val initialEnd = when {
                        firstNewline in 1 until endOfLine -> firstNewline
                        else -> endOfLine
                    }

                    val approxReserve = (if (includeEllipsis) 1 else 0) + expandText.length
                    val initialHigh = max(0, initialEnd - approxReserve)

                    val best = calculator.findBestCutIndex(
                        text = text,
                        maxWidthPx = measuredWidthPx,
                        collapsedLines = collapsedLines,
                        expandText = expandText,
                        includeEllipsis = includeEllipsis,
                        expandStyle = expandTextStyle,
                    ).coerceAtMost(initialHigh)

                    cutIndex = best
                } else {
                    canExpand = false
                }
            }

            if (!state.expanded && canExpand && cutIndex != null && result.hasVisualOverflow) {
                val best = calculator.findBestCutIndex(
                    text = text,
                    maxWidthPx = measuredWidthPx,
                    collapsedLines = collapsedLines,
                    expandText = expandText,
                    includeEllipsis = includeEllipsis,
                    expandStyle = expandTextStyle,
                )
                cutIndex = best
            }
        },
    )
}

@Preview
@Composable
private fun ExpandableTextPreview_SingleLine() {
    AppTheme {
        ExpandableText(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            collapsedLines = 1,
        )
    }
}

@Preview
@Composable
private fun ExpandableTextPreview_MultiLine() {
    AppTheme {
        ExpandableText(
            text =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            collapsedLines = 2,
        )
    }
}
