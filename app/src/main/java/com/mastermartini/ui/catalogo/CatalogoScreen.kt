package com.mastermartini.ui.catalogo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mastermartini.R
import com.mastermartini.data.model.Producto
import com.mastermartini.ui.theme.MasterMartiniTheme


@Composable
fun CatalogoScreen(
    uiState: CatalogoUiState,

    onQueryChange: (String) -> Unit,

    modifier: Modifier = Modifier
) {

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
                placeholder = { Text("Buscar productos") },
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

        /* Tarjetas de productos */
        items(uiState.productos, key = { it.id }) { producto ->
            ProductoCard(producto)
        }
    }
}

@Composable
fun ProductoCard(producto: Producto) {

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(producto.imageRes),
                contentDescription = producto.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = producto.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "$${producto.price}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CatalogoPreview() {
    MasterMartiniTheme {
        CatalogoScreen(
            uiState = CatalogoUiState(
                isLoading = false,
                productos = listOf(
                    Producto(1, "Chocolate", 0, R.drawable.pastelchocolate)
                ),
            ),
            onQueryChange = {},
        )
    }
}