package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.repository.CollectionRepository
import jakarta.inject.Inject


class RemoveFromCollectionUseCase @Inject constructor(
    private val repo: CollectionRepository
) {
    suspend operator fun invoke(recipeId: Long, collectionId: Long): Result<Unit> = runCatching {
        repo.removeRecipeFromCollection(recipeId, collectionId)
    }
}
