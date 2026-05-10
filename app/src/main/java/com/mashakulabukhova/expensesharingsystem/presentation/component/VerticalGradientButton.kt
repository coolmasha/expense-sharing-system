package com.mashakulabukhova.expensesharingsystem.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Testtt() {
    VerticalGradientButton({}) {
        Text("deeve")
    }
}

@Composable
fun VerticalGradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    gradientColor1: Color = MaterialTheme.colorScheme.primary,
    gradientColor2: Color = MaterialTheme.colorScheme.tertiary,
    content: @Composable (RowScope.() -> Unit)
) {

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            gradientColor1,
            gradientColor2
        )
    )

    Button(
        onClick = onClick,
        modifier = modifier.background(
            brush = gradientBrush,
            shape = shape
        ),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.onPrimary
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )
}