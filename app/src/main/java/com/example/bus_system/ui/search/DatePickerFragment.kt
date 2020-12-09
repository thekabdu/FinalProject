package com.example.bus_system.ui.search

import android.app.*
import android.os.Bundle
import android.widget.TextView
import android.widget.DatePicker
import android.widget.Toast
import com.example.bus_system.R

import java.text.DateFormat
import java.util.Calendar


/*class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var calendar:Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Initialize a calendar instance
        calendar = Calendar.getInstance()

        // Get the system current date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            activity, // Context
            // Put 0 to system default theme or remove this parameter
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, // Theme
            this, // DatePickerDialog.OnDateSetListener
            year, // Year
            month, // Month of year
            day // Day of month
        )
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        Toast.makeText(
            activity,
            "Date Set : ${formatDate(year,month,day)}"
            ,Toast.LENGTH_SHORT
        ).show()

        // Display the selected date in text view
        activity.findViewById<TextView>(R.id.date_text).text = formatDate(year,month,day)
    }

    private fun formatDate(year:Int, month:Int, day:Int):String{
        // Create a Date variable/object with user chosen date
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        // Format the date picker selected date
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }

}*/
