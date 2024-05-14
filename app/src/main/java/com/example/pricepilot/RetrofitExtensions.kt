package com.example.pricepilot

import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Converter
import retrofit2.converter.kotlinx.serialization.asConverterFactory

fun Json.asConverterFactoryWrapper(contentType: MediaType): Converter.Factory {
    return this.asConverterFactory(contentType)
}