package com.example.storytime


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {
    lateinit var readerEmail: EditText
    lateinit var readerPassword: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        readerEmail = findViewById(R.id.loginEmail)
        readerPassword = findViewById(R.id.loginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener{
            getLoginData()
        }
    }
    private fun getLoginData() {

        val readerEmail = readerEmail.text.toString().trim()
          if(readerEmail.isEmpty()){
              Toast.makeText(applicationContext,"Enter correct username/Email", Toast.LENGTH_LONG).show()
          }
        val readerPassword = readerPassword.text.toString().trim()
        if (readerPassword.isEmpty()) {
            Toast.makeText(applicationContext,"Enter correct password",Toast.LENGTH_LONG).show()
        }
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("reader")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    var flag = false//P0!! to address null value
                    for(item in p0.children) {
                        val emailOk = item.child("email").getValue().toString().equals(readerEmail)
                        val pwdOk = item.child("password").getValue().toString().equals(readerPassword)
                        if (emailOk && pwdOk) {
                            flag = true;
                            val myIntent = Intent(baseContext, DashboardActivity::class.java)
                            startActivity(myIntent)
                            return
                        }
                    }
                    if(!flag){
                        Toast.makeText(
                            applicationContext,
                            "Enter correct credentials",
                            Toast.LENGTH_LONG
                        ).show()
                        val myIntent = Intent(baseContext, LoginActivity::class.java)
                        startActivity(myIntent)
                    }
                }
            }

        })


    }

}