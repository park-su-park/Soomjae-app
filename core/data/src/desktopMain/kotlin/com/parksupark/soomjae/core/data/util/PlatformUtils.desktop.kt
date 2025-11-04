package com.parksupark.soomjae.core.data.util

actual object PlatformUtils {
    actual fun getOSName(): String = System.getProperty("os.name")
}
