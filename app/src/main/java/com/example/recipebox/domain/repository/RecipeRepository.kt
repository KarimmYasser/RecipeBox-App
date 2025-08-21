package com.example.recipebox.domain.repository

import com.example.recipebox.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun getRecipeById(id: Long): Recipe?
    suspend fun insertRecipe(recipe: Recipe): Long
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
    fun searchRecipes(query: String): Flow<List<Recipe>>
}