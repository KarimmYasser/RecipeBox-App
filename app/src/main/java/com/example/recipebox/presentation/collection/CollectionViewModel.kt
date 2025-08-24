package com.example.recipebox.presentation.collection


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.model.RecipeCollection
import com.example.recipebox.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CollectionsUiState(
    val collections: List<RecipeCollection> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val getCollections: GetCollectionsUseCase,
    private val createCollection: CreateCollectionUseCase,
    private val editCollection: EditCollectionUseCase,
    private val addToCollection: AddToCollectionUseCase,
    private val removeFromCollection: RemoveFromCollectionUseCase,
    private val getRecipesInCollection: GetRecipesInCollectionUseCase,
    private val deleteCollection: DeleteCollectionUseCase
) : ViewModel() {

    private val _ui = MutableStateFlow(CollectionsUiState(loading = true))
    val ui: StateFlow<CollectionsUiState> = _ui.asStateFlow()
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    fun loadRecipesForCollection(collectionId: Long) {
        viewModelScope.launch {
            getRecipesInCollection(collectionId).collect { list ->
                _recipes.value = list
            }
        }
    }
    init {
        viewModelScope.launch {
            getCollections().collect { list ->
                _ui.update { it.copy(collections = list, loading = false, error = null) }
            }
        }
    }
    fun getCollectionById(id: Long): RecipeCollection? {
        return ui.value.collections.find { it.id == id }
    }
    fun create(name: String, description: String = "") = viewModelScope.launch {
        createCollection(name, description)
            .onFailure { _ui.update { it.copy(error = null) } }
    }

    fun rename(collection: RecipeCollection, newName: String) = viewModelScope.launch {
        editCollection(collection.copy(name = newName))
            .onFailure { _ui.update { it.copy(error = null) } }
    }

    fun addRecipe(recipeId: Long, collectionId: Long) = viewModelScope.launch {
        addToCollection(recipeId, collectionId)
            .onFailure { _ui.update { it.copy(error = null) } }
    }

    fun removeRecipe(recipeId: Long, collectionId: Long) = viewModelScope.launch {
        removeFromCollection(recipeId, collectionId)
            .onFailure { _ui.update { it.copy(error = null) } }
    }
    fun delete(collection: RecipeCollection, onDone: () -> Unit = {}) = viewModelScope.launch {
        deleteCollection(collection)
            .onSuccess { onDone() }
            .onFailure { _ui.update { it.copy(error = null) } }
    }
    fun setError(error: String) { _ui.update { it.copy(error = error) } }
    fun clearError() { _ui.update { it.copy(error = null) } }


}

private fun <T> Result<T>.errorMessage(): String =
    exceptionOrNull()?.message ?: "Something went wrong"

