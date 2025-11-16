package com.parksupark.soomjae.core.presentation.ui.textfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.forEachChangeReversed
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.substring

/**
 * An [InputTransformation] that restricts password input to a specific set of allowed characters.
 *
 * Allowed characters:
 * - Uppercase and lowercase English letters (A-Z, a-z)
 * - Digits (0-9)
 * - Special characters: ! @ # $ % ^ & * ( ) - _ = + [ ] { } ; : ' " , . < > / ? \ |
 *
 * The restriction is enforced via the regular expression: [A-Za-z0-9!@#$%^&*()\-_=+\[\]{};:'",.<>/?\\|]
 *
 * Rationale:
 * - Ensures passwords contain only commonly accepted characters for security and compatibility.
 * - Prevents accidental inclusion of unsupported or problematic characters.
 * - Helps meet typical password policy requirements for complexity and character diversity.
 */
object PasswordAllowedCharactersTransformation : InputTransformation {
    override val keyboardOptions: KeyboardOptions =
        KeyboardOptions(keyboardType = KeyboardType.Password)

    private val allowedCharRegex = Regex("""[A-Za-z0-9!@#$%^&*()\-_=+\[\]{};:'",.<>/?\\|]""")

    @OptIn(ExperimentalFoundationApi::class)
    override fun TextFieldBuffer.transformInput() {
        changes.forEachChangeReversed { range, _ ->
            val newText = asCharSequence().substring(range)
            val filtered = newText.filter { allowedCharRegex.matches(it.toString()) }

            if (newText != filtered) {
                replace(range.start, range.end, filtered)
            }
        }
    }
}
