package com.mastermartini.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mastermartini.R
import com.mastermartini.data.model.Receta
import com.mastermartini.ui.theme.MasterMartiniTheme

@Composable
fun HomeScreen(
    uiState: HomeUiState,

    onQueryChange: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onRecetaClick: (Int) -> Unit,

    modifier: Modifier = Modifier
) {

    val categories = listOf("Destacados", "Nuevo", "Popular")

    LazyColumn(
        modifier = modifier.fillMaxSize(),

        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        /* Título Master Martini */
        item {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo de Master Martini",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                Text(
                    text = "Master Martini",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        /* Buscador */
        item {
            OutlinedTextField(
                value = uiState.query,
                onValueChange = onQueryChange,
                placeholder = { Text("Buscar recetas") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },

                singleLine = true,

                shape = RoundedCornerShape(50),

                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                ),

                modifier = Modifier.fillMaxWidth()
            )
        }

        /* Subtítulo Categoría y Ver Todos */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Categoría",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text("Ver todos", color = MaterialTheme.colorScheme.onBackground)
            }
        }

        /* Botones de categorías */
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                categories.forEach { name ->
                    val isSelected = name == uiState.selectedCategory

                    FilterChip(
                        selected = isSelected,
                        onClick = { onCategorySelected(name) },
                        label = { Text(
                            text = name,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        },
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.width(110.dp),

                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.White,
                            labelColor = MaterialTheme.colorScheme.primary,
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),

                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = MaterialTheme.colorScheme.tertiary,
                            selectedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }

        /* Tarjetas de recetas */
        items(uiState.recetas, key = { it.id }) { receta ->
            RecetaCard(receta, onClick = { onRecetaClick(receta.id) })
        }
    }
}

@Composable
fun RecetaCard(

    receta: Receta,
    onClick: () -> Unit,

) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column {
            Image(
                painter = painterResource(receta.imageRes),
                contentDescription = receta.title,
                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = receta.title,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = receta.author,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    MasterMartiniTheme {
        HomeScreen(
            uiState = HomeUiState(
                isLoading = false,
                recetas = listOf(
                    Receta(
                        1,
                        "Pastel Chocolate",
                        "Master Martini",
                        R.drawable.pastelchocolate,
                        60,
                        emptyList(),
                        emptyList(),
                    )
                ),
            ),
            onQueryChange = {},
            onCategorySelected = {},
            onRecetaClick = {},
        )
    }
}