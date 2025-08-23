package com.example.recipebox.di

import com.example.recipebox.domain.repository.RecipeRepository
import com.example.recipebox.domain.repository.CollectionRepository
import com.example.recipebox.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// di/UseCaseModule.kt
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides @Singleton
    fun provideAddRecipeUseCase(repo: RecipeRepository) = AddRecipeUseCase(repo)

    @Provides @Singleton
    fun provideGetRecipeByIdUseCase(repo: RecipeRepository) = GetRecipeByIdUseCase(repo)

    @Provides @Singleton
    fun provideSearchRecipesUseCase(repo: RecipeRepository) = SearchRecipesUseCase(repo)

    @Provides @Singleton
    fun provideGetCollectionsUseCase(repo: CollectionRepository) = GetCollectionsUseCase(repo)

    @Provides @Singleton
    fun provideCreateCollectionUseCase(repo: CollectionRepository) = CreateCollectionUseCase(repo)

    @Provides @Singleton
    fun provideEditCollectionUseCase(repo: CollectionRepository) = EditCollectionUseCase(repo)

    @Provides @Singleton
    fun provideAddToCollectionUseCase(repo: CollectionRepository) = AddToCollectionUseCase(repo)

    @Provides @Singleton
    fun provideRemoveFromCollectionUseCase(repo: CollectionRepository) = RemoveFromCollectionUseCase(repo)
}
