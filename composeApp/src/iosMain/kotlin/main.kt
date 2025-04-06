import androidx.compose.ui.window.ComposeUIViewController
import com.parksupark.soomjae.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
