package com.heixss.cats.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weight(@Json(name = "metric")
                  val metric: String = "",
                  @Json(name = "imperial")
                  val imperial: String = "")