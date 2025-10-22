package com.example.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.dplay.designsystem.R

val SuitBold = FontFamily(Font(R.font.suit_bold))
val SuitSemiBold = FontFamily(Font(R.font.suit_semibold))
val SuitMedium = FontFamily(Font(R.font.suit_medium))

@Immutable
data class DPlayTypography(
    val titleBold24: TextStyle,
    val titleBold18: TextStyle,

    val bodyBold16: TextStyle,
    val bodySemi16: TextStyle,
    val bodyMed16: TextStyle,
    val bodyBold14: TextStyle,
    val bodySemi14: TextStyle,
    val bodyMed14: TextStyle,

    val capMed12: TextStyle
)

val DefaultDPlayTypography = DPlayTypography(
    titleBold24 = TextStyle(
        fontFamily = SuitBold,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = (31.2).sp,
        letterSpacing = 0.sp
    ),
    titleBold18 = TextStyle(
        fontFamily = SuitBold,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = (23.4).sp,
        letterSpacing = 0.sp
    ),
    bodyBold16 = TextStyle(
        fontFamily = SuitBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = (20.8).sp,
        letterSpacing = 0.sp
    ),
    bodySemi16 = TextStyle(
        fontFamily = SuitSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = (20.8).sp,
        letterSpacing = 0.sp
    ),
    bodyMed16 = TextStyle(
        fontFamily = SuitMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = (20.8).sp,
        letterSpacing = 0.sp
    ),
    bodyBold14 = TextStyle(
        fontFamily = SuitBold,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = (19.6).sp,
        letterSpacing = 0.sp
    ),
    bodySemi14 = TextStyle(
        fontFamily = SuitSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = (19.6).sp,
        letterSpacing = 0.sp
    ),
    bodyMed14 = TextStyle(
        fontFamily = SuitMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = (19.6).sp,
        letterSpacing = 0.sp
    ),
    capMed12 = TextStyle(
        fontFamily = SuitMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = (15.6).sp,
        letterSpacing = 0.sp
    )
)

val LocalDPlayTypography = staticCompositionLocalOf { DefaultDPlayTypography }

@Preview(showBackground = true)
@Composable
fun DPlayTypographyPreview() {
    DPlayTheme {
        Column{
            Text(text = "titleBold24", style = DPlayTheme.typography.titleBold24)
            Text(text = "titleBold18", style = DPlayTheme.typography.titleBold18)
            Text(text = "bodyBold16", style = DPlayTheme.typography.bodyBold16)
            Text(text = "bodySemi16", style = DPlayTheme.typography.bodySemi16)
            Text(text = "bodyMed16", style = DPlayTheme.typography.bodyMed16)
            Text(text = "bodyBold14", style = DPlayTheme.typography.bodyBold14)
            Text(text = "bodySemi14", style = DPlayTheme.typography.bodySemi14)
            Text(text = "bodyMed14", style = DPlayTheme.typography.bodyMed14)
            Text(text = "capMed12", style = DPlayTheme.typography.capMed12)
        }
    }
}