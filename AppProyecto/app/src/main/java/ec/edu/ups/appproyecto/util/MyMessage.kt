package ec.edu.ups.appproyecto.util

import android.content.Context
import android.widget.Toast

class MyMessage {
    companion object{
        fun toast(context: Context,msn:String){
            Toast.makeText(context,msn,Toast.LENGTH_SHORT).show()
        }
    }
}