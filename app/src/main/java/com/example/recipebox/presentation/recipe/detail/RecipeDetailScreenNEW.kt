package com.example.recipebox.presentation.recipe.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.presentation.recipe.add.RecipeStepsScreen
import com.example.recipebox.presentation.recipe.add.StepCard
import com.example.recipebox.ui.theme.MontserratFontFamily

@Preview
@Composable
fun previeww()
{
    RecipeDetailsScreen(
        onBack = {},
        onNext = {}
    )
}

@Composable
fun RecipeDetailsScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    val suggestedDietaries = listOf(
        "Vegetarian", "High Fat", "Low Fat", "Sugar Free", "Lactose Free", "Gluten Free"
    )
    var selectedTab by remember{ mutableStateOf("Introduction") }

    var steps = listOf(
        "In a large bowl, mix together flour, baking powder, sugar, and salt.",
        "In a separate bowl, whisk together milk, egg, and oil or melted butter. Add vanilla extract if desired.",
        "Gradually add the liquid mixture to the dry ingredients, stirring continuously until a smooth, lump-free batter forms."
    )

    var ingredients = listOf(
        "2 cups all-purpose flour",
        "2 teaspoons baking powder",
        "2 tablespoons sugar",
        "1/2 teaspoon salt",
        "1.5 cups milk"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FBF6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Image as the background
            Image(
                painter = painterResource(id = R.drawable.pancakes),
                contentDescription = "Pancakes",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            // Back icon positioned on top of the image
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Back",
                    tint = Color.White,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Perfect homemade pancake",
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            color = Color.White,
            fontFamily = MontserratFontFamily,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(
                    color = Color(0xFFEDEDED),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            suggestedDietaries.chunked(4).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    rowItems.forEach { type ->
                        Spacer(Modifier.width(2.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    color = Color.White,
                                    width = 1.dp,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Text(
                                text = type,
                                color = Color(0xFF353535),
                                fontSize = 14.sp,
                                fontFamily = MontserratFontFamily,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                    }
                    repeat(4 - rowItems.size) {
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))

        Column (
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = Color(0xFFF8FCF4)
                )
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                TabItem(
                    title = "Introduction",
                    isSelected = selectedTab == "Introduction",
                    onClick = { selectedTab = "Introduction" }
                )
                Spacer(modifier = Modifier.width(8.dp))
                TabItem(
                    title = "Ingredients",
                    isSelected = selectedTab == "Ingredients",
                    onClick = { selectedTab = "Ingredients" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Content depending on tab ---
            when (selectedTab) {
                "Introduction" -> IntroductionContent(steps)
                "Ingredients" -> IngredientsContent(ingredients)
            }
        }
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = if (isSelected) Color.Black else Color.Gray,
            fontFamily =  MontserratFontFamily
        )
    }
}

@Composable
fun CardItem(
    title: String,
    item: String,
){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .width(400.dp)
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                        )
                )
                {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFF8FBF6),
                                shape = RoundedCornerShape(
                                    bottomEnd = 8.dp
                                )
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 8.dp, end = 8.dp)
                                .wrapContentSize()
                                .background(
                                    Color(0xFFFF6339),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(8.dp)

                        ) {
                            Text(
                                text = title,
                                color = Color.White,
                                fontFamily = MontserratFontFamily
                            )
                        }
                    }

                }


                Box(
                    modifier = Modifier
                        .weight(1f) // Takes remaining space
                        .height(41.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topStart = 8.dp,    // Top-left: square
                                topEnd = 8.dp,      // Top-right: square
                                bottomStart = 0.dp, // Bottom-left: rounded
                                bottomEnd = 0.dp    // Bottom-right: rounded
                            )
                        )
                )
                {

                }

            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,    // Top-left: square
                            topEnd = 0.dp,      // Top-right: square
                            bottomStart = 8.dp, // Bottom-left: rounded
                            bottomEnd = 8.dp    // Bottom-right: rounded
                        )
                    )
            ){
            }
            Text(
                text = item,
                modifier = Modifier
                    .offset(x = 50.dp, y = -70.dp) // Adjust position as needed
                    .width(320.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Left,
                color = Color.Black,
                fontFamily = MontserratFontFamily,
            )
        }
}

@Composable
fun IntroductionContent(
    steps: List<String>
) {
    Column {
        Text("${steps.size} Steps", style = MaterialTheme.typography.titleLarge,  fontFamily = MontserratFontFamily)
        Spacer(modifier = Modifier.height(8.dp))
        steps.forEachIndexed { index, step ->
            CardItem (
                title = "${index + 1}".padStart(2, '0'), // Automatically generates 01, 02, 03, etc.
                item = step,
                )
        }
    }
}

@Composable
fun IngredientsContent(
    ingredients: List<String>
) {
    Column {
        Text("${ingredients.size} Ingredients", style = MaterialTheme.typography.titleLarge, fontFamily = MontserratFontFamily)
        Spacer(modifier = Modifier.height(8.dp))
        ingredients.forEachIndexed { index, ingredient ->
            CardItem (
                title = "${index + 1}".padStart(2, '0'), // Automatically generates 01, 02, 03, etc.
                item = ingredient,
            )
        }
    }
}