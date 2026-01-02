package com.example.data.mapper

import com.example.domain.model.Dummy
import com.example.network.response.DummyResponse

fun DummyResponse.toDummyEntity(): Dummy {
    return Dummy(
        dummyName = dummyName
    )
}