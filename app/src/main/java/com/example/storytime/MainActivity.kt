package com.example.storytime

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    lateinit var readerName: EditText
    lateinit var readerEmail: EditText
    lateinit var readerPassword: EditText
    lateinit var readerRepeatPassword: EditText
    lateinit var btnSignup: Button
    lateinit var Signin: TextView
    lateinit var accept: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //here I am tagging the layout to the activity
        setContentView(R.layout.activity_main)

        readerName = findViewById(R.id.readerName)
        readerEmail = findViewById(R.id.readerEmail)
        readerPassword = findViewById(R.id.readerPassword)
        readerRepeatPassword = findViewById(R.id.readerRepeatPassword)
        accept = findViewById(R.id.accept)
        btnSignup = findViewById(R.id.btnSignup)
        //on click of sign up button saving captured information
        btnSignup.setOnClickListener{
            saveData()
        }
        //on click of sign in redirecting to the login page
        Signin = findViewById(R.id.btnSignin)
        Signin.setOnClickListener{
            val myIntent = Intent(baseContext, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }
//Email Validation check code
    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun saveData(){
        //validation of form field captured data
        val name = readerName.text.toString().trim()
        var pflag=false
        var msg="Fix validation error"
        if(name.isEmpty()){
            Toast.makeText(applicationContext,"Enter correct Name", Toast.LENGTH_LONG).show()
            pflag=true
        }
        val readerEmail = readerEmail.text.toString().trim()
        if(readerEmail.isEmpty() && isEmailValid(readerEmail)){
           msg="Enter correct Email"
            pflag=true
        }
        val readerPassword = readerPassword.text.toString().trim()
       if(readerPassword.isEmpty()){

           msg="Enter correct password"
           pflag=true
        }
        val readerRepeatPassword = readerRepeatPassword.text.toString().trim()
        if(readerRepeatPassword.isEmpty()){
            msg="Enter repeat password"
            pflag=true
        }
        if(readerPassword!=readerRepeatPassword){
            msg="Please Enter your Repeat Password same as password"
            pflag=true
        }
        if(!accept.isChecked){
            msg="Please Accept the terms and condition"
            pflag=true
        }
        if(!pflag) {
            //database insertion of data
            val database = FirebaseDatabase.getInstance()

            val myRef: DatabaseReference = database.getReference("reader")

            val refid = myRef.push().key
            val reader = Reader(refid, name, readerEmail, readerPassword)
            myRef.child(refid.toString()).setValue(reader).addOnCompleteListener {
                Toast.makeText(applicationContext, "Registration successful", Toast.LENGTH_LONG)
                    .show()
                val myIntent = Intent(baseContext, LoginActivity::class.java)
                startActivity(myIntent)
            }
        } else{
            Toast.makeText(applicationContext,msg, Toast.LENGTH_LONG).show()
        }
    }
}
