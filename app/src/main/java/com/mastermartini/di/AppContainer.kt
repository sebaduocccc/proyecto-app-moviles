package com.mastermartini.di

import android.content.Context
import androidx.room.Room
import com.mastermartini.data.local.AppDatabase
import com.mastermartini.data.repository.FakeProductoRepository
import com.mastermartini.data.repository.FakeRecetaRepository
import com.mastermartini.data.repository.ProductoRepository
import com.mastermartini.data.repository.RecetaRepository
import com.mastermartini.data.repository.RoomUsuarioRepository
import com.mastermartini.data.repository.UsuarioRepository

class AppContainer(context: Context) {

    private val database: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "master_martini.db")
            .fallbackToDestructiveMigration(true).build()

    val recetaRepository: RecetaRepository = FakeRecetaRepository()
    val productoRepository: ProductoRepository = FakeProductoRepository()
    val usuarioRepository: UsuarioRepository = RoomUsuarioRepository(context, database.usuarioDao())
}
