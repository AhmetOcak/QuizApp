package com.quizapp.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.quizapp.core.ui.theme.*
import com.smarttoolfactory.slider.ColorfulSlider
import com.smarttoolfactory.slider.MaterialSliderDefaults
import com.smarttoolfactory.slider.SliderBrushColor

@Composable
fun CustomSlider(
    modifier: Modifier,
    trackHeight: Dp,
    onValueChange: (Float) -> Unit,
    value: Float,
    borderStroke: BorderStroke? = null,
    inactiveTrackColor: Color = Color.LightGray
) {
    ColorfulSlider(
        modifier = modifier,
        value = value,
        thumbRadius = 0.dp,
        trackHeight = trackHeight,
        valueRange = 0f..100f,
        onValueChange = onValueChange,
        colors = MaterialSliderDefaults.materialColors(
            inactiveTrackColor = SliderBrushColor(color = inactiveTrackColor),
            activeTrackColor = SliderBrushColor(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        LightPurple,
                        LightPink,
                        StrangeRed,
                        StrangeOrange,
                        LightBrown,
                        LightYellow
                    )
                ),
            ),
            thumbColor = SliderBrushColor(color = Color.Transparent),
        ),
        borderStroke = borderStroke
    )
}