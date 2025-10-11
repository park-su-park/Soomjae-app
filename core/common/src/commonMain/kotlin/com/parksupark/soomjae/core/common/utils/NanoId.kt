package com.parksupark.soomjae.core.common.utils

import kotlin.random.Random

object NanoId {
    private const val DEFAULT_ALPHABET =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_"
    private const val DEFAULT_SIZE = 21

    fun generate(
        alphabet: String = DEFAULT_ALPHABET,
        size: Int = DEFAULT_SIZE,
    ): String {
        require(alphabet.isNotEmpty()) { "Alphabet must not be empty." }
        require(size > 0) { "Size must be greater than zero." }

        val builder = StringBuilder(size)
        val random = Random.Default

        repeat(size) {
            val index = random.nextInt(alphabet.length)
            builder.append(alphabet[index])
        }

        return builder.toString()
    }
}
