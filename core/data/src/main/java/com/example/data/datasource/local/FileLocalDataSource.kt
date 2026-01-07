package com.example.data.datasource.local

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import androidx.core.net.toUri

class FileLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getFileFromUri(uriString: String?): File? {
        if (uriString.isNullOrEmpty()) return null

        try {
            val uri = uriString.toUri()

            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            val tempFile = File.createTempFile("upload_${System.currentTimeMillis()}", ".jpg", context.cacheDir)

            inputStream.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }

            return tempFile

        } catch (e: Exception) {
            return null
        }
    }
}