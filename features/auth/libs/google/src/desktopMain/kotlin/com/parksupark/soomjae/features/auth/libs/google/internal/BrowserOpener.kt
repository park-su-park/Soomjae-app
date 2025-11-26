package com.parksupark.soomjae.features.auth.libs.google.internal

import java.awt.Desktop
import java.net.URI

internal class BrowserOpener {
    fun openUrl(url: String) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI(url))
        } else {
            throw UnsupportedOperationException("Desktop is not supported on this platform.")
        }
    }
}
