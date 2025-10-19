package com.example.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Primary
val Dplay_pink = Color(0xFFFF448B)
val Dplay_pink300 = Color(0xFFFF8FBA)
val Dplay_pink100 = Color(0xFFFFDAE8)

// GrayScale
val Dplay_white = Color(0xFFFFFFFF)
val Gray100 = Color(0xFFF7F8FC)
val Gray200 = Color(0xFFE5E7F0)
val Gray300 = Color(0xFFC7CCD3)
val Gray400 = Color(0xFF7F8A96)
val Gray500 = Color(0xFF4A555E)
val Gray600 = Color(0xFF31393F)
val Dplay_black = Color(0xFF14181B)

// Symentic
val Alert_red = Color(0xFFFC4649)

// Transparent
val Dplay_pink_trans = Color(0xCCFF8FBA)
val Dim_40 = Color(0x6614181B)
val Dim_80 = Color(0xCC14181B)

@Immutable
data class DPlayColors(
    val dplayPink: Color,
    val dplayPink300: Color,
    val dplayPink100: Color,

    val dplayWhite: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val dplayBlack: Color,

    val alertRed: Color,

    val dplayPinkTrans: Color,
    val dim40: Color,
    val dim80: Color
)

val defaultDPlayColors = DPlayColors(
    dplayPink = Dplay_pink,
    dplayPink300 = Dplay_pink300,
    dplayPink100 = Dplay_pink100,

    dplayWhite = Dplay_white,
    gray100 = Gray100,
    gray200 = Gray200,
    gray300 = Gray300,
    gray400 = Gray400,
    gray500 = Gray500,
    gray600 = Gray600,
    dplayBlack = Dplay_black,

    alertRed = Alert_red,

    dplayPinkTrans = Dplay_pink_trans,
    dim40 = Dim_40,
    dim80 = Dim_80
)

val LocalDPlayColors = staticCompositionLocalOf { defaultDPlayColors }