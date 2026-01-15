package com.example.data.datasource.remote

import com.example.data.model.request.RegisterPostRequest
import com.example.data.model.response.PostResponse
import com.example.data.service.PostService
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class PostRemoteDataSource
@Inject
constructor(
    private val postService: PostService,
){
    suspend fun registerPost(
        request: RegisterPostRequest
    ): PostResponse{
        try{
            val response = postService.registerPost(
                request = request
            )
            return response.data ?: throw Exception("Data is null")
        } catch(e: Exception){
            throw e
        }
    }
}