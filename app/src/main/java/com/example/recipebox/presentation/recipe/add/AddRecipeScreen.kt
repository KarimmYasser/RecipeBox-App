package com.example.recipebox.presentation.recipe.add

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.recipebox.presentation.recipe.detail.RecipeDetailScreen
import com.example.recipebox.presentation.recipe.detail.RecipeDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    navController: NavController,
    viewModel: AddRecipeViewModel = hiltViewModel()
) {
    val recipeData by viewModel.recipeData.collectAsState()
    val currentStep by viewModel.currentStep.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // If recipe saved successfully → navigate to detail screen
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            uiState.createdRecipeId?.let { recipeId ->
                navController.navigate("recipe_detail/$recipeId") {
                    popUpTo("add_recipe") { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        // Now include RecipeImageScreen as the FIRST STEP (step 0)
        when (currentStep) {
            0 -> RecipeImageScreen(
                onBack = { navController.popBackStack() },
                onRemove = { viewModel.updateImageUri(null) },
                onSave = {
                    // Save the selected image and move to Recipe Info step
                    viewModel.nextStep()
                },
            )

            1 -> RecipeInformationScreen(
                onBack = { viewModel.previousStep() },
                onNext = { viewModel.nextStep() },
                servings = recipeData.servings,
                onServingsChange = viewModel::updateServings
            )

            2 -> RecipeIngredientsScreen(
                onBack = { viewModel.previousStep() },
                onNext = { viewModel.nextStep() }
            )

            3 -> RecipeStepsScreen(
                onBack = { viewModel.previousStep() },
                onNext = { viewModel.nextStep() }
            )

            4 -> RecipeDetailsScreen(
                onBack = { viewModel.previousStep() },
                onNext = { viewModel.nextStep() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show bottom navigation buttons only for steps >= 1 (skip on RecipeImageScreen)
        if (currentStep > 0) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { viewModel.previousStep() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Back")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (currentStep < 4) {
                            viewModel.nextStep()
                        } else {
                            viewModel.saveRecipe()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (currentStep < 4) "Next" else "Finish")
                }
            }
        }


        // Show loading when saving recipe
        if (uiState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}
