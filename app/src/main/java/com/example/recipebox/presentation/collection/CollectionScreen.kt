package com.example.recipebox.presentation.collection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipebox.R
import com.example.recipebox.domain.model.RecipeCollection
import com.example.recipebox.ui.theme.AqradaFontFamily
import com.example.recipebox.ui.theme.NeutralBlack
import com.example.recipebox.ui.theme.Primary
import com.example.recipebox.ui.theme.RecipeBoxTheme
import com.example.recipebox.ui.theme.Secondary



/* ---------- Route that fetches state from VM ---------- */

@Composable
fun CollectionScreenRoute(
    onCollectionClick: (Long) -> Unit,
    viewModel: CollectionViewModel = hiltViewModel()
) {
    val ui by viewModel.ui.collectAsState()

    CollectionScreen(
        collections = ui.collections,
        onCollectionClick = onCollectionClick,
        onCreateCollection = { name -> viewModel.create(name) }
    )
}

/* ---------- UI ---------- */

@Composable
fun CollectionScreen(
    collections: List<RecipeCollection>,
    onCollectionClick: (Long) -> Unit,
    onCreateCollection: (String) -> Unit
) {
    var showCreate by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {

        // Header bar styled like Figma
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Saved",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = AqradaFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            // Dark rounded (+) button on the right
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .background(NeutralBlack) // #353535
                    .clickable { showCreate = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }

        // Content container padding like mock
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(collections) { c ->
                    CollectionCard(
                        name = c.name,
                        imageRes = c.image,
                        recipeCount = c.recipeIds.size,
                        onClick = { onCollectionClick(c.id) },
                        onOpenClick = { onCollectionClick(c.id) }
                    )
                }
            }
        }
    }

    if (showCreate) {
        CreateCollectionDialog(
            onDismiss = { showCreate = false },
            onCreate = { name ->
                onCreateCollection(name.trim())
                showCreate = false
            }
        )
    }
}

/* ---------- Create dialog ---------- */

@Composable
private fun CreateCollectionDialog(
    onDismiss: () -> Unit,
    onCreate: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    val isValid = name.trim().isNotEmpty()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New collection") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                label = { Text("Collection Name") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (isValid) onCreate(name) },
                enabled = isValid
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

/* ---------- Card (keeps your latest styling) ---------- */

// Add this import if needed:
// import androidx.compose.material.icons.automirrored.outlined.OpenInNew

@Composable
fun CollectionCard(
    name: String,
    imageRes: String,
    recipeCount: Int,               // kept for signature compatibility (unused)
    onClick: () -> Unit,
    onOpenClick: () -> Unit = {}
) {
    // tuned radii & spacing
    val outerRadius = 24.dp
    val frameRadius = 18.dp
    val framePadding = 4.dp          // tighter -> image looks bigger
    val imageRadius = frameRadius - 2.dp
    val imageAspect = 1.35f          // closer to square; raise to 1.5 for wider

    Surface(
        shape = RoundedCornerShape(outerRadius),
        shadowElevation = 6.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(14.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, end = 2.dp), // keeps chip off the rounded edge
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = AqradaFontFamily),
                    modifier = Modifier.weight(1f)
                )

                // ORANGE BOX (as in your screenshot)
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Secondary,                 // #FF6339
                    shadowElevation = 0.dp
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onOpenClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                            contentDescription = "Open",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Soft framed image that blends with the frame
            Surface(
                shape = RoundedCornerShape(frameRadius),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.16f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(Modifier.padding(framePadding)) {
                    Image(
                        painter = painterResource(id = getDrawableByName(imageRes)),
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(imageAspect)
                            .clip(RoundedCornerShape(imageRadius))
                    )
                }
            }
        }
    }
}

/* map string -> drawable */
fun getDrawableByName(name: String): Int = when (name.lowercase()) {
    "all" -> R.drawable.all
    "pizza" -> R.drawable.pizza
    "pastry" -> R.drawable.pastry
    "cake" -> R.drawable.cake
    "cookie" -> R.drawable.cookie
    "gravy" -> R.drawable.gravy
    "soup_collection", "soup" -> R.drawable.soup_collection
    "healthy_food" -> R.drawable.healthy_food
    else -> R.drawable.all
}

/* ---------- Preview ---------- */

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun CollectionScreenPreview() {
    val dummy = listOf(
        RecipeCollection(id = 1, name = "Pizza", image = "pizza", recipeIds = listOf(1,2)),
        RecipeCollection(id = 2, name = "Pastry", image = "pastry", recipeIds = listOf(3)),
        RecipeCollection(id = 3, name = "Soup", image = "soup_collection", recipeIds = listOf(4,5)),
        RecipeCollection(id = 4, name = "Cake", image = "cake", recipeIds = listOf(6)),
    )
    RecipeBoxTheme {
        CollectionScreen(
            collections = dummy,
            onCollectionClick = {},
            onCreateCollection = {}
        )
    }
}
