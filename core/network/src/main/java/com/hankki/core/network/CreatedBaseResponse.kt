package com.hankki.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatedBaseResponse(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
)
