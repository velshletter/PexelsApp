package com.example.pexelsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pexelsapp.R

val mulishFontFamily = FontFamily(
    Font(R.font.mulish_bold, FontWeight.W700),
    Font(R.font.mulish_medium, FontWeight.W500),
    Font(R.font.mulish_regular, FontWeight.W400),
)
// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = mulishFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = mulishFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = mulishFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 16.sp
    )
)

