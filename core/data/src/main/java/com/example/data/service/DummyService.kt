package com.example.data.service

import com.example.data.ApiConstants
import com.example.data.model.response.BaseResponse
import com.example.data.model.response.DummyResponse
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Path

@OptIn(InternalSerializationApi::class)
interface DummyService {
    @GET("${ApiConstants.API}/${ApiConstants.VERSIONS}/dummy/{dummyId}")
    suspend fun getDummy(
        @Path("dummyId") dummyId: Long,
    ): BaseResponse<DummyResponse>
}
