package com.example.recipebox.presentation.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.recipebox.R
import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.model.RecipeCollection
import com.example.recipebox.ui.theme.AqradaFontFamily
import com.example.recipebox.ui.theme.Primary


/* ---------- Screen entry ---------- */

@Composable
fun CollectionDetailScreen(
    collectionId: Long,
    navController: NavController,
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val ui by viewModel.ui.collectAsState()
    val recipes by viewModel.recipes.collectAsState()

    LaunchedEffect(collectionId) { viewModel.loadRecipesForCollection(collectionId) }

    val collection = remember(ui.collections, collectionId) {
        ui.collections.firstOrNull { it.id == collectionId }
    }

    when {
        ui.loading && collection == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        collection == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Collection not found")
        }
        else -> CollectionDetailContentScreen(
            collection = collection,
            recipes = recipes,
            onBackClick = { navController.popBackStack() },
            onDeleteClick = {
                viewModel.delete(collection) { navController.popBackStack() }
            },
            onRename = { newName ->
                viewModel.rename(collection, newName)
            },
            onUnsave = { recipeId ->
                viewModel.removeRecipe(recipeId, collection.id)
            }
        )
    }
}

/* ---------- Content ---------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionDetailContentScreen(
    collection: RecipeCollection,
    recipes: List<Recipe>,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onSelectClick: () -> Unit = {},
    onRename: (String) -> Unit = {},
    onUnsave: (Long) -> Unit = {}
) {
    // Sheet + dialogs
    var sheetOpen by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var confirmDelete by rememberSaveable { mutableStateOf(false) }

    // Select mode
    var selectionMode by rememberSaveable { mutableStateOf(false) }
    var selectedIds by rememberSaveable { mutableStateOf(setOf<Long>()) }
    fun toggleSelect(id: Long) {
        selectedIds = if (id in selectedIds) selectedIds - id else selectedIds + id
    }
    fun clearSelection() {
        selectedIds = emptySet()
        selectionMode = false
    }

    // Rename sheet
    var renameOpen by rememberSaveable { mutableStateOf(false) }
    var renameText by rememberSaveable { mutableStateOf(collection.name) }

    // Unsave confirm (per-recipe)
    var pendingUnsaveId by rememberSaveable { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (selectionMode) {
                        Text("${selectedIds.size} selected")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectionMode) clearSelection() else onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (selectionMode) {
                        IconButton(onClick = {
                            // TODO: batch action (e.g., remove selected)
                            clearSelection()
                        }) {
                            Icon(Icons.Default.Done, contentDescription = "Done", tint = Color.White)
                        }
                    } else {
                        Surface(
                            shape = CircleShape,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(40.dp)
                        ) {
                            IconButton(onClick = { sheetOpen = true }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize()
        ) {

            if (!selectionMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = collection.name,
                        style = TextStyle(
                            fontFamily = AqradaFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 20.sp,
                            lineHeight = 24.sp,
                            letterSpacing = 0.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.weight(1f)
                    )

                }
                Spacer(Modifier.height(16.dp))
            }
            // before the grid, handle empty state
            if (recipes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No recipes in this collection yet",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 12.dp)
                ) {
                    items(recipes) { recipe ->
                        val id = recipe.id
                        RecipeCardSelectable(
                            title = recipe.title,
                            imageRes = R.drawable.card, // keep placeholder for now (step 2b below upgrades this)
                            rating = 4.8,
                            selectionMode = selectionMode,
                            selected = id in selectedIds,
                            onClick = { if (selectionMode) toggleSelect(id) /* else onRecipeClick(id) if you added it */ },
                            onLongClick = { selectionMode = true; toggleSelect(id) },
                            onBookmarkClick = {
                                if (selectionMode) toggleSelect(id) else { pendingUnsaveId = id }
                            }
                        )
                    }
                }
            }

        }
    }

    // Bottom sheet menu (Delete / Edit / Select / Text)
    if (sheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { sheetOpen = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                OptionRow(
                    title = "Delete collection",
                    textColor = MaterialTheme.colorScheme.error
                ) {
                    sheetOpen = false
                    confirmDelete = true
                }
                HorizontalDivider(
                    Modifier,
                    DividerDefaults.Thickness,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                OptionRow(title = "Edit collection") {
                    sheetOpen = false
                    renameText = collection.name
                    renameOpen = true
                }
                HorizontalDivider(
                    Modifier,
                    DividerDefaults.Thickness,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
                OptionRow(title = "Select") {
                    sheetOpen = false
                    selectionMode = true
                    onSelectClick()
                }
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { sheetOpen = false /* TODO: “Text” action */ },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth().height(54.dp)
                ) { Text("Text", style = MaterialTheme.typography.titleMedium) }
                Spacer(Modifier.height(12.dp))
            }
        }
    }

    // Confirm delete collection
    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Delete collection?") },
            text = { Text("When you delete this collection, the photo will still be saved.") },
            confirmButton = {
                TextButton(onClick = { confirmDelete = false; onDeleteClick() }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { confirmDelete = false }) { Text("Cancel") } }
        )
    }

    // Unsave confirm (per-recipe)
    pendingUnsaveId?.let { rid ->
        AlertDialog(
            onDismissRequest = { pendingUnsaveId = null },
            title = { Text("Unsave post?") },
            text  = { Text("Unsaving this recipe will also remove it from any collections.") },
            confirmButton = {
                TextButton(onClick = { onUnsave(rid); pendingUnsaveId = null }) { Text("Unsave") }
            },
            dismissButton = {
                TextButton(onClick = { pendingUnsaveId = null }) { Text("Cancel") }
            }
        )
    }

    // Rename sheet (simple “Edit Collection”)
    if (renameOpen) {
        ModalBottomSheet(onDismissRequest = { renameOpen = false }) {
            Column(Modifier.padding(20.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = { renameOpen = false }) { Text("Cancel") }
                    Text("Edit Collection", style = MaterialTheme.typography.titleLarge.copy(fontFamily = AqradaFontFamily))
                    TextButton(
                        onClick = {
                            if (renameText.isNotBlank()) onRename(renameText.trim())
                            renameOpen = false
                        }
                    ) { Text("Done") }
                }
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = renameText,
                    onValueChange = { renameText = it },
                    label = { Text("Collection Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                // You already have a red Delete elsewhere; keep this as extra if you want.
                Button(
                    onClick = { renameOpen = false; confirmDelete = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) { Text("Delete") }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

/* ---------- Small helpers ---------- */

@Composable
private fun OptionRow(
    title: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) { Text(title, color = textColor, style = MaterialTheme.typography.titleMedium) }
}

@Composable
private fun RecipeCardSelectable(
    title: String,
    imageRes: Int,
    rating: Double,
    selectionMode: Boolean,
    selected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .aspectRatio(1f)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // gradient for readability
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.65f)),
                    startY = 120f
                )
            )
        )

        // rating + bookmark/check
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text(rating.toString(), style = MaterialTheme.typography.labelSmall.copy(color = Color.White))
            }

            if (selectionMode) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = if (selected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.6f)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    contentDescription = "Unsave",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onBookmarkClick() }
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White, fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)
        )
    }
}
