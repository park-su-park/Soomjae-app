package com.parksupark.soomjae.features.auth.domain

class UserDataValidator {
    fun isValidEmail(email: String): Boolean = EMAIL_ADDRESS_REGEX.matches(email)

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCaseCharacter = password.any { it.isLowerCase() }
        val hasUpperCaseCharacter = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasDigit,
            hasLowerCaseCharacter = hasLowerCaseCharacter,
            hasUpperCaseCharacter = hasUpperCaseCharacter,
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9

        val EMAIL_ADDRESS_REGEX = Regex(
            pattern =
                buildString {
                    append("""[a-zA-Z0-9\+\.\_\%\-\+]{1,256}""")
                    append("""\@""")
                    append("""[a-zA-Z0-9][a-zA-Z0-9\-]{0,64}""")
                    append("""(""")
                    append("""\.""")
                    append("""[a-zA-Z0-9][a-zA-Z0-9\-]{0,25}""")
                    append(""")+""")
                },
        )
    }
}
