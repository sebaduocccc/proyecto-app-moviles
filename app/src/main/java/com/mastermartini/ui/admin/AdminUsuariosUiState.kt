package com.mastermartini.ui.admin

import com.mastermartini.data.model.Usuario

data class AdminUsuariosUiState(

    val isLoading: Boolean = true,

    val usuarios: List<Usuario> = emptyList(),

    val query: String = "",

    val usuarioActualId: Int? = null,

    val usuarioAEliminar: Usuario? = null,

    val mensaje: String? = null,

)
