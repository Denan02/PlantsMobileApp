package com.example.spirala1

import com.google.gson.annotations.SerializedName

data class VraceneBiljke (
    @SerializedName("data") val data: List<BiljkaWebServisa>,
    @SerializedName("links") val linkovi: Links,
    @SerializedName("meta") val meta: Meta
)