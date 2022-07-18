package com.example.appregistro.data.requests

import com.google.gson.annotations.SerializedName

data class SensorRequest(
    @SerializedName("Pulso")
    var Pulso: String,
    )