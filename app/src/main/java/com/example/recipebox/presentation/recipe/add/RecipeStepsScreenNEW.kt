package com.example.recipebox.presentation.recipe.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.ui.theme.MontserratFontFamily

@Preview
@Composable
fun preview()
{
    RecipeStepsScreen(
        onBack = {},
        onNext = {}
    )
}

@Composable
fun RecipeStepsScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    var stepText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FBF4))
            .verticalScroll(rememberScrollState()), // ADD THIS for scrolling,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NewRecipeHeader(onBack)

        Spacer(Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.width(8.dp))
            Stepper(activeStep = 4, totalSteps = 4, activeLabel = "Steps")
        }

        Spacer(Modifier.height(28.dp))

        StepsSection (
            steps = listOf(
                "In a large bowl, mix together flour, baking powder, sugar, and salt.",
                "In a separate bowl, whisk together milk, egg, and oil or melted butter. Add vanilla extract if desired.",
                "Gradually add the liquid mixture to the dry ingredients, stirring continuously until a smooth, lump-free batter forms."
            ),
            onChange = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input section with TextField and Add button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = stepText,
                onValueChange = { stepText = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        "e.g., Add water to the mixture",
                        fontFamily = MontserratFontFamily,
                        color = Color.Gray
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors( // Fixed: lowercase 'outlinedTextFieldColors'
                    focusedBorderColor = Color(0xFFFF6339),
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = Color(0xFFFF6339),
                    cursorColor = Color(0xFFFF6339)
                ),
                shape = RoundedCornerShape(8.dp)
            )

            IconButton(
                onClick = {
                    // Add logic to add ingredient here
                    if (stepText.isNotBlank()) {
                        // You would typically update your ingredients list here
                        stepText = "" // Clear the text field
                    }
                },
                modifier = Modifier
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Step",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onNext,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontFamily = MontserratFontFamily,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun StepCard(
    title: String,
    step: String,
    onClick: () -> Unit,
//    content: @Composable ColumnScope.() -> Unit
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
            Box (
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            ){

                IconButton (
                    onClick = onClick,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "Decrement",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                            }
                    )
                }
            }

        }
        Text(
            text = step,
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
fun StepsSection(
    steps: List<String>,
    onChange: (List<String>) -> Unit
){
    steps.forEachIndexed { index, step ->
        StepCard (
            title = "${index + 1}".padStart(2, '0'), // Automatically generates 01, 02, 03, etc.
            step = step,
            onClick = {
                // Remove this ingredient
                val newList = steps.toMutableList()
                newList.removeAt(index)
                onChange(newList)
            },

        )
    }

}