package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.repository.CollectionRepository
import jakarta.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val repo: CollectionRepository
) {
    operator fun invoke() = repo.getAllCollections() // Flow<List<RecipeCollection>>
}
