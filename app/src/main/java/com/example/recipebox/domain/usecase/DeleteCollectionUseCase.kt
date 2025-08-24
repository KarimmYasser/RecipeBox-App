package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.RecipeCollection
import com.example.recipebox.domain.repository.CollectionRepository
import jakarta.inject.Inject

class DeleteCollectionUseCase @Inject constructor(
    private val repo: CollectionRepository
) {
    suspend operator fun invoke(collection: RecipeCollection): Result<Unit> = runCatching {
        repo.deleteCollection(collection)
    }
}
