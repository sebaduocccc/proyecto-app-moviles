package com.mastermartini.ui.admin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastermartini.data.model.Paso
import com.mastermartini.data.model.Receta
import com.mastermartini.data.repository.RecetaRepository
import com.mastermartini.ui.navigation.AdminRecetaFormDestination
import com.mastermartini.validation.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminRecetaFormViewModel(
    savedStateHandle: SavedStateHandle,

    private val repository: RecetaRepository,
) : ViewModel() {

    private val recetaId: Int =
        savedStateHandle[AdminRecetaFormDestination.ARG_RECETA_ID] ?: AdminRecetaFormDestination.NUEVO

    private val esNueva = recetaId == AdminRecetaFormDestination.NUEVO

    private val _uiState = MutableStateFlow(
        AdminRecetaFormUiState(esNueva = esNueva, isLoading = !esNueva)
    )

    val uiState: StateFlow<AdminRecetaFormUiState> = _uiState.asStateFlow()

    private var recetaOriginal: Receta? = null

    init {
        if (!esNueva) cargarReceta()
    }

    private fun cargarReceta() {
        viewModelScope.launch {
            val receta = repository.getReceta(recetaId)
            if (receta == null) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "No se encontró la receta") }
                return@launch
            }
            recetaOriginal = receta

            _uiState.update {
                it.copy(
                    isLoading = false,
                    titulo = receta.title,
                    tiempoMin = receta.tiempoMin.toString(),
                    imageUri = receta.imageUri,
                    videoId = receta.videoId.orEmpty(),
                    ingredientes = receta.ingredientes.ifEmpty { listOf("") },
                    pasos = receta.pasos.ifEmpty { listOf(Paso("")) },
                )
            }
        }
    }

    fun onTituloChange(valor: String) {
        _uiState.update { it.copy(titulo = valor, tituloError = null, errorMessage = null) }
    }

    fun onTiempoChange(valor: String) {
        _uiState.update { it.copy(tiempoMin = valor, tiempoError = null, errorMessage = null) }
    }

    fun onImagenChange(uri: String) {
        _uiState.update { it.copy(imageUri = uri, imagenError = null, errorMessage = null) }
    }

    fun onVideoIdChange(valor: String) {
        _uiState.update { it.copy(videoId = valor, videoError = null, errorMessage = null) }
    }

    fun onIngredienteChange(index: Int, valor: String) {
        _uiState.update { s ->
            s.copy(
                ingredientes = s.ingredientes.mapIndexed { i, actual -> if (i == index) valor else actual },
                ingredientesError = null,
                errorMessage = null
            )
        }
    }

    fun onAgregarIngrediente() {
        _uiState.update { it.copy(ingredientes = it.ingredientes + "") }
    }

    fun onEliminarIngrediente(index: Int) {
        _uiState.update { s ->
            s.copy(ingredientes = s.ingredientes.filterIndexed { i, _ -> i != index })
        }
    }

    fun onPasoTextoChange(index: Int, texto: String) {
        _uiState.update { s ->
            s.copy(
                pasos = s.pasos.mapIndexed { i, paso -> if (i == index) paso.copy(texto = texto) else paso },
                pasosError = null,
                errorMessage = null
            )
        }
    }

    fun onPasoImagenChange(index: Int, uri: String?) {
        _uiState.update { s ->
            s.copy(pasos = s.pasos.mapIndexed { i, paso -> if (i == index) paso.copy(imageUri = uri) else paso })
        }
    }

    fun onAgregarPaso() {
        _uiState.update { it.copy(pasos = it.pasos + Paso("")) }
    }

    fun onEliminarPaso(index: Int) {
        _uiState.update { s ->
            s.copy(pasos = s.pasos.filterIndexed { i, _ -> i != index })
        }
    }


    fun onGuardarClick() {
        val state = _uiState.value

        val ingredientes = state.ingredientes.map { it.trim() }.filter { it.isNotEmpty() }
        val pasos = state.pasos
            .filter { it.texto.isNotBlank() }
            .map { it.copy(texto = it.texto.trim()) }

        val validado = state.copy(
            tituloError = FormValidator.validarTituloReceta(state.titulo),
            tiempoError = FormValidator.validarTiempoMin(state.tiempoMin),
            imagenError = FormValidator.validarImagen(state.imageUri),
            videoError = FormValidator.validarVideoId(state.videoId),
            ingredientesError = FormValidator.validarIngredientes(ingredientes),
            pasosError = FormValidator.validarPasos(pasos),
        )

        if (validado.tieneErrores()) {
            _uiState.value = validado
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }

            try {
                val original = recetaOriginal
                val titulo = state.titulo.trim()
                val tiempo = state.tiempoMin.trim().toInt()
                val video = state.videoId.trim().ifEmpty { null }

                if (original == null) {
                    repository.crearReceta(
                        Receta(
                            title = titulo,
                            author = AUTOR_POR_DEFECTO,
                            imageUri = state.imageUri,
                            tiempoMin = tiempo,
                            ingredientes = ingredientes,
                            pasos = pasos,
                            videoId = video,
                        )
                    )
                } else {
                    repository.actualizarReceta(
                        original.copy(
                            title = titulo,
                            imageUri = state.imageUri,
                            tiempoMin = tiempo,
                            ingredientes = ingredientes,
                            pasos = pasos,
                            videoId = video,
                        )
                    )
                }
                _uiState.update { it.copy(isSaving = false, guardado = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, errorMessage = "No se pudo guardar la receta") }
            }
        }
    }

    private fun AdminRecetaFormUiState.tieneErrores(): Boolean = listOf(
        tituloError, tiempoError, imagenError, videoError, ingredientesError, pasosError
    ).any { it != null }

    private companion object {
        const val AUTOR_POR_DEFECTO = "Master Martini"
    }
}