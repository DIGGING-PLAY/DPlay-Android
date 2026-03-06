package com.example.data.datasource.local

import android.content.Context
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileLocalDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        suspend fun createAndGetFile(uriString: String?): File? {
            if (uriString.isNullOrEmpty()) return null

            return withContext(Dispatchers.IO) {
                try {
                    val uri = uriString.toUri()
                    val inputStream = context.contentResolver.openInputStream(uri) ?: return@withContext null

                    val fileName = "profile_${System.currentTimeMillis()}.jpg"
                    val file = File(context.filesDir, fileName)

                    inputStream.use { input ->
                        FileOutputStream(file).use { output ->
                            input.copyTo(output)
                        }
                    }

                    file
                } catch (e: Exception) {
                    null
                }
            }
        }

        fun createEmptyFile(): File {
            val emptyFile = File(context.cacheDir, "empty_profile_temp")

            if (emptyFile.exists()) {
                emptyFile.delete()
            }
            emptyFile.createNewFile()

            return emptyFile
        }
    }
