package ec.edu.ups.appproyecto.data.responses

import com.google.gson.annotations.SerializedName
import ec.edu.ups.appproyecto.data.models.Sensor


data class SensorResponse (
    @SerializedName("code")
    var code:Int,
    @SerializedName("error")
    var error:Boolean,
    @SerializedName("message")
    var message:String,
    @SerializedName("users")
    var users:List<Sensor>
)
