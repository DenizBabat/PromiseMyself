package com.babatman.promisemyself.ui.theme

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

// Primary colors - Deep Ocean theme
val PrimaryLight = Color(0xFF2D3250)
val PrimaryDark = Color(0xFF424769)
val SecondaryLight = Color(0xFF676F9D)
val SecondaryDark = Color(0xFF7B84B2)

// Background colors
val BackgroundLight = Color(0xFFF5F6FA)
val BackgroundDark = Color(0xFF1A1B26)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceDark = Color(0xFF24283B)

// Card gradients
data class CardGradient(
    val start: Color,
    val end: Color
) {
    fun getContrastingTextColor(): Color {
        // Calculate the average brightness of the gradient
        val startBrightness = calculateBrightness(start)
        val endBrightness = calculateBrightness(end)
        val averageBrightness = (startBrightness + endBrightness) / 2

        // Return white for dark backgrounds and dark gray for light backgrounds
        return if (averageBrightness < 0.5) {
            Color.White
        } else {
            Color(0xFF2D3250) // Dark gray for better readability
        }
    }

    private fun calculateBrightness(color: Color): Float {
        // Convert color to RGB values
        val red = color.red
        val green = color.green
        val blue = color.blue

        // Calculate relative luminance using the formula:
        // (0.299 * R + 0.587 * G + 0.114 * B)
        return (0.299f * red + 0.587f * green + 0.114f * blue)
    }
}

