package com.example.recipebox.domain.model

data class RecipeCollection(
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val image: String = "all",
    val recipeIds: List<Long> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
