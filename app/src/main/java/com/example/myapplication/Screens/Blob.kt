package com.example.myapplication.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Composable
fun BlobBackground(
    modifier: Modifier = Modifier,
    blobColor: Color = Color(0xFFAAD7A4)  // Green color
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Define a blob-like path using cubic curves
        val blobPath = Path().apply {
            moveTo(0f, height * 0.4f)
            cubicTo(
                width * 0.25f, height * 0.1f,
                width * 0.75f, height * 0.7f,
                width, height * 0.4f
            )
            lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }

        // Draw the path using the specified color
        drawPath(path = blobPath, color = blobColor)
    }
}
