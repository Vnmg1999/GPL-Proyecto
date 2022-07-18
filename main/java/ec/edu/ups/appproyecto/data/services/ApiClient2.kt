package ec.edu.ups.appproyecto.data.services

import android.content.Context
import ec.edu.ups.appproyecto.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient2 {
    private lateinit var apiService:ApiService2
    fun getApiService(context: Context): ApiService2 {
     if(!::apiService.isInitialized){
         val retrofit = Retrofit.Builder()
             .baseUrl(Constants.BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
         apiService = retrofit.create(ApiService2::class.java)
      }
        return apiService
    }
}

