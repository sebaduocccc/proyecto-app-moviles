package com.mastermartini.data.local

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import java.io.File
import java.util.UUID

class ImageStorage(private val context: Context) {

    private val carpeta: File by lazy {
        File(context.filesDir, "imagenes").apply { mkdirs() }
    }

    suspend fun importar(uri: String?): String? {
        if (uri == null) return null
        val original = Uri.parse(uri)
        if (original.scheme != ContentResolver.SCHEME_CONTENT) return uri

        return withContext(Dispatchers.IO) {
            val destino = File(carpeta, "img_${UUID.randomUUID()}.jpg")
            val entrada = context.contentResolver.openInputStream(original)
                ?: throw IOException("No se pudo abrir la imagen")
            entrada.use { input ->
                destino.outputStream().use { output -> input.copyTo(output) }
            }
            Uri.fromFile(destino).toString()
        }
    }

    suspend fun borrar(uri: String?) {
        if (uri == null) return
        val parsed = Uri.parse(uri)
        if (parsed.scheme != "file") return
        withContext(Dispatchers.IO) {
            val ruta = parsed.path ?: return@withContext
            val archivo = File(ruta)
            if (archivo.parentFile == carpeta) archivo.delete()
        }
    }
}