val cardGradients = listOf(
    // Original gradients
    CardGradient(start = Color(0xFFE6E6FA), end = Color(0xFFD8BFD8)), // Soft Lavender
    CardGradient(start = Color(0xFFE0F7FA), end = Color(0xFFB2EBF2)), // Gentle Mint
    CardGradient(start = Color(0xFFFFE4E1), end = Color(0xFFFFDAB9)), // Warm Peach
    CardGradient(start = Color(0xFFE8F5E9), end = Color(0xFFC8E6C9)), // Soft Sage
    CardGradient(start = Color(0xFFE3F2FD), end = Color(0xFFBBDEFB)), // Light Blue
    CardGradient(start = Color(0xFFFCE4EC), end = Color(0xFFF8BBD0)), // Soft Rose
    CardGradient(start = Color(0xFFFFFDE7), end = Color(0xFFFFF9C4)), // Gentle Yellow
    CardGradient(start = Color(0xFFE0F2F1), end = Color(0xFFB2DFDB)), // Soft Teal

    // Pastel Gradients
    CardGradient(start = Color(0xFFFFB6C1), end = Color(0xFFFFC0CB)), // Pink
    CardGradient(start = Color(0xFF98FB98), end = Color(0xFF90EE90)), // Light Green
    CardGradient(start = Color(0xFF87CEEB), end = Color(0xFF87CEFA)), // Sky Blue
    CardGradient(start = Color(0xFFDDA0DD), end = Color(0xFFEE82EE)), // Plum
    CardGradient(start = Color(0xFFF0E68C), end = Color(0xFFFFD700)), // Khaki
    CardGradient(start = Color(0xFFE6E6FA), end = Color(0xFFD8BFD8)), // Lavender
    CardGradient(start = Color(0xFFFFA07A), end = Color(0xFFFF7F50)), // Light Salmon
    CardGradient(start = Color(0xFF20B2AA), end = Color(0xFF48D1CC)), // Light Sea Green

    // Nature Inspired
    CardGradient(start = Color(0xFF98FB98), end = Color(0xFF32CD32)), // Spring Green
    CardGradient(start = Color(0xFF87CEEB), end = Color(0xFF4169E1)), // Sky to Royal Blue
    CardGradient(start = Color(0xFFFFD700), end = Color(0xFFFFA500)), // Gold to Orange
    CardGradient(start = Color(0xFFE6E6FA), end = Color(0xFF9370DB)), // Lavender to Medium Purple
    CardGradient(start = Color(0xFFF0E68C), end = Color(0xFFDAA520)), // Khaki to Goldenrod
    CardGradient(start = Color(0xFF98FB98), end = Color(0xFF228B22)), // Light Green to Forest Green
    CardGradient(start = Color(0xFFFFB6C1), end = Color(0xFFFF69B4)), // Light Pink to Hot Pink
    CardGradient(start = Color(0xFF87CEFA), end = Color(0xFF1E90FF)), // Light Sky Blue to Dodger Blue

    // Sunset Colors
    CardGradient(start = Color(0xFFFFA07A), end = Color(0xFFFF6347)), // Light Salmon to Tomato
    CardGradient(start = Color(0xFFFFD700), end = Color(0xFFFF8C00)), // Gold to Dark Orange
    CardGradient(start = Color(0xFFFF69B4), end = Color(0xFFFF1493)), // Hot Pink to Deep Pink
    CardGradient(start = Color(0xFFFFA500), end = Color(0xFFFF4500)), // Orange to Orange Red
    CardGradient(start = Color(0xFFFFDAB9), end = Color(0xFFDEB887)), // Peach Puff to Burlywood
    CardGradient(start = Color(0xFFFFC0CB), end = Color(0xFFFF69B4)), // Pink to Hot Pink
    CardGradient(start = Color(0xFFFFE4B5), end = Color(0xFFDAA520)), // Moccasin to Goldenrod
    CardGradient(start = Color(0xFFFFE4E1), end = Color(0xFFFFA07A)), // Misty Rose to Light Salmon

    // Ocean Colors
    CardGradient(start = Color(0xFF87CEEB), end = Color(0xFF4682B4)), // Sky Blue to Steel Blue
    CardGradient(start = Color(0xFFB0E0E6), end = Color(0xFF6495ED)), // Powder Blue to Cornflower Blue
    CardGradient(start = Color(0xFFE0FFFF), end = Color(0xFF40E0D0)), // Light Cyan to Turquoise
    CardGradient(start = Color(0xFFF0FFFF), end = Color(0xFF87CEFA)), // Azure to Light Sky Blue
    CardGradient(start = Color(0xFFE0F7FA), end = Color(0xFF00BCD4)), // Light Cyan to Cyan
    CardGradient(start = Color(0xFFB3E5FC), end = Color(0xFF03A9F4)), // Light Blue to Light Blue
    CardGradient(start = Color(0xFFBBDEFB), end = Color(0xFF2196F3)), // Light Blue to Blue
    CardGradient(start = Color(0xFFE3F2FD), end = Color(0xFF1976D2)), // Light Blue to Dark Blue

    // Forest Colors
    CardGradient(start = Color(0xFF98FB98), end = Color(0xFF32CD32)), // Light Green to Lime Green
    CardGradient(start = Color(0xFF90EE90), end = Color(0xFF228B22)), // Light Green to Forest Green
    CardGradient(start = Color(0xFFE8F5E9), end = Color(0xFF4CAF50)), // Light Green to Green
    CardGradient(start = Color(0xFFC8E6C9), end = Color(0xFF388E3C)), // Light Green to Dark Green
    CardGradient(start = Color(0xFFA5D6A7), end = Color(0xFF2E7D32)), // Light Green to Dark Green
    CardGradient(start = Color(0xFF81C784), end = Color(0xFF1B5E20)), // Light Green to Dark Green
    CardGradient(start = Color(0xFF66BB6A), end = Color(0xFF1B5E20)), // Light Green to Dark Green
    CardGradient(start = Color(0xFF4CAF50), end = Color(0xFF1B5E20)), // Green to Dark Green

    // Desert Colors
    CardGradient(start = Color(0xFFFFE4C4), end = Color(0xFFDEB887)), // Bisque to Burlywood
    CardGradient(start = Color(0xFFFFDAB9), end = Color(0xFFD2B48C)), // Peach Puff to Tan
    CardGradient(start = Color(0xFFFFE4B5), end = Color(0xFFCD853F)), // Moccasin to Peru
    CardGradient(start = Color(0xFFFFDEAD), end = Color(0xFFD2691E)), // Navajo White to Chocolate
    CardGradient(start = Color(0xFFFFD700), end = Color(0xFFDAA520)), // Gold to Goldenrod
    CardGradient(start = Color(0xFFFFA500), end = Color(0xFFCD853F)), // Orange to Peru
    CardGradient(start = Color(0xFFFF8C00), end = Color(0xFF8B4513)), // Dark Orange to Saddle Brown
    CardGradient(start = Color(0xFFFF7F50), end = Color(0xFFA52A2A)), // Coral to Brown

    // Mountain Colors
    CardGradient(start = Color(0xFFE6E6FA), end = Color(0xFF9370DB)), // Lavender to Medium Purple
    CardGradient(start = Color(0xFFD8BFD8), end = Color(0xFF8A2BE2)), // Thistle to Blue Violet
    CardGradient(start = Color(0xFFDDA0DD), end = Color(0xFF9932CC)), // Plum to Dark Orchid
    CardGradient(start = Color(0xFFEE82EE), end = Color(0xFF8B008B)), // Violet to Dark Magenta
    CardGradient(start = Color(0xFFDA70D6), end = Color(0xFF800080)), // Orchid to Purple
    CardGradient(start = Color(0xFFBA55D3), end = Color(0xFF4B0082)), // Medium Orchid to Indigo
    CardGradient(start = Color(0xFF9370DB), end = Color(0xFF483D8B)), // Medium Purple to Dark Slate Blue
    CardGradient(start = Color(0xFF8A2BE2), end = Color(0xFF191970)), // Blue Violet to Midnight Blue

    // Tropical Colors
    CardGradient(start = Color(0xFFFF69B4), end = Color(0xFFFF1493)), // Hot Pink to Deep Pink
    CardGradient(start = Color(0xFFFF1493), end = Color(0xFFC71585)), // Deep Pink to Medium Violet Red
    CardGradient(start = Color(0xFFFF00FF), end = Color(0xFF8B008B)), // Magenta to Dark Magenta
    CardGradient(start = Color(0xFFFF00FF), end = Color(0xFF800080)), // Magenta to Purple
    CardGradient(start = Color(0xFFFF00FF), end = Color(0xFF4B0082)), // Magenta to Indigo
    CardGradient(start = Color(0xFFFF00FF), end = Color(0xFF191970)), // Magenta to Midnight Blue
    CardGradient(start = Color(0xFFFF00FF), end = Color(0xFF000080)), // Magenta to Navy
    CardGradient(start = Color(0xFFFF00FF), end = Color(0xFF000000)), // Magenta to Black

    // Arctic Colors
    CardGradient(start = Color(0xFFF0FFFF), end = Color(0xFFE0FFFF)), // Azure to Light Cyan
    CardGradient(start = Color(0xFFE0FFFF), end = Color(0xFFB0E0E6)), // Light Cyan to Powder Blue
    CardGradient(start = Color(0xFFB0E0E6), end = Color(0xFF87CEEB)), // Powder Blue to Sky Blue
    CardGradient(start = Color(0xFF87CEEB), end = Color(0xFF00BFFF)), // Sky Blue to Deep Sky Blue
    CardGradient(start = Color(0xFF00BFFF), end = Color(0xFF1E90FF)), // Deep Sky Blue to Dodger Blue
    CardGradient(start = Color(0xFF1E90FF), end = Color(0xFF4169E1)), // Dodger Blue to Royal Blue
    CardGradient(start = Color(0xFF4169E1), end = Color(0xFF0000CD)), // Royal Blue to Medium Blue
    CardGradient(start = Color(0xFF0000CD), end = Color(0xFF000080)), // Medium Blue to Navy

    // Autumn Colors
    CardGradient(start = Color(0xFFFFA07A), end = Color(0xFFFA8072)), // Light Salmon to Salmon
    CardGradient(start = Color(0xFFFA8072), end = Color(0xFFE9967A)), // Salmon to Dark Salmon
    CardGradient(start = Color(0xFFE9967A), end = Color(0xFFF08080)), // Dark Salmon to Light Coral
    CardGradient(start = Color(0xFFF08080), end = Color(0xFFCD5C5C)), // Light Coral to Indian Red
    CardGradient(start = Color(0xFFCD5C5C), end = Color(0xFFDC143C)), // Indian Red to Crimson
    CardGradient(start = Color(0xFFDC143C), end = Color(0xFFB22222)), // Crimson to Firebrick
    CardGradient(start = Color(0xFFB22222), end = Color(0xFF8B0000)), // Firebrick to Dark Red
    CardGradient(start = Color(0xFF8B0000), end = Color(0xFF800000)), // Dark Red to Maroon

    // Spring Colors
    CardGradient(start = Color(0xFFFFB6C1), end = Color(0xFFFFC0CB)), // Light Pink to Pink
    CardGradient(start = Color(0xFFFFC0CB), end = Color(0xFFFF69B4)), // Pink to Hot Pink
    CardGradient(start = Color(0xFFFF69B4), end = Color(0xFFFF1493)), // Hot Pink to Deep Pink
    CardGradient(start = Color(0xFFFF1493), end = Color(0xFFC71585)), // Deep Pink to Medium Violet Red
    CardGradient(start = Color(0xFFC71585), end = Color(0xFFDB7093)), // Medium Violet Red to Pale Violet Red
    CardGradient(start = Color(0xFFDB7093), end = Color(0xFFFF69B4)), // Pale Violet Red to Hot Pink
    CardGradient(start = Color(0xFFFF69B4), end = Color(0xFFFF1493)), // Hot Pink to Deep Pink
    CardGradient(start = Color(0xFFFF1493), end = Color(0xFFC71585)), // Deep Pink to Medium Violet Red

    // Summer Colors
    CardGradient(start = Color(0xFFFFD700), end = Color(0xFFFFA500)), // Gold to Orange
    CardGradient(start = Color(0xFFFFA500), end = Color(0xFFFF8C00)), // Orange to Dark Orange
    CardGradient(start = Color(0xFFFF8C00), end = Color(0xFFFF7F50)), // Dark Orange to Coral
    CardGradient(start = Color(0xFFFF7F50), end = Color(0xFFFF6347)), // Coral to Tomato
    CardGradient(start = Color(0xFFFF6347), end = Color(0xFFFF4500)), // Tomato to Orange Red
    CardGradient(start = Color(0xFFFF4500), end = Color(0xFFFF0000)), // Orange Red to Red
    CardGradient(start = Color(0xFFFF0000), end = Color(0xFFDC143C)), // Red to Crimson
    CardGradient(start = Color(0xFFDC143C), end = Color(0xFFB22222)), // Crimson to Firebrick

    // Winter Colors
    CardGradient(start = Color(0xFFF0FFFF), end = Color(0xFFE0FFFF)), // Azure to Light Cyan
    CardGradient(start = Color(0xFFE0FFFF), end = Color(0xFFB0E0E6)), // Light Cyan to Powder Blue
    CardGradient(start = Color(0xFFB0E0E6), end = Color(0xFF87CEEB)), // Powder Blue to Sky Blue
    CardGradient(start = Color(0xFF87CEEB), end = Color(0xFF00BFFF)), // Sky Blue to Deep Sky Blue
    CardGradient(start = Color(0xFF00BFFF), end = Color(0xFF1E90FF)), // Deep Sky Blue to Dodger Blue
    CardGradient(start = Color(0xFF1E90FF), end = Color(0xFF4169E1)), // Dodger Blue to Royal Blue
    CardGradient(start = Color(0xFF4169E1), end = Color(0xFF0000CD)), // Royal Blue to Medium Blue
    CardGradient(start = Color(0xFF0000CD), end = Color(0xFF000080))  // Medium Blue to Navy
)

// Text colors
val TextPrimaryLight = Color(0xFF2D3250)
val TextPrimaryDark = Color(0xFFE2E8F0)
val TextSecondaryLight = Color(0xFF676F9D)
val TextSecondaryDark = Color(0xFF94A3B8)

// Accent colors
val AccentLight = Color(0xFF7C3AED)
val AccentDark = Color(0xFF9F67FF)
val SuccessLight = Color(0xFF10B981)
val SuccessDark = Color(0xFF34D399)
val WarningLight = Color(0xFFF59E0B)
val WarningDark = Color(0xFFFBBF24)

// Overlay colors
val OverlayLight = Color(0x1A2D3250)
val OverlayDark = Color(0x1A424769)

// Card shadow colors
val ShadowLight = Color(0x1A2D3250)
val ShadowDark = Color(0x1A424769)