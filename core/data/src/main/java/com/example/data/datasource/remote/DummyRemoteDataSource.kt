package com.example.data.datasource.remote

import com.example.data.model.response.BaseResponse
import com.example.data.model.response.DummyResponse
import com.example.data.service.DummyService
import javax.inject.Inject

class DummyRemoteDataSource
    @Inject
    constructor(
        private val dummyService: DummyService,
    ) {
        suspend fun getDummy(dummyId: Long): BaseResponse<DummyResponse> = dummyService.getDummy(dummyId = dummyId)
    }
