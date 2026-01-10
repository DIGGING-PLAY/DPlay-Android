package com.example.domain.repository

import com.example.domain.model.Dummy

interface DummyRepository {
    suspend fun getDummy(dummyId: Long): Result<Dummy>
}