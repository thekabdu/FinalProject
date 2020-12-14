package com.example.bus_system

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.bus_system.admin.LogInAdmin


fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.logout(){
    val intent = Intent(this, LoginActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

fun Context.adminlog(){
        val intent = Intent(applicationContext, LogInAdmin::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        startActivity(intent)

}