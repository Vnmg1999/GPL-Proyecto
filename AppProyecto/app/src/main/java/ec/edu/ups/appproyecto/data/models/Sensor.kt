package ec.edu.ups.appproyecto.data.models

import com.google.gson.annotations.SerializedName


data class Sensor(
    @SerializedName("Movimiento")
    var Movimiento:String,
    @SerializedName("fecha")
    var fecha:String,

)

