package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.model.Ingredient
import com.example.recipebox.domain.model.Step
import com.example.recipebox.domain.repository.RecipeRepository
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        hashtags: String = "",
        servings: Int,
        cookTimeHours: String,
        cookTimeMinutes: String,
        difficulty: String?,
        dishTypes: Set<String>,
        dietTypes: Set<String>,
        imageUri: String?,
        ingredientNames: List<String>,
        stepDescriptions: List<String>
    ): Result<Long> {
        return try {
            if (title.isBlank()) {
                return Result.failure(IllegalArgumentException("Recipe title cannot be empty"))
            }
            
            if (ingredientNames.isEmpty() || ingredientNames.all { it.isBlank() }) {
                return Result.failure(IllegalArgumentException("Recipe must have at least one ingredient"))
            }
            
            if (stepDescriptions.isEmpty() || stepDescriptions.all { it.isBlank() }) {
                return Result.failure(IllegalArgumentException("Recipe must have at least one step"))
            }
            
            val ingredients = ingredientNames
                .filter { it.isNotBlank() }
                .mapIndexed { index, name ->
                    Ingredient(
                        name = name.trim(),
                        order = index
                    )
                }
            
            val steps = stepDescriptions
                .filter { it.isNotBlank() }
                .mapIndexed { index, description ->
                    Step(
                        description = description.trim(),
                        order = index
                    )
                }
            
            val recipe = Recipe(
                title = title.trim(),
                description = description.trim(),
                hashtags = hashtags.trim(),
                servings = servings,
                cookTimeHours = cookTimeHours,
                cookTimeMinutes = cookTimeMinutes,
                difficulty = difficulty,
                dishTypes = dishTypes,
                dietTypes = dietTypes,
                imageUri = imageUri,
                ingredients = ingredients,
                steps = steps
            )
            
            val recipeId = repository.insertRecipe(recipe)
            Result.success(recipeId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}