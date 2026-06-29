package com.movievault.app.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SplashScreen(
    onEnter: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Dynamic animated background
        AnimatedMovieBackground()

        // Content overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App title with animation
            AppTitleAnimation()

            Spacer(modifier = Modifier.height(60.dp))

            // Enter button
            Button(
                onClick = onEnter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE94560),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Enter MovieVault",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

/**
 * Animated background with floating movie-themed particles:
 * - Rotating film strip accent
 * - Twinkling stars with glow and sparkle
 * - Floating clapperboard silhouette
 * - Deep gradient background
 */
@Composable
private fun AnimatedMovieBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "bg_anim")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val starPulse by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star_pulse"
    )

    val floatY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float_y"
    )

    val density = LocalDensity.current
    val floatYpx = with(density) { floatY.dp.toPx() }

    data class Star(val x: Float, val y: Float, val radius: Float, val phase: Float)

    val stars = remember {
        listOf(
            Star(0.15f, 0.12f, 3f, 0f),
            Star(0.25f, 0.28f, 2f, 0.5f),
            Star(0.08f, 0.35f, 4f, 1f),
            Star(0.35f, 0.15f, 2.5f, 1.5f),
            Star(0.45f, 0.40f, 3f, 2f),
            Star(0.55f, 0.10f, 2f, 2.5f),
            Star(0.70f, 0.22f, 3.5f, 3f),
            Star(0.85f, 0.08f, 2f, 3.5f),
            Star(0.90f, 0.32f, 3f, 4f),
            Star(0.78f, 0.45f, 2.5f, 4.5f),
            Star(0.60f, 0.50f, 2f, 5f),
            Star(0.20f, 0.55f, 3f, 5.5f),
            Star(0.40f, 0.65f, 2.5f, 6f),
            Star(0.10f, 0.70f, 2f, 0.3f),
            Star(0.30f, 0.78f, 3f, 1.3f),
            Star(0.50f, 0.82f, 2f, 2.3f),
            Star(0.65f, 0.72f, 3.5f, 3.3f),
            Star(0.80f, 0.68f, 2f, 4.3f),
            Star(0.92f, 0.55f, 2.5f, 5.3f),
            Star(0.95f, 0.80f, 2f, 6.3f),
            Star(0.12f, 0.88f, 1.5f, 0.7f),
            Star(0.48f, 0.92f, 2f, 2.7f),
            Star(0.72f, 0.90f, 1.5f, 4.7f),
            Star(0.88f, 0.88f, 2f, 5.7f),
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A2E),
                            Color(0xFF16213E),
                            Color(0xFF0F3460),
                            Color(0xFF1A1A2E)
                        )
                    )
                )
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val filmRadius = minOf(size.width, size.height) * 0.55f
            val rotRad = rotation * PI.toFloat() / 180f

            // Rotating film strip perforations
            for (i in 0 until 12) {
                val angle = rotRad + (i * PI.toFloat() / 6f)
                val x = centerX + cos(angle) * filmRadius
                val y = centerY + sin(angle) * filmRadius
                drawCircle(
                    color = Color(0xFFE94560).copy(alpha = 0.15f),
                    radius = 4f + 2f * sin(angle * 3),
                    center = Offset(x, y)
                )
            }

            // Inner rotating ring
            val innerRadius = filmRadius * 0.7f
            for (i in 0 until 8) {
                val angle = -rotRad * 0.5f + (i * PI.toFloat() / 4f)
                val x = centerX + cos(angle) * innerRadius
                val y = centerY + sin(angle) * innerRadius
                drawCircle(
                    color = Color.White.copy(alpha = 0.06f),
                    radius = 2f + sin(angle * 2),
                    center = Offset(x, y)
                )
            }

            // Stars with pulse and float
            stars.forEach { star ->
                val sx = size.width * star.x
                val sy = size.height * star.y + floatYpx * sin(star.phase)
                val frac = star.phase - star.phase.toInt().toFloat()

                drawCircle(
                    color = Color(0xFFE94560).copy(alpha = starPulse * 0.12f),
                    radius = star.radius * 3f,
                    center = Offset(sx, sy)
                )
                drawCircle(
                    color = Color.White.copy(alpha = starPulse * (0.3f + 0.3f * frac)),
                    radius = star.radius,
                    center = Offset(sx, sy)
                )
                if (star.radius > 2f) {
                    val sparkLen = star.radius * 2f
                    val sa = starPulse * 0.4f
                    drawLine(Color.White.copy(alpha = sa), Offset(sx - sparkLen, sy), Offset(sx + sparkLen, sy), 0.5f)
                    drawLine(Color.White.copy(alpha = sa), Offset(sx, sy - sparkLen), Offset(sx, sy + sparkLen), 0.5f)
                }
            }

            // Clapperboard silhouette
            val cbWidth = size.width * 0.2f
            val cbHeight = cbWidth * 0.7f
            val cbX = centerX - cbWidth / 2f
            val cbY = size.height * 0.88f
            val clipPath = Path().apply {
                moveTo(cbX, cbY)
                lineTo(cbX + cbWidth, cbY)
                lineTo(cbX + cbWidth * 0.85f, cbY + cbHeight)
                lineTo(cbX + cbWidth * 0.15f, cbY + cbHeight)
                close()
            }
            drawPath(clipPath, Color.White.copy(alpha = 0.04f), style = Fill)
            drawPath(clipPath, Color.White.copy(alpha = 0.08f), style = Stroke(width = 1f))

            val stickOpen = floatYpx * 0.2f
            val stickPath = Path().apply {
                moveTo(cbX, cbY)
                lineTo(cbX + cbWidth * 0.7f, cbY - stickOpen)
                lineTo(cbX + cbWidth * 0.6f, cbY + cbHeight * 0.1f - stickOpen)
                lineTo(cbX + cbWidth * 0.1f, cbY + cbHeight * 0.3f)
                close()
            }
            drawPath(stickPath, Color.White.copy(alpha = 0.04f), style = Fill)
        }
    }
}

@Composable
private fun AppTitleAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "title_anim")

    val titleScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "title_scale"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "\uD83C\uDFAC",
            fontSize = 56.sp,
            modifier = Modifier.scale(titleScale)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "MovieVault",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = (-1).sp,
            modifier = Modifier.scale(titleScale)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Your personal movie collection",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Light
        )
    }
}
