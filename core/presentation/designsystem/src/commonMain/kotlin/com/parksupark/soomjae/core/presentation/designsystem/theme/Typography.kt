package com.parksupark.soomjae.core.presentation.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.parksupark.soomjae.core.presentation.designsystem.resources.Res
import com.parksupark.soomjae.core.presentation.designsystem.resources.pretendard_bold
import com.parksupark.soomjae.core.presentation.designsystem.resources.pretendard_light
import com.parksupark.soomjae.core.presentation.designsystem.resources.pretendard_medium
import com.parksupark.soomjae.core.presentation.designsystem.resources.pretendard_regular
import com.parksupark.soomjae.core.presentation.designsystem.resources.pretendard_semibold
import org.jetbrains.compose.resources.Font

val pretendardFamily
    @Composable
    get() = FontFamily(
        Font(
            Res.font.pretendard_light,
            FontWeight.Light,
        ),
        Font(
            Res.font.pretendard_regular,
            FontWeight.Normal,
        ),
        Font(
            Res.font.pretendard_medium,
            FontWeight.Medium,
            FontStyle.Italic,
        ),
        Font(
            Res.font.pretendard_medium,
            FontWeight.Medium,
        ),
        Font(
            Res.font.pretendard_semibold,
            FontWeight.SemiBold,
        ),
        Font(
            Res.font.pretendard_bold,
            FontWeight.Bold,
        ),

    )

object SoomjaeTypography {

    // ─── Headlines ────────────────────────────────────────────────────────

    val headlineL: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 45.sp, // 125 %
        )

    val headlineM: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp, // 125 %
        )

    val headlineS: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 35.sp, // 125 %
        )

    // ─── Titles ───────────────────────────────────────────────────────────

    val title1: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 30.sp, // 125 %
        )

    val title2: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 25.sp, // 125 %
        )

    val title3: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 22.5.sp, // 125 %
        )

    // ─── Labels ───────────────────────────────────────────────────────────

    val labelL: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        )

    val labelM: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        )

    val labelS: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 20.sp,
        )

    // ─── Body copy ────────────────────────────────────────────────────────

    val body1: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 28.8.sp, // 180 %
        )

    val body2: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 25.2.sp, // 180 %
        )

    // ─── Buttons ──────────────────────────────────────────────────────────

    val button1: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        )

    val button2: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        )

    val button3: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 20.sp,
        )

    // ─── Captions ─────────────────────────────────────────────────────────

    val captionL: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        )

    val captionM: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        )

    val captionS: TextStyle
        @Composable get() = TextStyle(
            fontFamily = pretendardFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 20.sp,
        )
}
