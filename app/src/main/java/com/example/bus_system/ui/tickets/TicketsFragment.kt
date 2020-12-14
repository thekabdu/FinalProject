package com.example.bus_system.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_system.R
import com.example.bus_system.adapter.BusAdapter
import com.example.bus_system.model.BusesModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_outbound_buses.*

class TicketsFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance();
    private val collectionReference: CollectionReference =db.collection("buses");

    var busAdapter:BusAdapter? =null

    private lateinit var ticketsViewModel: TicketsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerview()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ticketsViewModel =
                ViewModelProvider(this).get(TicketsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tickets, container, false)

        return root
    }

    fun setUpRecyclerview(){

        val query: Query = collectionReference
        val firestoreRecyclerOptions: FirestoreRecyclerOptions<BusesModel> = FirestoreRecyclerOptions.Builder<BusesModel>()
            .setQuery(query, BusesModel::class.java)
            .build();

        busAdapter = BusAdapter(firestoreRecyclerOptions);

        recycler_view_search_ticket.layoutManager = LinearLayoutManager(activity)
        recycler_view_search_ticket.adapter = busAdapter
    }

    override fun onStart(){
        super.onStart()
        busAdapter!!.startListening()
    }

    override fun onDestroy(){
        super.onDestroy()
        busAdapter!!.stopListening()
    }
}