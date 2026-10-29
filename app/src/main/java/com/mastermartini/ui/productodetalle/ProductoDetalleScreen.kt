package com.mastermartini.ui.productodetalle


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mastermartini.data.model.Producto
import com.mastermartini.data.model.Receta
import com.mastermartini.ui.theme.Background
import com.mastermartini.ui.theme.MasterMartiniTheme
import com.mastermartini.ui.home.RecetaCard
import com.mastermartini.ui.theme.RecetaTextSoft

@Composable
fun ProductoDetalleScreen(
    uiState: ProductoDetalleUiState,
    onClose: () -> Unit,
    onRecetaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val producto = uiState.producto

    when {
        uiState.isLoading -> Text("Cargando...", modifier)
        producto == null -> Text("Producto no encontrado", modifier)
        else -> ProductoDetalleContent(
            producto = producto,
            recetas = uiState.recetas,
            onClose = onClose,
            onRecetaClick = onRecetaClick,
            modifier = modifier,
        )
    }
}

@Composable
private fun ProductoDetalleContent(
    producto: Producto,
    recetas: List<Receta>,
    onClose: () -> Unit,
    onRecetaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { Header(producto, onClose) }

        item {
            Text(
                text = "Recetas relacionadas",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        }

        if (recetas.isEmpty()) {
            item {
                Text(
                    text = "Este producto aún no tiene recetas",
                    color = RecetaTextSoft,
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
            }
        } else {
            items(recetas, key = { it.id }) { receta ->
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    RecetaCard(receta, onClick = { onRecetaClick(receta.id) })
                }
            }
        }
    }
}

@Composable
private fun Header(producto: Producto, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {

        AsyncImage(
            model = producto.imageUri,
            contentDescription = producto.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp

                    )
                )
        )

        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 260.dp),
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = producto.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "$${producto.price}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        IconButton(
            onClick = onClose,
            shape = RoundedCornerShape(8.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = Color(0xFFEDEDED).copy(alpha = 0.9f),
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Cerrar",
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductoDetallePreview() {
    MasterMartiniTheme {
        ProductoDetalleScreen(
            uiState = ProductoDetalleUiState(
                isLoading = false,
                producto = Producto(1, "Chocolate", 0, null),
                recetas = listOf(
                    Receta(
                        1,
                        "Pastel Chocolate",
                        "Master Martini",
                        null,
                        60,
                        emptyList(),
                        emptyList(),
                    )
                ),
            ),
            onClose = {},
            onRecetaClick = {},
        )
    }
}