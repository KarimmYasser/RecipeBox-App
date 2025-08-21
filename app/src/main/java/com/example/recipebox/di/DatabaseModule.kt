package com.example.recipebox.di

import android.content.Context
import androidx.room.Room
import com.example.recipebox.data.local.AppDatabase
import com.example.recipebox.data.local.dao.CollectionDao
import com.example.recipebox.data.local.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "recipe_database"
        ).build()
    }
    
    @Provides
    fun provideRecipeDao(database: AppDatabase): RecipeDao {
        return database.recipeDao()
    }
    
    @Provides
    fun provideCollectionDao(database: AppDatabase): CollectionDao {
        return database.collectionDao()
    }
}