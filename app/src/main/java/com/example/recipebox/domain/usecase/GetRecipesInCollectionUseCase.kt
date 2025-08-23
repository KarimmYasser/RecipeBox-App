package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesInCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    operator fun invoke(collectionId: Long): Flow<List<Recipe>> {
        return collectionRepository.getRecipesInCollection(collectionId)
    }
}
