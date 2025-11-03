import com.parksupark.soomjae.features.auth.libs.google.di.authenticators.GoogleAuthService

class IosGoogleAuthService : GoogleAuthService {
    override fun signInWithScope(
        scope: List<String>,
        onComplete: (
            idToken: String?,
            accessToken: String?,
            email: String?,
            name: String?,
            photoUrl: String?,
            serverAuthCode: String?,
            error: String?,
        ) -> Unit,
    ) {
        nativeIosAnalyticsProvider?.signInWithScope(
            scope = scope,
            onComplete = onComplete,
        )
    }
}

interface SwiftIosGoogleAuthProtocol {
    fun signInWithScope(
        scope: List<String>,
        onComplete: (
            idToken: String?,
            accessToken: String?,
            email: String?,
            name: String?,
            photoUrl: String?,
            serverAuthCode: String?,
            error: String?,
        ) -> Unit,
    )
}

private var nativeIosAnalyticsProvider: SwiftIosGoogleAuthProtocol? = null

fun setIosAnalyticsProvider(provider: SwiftIosGoogleAuthProtocol) {
    nativeIosAnalyticsProvider = provider
}
