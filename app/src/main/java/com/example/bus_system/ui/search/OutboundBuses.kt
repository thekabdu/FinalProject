package com.example.bus_system.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bus_system.R
import com.example.bus_system.adapter.BusAdapter
import com.example.bus_system.model.BusesModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_outbound_buses.*

class OutboundBuses : AppCompatActivity() {

    private val db:FirebaseFirestore= FirebaseFirestore.getInstance();
    private val collectionReference:CollectionReference=db.collection("buses");

    var busAdapter:BusAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outbound_buses)

        setUpRecyclerview()

        val actionbar =supportActionBar
        actionbar!!.title ="Tickets"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun setUpRecyclerview(){

        val query:Query = collectionReference
        val firestoreRecyclerOptions:FirestoreRecyclerOptions<BusesModel> = FirestoreRecyclerOptions.Builder<BusesModel>()
            .setQuery(query, BusesModel::class.java)
            .build();

        busAdapter = BusAdapter(firestoreRecyclerOptions);

        recycler_view_search_ticket.layoutManager = LinearLayoutManager(this)
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