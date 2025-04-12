import androidx.compose.ui.window.ComposeUIViewController
import com.parksupark.soomjae.Soomjae
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { Soomjae() }
