package com.example.data.repository

import com.example.data.datasource.remote.DummyRemoteDataSource
import com.example.domain.model.Dummy
import com.example.domain.repository.DummyRepository
import kotlinx.serialization.InternalSerializationApi
import javax.inject.Inject

@OptIn(InternalSerializationApi::class)
class DummyRepositoryImpl
@Inject
constructor(
    private val dummyRemoteDataSource: DummyRemoteDataSource,
) : DummyRepository {
    override suspend fun getDummy(dummyId: Long): Result<Dummy> =
        runCatching {
            dummyRemoteDataSource.getDummy(dummyId = dummyId).data?.toDummyEntity() ?: throw Exception()
        }
}