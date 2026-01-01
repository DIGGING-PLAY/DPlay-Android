package com.example.network.service

import com.example.network.NetworkConstants
import com.example.network.response.BaseResponse
import com.example.network.response.DummyResponse
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Path

@OptIn(InternalSerializationApi::class)
interface DummyService {
    @GET("${NetworkConstants.API}/${NetworkConstants.VERSIONS}/dummy/{dummyId}")
    suspend fun getDummy(
        @Path("dummyId") dummyId: Long,
    ): BaseResponse<DummyResponse>
}