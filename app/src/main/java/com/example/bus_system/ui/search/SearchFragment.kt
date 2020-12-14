package com.example.bus_system.ui.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.bus_system.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class SearchFragment : Fragment(), DatePickerDialog.OnDateSetListener {

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
        val view: View = inflater.inflate(R.layout.fragment_search, null)

        initView(view)

        return view
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

    private fun initView(view: View) {
        //mbtn_search_buses = view.findViewById(R.id. btn_search_buses);
    }

}