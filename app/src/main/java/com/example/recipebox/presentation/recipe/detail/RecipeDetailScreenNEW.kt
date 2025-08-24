package com.example.recipebox.presentation.recipe.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipebox.presentation.recipe.add.RecipeStepsScreen

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
){

}