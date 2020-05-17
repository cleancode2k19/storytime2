package com.example.storytime

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    lateinit var readerName: EditText
    lateinit var readerEmail: EditText
    lateinit var readerPassword: EditText
    lateinit var readerRepeatPassword: EditText
    lateinit var btnSignup: Button
    lateinit var accept: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readerName = findViewById(R.id.readerName)
        readerEmail = findViewById(R.id.readerEmail)
        readerPassword = findViewById(R.id.readerPassword)
        readerRepeatPassword = findViewById(R.id.readerRepeatPassword)
        accept = findViewById(R.id.accept)
        btnSignup = findViewById(R.id.btnSignup)
        btnSignup.setOnClickListener{
            saveData()
        }
    }
    private fun saveData(){
        val name = readerName.text.toString().trim()
        if(name.isEmpty()){
            readerName.error = "Please Enter your Name"
            return
        }
        val readerEmail = readerEmail.text.toString().trim()
      /*  if(readerEmail.isEmpty()){
            readerEmail.error = "Please Enter your Email"
            return
        }*/
        val readerPassword = readerPassword.text.toString().trim()
      /*  if(readerPassword.isEmpty()){
            readerPassword.error = "Please Enter your Password"
            return
        }
        val readerRepeatPassword = readerRepeatPassword.text.toString().trim()
        if(readerRepeatPassword.isEmpty()){
            readerRepeatPassword.error = "Please Enter your RepeatPassword"
            return
        }
        if(readerPassword!=readerRepeatPassword){
            readerRepeatPassword.error = "Please Enter your Repeat Password same as password"
            return
        }
        if(accept.isChecked){
            accept.error = "Please Accept the terms and conditions"
            return
        } */
        val database = FirebaseDatabase.getInstance()

        val myRef: DatabaseReference = database.getReference("reader")

        val refid = myRef.push().key
        val reader = Reader(refid, name,readerEmail,readerPassword )
        myRef.child(refid.toString()).setValue(reader).addOnCompleteListener {
            Toast.makeText(applicationContext,"Registration successful",Toast.LENGTH_LONG).show()
            val myIntent = Intent(baseContext, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }
}
