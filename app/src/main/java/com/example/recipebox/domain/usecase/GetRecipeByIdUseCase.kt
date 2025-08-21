package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeByIdUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: Long): Recipe {
        return repository.getRecipeById(recipeId) 
            ?: throw IllegalArgumentException("Recipe with ID $recipeId not found")
    }
}