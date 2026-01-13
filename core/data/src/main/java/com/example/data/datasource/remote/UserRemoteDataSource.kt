package com.example.data.datasource.remote

import com.example.data.model.response.UserResponse
import com.example.data.service.UserService
import kotlinx.serialization.InternalSerializationApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class UserRemoteDataSource @Inject constructor(
    private val userService : UserService
) {
    suspend fun patchProfile(
        nickname: String?,
        imageFile: File?,
    ){
        try {
            val imagePart =
                if (imageFile != null) {
                    val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("profileImg", imageFile.name, requestFile)
                } else {
                    null
                }

            userService.patchProfile(
                profileImg = imagePart,
                request = nickname,
            )
        } catch (e: Exception) {
            Timber.e(e)
            throw e
        }
    }

    suspend fun getUser(
        userId: Long,
    ): UserResponse {
        try {
            val response = userService.getUser(
                userId = userId,
            )
            return response.data ?: throw Exception("Data is null")
        } catch (e: Exception) {
            Timber.e(e)
            throw e
        }
    }
}