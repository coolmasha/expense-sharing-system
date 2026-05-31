package com.mashakulabukhova.expensesharingsystem.presentation.component

import android.graphics.Matrix
import android.graphics.Shader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.max
import kotlin.math.min

class EllipticalGradientBrush(
    private val colors: List<Color>,
    private val centerX: Float = 0.5f,
    private val centerY: Float = 0.5f,
    private val radiusX: Float = 0.5f,
    private val radiusY: Float = 0.5f
) : ShaderBrush() {

    override fun createShader(size: Size): Shader {
        val width = size.width
        val height = size.height
        val cx = centerX * width
        val cy = centerY * height
        val rx = radiusX * width
        val ry = radiusY * width  // Используем width для обоих радиусов

        // Важно: радиус должен быть достаточно большим, чтобы покрыть эллипс
        val gradientRadius = max(rx, ry)

        val androidColors = colors.map { it.toArgb() }.toIntArray()

        val radialGradient = android.graphics.RadialGradient(
            cx, cy,
            gradientRadius,
            androidColors,
            null,
            Shader.TileMode.CLAMP
        )

        val matrix = Matrix().apply {
            // Растягиваем градиент под форму эллипса
            setScale(1f, ry / rx, cx, cy)
        }
        radialGradient.setLocalMatrix(matrix)

        return radialGradient
    }
}

// Создаём форму эллипса для обрезки
class EllipticalShape(
    private val radiusX: Float = 0.5f,
    private val radiusY: Float = 0.5f
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height
        val rx = radiusX * width
        val ry = radiusY * height
        val left = (width / 2) - rx
        val top = (height / 2) - ry
        val right = (width / 2) + rx
        val bottom = (height / 2) + ry

        return Outline.Rectangle(androidx.compose.ui.geometry.Rect(left, top, right, bottom))
    }
}