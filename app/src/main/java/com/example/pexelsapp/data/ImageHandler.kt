package com.example.pexelsapp.data

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageHandler @Inject constructor(private val context: Context) {

    suspend fun saveImageToInternalStorage(url: String, imageName: String): Uri? {
        val bitmap = loadImage(url)
        return if (bitmap != null) {
            val directory = context.filesDir
            saveImage(bitmap, imageName, directory)
        } else {
            null
        }
    }

    suspend fun saveImageToGallery(url: String, imageName: Int): Uri? {
        val bitmap = loadImage(url)
        return if (bitmap != null) {
            val picturesDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            if (!picturesDirectory.exists()) {
                picturesDirectory.mkdirs()
            }
            saveImage(bitmap, imageName.toString(), picturesDirectory)
        } else {
            null
        }
    }

    private suspend fun loadImage(url: String): Bitmap? {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        val result = ImageLoader.Builder(context).build().execute(request)

        return if (result is SuccessResult) {
            result.drawable.toBitmap()
        } else {
            null
        }
    }


    private fun saveImage(bitmap: Bitmap, imageName: String, directory: File): Uri? {
        return try {
            val timestamp = System.currentTimeMillis()
            val file = File(directory, "$imageName-$timestamp.jpg")
            val uri = Uri.fromFile(file)
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            uri
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
