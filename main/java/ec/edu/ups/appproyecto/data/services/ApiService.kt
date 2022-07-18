package ec.edu.ups.appproyecto.data.services


import ec.edu.ups.appproyecto.data.responses.SensorResponse
import retrofit2.Call
import retrofit2.http.GET


//en que metodo vas a trabajar
interface ApiService {
    @GET("/")
    fun listUser(): Call<SensorResponse>

}


