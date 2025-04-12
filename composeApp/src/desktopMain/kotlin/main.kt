import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.parksupark.soomjae.Soomjae
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import java.awt.Dimension

fun main() = application {
    Window(
        title = "SoomJae",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        DevelopmentEntryPoint {
            Soomjae()
        }
    }
}

@Preview
@Composable
private fun SoomjaePreview() {
    Soomjae()
}
