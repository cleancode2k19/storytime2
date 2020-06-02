package com.example.storytime

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var forgotEmail: EditText
    lateinit var forgotPassword: EditText
    lateinit var forgotPasswordReenter: EditText
    lateinit var btnforgotpass: Button
    lateinit var loginBack:TextView
    lateinit var signUpBack:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)
        forgotEmail = findViewById(R.id.forgotEmail)
        forgotPassword = findViewById(R.id.forgotPassword)
        forgotPasswordReenter = findViewById(R.id.forgotPasswordReenter)
        btnforgotpass = findViewById(R.id.btnLogin)
        signUpBack = findViewById(R.id.signUpBack)
        loginBack = findViewById(R.id.loginBack)
        btnforgotpass.setOnClickListener{
            setForgotData()
        }
        signUpBack.setOnClickListener{
            val myIntent = Intent(baseContext, MainActivity::class.java)
            startActivity(myIntent)
        }
        loginBack.setOnClickListener{
            val myIntent = Intent(baseContext, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }
    private fun setForgotData() {
        val forgotEmail = forgotEmail.text.toString().trim()
        if(forgotEmail.isEmpty()){
            Toast.makeText(applicationContext,"Enter correct username/Email", Toast.LENGTH_LONG).show()
        }
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("reader")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                forgotPassword.setVisibility(View.GONE)
                if(p0!!.exists()){
                    //P0!! to address null value
                    for(item in p0.children) {
                        val emailOk = item.child("email").getValue().toString().equals(forgotEmail)
                        if (emailOk) {
                            forgotPassword.setVisibility(View.VISIBLE)
                            val forgotPassword = forgotPassword.text.toString().trim()

                            if (forgotPassword.isEmpty()) {
                                Toast.makeText(applicationContext,"Enter correct password", Toast.LENGTH_LONG).show()
                            }
                            forgotPasswordReenter.setVisibility(View.VISIBLE)
                            val forgotPasswordReenter = forgotPasswordReenter.text.toString().trim()

                            if (forgotPasswordReenter.isEmpty()) {
                                Toast.makeText(applicationContext,"Enter correct password", Toast.LENGTH_LONG).show()
                            }
                            if(forgotPassword!=forgotPasswordReenter){
                                Toast.makeText(applicationContext,"Enter same password and reenter password", Toast.LENGTH_LONG).show()
                            }
                            val reader = Reader(item.child("id").getValue().toString(), item.child("name").getValue().toString(),item.child("email").getValue().toString(),forgotPassword )
                            myRef.child(item.child("id").getValue().toString()).setValue(reader).addOnCompleteListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Password updated successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                                val myIntent = Intent(baseContext, LoginActivity::class.java)
                                startActivity(myIntent)
                            }
                            return
                        }else{
                            Toast.makeText(
                                applicationContext,
                                "Enter correct user name",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(
                    applicationContext,
                    "Database Error",
                    Toast.LENGTH_LONG
                ).show()
            }


        })
    }
}