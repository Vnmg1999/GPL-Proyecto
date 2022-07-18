package ec.edu.ups.appproyecto.data.adapterstemp

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ec.edu.ups.appproyecto.R
import ec.edu.ups.appproyecto.data.models.Sensor


open class MyViewHolderUsermotion(itemView: View):RecyclerView.ViewHolder(itemView){
    private lateinit var Movimiento:TextView
    private lateinit var dateData:TextView

    fun bind(user: Sensor) {
        // prefix =itemView.findViewById()
        Movimiento = itemView.findViewById(R.id.movimiento)
        dateData = itemView.findViewById(R.id.fecha)

        user.Movimiento == "S"
        Movimiento.text = "Movimiento detectado"

        dateData.text=user.fecha


        itemView.setOnClickListener {

            Toast.makeText(itemView.context,user.Movimiento,Toast.LENGTH_SHORT).show()


        }

    }
}