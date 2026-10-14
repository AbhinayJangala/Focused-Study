package com.example.foucsedstudyapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Premium Brand Palette
val BrandPrimary = Color(0xFF4F46E5)       // Modern Indigo
val BrandPrimaryDark = Color(0xFF3730A3)
val BrandSecondary = Color(0xFF10B981)     // Emerald Accent
val BrandTertiary = Color(0xFF8B5CF6)      // Vibrant Violet

// Functional & Semantic Colors
val FocusActive = Color(0xFF10B981)        // Emerald Green for Active Timer
val FocusPaused = Color(0xFFF59E0B)        // Amber/Gold for Paused
val FocusError = Color(0xFFEF4444)         // Coral Red for Stop/Error
val GoldRank = Color(0xFFF59E0B)           // 1st Place Gold
val SilverRank = Color(0xFF94A3B8)         // 2nd Place Silver
val BronzeRank = Color(0xFFD97706)         // 3rd Place Bronze

// Light Mode Neutral Surfaces
val BackgroundLight = Color(0xFFF8FAFC)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceElevatedLight = Color(0xFFFFFFFF)
val TextPrimaryLight = Color(0xFF0F172A)
val TextSecondaryLight = Color(0xFF64748B)
val TextMutedLight = Color(0xFF94A3B8)
val BorderLight = Color(0xFFE2E8F0)

// Dark Mode Neutral Surfaces
val BackgroundDark = Color(0xFF090D16)
val SurfaceDark = Color(0xFF111827)
val SurfaceElevatedDark = Color(0xFF1F2937)
val TextPrimaryDark = Color(0xFFF8FAFC)
val TextSecondaryDark = Color(0xFF94A3B8)
val TextMutedDark = Color(0xFF64748B)
val BorderDark = Color(0xFF1F2937)

// Beautiful Gradients
val PrimaryHeroGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF4F46E5), Color(0xFF3B82F6), Color(0xFF06B6D4))
)

val StreakGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF5722), Color(0xFFFF9800))
)

val GoldPodiumGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFFCD34D), Color(0xFFF59E0B))
)

val SilverPodiumGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFE2E8F0), Color(0xFF94A3B8))
)

val BronzePodiumGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFFFDBA74), Color(0xFFD97706))
)

// Legacy compatibility fallbacks
val PrimaryBlue = BrandPrimary
val PrimaryGreen = BrandSecondary
val TertiaryPurple = BrandTertiary
val TitleDark = TextPrimaryLight
val TextGray = TextSecondaryLight
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
