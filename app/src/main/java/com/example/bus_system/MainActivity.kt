package com.example.bus_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ActionMenuView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //val logout = findViewById<Button>(R.id.logout_btn)

        auth= FirebaseAuth.getInstance()
        var currentUser=auth.currentUser

        if(currentUser==null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

      /*  logout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }*/

    }


}