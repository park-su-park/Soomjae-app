package com.parksupark.soomjae.core.presentation.designsystem.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun SoomjaeCircularProgressIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    strokeWidth: Dp = 6.dp,
    colorStops: Array<Pair<Float, Color>> = SoomjaeCircularProgressIndicatorDefaults.colorStops,
    animationDuration: Duration = 1.seconds,
) {
    val transition = rememberInfiniteTransition()
    val animatedProgress by transition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration.inWholeMilliseconds.toInt(),
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "CircularProgressIndicatorAnimation",
    )

    Canvas(modifier.size(size)) {
        val sizePx = size.toPx()
        val strokeWidthPx = strokeWidth.toPx()

        val arcSize = sizePx - strokeWidthPx

        withTransform(
            { rotate(degrees = animatedProgress, pivot = center) },
        ) {
            drawArc(
                brush = Brush.sweepGradient(colorStops = colorStops),
                startAngle = 10f,
                sweepAngle = 340f,
                useCenter = false,
                topLeft = Offset(x = strokeWidthPx / 2, y = strokeWidthPx / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
            )
        }
    }
}

object SoomjaeCircularProgressIndicatorDefaults {
    val colorStops
        @Composable
        get() = arrayOf(
            0.0f to SoomjaeTheme.colorScheme.primary,
            0.18f to SoomjaeTheme.colorScheme.primary,
            1f to SoomjaeTheme.colorScheme.iconW.copy(alpha = 0f),
        )
}
