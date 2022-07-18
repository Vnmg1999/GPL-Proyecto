package ec.edu.ups.appproyecto.data.services

import android.content.Context
import ec.edu.ups.appproyecto.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var apiService:ApiService
    fun getApiService(context: Context): ApiService{
     if(!::apiService.isInitialized){
         val retrofit = Retrofit.Builder()
             .baseUrl(Constants.BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
         apiService = retrofit.create(ApiService::class.java)
      }
        return apiService
    }
}

