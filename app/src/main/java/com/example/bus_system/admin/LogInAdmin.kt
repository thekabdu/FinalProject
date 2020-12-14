package com.example.bus_system.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bus_system.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in_admin.*

class LogInAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in_admin)

        setupViews()
    }


    private fun setupViews(){
        btn_sign_in.setOnClickListener {
            val email = editTextEmailSignin.text.toString()
            val password = editTextPsswdSignin.text.toString()

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { result ->
                    if (!result.isSuccessful) {
                        Toast.makeText(this,
                            result.exception.toString(), Toast.LENGTH_LONG).show()

                        return@addOnCompleteListener
                    }
                    Toast.makeText(this, result.result?.user?.email,
                        Toast.LENGTH_LONG).show()

                    startActivity(Intent(this, AdminMain::class.java))
                    finish()
                }
        }
    }




}