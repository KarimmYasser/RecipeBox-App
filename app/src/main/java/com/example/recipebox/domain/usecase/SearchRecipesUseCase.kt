package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.repository.RecipeRepository
import jakarta.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val repo: RecipeRepository
) {
    operator fun invoke(query: String) = repo.searchRecipes(query.trim())
}