package com.example.data.model.response

import com.example.domain.model.Dummy

data class DummyResponse(
    val dummyName: String,
) {
    fun toDummyEntity(): Dummy = Dummy(dummyName = dummyName)
}
