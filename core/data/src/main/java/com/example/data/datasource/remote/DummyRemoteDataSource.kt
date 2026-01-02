package com.example.data.datasource.remote

import com.example.network.response.BaseResponse
import com.example.network.response.DummyResponse
import com.example.network.service.DummyService
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class DummyRemoteDataSource
@Inject
constructor(
    private val dummyService: DummyService,
) {
    suspend fun getDummy(dummyId: Long): BaseResponse<DummyResponse> = dummyService.getDummy(dummyId = dummyId)
}