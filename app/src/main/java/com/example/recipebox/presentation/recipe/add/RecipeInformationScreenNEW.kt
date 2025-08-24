package com.example.recipebox.presentation.recipe.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.ui.theme.MontserratFontFamily
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Preview
@Composable
fun previewwwwwww()
{
    var servings = 0
    RecipeInformationScreen(
        onBack = {},
        onNext = {},
        servings = servings,
        onServingsChange = {})
}

@Composable
fun RecipeInformationScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
    servings: Int,
    onServingsChange: (Int) -> Unit
)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FBF6))
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
            Stepper(activeStep = 2, totalSteps = 4, activeLabel = "Recipe Information")
        }

        Spacer(Modifier.height(28.dp))

        Column (
            modifier = Modifier.padding(8.dp)
        )
        {
            RecipeNameSection()

            Spacer(Modifier.height(8.dp))

            ServingNumberSection(servings = servings, onServingsChange = onServingsChange)

            Spacer(Modifier.height(8.dp))

            CookingTimesSection(
                hours = "",
                minutes = "",
                onTimeChange = { hours, minutes ->  }
            )

            Spacer(Modifier.height(8.dp))

            DifficultySection(
                selectedDifficulty = "Medium",
                onDifficultyChange = {  }
            )

            Spacer(Modifier.height(8.dp))

            DishTypeSection(
                selectedDishTypes = setOf("Breakfast", "Snack", "Dinner"),
                onDishTypesChange = {}
            )

            Spacer(Modifier.height(8.dp))

            SuggestedDietaryTargetSection(
                selectedSuggestedDietaries = setOf("High Fat", "Sugar Free"),
                onSuggestedDietariesChange = {}
            )

            Spacer(Modifier.height(8.dp))

            HashtagsSection(
                hashtags = setOf("egg", "Vegan", "Sugarfree", "lowfat"),
                addHashtag = { hashtag -> },
                removeHashtag = {}
            )
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
fun RecipeFormCard(title: String, content: @Composable ColumnScope.() -> Unit)
{
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF4058A0),
                    )
            )
            {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
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
                        color = Color(0xFF4058A0),
                        shape = RoundedCornerShape(
                            topStart = 8.dp,    // Top-left: square
                            topEnd = 8.dp,      // Top-right: square
                            bottomStart = 0.dp, // Bottom-left: rounded
                            bottomEnd = 0.dp    // Bottom-right: rounded
                        )
                    )
            )

        }

        content()
    }
}

@Composable
fun RecipeNameSection(){
    RecipeFormCard(title = "Name") {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,    // Top-left: square
                        topEnd = 0.dp,      // Top-right: square
                        bottomStart = 8.dp, // Bottom-left: rounded
                        bottomEnd = 8.dp    // Bottom-right: rounded
                    )
                )
        ){

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(
                    text = "Name your recipe",
                    color = Color(0xFFADADAD),
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Thin,
                    fontSize = 12.sp
                ) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(28.dp)
                    ),

                )
        }
    }
}

