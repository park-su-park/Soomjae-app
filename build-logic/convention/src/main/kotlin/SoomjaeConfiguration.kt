import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget as KtJvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion as KtVersion

internal object SoomjaeConfiguration {
    const val COMPILE_SDK = 36
    const val BUILD_TOOLS = "36.0.0"
    const val PACKAGE_NAME = "com.parksupark.soomjae"
    const val MIN_SDK = 21
    const val TARGET_SDK = 36
    const val VERSION_NAME = "0.0.1"
    const val VERSION_CODE = 1

    val UseJavaVersion = JavaVersion.VERSION_21
    val JvmTarget = KtJvmTarget.fromTarget(UseJavaVersion.toString())
    val JvmTargetStr = JvmTarget.target
    val KotlinVersion = KtVersion.KOTLIN_2_1
}
