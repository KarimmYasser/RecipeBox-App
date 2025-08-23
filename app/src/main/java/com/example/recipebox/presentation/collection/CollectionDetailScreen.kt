package com.example.recipebox.presentation.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.model.RecipeCollection
import com.example.recipebox.ui.theme.AqradaFontFamily
import com.example.recipebox.ui.theme.Primary
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.recipebox.R

@Composable
fun CollectionDetailScreen(
    collectionId: Long,
    navController: NavController,
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val collection = viewModel.getCollectionById(collectionId)

    if (collection == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val savedCount = 3 // TODO: get from VM

    CollectionDetailContentScreen(
        collection = collection,
        savedCount = savedCount,
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionDetailContentScreen(
    collection: RecipeCollection,
    savedCount: Int,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onSelectClick: () -> Unit = {}
) {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = CircleShape,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(40.dp)
                    ) {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(text = { Text("Edit collection") }, onClick = {
                            menuExpanded = false
                            onEditClick()
                        })
                        DropdownMenuItem(text = { Text("Delete collection") }, onClick = {
                            menuExpanded = false
                            onDeleteClick()
                        })
                        DropdownMenuItem(text = { Text("Select") }, onClick = {
                            menuExpanded = false
                            onSelectClick()
                        })
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
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            ) {
                Column(Modifier.padding(16.dp)) {
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

                        Text(
                            text = if (savedCount == 1) "1 saved post" else "$savedCount saved posts",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 12.dp)
                    ) {
                        items(sampleRecipes) { recipe ->
                            RecipeCard(
                                title = recipe.title,
                                imageRes = R.drawable.card, // Placeholder for now
                                rating = 4.8,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                }
            }
        }
    }
}
@Composable
fun RecipeCard(
    title: String,
    imageRes: Int,
    rating: Double,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .aspectRatio(1f)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 100f
                    )
                )
        )

        // Top icons: rating and bookmark
        //this icons need to be functionally
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = rating.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.White)
                )
            }

            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        // Title at bottom
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        )
    }
}

val sampleRecipes = listOf(
    Recipe(
        id = 1,
        title = "Oreo Cheesecake",
        description = "",
        servings = 4,
        cookTimeHours = "1",
        cookTimeMinutes = "30",
        difficulty = null,
        dishTypes = emptySet(),
        dietTypes = emptySet(),
        imageUri = null
    ),
    Recipe(id = 2, title = "Classic Brownie", description = "", servings = 4, cookTimeHours = "1", cookTimeMinutes = "0", difficulty = null, dishTypes = emptySet(), dietTypes = emptySet(), imageUri = null),
    Recipe(id = 3, title = "Nutella Cookie", description = "", servings = 2, cookTimeHours = "0", cookTimeMinutes = "45", difficulty = null, dishTypes = emptySet(), dietTypes = emptySet(), imageUri = null)
)

@Preview(showBackground = true)
@Composable
fun CollectionDetailScreenPreview() {
    RecipeBoxTheme {
        CollectionDetailContentScreen(
            collection = RecipeCollection(
                id = 1,
                name = "Cookies",
                description = "Sweet and delicious treats"
            ),
            savedCount = 3
        )
    }
}