@Composable
fun ServingNumberSection(
    servings: Int,
    onServingsChange: (Int) -> Unit
){
    RecipeFormCard(title = "Number") {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,    // Top-left: square
                        topEnd = 0.dp,      // Top-right: square
                        bottomStart = 8.dp, // Bottom-left: rounded
                        bottomEnd = 8.dp    // Bottom-right: rounded
                    )
                )
                .padding(20.dp)
        ) {
            Text(
                "Serving",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = MontserratFontFamily
                ),
                modifier = Modifier.padding(end = 8.dp)
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                painter = painterResource(R.drawable.minus),
                contentDescription = "Decrement",
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp)
                    .clickable {
                        if (servings >= 1) onServingsChange(servings - 1)
                    }
            )

            Text(
                text = servings.toString(),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Icon(
                painter = painterResource(R.drawable.add_icon),
                contentDescription = "Increment",
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .padding(start = 8.dp)
                    .clickable {
                        onServingsChange(servings + 1)
                    }
            )
            Spacer(Modifier.width(8.dp))

            Text(
                "People",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = MontserratFontFamily
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun CookingTimesSection(
    hours: String,
    minutes: String,
    onTimeChange: (String, String) -> Unit
) {
    RecipeFormCard(title = "Cook Time") {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,    // Top-left: square
                        topEnd = 0.dp,      // Top-right: square
                        bottomStart = 8.dp, // Bottom-left: rounded
                        bottomEnd = 8.dp    // Bottom-right: rounded
                    )
                )
                .padding(20.dp)
        ) {

            OutlinedTextField(
                value = hours,
                onValueChange = { onTimeChange(it, minutes) },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .width(168.dp)
                    .background(Color(0xFF4058A0))
                    .padding(end = 8.dp),
                suffix = {
                    Text(
                        text = "h",
                        style = TextStyle(
                            color = Color(0xFFADADAD),
                            fontSize = 16.sp,
                            fontFamily = MontserratFontFamily
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            )

            Spacer(Modifier.width(8.dp))

            OutlinedTextField(
                value = minutes,
                onValueChange = { onTimeChange(hours, it) },
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(start = 8.dp)
                    .background(Color(0xFF4058A0)),
                suffix = {
                    Text(
                        text = "m",
                        style = TextStyle(
                            color = Color(0xFFADADAD),
                            fontSize = 16.sp,
                            fontFamily = MontserratFontFamily
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            )
        }
    }
}

@Composable
fun DifficultySection(
    selectedDifficulty: String?,
    onDifficultyChange: (String?) -> Unit
){
    RecipeFormCard(title = "Difficulty") {

        val difficulties = listOf("Easy", "Medium", "Hard")

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,    // Top-left: square
                        topEnd = 0.dp,      // Top-right: square
                        bottomStart = 8.dp, // Bottom-left: rounded
                        bottomEnd = 8.dp    // Bottom-right: rounded
                    )
                )
        ){
            difficulties.forEach { diff ->
                val isSelected = selectedDifficulty == diff
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            if (isSelected) Color(0xFFDEE21B) else Color(0xFF4058A0),
                            shape = RoundedCornerShape(50)
                        )
                        .border(
                            color = Color.White,
                            width = 1.dp,
                            shape = RoundedCornerShape(50)
                        )
                        .clickable { onDifficultyChange(diff) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {

                    Text(
                        text = diff,
                        color = if (isSelected) Color.Black else Color.White,
                        fontSize = 16.sp,
                        fontFamily = MontserratFontFamily
                    )

                }
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun DishTypeSection(
    selectedDishTypes: Set<String>,
    onDishTypesChange: (Set<String>) -> Unit
){
    RecipeFormCard(title = "Dish Type") {

        val dishTypes = listOf(
            "Breakfast", "Lunch", "Snack", "Brunch", "Dessert", "Dinner", "Appetizers"
        )

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,    // Top-left: square
                        topEnd = 0.dp,      // Top-right: square
                        bottomStart = 8.dp, // Bottom-left: rounded
                        bottomEnd = 8.dp    // Bottom-right: rounded
                    )
                )
        ){

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dishTypes.chunked(4).forEach { rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowItems.forEach { type ->
                            val isSelected = type in selectedDishTypes
                            Spacer(Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (isSelected) Color(0xFFDEE21B) else Color(0xFF4058A0),
                                        shape = RoundedCornerShape(50)
                                    )
                                    .border(
                                        color = Color.White,
                                        width = 1.dp,
                                        shape = RoundedCornerShape(50)
                                    )
                                    .clickable {
                                        val newSelection = selectedDishTypes.toMutableSet()
                                        if (isSelected) {
                                            newSelection.remove(type)
                                        } else {
                                            newSelection.add(type)
                                        }
                                        onDishTypesChange(newSelection)
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = type,
                                    color = if (isSelected) Color.Black else Color.White,
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
        }
    }
}

@Composable
fun SuggestedDietaryTargetSection(
    selectedSuggestedDietaries: Set<String>,
    onSuggestedDietariesChange: (Set<String>) -> Unit
){
    RecipeFormCard(title = "Suggested Dietary Target") {

        val suggestedDietaries = listOf(
            "Vegetarian", "High Fat", "Low Fat", "Sugar Free", "Lactose Free", "Gluten Free"
        )

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,    // Top-left: square
                        topEnd = 0.dp,      // Top-right: square
                        bottomStart = 8.dp, // Bottom-left: rounded
                        bottomEnd = 8.dp    // Bottom-right: rounded
                    )
                )
        ){

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                suggestedDietaries.chunked(3).forEach { rowItems ->
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        rowItems.forEach { type ->
                            val isSelected = type in selectedSuggestedDietaries
                            Spacer(Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (isSelected) Color(0xFFDEE21B) else Color(0xFF4058A0),
                                        shape = RoundedCornerShape(50)
                                    )
                                    .border(
                                        color = Color.White,
                                        width = 1.dp,
                                        shape = RoundedCornerShape(50)
                                    )
                                    .clickable {
                                        val newSelection = selectedSuggestedDietaries.toMutableSet()
                                        if (isSelected) {
                                            newSelection.remove(type)
                                        } else {
                                            newSelection.add(type)
                                        }
                                        onSuggestedDietariesChange(newSelection)
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = type,
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontSize = 16.sp,
                                    fontFamily = MontserratFontFamily,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                        }
                        repeat(3 - rowItems.size) {
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun HashtagsSection(
//    hashtags: Set<String>,
//    addHashtag: (String) -> Unit,
//    onHashtagChange: () -> Unit
//) {
//    var hashtag = ""
//    RecipeFormCard(title = "Hashtags") {
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .background(
//                    color = Color(0xFF4058A0),
//                    shape = RoundedCornerShape(
//                        topStart = 8.dp,    // Top-left: square
//                        topEnd = 0.dp,      // Top-right: square
//                        bottomStart = 8.dp, // Bottom-left: rounded
//                        bottomEnd = 8.dp    // Bottom-right: rounded
//                    )
//                )
//        ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                hashtags.chunked(5).forEach { rowItems ->
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        rowItems.forEach { type ->
//                            Spacer(Modifier.width(8.dp))
//                            Text(
//                                text = type,
//                                color = Color.White,
//                                fontSize = 16.sp,
//                                fontFamily = MontserratFontFamily,
//                            )
//                        }
//                        Spacer(Modifier.width(8.dp))
//                    }
//                    repeat(5 - rowItems.size) {
//                    }
//                }
//                Row {
//                    OutlinedTextField(
//                        value = hashtag,
//                        onValueChange = { onHashtagChange() },
//                        modifier = Modifier
//                            .border(
//                                width = 1.dp,
//                                color = Color.White,
//                                shape = RoundedCornerShape(20.dp)
//                            )
//                            .padding(start = 8.dp)
//                            .background(Color(0xFF4058A0)),
//                        suffix = {
//                            Text(
//                                text = "m",
//                                style = TextStyle(
//                                    color = Color(0xFFADADAD),
//                                    fontSize = 16.sp,
//                                    fontFamily = MontserratFontFamily
//                                ),
//                                modifier = Modifier.padding(start = 8.dp)
//                            )
//                        }
//                    )
//                    Button(addHashtag(hashtag)) {
//                        text = "Add hashtag",
//                    }
//                }
//
//            }
//        }
//    }
//}

@Composable
fun HashtagsSection(
    hashtags: Set<String>,
    addHashtag: (String) -> Unit,
    removeHashtag: (String) -> Unit // Added remove functionality
) {
    var hashtag by remember { mutableStateOf("") } // Add state for text field

    RecipeFormCard(title = "Hashtags") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF4058A0),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 0.dp,
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .padding(16.dp)
        ) {
            // Display existing hashtags
            if (hashtags.isNotEmpty()) {
                FlowRow (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    hashtags.forEach { tag ->
                        HashtagChip(
                            tag = tag,
                            onRemove = { removeHashtag(tag) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Add new hashtag input
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = hashtag,
                    onValueChange = { hashtag = it },
                    placeholder = {
                        Text(
                            "Add hashtag...",
                            color = Color(0xFFADADAD),
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF4058A0), RoundedCornerShape(20.dp))
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        ),

                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (hashtag.isNotBlank()) {
                            addHashtag(hashtag)
                            hashtag = "" // Clear input after adding
                        }
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDEE21B)
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        "Add",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = MontserratFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun HashtagChip(
    tag: String,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFDEE21B),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = "#$tag", // Add hashtag symbol
                color = Color.Black,
                fontSize = 14.sp,
                fontFamily = MontserratFontFamily
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton (
                onClick = onRemove,
                modifier = Modifier.size(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = Color.Black,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}