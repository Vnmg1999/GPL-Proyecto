package ec.edu.ups.appproyecto.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import com.example.appregistro.data.requests.SensorRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ec.edu.ups.appproyecto.R
import ec.edu.ups.appproyecto.data.responses.DefaultResponse
import ec.edu.ups.appproyecto.data.services.ApiClient2
import ec.edu.ups.appproyecto.util.MyMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    private lateinit var btnexit: FloatingActionButton
    private lateinit var motionimage: CardView
    private lateinit var ledcardview: CardView
    private lateinit var btnled: ToggleButton
    lateinit var apiClient: ApiClient2
    lateinit var txtpulso: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnexit = findViewById(R.id.btnexit)
        motionimage = findViewById(R.id.cardMotion)
        //ledcardview = findViewById(R.id.cardLed)
        apiClient = ApiClient2()




        btnexit.setOnClickListener {
            Toast.makeText(this, "Salir", Toast.LENGTH_SHORT).show()
            finish()
        }

        motionimage.setOnClickListener {
            val intent = Intent(this, DataMotion::class.java)
            startActivity(intent)

        }

        btnled= findViewById(R.id.buttonled)

        btnled.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                txtpulso = "1"
                Toast.makeText(this, txtpulso, Toast.LENGTH_SHORT).show()


            } else {
                txtpulso = "0"
                Toast.makeText(this, txtpulso, Toast.LENGTH_SHORT).show()
            }
            CoroutineScope(Dispatchers.IO).launch {
                addpulso()
            }
        }

        //ledcardview.setOnClickListener {}

    }

    private suspend fun addpulso() {
        val Pulso= txtpulso.trim()
        apiClient.getApiService(this)
            .addUser(SensorRequest(Pulso))
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {

                    val defaultResponse = response.body()
                    if (defaultResponse != null) {
                        if (response.code() == 200 && defaultResponse.error == false) {
                            MyMessage.toast(applicationContext, defaultResponse.message)
                            return
                        }

                    }
                    MyMessage.toast(applicationContext, "Error al procesar")
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    MyMessage.toast(applicationContext, t.toString())
                }

            })//End enqueue
    }
}














