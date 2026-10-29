package com.mastermartini.ui.recetadetalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
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
import com.mastermartini.data.model.Paso
import com.mastermartini.data.model.Receta
import com.mastermartini.ui.theme.Background
import com.mastermartini.ui.theme.MasterMartiniTheme
import com.mastermartini.ui.theme.RecetaTextSoft

@Composable
fun RecetaDetalleScreen(
    uiState: RecetaDetalleUiState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val receta = uiState.receta

    when {
        uiState.isLoading -> Text("Cargando...", modifier)
        receta == null -> Text("Receta no encontrada", modifier)
        else -> RecetaDetalleContent(receta, onClose, modifier)
    }
}

@Composable
private fun RecetaDetalleContent(
    receta: Receta,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item { Header(receta, onClose) }

        receta.videoId?.let { videoId ->
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "Video",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    YouTubePlayer(
                        videoId = videoId,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(16.dp)),
                    )
                }
            }
        }
        itemsIndexed(receta.pasos) { index, paso ->
            PasoItem(numero = index + 1, paso = paso)
        }
    }
}

@Composable
private fun Header(receta: Receta, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {

        AsyncImage(
            model = receta.imageUri,
            contentDescription = receta.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 12.dp,
                        bottomEnd = 12.dp,
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = receta.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f),
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = "${receta.tiempoMin} min",
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
                Text(
                    text = "Ingredientes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    receta.ingredientes.forEach { ingrediente ->
                        IngredienteItem(ingrediente)
                    }
                }
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

@Composable
private fun IngredienteItem(texto: String) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
                .background(MaterialTheme.colorScheme.primary),
        )
        Text(
            text = texto,
            color = RecetaTextSoft,
            modifier = Modifier.padding(start = 10.dp),
        )
    }
}

@Composable
private fun PasoItem(numero: Int, paso: Paso) {
    val colorTitulo = if (numero % 2 == 1) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Paso $numero",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorTitulo,
        )
        Text(
            text = paso.texto,
            color = MaterialTheme.colorScheme.onBackground,
        )
        paso.imageUri?.let { imageUri ->
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecetaDetallePreview() {
    MasterMartiniTheme {
        RecetaDetalleScreen(
            uiState = RecetaDetalleUiState(
                isLoading = false,
                receta = Receta(
                    1,
                    "Pastel Chocolate",
                    "Master Martini",
                    null,
                    60,
                    listOf("200g de chocolate", "huevos", "harina"),
                    listOf(
                        Paso("Mezcla todo"),
                        Paso("Derrite chocoalte"),
                        Paso("Hornea"),
                        Paso("comeee"),
                    ),
                ),
            ),
            onClose = {},
        )
    }
}