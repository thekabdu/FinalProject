package com.example.bus_system.admin

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bus_system.R
import com.example.bus_system.logout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_admin_main.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

class AdminMain : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    lateinit var filepath:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        img_bus.setOnClickListener {
            chooseImage()
        }
        btn_add_img_bus.setOnClickListener {
            saveImage()
        }
        btn_add_bus.setOnClickListener {
            val busName = edit_admin_bus_name.text.toString()
            val from = edit_admin_from.text.toString()
            val to = edit_admin_to.text.toString()
            val date = txt_admin_date.text.toString()
            val seats = edit_admin_num_seats.text.toString()
            val price = edit_admin_price.text.toString()
            saveFireStore(busName, from, to, date, seats, price)
        }
        //date
        pickDate()

        //action bar
        val actionbar =supportActionBar
        actionbar!!.title ="Admin Page"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDate(){
        admin_departure_date.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, this,year,month,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay =dayOfMonth
        savedMonth= month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        txt_admin_date.text = "$savedYear-$savedMonth-$savedDay"
    }

    //for upload img
    private fun chooseImage(){
        var img = Intent()
        img.setType("image/*")
        img.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(img, "Choose Picture Bus"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data!= null){
            filepath= data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
            img_bus.setImageBitmap(bitmap)
        }
    }

    private fun saveImage() {
        if(filepath!= null) {
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()

            var imageRef = FirebaseStorage.getInstance().reference.child("images/bus.jpg")
            imageRef.putFile(filepath)
                .addOnSuccessListener {
                    pd.dismiss()
                    Toast.makeText(applicationContext,"File Uploaded",Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun saveFireStore(busName:String, from:String, to:String, date:String, seats:String, price:String){
        val db = FirebaseFirestore.getInstance()
        val bus: MutableMap<String, Any> = HashMap()
        bus["busName"] = busName
        bus["from"] = from
        bus["to"] = to
        bus["date"] = date
        bus["seats"] = seats
        bus["price"] = price

        db.collection("buses")
            .add(bus)
            .addOnSuccessListener {
                Toast.makeText(this@AdminMain, "Bus added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this@AdminMain, "Record Failed!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateOptionsMenu(Menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, Menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.nav_logout) {
            AlertDialog.Builder(this).apply {
                setTitle("Are you sure ?")
                setPositiveButton("Yes") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    logout()
                }
                setNegativeButton("Cancle") { _, _ ->
                }
            }.create().show()
        }
        return super.onOptionsItemSelected(item)
    }

}