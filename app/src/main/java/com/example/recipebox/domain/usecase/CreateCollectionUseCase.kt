package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.RecipeCollection
import com.example.recipebox.domain.repository.CollectionRepository
import jakarta.inject.Inject

class CreateCollectionUseCase @Inject constructor(
    private val repo: CollectionRepository
) {
    suspend operator fun invoke(name: String, description: String = ""): Result<Long> = runCatching {
        require(name.isNotBlank()) { "Collection name cannot be empty" }
        repo.insertCollection(
            RecipeCollection(
                name = name.trim(),
                description = description.trim()
            )
        )
    }
}


