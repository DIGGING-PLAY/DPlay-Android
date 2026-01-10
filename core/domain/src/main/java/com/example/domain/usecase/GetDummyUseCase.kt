package com.example.domain.usecase

import com.example.domain.model.Dummy
import com.example.domain.repository.DummyRepository
import javax.inject.Inject

class GetDummyUseCase
@Inject
constructor(
    private val dummyRepository: DummyRepository,
) {
    suspend operator fun invoke(id: Long): Result<Dummy> = dummyRepository.getDummy(dummyId = id)
}