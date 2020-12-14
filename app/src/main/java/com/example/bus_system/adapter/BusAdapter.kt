package com.example.bus_system.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_system.R
import com.example.bus_system.model.BusesModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.cardview_buses.view.*

class BusAdapter(options: FirestoreRecyclerOptions<BusesModel>) :
    FirestoreRecyclerAdapter<BusesModel, BusAdapter.BusAdapterVH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusAdapterVH {
        return BusAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.cardview_buses,parent,false))
    }

    override fun onBindViewHolder(holder: BusAdapterVH, position: Int, model: BusesModel) {
        holder.busName.text = model.busName
        holder.from.text = model.from
        holder.to.text = model.to
        holder.date.text = model.date
        holder.seats.text = model.seats
        holder.price.text = model.price
    }

    class BusAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var busName = itemView.ticket_bus_name
        var from = itemView.txt_ticket_from
        var to = itemView.txt_ticket_to
        var date = itemView.txt_ticket_bus_date
        var seats = itemView.txt_ticket_bus_seats
        var price = itemView.txt_ticket_price
    }
}