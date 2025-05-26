package com.babatman.promisemyself.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.babatman.promisemyself.ui.theme.*
import kotlin.random.Random

@Composable
fun PageBackground(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "backgroundAnimation")
    
    // Rotating gradient animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAnimation"
    )

    // Particle system
    val particles = remember {
        List(30) {
            Particle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 3f + 1f,
                speed = Random.nextFloat() * 0.3f + 0.1f,
                alpha = Random.nextFloat() * 0.3f + 0.1f
            )
        }
    }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        // Draw base gradient
        rotate(rotation) {
            val gradient = Brush.sweepGradient(
                colors = listOf(
                    BackgroundLight.copy(alpha = 0.9f),
                    PrimaryLight.copy(alpha = 0.1f),
                    SecondaryLight.copy(alpha = 0.1f),
                    AccentLight.copy(alpha = 0.1f),
                    BackgroundLight.copy(alpha = 0.9f)
                )
            )
            drawRect(brush = gradient)
        }

        // Draw particles
        particles.forEach { particle ->
            drawCircle(
                color = PrimaryLight.copy(alpha = particle.alpha),
                radius = particle.size,
                center = Offset(
                    x = particle.x * size.width,
                    y = particle.y * size.height
                )
            )
        }
    }
}

private data class Particle(
    var x: Float,
    var y: Float,
    val size: Float,
    val speed: Float,
    val alpha: Float
) 