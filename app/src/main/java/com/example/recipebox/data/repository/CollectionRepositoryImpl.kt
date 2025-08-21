package com.example.recipebox.data.repository

import com.example.recipebox.data.local.dao.CollectionDao
import com.example.recipebox.data.mapper.*
import com.example.recipebox.domain.model.RecipeCollection
import com.example.recipebox.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao
) : CollectionRepository {
    
    override fun getAllCollections(): Flow<List<RecipeCollection>> {
        return collectionDao.getAllCollections().map { collections ->
            collections.map { it.toDomain() }
        }
    }
    
    override suspend fun getCollectionById(id: Long): RecipeCollection? {
        return collectionDao.getCollectionById(id)?.toDomain()
    }
    
    override suspend fun insertCollection(collection: RecipeCollection): Long {
        return collectionDao.insertCollection(collection.toEntity())
    }
    
    override suspend fun updateCollection(collection: RecipeCollection) {
        collectionDao.updateCollection(collection.toEntity())
    }
    
    override suspend fun deleteCollection(collection: RecipeCollection) {
        collectionDao.deleteCollection(collection.toEntity())
    }
    
    override suspend fun addRecipeToCollection(recipeId: Long, collectionId: Long) {
    }
    
    override suspend fun removeRecipeFromCollection(recipeId: Long, collectionId: Long) {
    }
}