package ec.edu.ups.appproyecto.data.adapterstemp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ec.edu.ups.appproyecto.R
import ec.edu.ups.appproyecto.data.models.Sensor


class RecyclerAdaptermotion(val userList: List<Sensor>): RecyclerView.Adapter<MyViewHolderUsermotion>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolderUsermotion {
        val layoutInflater=LayoutInflater.from(parent.context)
        val view =layoutInflater.inflate(R.layout.row_datamotion,parent,false)
        val viewHolder = MyViewHolderUsermotion(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolderUsermotion, position: Int) {
        userList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = userList?.size!!

}