package com.example.recipebox.presentation.recipe.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.recipebox.ui.theme.AqradaFontFamily
import com.example.recipebox.ui.theme.MontserratFontFamily
import okhttp3.internal.http2.Header

//@Composable
//fun AddRecipeScreen()
//{
//    var servings = 0
//    RecipeInformationScreen(onBack = {}, onNext = {}, servings = servings, onServingsChange = {})
//}

/* ---------- Stepper + helpers ---------- */

@Composable
fun NewRecipeHeader(onBack: () -> Unit)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF4058A0),
                shape = RoundedCornerShape(
                    topStart = 0.dp,    // Top-left: square
                    topEnd = 0.dp,      // Top-right: square
                    bottomStart = 20.dp, // Bottom-left: rounded
                    bottomEnd = 20.dp    // Bottom-right: rounded
                )
            )
            .height(56.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Arrow at start
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "Back",
                tint = Color.White,
            )
        }

        // Centered text
        Text(
            text = "New Recipe",
            fontFamily = AqradaFontFamily,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }

}

@Composable
fun Stepper(
    activeStep: Int,
    totalSteps: Int,
    activeLabel: String? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy((-4).dp) // Negative spacing for overlap
    ) {
        for (i in 1..totalSteps) {
            // step circle
            StepCircle(number = i, isActive = i == activeStep)

            // active label chip
            if (i == activeStep && activeLabel != null) {
                Spacer(Modifier.width(8.dp))
                ActiveLabelChip(activeLabel)
            }

            // connector
            if (i < totalSteps) {
                //Spacer(Modifier.width(8.dp))
                StepConnector()
                //Spacer(Modifier.width(8.dp))
            }
        }
    }
}

@Composable
private fun StepCircle(number: Int, isActive: Boolean) {
    val circleColor = Color(0xFF353535)
    val textColor = if (isActive) Color(0xFFF2E739) else Color(0xFF717171)

    Box(
        modifier = Modifier
            .size(36.dp)
            .background(circleColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            fontFamily = MontserratFontFamily,
        )
    }
}

@Composable
private fun ActiveLabelChip(text: String) {
    Box(
        modifier = Modifier
            .height(36.dp)
            .width(160.dp)
            .background(Color(0xFF353535), RoundedCornerShape(18.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = Color(0xFFF2E739),
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            fontFamily = MontserratFontFamily
        )
    }
}

@Composable
private fun StepConnector() {
    Box(
        modifier = Modifier
            .width(20.dp)
            .height(26.dp)
            .background(
                color = Color(0xFF353535),
                shape = HorizontalConcaveBothSidesShape(concaveDepth = 0.55f)
            )
    )
}

@Composable
fun HorizontalConcaveBothSidesShape(concaveDepth: Float = 0.3f) : Shape {
    return object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            return Outline.Generic(
                Path().apply {
                    val depth = size.height * concaveDepth

                    // Start at top-left
                    moveTo(0f, 0f)

                    // Curve inward from left side (top to bottom)
                    cubicTo(
                        0f, depth,                    // Control point 1
                        size.width, depth,           // Control point 2
                        size.width, 0f               // End point (bottom-left)
                    )

                    // Line to bottom-right
                    lineTo(size.width, size.height)

                    // Curve inward from right side (bottom to top)
                    cubicTo(
                        size.width, size.height - depth,   // Control point 1
                        0f, size.height - depth,           // Control point 2
                        0f, size.height                    // End point (bottom-left)
                    )

                    // Close the path back to start
                    close()
                }
            )
        }
    }
}

/* ---------- Preview ---------- */

//@Preview(showBackground = true, backgroundColor = 0xFFF8FBF6)
//@Composable
//private fun RecipePreview() {
//    AddRecipeScreen()
//}
