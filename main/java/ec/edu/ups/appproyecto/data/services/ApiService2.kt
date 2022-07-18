package ec.edu.ups.appproyecto.data.services


import com.example.appregistro.data.requests.SensorRequest
import ec.edu.ups.appproyecto.data.responses.DefaultResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//en que metodo vas a trabajar
interface ApiService2 {
    @POST("/led/")
    fun addUser(@Body request: SensorRequest): Call<DefaultResponse>

}


