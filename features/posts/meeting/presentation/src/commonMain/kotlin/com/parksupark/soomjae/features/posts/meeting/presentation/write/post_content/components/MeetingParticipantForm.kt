package com.parksupark.soomjae.features.posts.meeting.presentation.write.post_content.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeIcon
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeOutlinedTextField
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeToggleSwitch
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.common.presentation.components.WriteSelectionLayout
import com.parksupark.soomjae.features.posts.meeting.presentation.models.ParticipantLimitUi
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.Res
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_additional_info
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_display
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_label
import com.parksupark.soomjae.features.posts.meeting.presentation.resources.meeting_create_participant_count_textfield_hint
import com.parksupark.soomjae.features.posts.meeting.presentation.write.MeetingPostWriteAction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun MeetingParticipantForm(
    participantLimit: ParticipantLimitUi,
    onAction: (MeetingPostWriteAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val requester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    WriteSelectionLayout(
        modifier = modifier.fillMaxWidth()
            .background(SoomjaeTheme.colorScheme.background1)
            .bringIntoViewRequester(requester),
        header = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(Res.string.meeting_create_participant_count_label.value)

                UnlimitedParticipantsToggleSwitch(
                    isUnlimited = participantLimit.isUnlimited,
                    onToggle = {
                        onAction(MeetingPostWriteAction.OnParticipantLimitToggled(it))
                    },
                )
            }
        },
        content = {
            AnimatedVisibility(visible = participantLimit.isLimited) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MeetingParticipantCountTextField(
                        state = participantLimit.limitCount,
                        onFocusedChange = { isFocused ->
                            if (isFocused) {
                                coroutineScope.launch {
                                    delay(300)
                                    requester.bringIntoView()
                                }
                            }
                        },
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        SoomjaeIcon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = SoomjaeTheme.colorScheme.text3,
                        )
                        Text(
                            Res.string.meeting_create_participant_count_additional_info.value,
                            style = SoomjaeTheme.typography.labelM.copy(),
                            color = SoomjaeTheme.colorScheme.text3,
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun UnlimitedParticipantsToggleSwitch(
    isUnlimited: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = { onToggle(!isUnlimited) },
        ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "제한 없음",
            style = SoomjaeTheme.typography.labelM.copy(
                color = SoomjaeTheme.colorScheme.text2,
            ),
        )
        SoomjaeToggleSwitch(
            toggled = isUnlimited,
            onChangeToggle = onToggle,
            interactionSource = interactionSource,
        )
    }
}

@Composable
private fun MeetingParticipantCountTextField(
    state: TextFieldState,
    onFocusedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val participantCountText = Res.string.meeting_create_participant_count_display.value

    var isFocused by remember { mutableStateOf(false) }

    if (isFocused) {
        LaunchedEffect(Unit) {
            state.edit {
                selection = TextRange(0, originalText.length)
            }
        }
    }

    SoomjaeOutlinedTextField(
        state = state,
        modifier = modifier.fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                onFocusedChange(focusState.isFocused)
            },
        hint = Res.string.meeting_create_participant_count_textfield_hint.value,
        startIcon = Icons.Outlined.Person,
        keyboardType = KeyboardType.Number,
        inputTransformation = {
            if (originalText.startsWith("0")) {
                replace(0, 1, "")
            }

            val newText = originalText.filter { it.isDigit() }
            if (newText.length != originalText.length) {
                replace(0, originalText.length, newText)
            }
        },
        outputTransformation = {
            if (!isFocused) {
                val num = originalText.toString().toLongOrNull()

                if (num != null) {
                    val text = participantCountText.replace($$"%1$d", num.toString())
                    replace(
                        start = 0,
                        end = originalText.length,
                        text = text,
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun MeetingParticipantFormPreview() {
    AppTheme {
        MeetingParticipantForm(
            participantLimit = ParticipantLimitUi(),
            onAction = { },
        )
    }
}
