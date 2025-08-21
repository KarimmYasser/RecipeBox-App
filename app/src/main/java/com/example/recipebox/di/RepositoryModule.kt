package com.example.recipebox.di

import com.example.recipebox.data.repository.RecipeRepositoryImpl
import com.example.recipebox.data.repository.CollectionRepositoryImpl
import com.example.recipebox.domain.repository.RecipeRepository
import com.example.recipebox.domain.repository.CollectionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository
    
    @Binds
    @Singleton
    abstract fun bindCollectionRepository(
        collectionRepositoryImpl: CollectionRepositoryImpl
    ): CollectionRepository
}