package com.example.recipebox.presentation.onboarding

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.ui.theme.AqradaFontFamily
import com.example.recipebox.ui.theme.Black
import com.example.recipebox.ui.theme.Primary
import com.example.recipebox.ui.theme.Secondary
import com.example.recipebox.ui.theme.Tertiary
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            background = Primary,
            images = listOf(R.drawable.dish_1, R.drawable.dish_2, R.drawable.dish_3),
            text = "Your personal guide to be a chef"
        ),
        OnboardingPage(
            background = Secondary,
            images = listOf(R.drawable.dish_4, R.drawable.dish_5, R.drawable.dish_6),
            text = "Share the Love, Share the Recipe"
        ),
        OnboardingPage(
            background = Tertiary,
            images = listOf(R.drawable.dish_7, R.drawable.dish_8, R.drawable.dish_9),
            text = "Foodify Your Global Kitchen"
        )
    )

    var currentPage by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    // Fraction of the screen width we've dragged
    val fraction = (offsetX.value / screenWidth).coerceIn(-1f, 1f)

    // The next page index, calculated based on drag direction
    val nextPage = (currentPage - fraction.sign.toInt()).coerceIn(0, pages.lastIndex)

    // The background color, interpolated between the current and next page's color
    val bgColor = lerp(pages[currentPage].background, pages[nextPage].background, abs(fraction))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    scope.launch {
                        offsetX.snapTo(offsetX.value + delta)
                    }
                },
                onDragStopped = {
                    scope.launch {
                        // After the animation finishes, update the current page
                        if (abs(fraction) > 0.25f) {
                            currentPage = nextPage
                        }
                        // Reset the offset for the new page state
                        offsetX.snapTo(0f)
                    }
                },
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Current Page Images
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier.offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    ) {
                        pages[currentPage].images.forEach { imageRes ->
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = null,
                                modifier = Modifier.size(140.dp)
                            )
                        }
                    }

                    // Next Page Images (only shown while dragging)
                    if (nextPage != currentPage) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            modifier = Modifier.offset {
                                IntOffset(
                                    (offsetX.value - screenWidth * fraction.sign).roundToInt(),
                                    0
                                )
                            }
                        ) {
                            pages[nextPage].images.forEach { imageRes ->
                                Image(
                                    painter = painterResource(id = imageRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(140.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Black, shape = MaterialTheme.shapes.large)
                    .size(320.dp, 220.dp)
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(320.dp, 120.dp)
                ) {
                    Crossfade(targetState = currentPage, label = "OnboardingText") { page ->
                        Text(
                            text = pages[page].text,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontFamily = AqradaFontFamily,
                            lineHeight = 38.sp
                        )
                    }
                }
                val progress = (currentPage - fraction + 1) / pages.size.toFloat()
                Box(
                    modifier = Modifier
                        .size(70.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { 0f },
                        color = Color.White.copy(alpha = 0.3f),
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .size(70.dp),
                    )
                    CircularProgressIndicator(
                        progress = { progress.coerceIn(0f, 1f) },
                        color = Color.White,
                        strokeWidth = 6.dp,
                        trackColor = Color.Transparent,
                        modifier = Modifier
                            .size(70.dp),
                    )

                    if (progress >= 1.0f) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                containerColor = Color.White
                            ),
                            onClick = {
                                if (currentPage == pages.lastIndex) {
                                    onFinish()
                                }
                            },
                            modifier = Modifier
                                .size(70.dp)
                        ) {
                            Text("Go")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}


data class OnboardingPage(
    val background: Color,
    val images: List<Int>,
    val text: String
)