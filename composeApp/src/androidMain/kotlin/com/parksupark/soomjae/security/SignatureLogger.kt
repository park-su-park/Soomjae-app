package com.parksupark.soomjae.security

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import co.touchlab.kermit.Logger
import java.security.MessageDigest

private const val TAG = "AppSignature"

object SignatureLogger {

    fun logAppSignatures(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            Logger.w(TAG) { "App signature logging requires API level 28 or higher." }
            return
        }

        runCatching {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNING_CERTIFICATES,
            )

            val signatures = packageInfo.signingInfo?.apkContentsSigners ?: emptyArray()
            signatures.forEach { signature ->
                val byteArray = signature.toByteArray()
                val sha1 = byteArray.toHex("SHA-1")
                val sha256 = byteArray.toHex("SHA-256")

                Logger.d(TAG) { "SHA-1: $sha1" }
                Logger.d(TAG) { "SHA-256: $sha256" }
            }
        }.onFailure {
            Logger.e(TAG, it) { "Failed to read app signatures" }
        }
    }

    private fun ByteArray.toHex(algorithm: String): String {
        val digest = MessageDigest.getInstance(algorithm).digest(this)
        return digest.joinToString(":") { b -> "%02X".format(b) }
    }
}
