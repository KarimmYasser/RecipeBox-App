package com.example.recipebox.presentation.recipe.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipebox.R
import com.example.recipebox.ui.theme.AqradaFontFamily
import com.example.recipebox.ui.theme.MontserratFontFamily

@Preview
@Composable
fun previewwww()
{
    RecipeImageScreen(
        onBack = {},
        onRemove = {},
        onSave = {}
    )
}

@Composable
fun RecipeImageScreen(
    onBack: () -> Unit,
    onRemove: () -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FBF6)),
//            .padding(16.dp),
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
            Stepper(activeStep = 1, totalSteps = 4, activeLabel = "Recipe Image")
        }

        Spacer(Modifier.height(28.dp))

        Text(
            text = "Preview",
            fontSize = 20.sp,
            fontFamily = AqradaFontFamily,
        )
        Spacer(Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.pancakes),
            contentDescription = "Pancakes",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(32.dp))

        OutlinedButton(
            onClick = onRemove,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .width(160.dp)
                .height(48.dp)
        ) {
            Text(
                text = "Remove",
                color = Color(0xFF353535),
                fontFamily = MontserratFontFamily,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onSave,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                text = "Save",
                color = Color.White,
                fontFamily = MontserratFontFamily,
                fontSize = 16.sp
            )
        }
    }
}