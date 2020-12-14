package com.example.bus_system.ui.search

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bus_system.R
import com.example.bus_system.model.BusesModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_admin_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class SearchFragment : Fragment(R.layout.fragment_search), DatePickerDialog.OnDateSetListener {

    var uday = 0
    var umonth = 0
    var uyear = 0

    var saveduDay = 0
    var saveduMonth = 0
    var saveduYear = 0

    private val db by lazy {FirebaseFirestore.getInstance()}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        /*db.collection("buses")
            .whereEqualTo("from", "Almaty")
            .addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
                val buses = querySnapshot?.documents?.map {
                    it.toObject(BusesModel::class.java)
                }
                Log.d ("taaag", buses.toString())
            }*/
       // pickDate()
        //searchTicket()
    }

    private fun getDateCalendar(){
        val cal = Calendar.getInstance()
        uday = cal.get(Calendar.DAY_OF_MONTH)
        umonth = cal.get(Calendar.MONTH)
        uyear = cal.get(Calendar.YEAR)
    }

    private fun pickDate(){
        user_departure_date.setOnClickListener{
            getDateCalendar()

            DatePickerDialog(requireContext(),this, uyear,umonth,uday).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        saveduDay =dayOfMonth
        saveduMonth= month
        saveduYear = year

        getDateCalendar()
        txt_user_date.text = "$saveduYear-$saveduMonth-$saveduDay"
    }

    /*private fun searchTicket(){
        btn_search_buses.setOnClickListener {
            val intent = Intent (getActivity(), OutboundBuses::class.java)
            getActivity()?.startActivity(intent)
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_search_buses.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_search_to_outboundBuses)
        }
        pickDate()

    }


}