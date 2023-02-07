package com.lrp.domain.enteties

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedResponse(
    @Json(name = "status") val status: String,
    @Json(name = "message") val message: Map<String, List<String>>
)