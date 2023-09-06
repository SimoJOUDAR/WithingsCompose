package com.mjoudar.withingscompose.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mjoudar.withingscompose.R

private val Lato = FontFamily(
    Font(R.font.lato_bold),
    Font(R.font.lato_regular),
    Font(R.font.lato_light),
    Font(R.font.lato_thin)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val LatoTypograpgy = Typography(
    titleLarge = TextStyle(
        fontFamily = Lato,
        fontSize = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Lato,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Lato,
        fontSize = 16.sp
    )
)