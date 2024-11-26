package com.inmobixpress.inmobixpressmanager.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.inmobixpress.inmobixpressmanager.ui.theme.Purple40
import com.inmobixpress.inmobixpressmanager.ui.theme.Purple80

enum class ProgressBarColor(val gradientStart: Color, val gradientEnd: Color) {
    Red(Color(254, 222, 224), Color(255, 31, 43)),
    Green(Color(168, 242, 205), Color(38, 222, 129)),
    Blue(Color(219, 229, 251), Color(75, 123, 236)),
    Purple(Color(224, 204, 255), Color(98, 0, 254, 255));

    operator fun invoke(): Color {
        return gradientEnd
    }
}

val trackColor = White

@Composable
fun GradientProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    gradientStart: Color,
    gradientEnd: Color,
    trackColor: Color,
    strokeWidth: Dp,
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }
    Canvas(
        modifier
            .progressSemantics(progress)
    ) {
        // Start at 12 o'clock
        val startAngle = 270f
        val sweep = progress * 360f
        drawDeterminateCircularIndicator(startAngle, 360f, trackColor, stroke)
        drawCircularIndicator(
            startAngle = startAngle,
            sweep = sweep,
            gradientStart = gradientStart,
            gradientEnd = gradientEnd,
            stroke = stroke
        )
    }
}

private fun DrawScope.drawDeterminateCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
) = drawCircularIndicator(startAngle, sweep, color, stroke)

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke,
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    gradientStart: Color,
    gradientEnd: Color,
    stroke: Stroke,
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    rotate(degrees = -90f) {
        drawArc(
            brush = Brush.sweepGradient(
                colorStops = listOf(
                    0.0f to gradientStart,
                    sweep / 360 to gradientEnd,
                ).toTypedArray()
            ),
            startAngle = startAngle + 90,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = Offset(diameterOffset, diameterOffset),
            size = Size(arcDimen, arcDimen),
            style = stroke
        )
    }
}

@Preview
@Composable
fun GradientProgressIndicatorPreview() {
    val progress: Float by remember { mutableFloatStateOf(0.75f) }
    val indicatorSize = 144.dp
    val trackWidth: Dp = (indicatorSize * .1f)
    val commonModifier = Modifier.size(indicatorSize)
    GradientProgressIndicator(
        progress = progress,
        modifier = commonModifier,
        strokeWidth = trackWidth,
        gradientStart = Purple80,
        gradientEnd = Purple40,
        trackColor = trackColor
    )
}