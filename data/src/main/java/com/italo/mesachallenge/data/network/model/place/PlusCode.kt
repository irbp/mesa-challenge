package com.italo.mesachallenge.data.network.model.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlusCode(
    @Json(name = "plus_code")
    val compoundCode: String = "",
    @Json(name = "global_code")
    val globalCode: String = ""
)