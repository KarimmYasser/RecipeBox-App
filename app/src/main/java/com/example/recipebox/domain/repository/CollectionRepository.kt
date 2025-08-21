package com.example.recipebox.domain.repository

import com.example.recipebox.domain.model.RecipeCollection
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getAllCollections(): Flow<List<RecipeCollection>>
    suspend fun getCollectionById(id: Long): RecipeCollection?
    suspend fun insertCollection(collection: RecipeCollection): Long
    suspend fun updateCollection(collection: RecipeCollection)
    suspend fun deleteCollection(collection: RecipeCollection)
    suspend fun addRecipeToCollection(recipeId: Long, collectionId: Long)
    suspend fun removeRecipeFromCollection(recipeId: Long, collectionId: Long)
}